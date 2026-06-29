package com.hooni.cache;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class CacheConnectionPool {
    private final BlockingQueue<HooniCache> pool;

    public CacheConnectionPool(int size) {
        pool = new ArrayBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            pool.add(new HooniCache());
        }
    }

    public HooniCache borrow() throws InterruptedException {
        HooniCache client = pool.take();
        if (!client.isHealthy()) {
            client.reconnect();
        }
        return client;
    }




    public void release(HooniCache client) {
        if (!client.isHealthy()) {
            client.reconnect();
        }
        pool.offer(client);
    }


}
