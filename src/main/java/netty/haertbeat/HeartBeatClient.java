package netty.haertbeat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HeartBeatClient {
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new HeartBeatClientInitializer());
            ChannelFuture future = bootstrap.connect("localhost", 8080).sync();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String msg;
            while (!"exit".equals(msg = reader.readLine())) {
                future.channel().writeAndFlush(msg + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new HeartBeatClient().run();
    }
}
