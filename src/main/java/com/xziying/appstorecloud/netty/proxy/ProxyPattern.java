package com.xziying.appstorecloud.netty.proxy;


import com.xziying.appstorecloud.netty.control.NettyClient;
import com.xziying.appstorecloud.netty.protocol.domain.AppStoreRpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ProxyPattern
 *
 * @author : xziying
 * @create : 2021-03-29 20:13
 */
public class ProxyPattern {
    public static Object getProxy(Class<?> c,String ip,int port){
        return Proxy.newProxyInstance(c.getClassLoader(),
                new Class[]{c},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        AppStoreRpcRequest request = new AppStoreRpcRequest(method.getName(), method.getParameterTypes(), args);
                        NettyClient client = new NettyClient(ip, port);
                        Object reply = client.request(request);
                        if (reply instanceof Throwable){
                            Throwable throwable = (Throwable) reply;
                            throw throwable.getCause();
                        }
                        return reply;
                    }
                });
    }
}