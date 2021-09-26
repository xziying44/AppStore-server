package com.xziying.appstorecloud.netty.protocol;

import com.xziying.appstorecloud.netty.protocol.domain.AppStoreRpcRequest;
import com.xziying.appstorecloud.netty.protocol.domain.PackageAppStoreRpcRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * AppStoreRpcRequestDecoder 解码器
 *
 * @author : xziying
 * @create : 2021-04-30 23:20
 */
public class AppStoreRpcRequestDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int headLen = in.readInt();
        byte[] headByte = new byte[headLen];
        in.readBytes(headByte);
        String head = new String(headByte, StandardCharsets.UTF_8);
        if (head.equals("APP_STORE_RPC_REQUEST")){
            // RPC 协议
            int nameLen = in.readInt();
            int typeLen = in.readInt();
            int argsLen = in.readInt();
            byte[] nameByte = new byte[nameLen];
            byte[] typeByte = new byte[typeLen];
            byte[] argsByte = new byte[argsLen];
            in.readBytes(nameByte);
            in.readBytes(typeByte);
            in.readBytes(argsByte);
            PackageAppStoreRpcRequest request = new PackageAppStoreRpcRequest();
            request.setNameLen(nameLen);
            request.setTypeLen(typeLen);
            request.setArgsLen(argsLen);
            request.setName(nameByte);
            request.setType(typeByte);
            request.setArgs(argsByte);
            AppStoreRpcRequest reply = new AppStoreRpcRequest(request);
            out.add(reply);
        }
    }
}
