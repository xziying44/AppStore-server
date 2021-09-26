package com.xziying.appstorecloud.control.config;

import com.xziying.appstore.api.DatabaseService;
import com.xziying.appstorecloud.utils.ProducerConsumer;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * MySqlService
 *
 * @author : xziying
 * @create : 2021-03-29 18:53
 */

public class MySqlControl{
    //创建服务器对象
    private ServerSocket server;

    @Resource
    TaskExecutor taskExecutor;  // 线程池

    @Resource
    ProducerConsumer<Socket> producerConsumer;

    @Resource
    DatabaseService databaseService;

    public MySqlControl() {
        try {
            //初始化服务器对象
            server = new ServerSocket(7799);
            System.out.println("插件数据库服务开启成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("插件数据库服务开启失败");
        }
    }


    /**
     * 接收新的连接
     */
    @Async
    public void receive() {
        System.out.println(server);
        //创建一个while循环，保证每个请求都可以接收到
        while (true) {
            try {
                //接收客服端
                Socket client = this.server.accept();
                try {
                    producerConsumer.produce(client);
                    //执行线程方法
                    taskExecutor.execute(new ServeThread(producerConsumer, databaseService));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

}
