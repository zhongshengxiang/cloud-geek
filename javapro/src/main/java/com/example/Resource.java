package com.example;

public class Resource {
    private String name;
    private String sex;
    private boolean flag = false;
    int count = 0;

    public synchronized void set(String name, String sex) {

        if (flag)
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        //设置成员变量
        this.name = name;
        this.sex = sex;
        count++;
        System.out.println(Thread.currentThread().getName() + "------>" + count);
        //设置之后，Resource中有值，将标记该为 true ,
        flag = true;
        //唤醒output
        this.notifyAll();
    }

    public synchronized void out() {
        if (!flag)
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        //输出线程将数据输出
        System.out.println(Thread.currentThread().getName() + "-->" + count);
//        System.out.println("The name is : " + name + " &&  The sex is : " + sex);
        //改变标记，以便输入线程输入数据
        flag = false;
        //唤醒input，进行数据输入
        this.notifyAll();
    }

}

class Input implements Runnable {
    int index;
    private Resource r;

    public Input(Resource r) {
        this.r = r;
    }

    @Override
    public void run() {

        while (index < 100) {
            r.set("Lily", "woman");
            index++;
            //在连个数据之间进行切换。

        }
    }

}

class Output implements Runnable {

    private Resource r;

    public Output(Resource r) {
        this.r = r;
    }

    @Override
    public void run() {
        while (true) {
            r.out();
        }

    }
}

class ResourceDemo {

    public static void main(String[] args) {
        //资源对象
        Resource r = new Resource();
        //任务对象
        Input in = new Input(r);
        Output out = new Output(r);
        //线程对象
        Thread t1 = new Thread(in);
        Thread t2 = new Thread(out);
        Thread t3 = new Thread(in);
        Thread t4 = new Thread(out);
        //开启线程
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}


