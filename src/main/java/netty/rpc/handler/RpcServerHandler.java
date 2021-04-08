package netty.rpc.handler;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Splitter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;
import java.util.List;

public class RpcServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        List<String> strings = Splitter.on('$').omitEmptyStrings().trimResults().splitToList(s);
        Class<?> aClass = Class.forName(strings.get(0));
        Object res;
        if (strings.size() == 4) {
            Class<?>[] classes = JSON.parseObject(strings.get(2), Class[].class);
            Method method = aClass.getMethod(strings.get(1), classes);
            Object[] objects = JSON.parseObject(strings.get(3), Object[].class);
            res = method.invoke(aClass.newInstance(), objects);
        } else {
            Method method = aClass.getMethod(strings.get(1));
            res = method.invoke(aClass.newInstance());
        }
        String json = JSON.toJSONString(res);
        ctx.writeAndFlush(json);
    }
}
