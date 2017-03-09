package com.example;

/**
 * Created by Administrator on 2017/2/4.
 */

public class Consumer implements Runnable {
    String name;
    Storage mStorage;

    public Consumer(String name, Storage storage) {
        this.name = name;
        mStorage = storage;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Product product = mStorage.output();
                System.out.println(name + "yxf: " + product.getId());
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
