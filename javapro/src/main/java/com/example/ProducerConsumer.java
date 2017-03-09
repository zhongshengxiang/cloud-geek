package com.example;

/**
 * Created by Administrator on 2017/2/4.
 */

public class ProducerConsumer {

    public static void main(String[] args) {
//        Storage mStorage = new Storage();
//        Producer p1 = new Producer("scz 1#", mStorage);
//        Producer p2 = new Producer("scz 2#", mStorage);
//        Consumer c1 = new Consumer("xfz 1#", mStorage);
//        Consumer c2 = new Consumer("xfz 2#", mStorage);
//        ExecutorService service = Executors.newCachedThreadPool();
//        service.submit(p1);
//        service.submit(p2);
//        service.submit(c1);
//        service.submit(c2);
//        String str = "a,b,c, ";
//        String[] ary = str.split(",");
////预期大于 3，结果是 3
//        System.out.println(ary.length);
//        for (int i=0;i<ary.length;i++){
//            System.out.println(ary[i]);
//        }
        int a = 10;
        int b = 10;
        method(a, b);
        System.out.println("a=" + a);
        System.out.println("b=" + b);
    }

    private static void method(int a, int b) {
        System.out.println("a=" + 100);
        System.out.println("b=" + 200);
        return;
    }
}
