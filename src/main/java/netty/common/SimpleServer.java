package netty.common;

import com.google.common.collect.Lists;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.chat.SimpleChatServerHandler;

import java.util.List;

public class SimpleServer {
    private final int port;
    private DefaultServerInitializer initializer;
    private Channel channel;
    private List<Runnable> runnables;

    public SimpleServer(int port) {
        this.port = port;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(initializer);
            ChannelFuture future = bootstrap.bind(port).sync();
            process();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public void addHandler(Class<? extends ChannelHandler> handler) {
        if (initializer == null) {
            initializer = new DefaultServerInitializer();
        }
        initializer.addHandler(handler);
    }

    public static class DefaultServerInitializer extends ChannelInitializer<SocketChannel> {
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

    public static void main(String[] args) {
        SimpleServer server = new SimpleServer(8848);
        server.addHandler(SimpleChatServerHandler.class);
        server.run();
    }
}
