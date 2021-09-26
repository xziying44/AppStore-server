package com.xziying.appstorecloud.control.config;

import com.xziying.appstore.api.DatabaseService;
import com.xziying.appstorecloud.control.DatabaseServiceImpl;
import com.xziying.appstorecloud.utils.ProducerConsumer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * ServeThread
 *
 * @author : xziying
 * @create : 2021-03-29 18:59
 */
public class ServeThread implements Runnable{

    private final DatabaseService databaseService;

    ProducerConsumer<Socket> producerConsumer;

    public ServeThread(ProducerConsumer<Socket> producerConsumer, DatabaseService databaseService) {
        //初始化客户端
        this.producerConsumer = producerConsumer;
        this.databaseService = databaseService;

    }
    @Override
    public void run() {
        Socket client = null;
        try {
            //创建客户端对象
            client = producerConsumer.consume();
            //获取对象输入while ()流
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            //获取对象输出流
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            //获取客户端传送过来的方法名
            String methodName = (String) ois.readObject();
            //读取客户端发送过来的方法参数类型
            Class<?>[] parameterTypes = (Class<?>[]) ois.readObject();
            //获取客户端发送过来的具体的参数
            Object[] params = (Object[]) ois.readObject();
            //获取方法
            Method method = databaseService.getClass().getMethod(methodName, parameterTypes);
            //执行方法，并将结果传送给客户端
            oos.writeObject(method.invoke(databaseService, params));
            //刷新缓存
            oos.flush();
            client.sendUrgentData(0xff);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("对象执行方法失败!");
        } finally {
            try {
                if (client != null) client.close();
            } catch (IOException e){
                e.printStackTrace();
            }

        }
    }
}
