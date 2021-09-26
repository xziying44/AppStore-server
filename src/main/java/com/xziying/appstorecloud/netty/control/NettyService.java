package com.xziying.appstorecloud.netty.control;

import com.xziying.appstorecloud.netty.protocol.AppStoreRpcRequestDecoder;
import com.xziying.appstorecloud.netty.protocol.AppStoreRpcResponseEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * NettyService
 *
 * @author : xziying
 * @create : 2021-05-01 12:38
 */
@Component
public class NettyService {

    @Resource(name="bossGroup")
    EventLoopGroup bossGroup;

    @Resource(name="workGroup")
    EventLoopGroup workGroup;

    @Resource
    NettyServiceHandler nettyServiceHandler;

    @Async
    public void start() throws InterruptedException {
// 创建服务器端启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup, workGroup)   // 设置两个线程组
                .channel(NioServerSocketChannel.class) // 设置通道
                .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列得到的连接个数
                .childOption(ChannelOption.SO_KEEPALIVE, true)  // 设置保持活动连接状态
                .childHandler(new ChannelInitializer<SocketChannel>() {


                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new AppStoreRpcResponseEncoder()); // 响应协议编码器
                        ch.pipeline().addLast(new AppStoreRpcRequestDecoder()); // 服务器解码器
                        ch.pipeline().addLast(nettyServiceHandler);
                    }
                });     // 设置处理器

        // 绑定端口
        ChannelFuture cf = bootstrap.bind(7799).sync();
        System.out.println("Netty服务器已在7799端口启动");

        // 监听关闭消息通道
        cf.channel().closeFuture().sync();
        System.out.println("Netty服务器已关闭");
    }
}
