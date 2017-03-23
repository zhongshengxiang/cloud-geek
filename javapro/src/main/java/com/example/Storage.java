package com.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Administrator on 2017/2/4.
 */

public class Storage {
    private BlockingQueue<Product> mQueue = new LinkedBlockingQueue<>(2);

    public void input(Product product) throws InterruptedException {
        mQueue.put(product);
    }

    public Product output() throws InterruptedException {
        return mQueue.take();
    }
}
