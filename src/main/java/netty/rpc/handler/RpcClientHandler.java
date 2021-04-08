package netty.rpc.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.rpc.handler.ResultHandler;

public class RpcClientHandler extends SimpleChannelInboundHandler<String> {
    private Object res;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        Object res = JSON.parse(s);
        ctx.channel().pipeline().addLast("res", new ResultHandler(res));
    }
}
