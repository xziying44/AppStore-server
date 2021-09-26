package com.xziying.appstorecloud.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * ProducerConsumer 生产者消费者
 *
 * @author : xziying
 * @create : 2021-03-31 11:25
 */
public class ProducerConsumer<T> {
    int N;
    final Semaphore mutex = new Semaphore(1);  // 互斥信号量
    final Semaphore full = new Semaphore(0);   // 满缓冲区资源信号量
    final Semaphore empty;
    static public int in = 0;
    static public int out = 0;
    Object[] resources;

    /**
     *
     * @param N 缓冲区资源数
     */
    public ProducerConsumer(int N) {
        this.N = N;
        this.empty = new Semaphore(N);  // 缓冲区资源信号量
        this.resources = new Object[N];
    }

    /**
     * 生产资源
     */
    public void produce(T supplies) throws InterruptedException {
        empty.acquire();
        mutex.acquire();
        resources[in] = supplies;
        in = (in + 1) % N;
        mutex.release();
        full.release();
    }

    /**
     * 消费资源
     */
    @SuppressWarnings("unchecked")
    public T consume() throws InterruptedException {
        T reply;
        full.acquire();
        mutex.acquire();
        reply = (T) resources[out];
        out = (out + 1) % N;
        mutex.release();
        empty.release();
        return reply;
    }
}
