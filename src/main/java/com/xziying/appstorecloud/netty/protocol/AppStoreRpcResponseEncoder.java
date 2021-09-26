package com.xziying.appstorecloud.netty.protocol;

import com.xziying.appstorecloud.netty.protocol.domain.AppStoreRpcResponse;
import com.xziying.appstorecloud.netty.protocol.domain.PackageAppStoreRpcResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * AppStoreRpcResponseEncoder 编码器
 *
 * @author : xziying
 * @create : 2021-05-01 10:23
 */
public class AppStoreRpcResponseEncoder extends MessageToByteEncoder<AppStoreRpcResponse> {

    @Override
    protected void encode(ChannelHandlerContext ctx, AppStoreRpcResponse msg, ByteBuf out) throws Exception {
        PackageAppStoreRpcResponse response = new PackageAppStoreRpcResponse(msg);
        // 发送协议头
        out.writeInt(response.getHeadLen());
        out.writeBytes(response.getHead());
        // 发送协议内容
        out.writeInt(response.getObjLen());
        out.writeBytes(response.getObj());
    }
}
