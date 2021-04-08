package netty.common;

import com.google.common.collect.Lists;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.chat.SimpleChatClientHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class SimpleClient {
    private final String host;
    private final int port;
    private DefaultClientInitializer initializer;
    private Channel channel;
    private List<Runnable> runnables;

    public SimpleClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(initializer);
            ChannelFuture future = bootstrap.connect(host, port).sync();
            channel = future.channel();
            process();
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void addHandler(Class<? extends ChannelHandler> handler) {
        if (initializer == null) {
            initializer = new DefaultClientInitializer();
        }
        initializer.addHandler(handler);
    }

    public static class DefaultClientInitializer extends ChannelInitializer<SocketChannel> {
        private List<Class<? extends ChannelHandler>> handlers;

        public void addHandler(Class<? extends ChannelHandler> handler) {
            if (handlers == null) {
                handlers = Lists.newLinkedList();
            }
            handlers.add(handler);
        }

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ChannelPipeline pipeline = socketChannel.pipeline();
//            pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
//            pipeline.addLast(new StringEncoder());
//            pipeline.addLast(new StringDecoder());
            pipeline.addLast(new SimpleEncoder());
            pipeline.addLast(new SimpleDecoder());
            for (Class<? extends ChannelHandler> handler : handlers) {
                pipeline.addLast(handler.newInstance());
            }
        }
    }

    public void addProcess(Runnable runnable) {
        if (runnables == null) {
            runnables = Lists.newLinkedList();
        }
        runnables.add(runnable);
    }

    private void process() {
        if (runnables != null) {
            for (Runnable runnable : runnables) {
                runnable.run();
            }
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public void sendMsg() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String msg;
            while (!"exit".equals(msg = reader.readLine())) {
                channel.writeAndFlush(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SimpleClient client = new SimpleClient("localhost", 8848);
        client.addHandler(SimpleChatClientHandler.class);
        client.addProcess(client::sendMsg);
        client.run();
    }
}
