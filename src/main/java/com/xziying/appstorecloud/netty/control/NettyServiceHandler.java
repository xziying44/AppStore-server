package com.xziying.appstorecloud.netty.control;

import com.xziying.appstore.api.DatabaseService;
import com.xziying.appstorecloud.netty.protocol.domain.AppStoreRpcRequest;
import com.xziying.appstorecloud.netty.protocol.domain.AppStoreRpcResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * NettyServiceHandler
 * 处理器
 * @author : xziying
 * @create : 2021-04-30 21:14
 */
@Component
@ChannelHandler.Sharable
public class NettyServiceHandler extends SimpleChannelInboundHandler<AppStoreRpcRequest> {

    @Resource
    DatabaseService databaseService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AppStoreRpcRequest msg) throws Exception {
        System.out.println(ctx);
        Method method = databaseService.getClass().getMethod(msg.getName(), msg.getType());
        Object invoke = method.invoke(databaseService, msg.getArgs());
        AppStoreRpcResponse response = new AppStoreRpcResponse(invoke);
        ctx.writeAndFlush(response);
    }


    // 处理异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
