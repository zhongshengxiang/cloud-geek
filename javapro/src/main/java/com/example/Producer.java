package com.example;

import java.util.Random;

/**
 * Created by Administrator on 2017/2/4.
 */

public class Producer implements Runnable {
    String name;
    Storage mStorage;

    public Producer(String name, Storage storage) {
        this.name = name;
        mStorage = storage;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int id = new Random().nextInt(1000);
                Product product = new Product(id);
                mStorage.input(product);
                System.out.println(name + "ysc: " + product.getId());
                Thread.sleep(200);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
