package aio;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AIOServer {
    private ByteBuffer buffer = ByteBuffer.allocate(1024);
    private Map<Integer ,Future<AsynchronousSocketChannel>> group = Maps.newHashMap();
    private Integer id = 0;

    public void start() {
        try {
            AsynchronousServerSocketChannel channel = AsynchronousServerSocketChannel.open();
            channel.bind(new InetSocketAddress(8848));
            Future<AsynchronousSocketChannel> future = channel.accept();
            group.put(id++, future);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Future<Integer> sendMsg(String msg, Integer id) {
        try {
            Future<AsynchronousSocketChannel> future = group.get(id);
            AsynchronousSocketChannel channel = future.get();
            return channel.write(StandardCharsets.UTF_8.encode(msg));
        } catch (Exception e) {
            return null;
        }
    }
    
    public Future<Integer> receiveMsg(Integer id) {
        try {
            Future<AsynchronousSocketChannel> future = group.get(id);
            AsynchronousSocketChannel channel = future.get();
            return channel.read(buffer);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        AIOServer server = new AIOServer();
        server.start();
        Future<Integer> hello = server.sendMsg("Hello", 0);
    }
}
