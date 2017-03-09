package com.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by Administrator on 2017/2/4.
 */

public class Storage {
    BlockingQueue<Product> mQueue = new SynchronousQueue<>();

    public void input(Product product) throws InterruptedException {
        mQueue.put(product);
    }

    public Product output() throws InterruptedException {
        return mQueue.take();
    }
}
