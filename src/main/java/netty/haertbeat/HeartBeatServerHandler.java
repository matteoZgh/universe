package netty.haertbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.commons.lang3.StringUtils;

public class HeartBeatServerHandler extends SimpleChannelInboundHandler<String> {
    private int times = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (StringUtils.isNotEmpty(msg) && msg.equals("heartbeat")) {
            System.out.println("HeartBeat: " + ctx.channel().remoteAddress());
        } else {
            System.out.println(msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                if (times < 3) {
                    times++;
                } else {
                    System.out.println("close client");
                    ctx.close();
                }
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client active");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client inactive");
    }
}
