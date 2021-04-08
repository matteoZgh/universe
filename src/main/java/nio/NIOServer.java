package nio;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class NIOServer {
    private ByteBuffer buffer = ByteBuffer.allocate(1024);
    private Map<SocketChannel, String> group = Maps.newHashMap();
    private int sum = 0;
    public void start() {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.bind(new InetSocketAddress(8848));
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                if (selector.select() == 0) {
                    continue;
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        ServerSocketChannel sc = (ServerSocketChannel) key.channel();
                        SocketChannel accept = sc.accept();
                        System.out.println("Accept a connect");
                        accept.configureBlocking(false);
                        accept.register(selector, SelectionKey.OP_READ);
                        group.put(accept, "P" + (++sum));
                    }
                    if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        String userName = group.get(sc) + ": ";
                        buffer.put(userName.getBytes());
                        int status = sc.read(buffer);
                        if (status > 0) {
                            buffer.flip();
                            CharBuffer decode = StandardCharsets.UTF_8.decode(buffer);
                            for (Map.Entry<SocketChannel, String> c : group.entrySet()) {
                                if (!c.getKey().equals(sc)) {
                                    c.getKey().write(StandardCharsets.UTF_8.encode(decode));
                                    c.getKey().register(selector, SelectionKey.OP_READ);
                                }
                            }
                            buffer.clear();
                        }
                        sc.register(selector, SelectionKey.OP_WRITE);
                    }
//                    if (key.isWritable()) {
//                        SocketChannel sc = (SocketChannel) key.channel();
//                        sc.write(StandardCharsets.UTF_8.encode("Send a message"));
//                        sc.register(selector, SelectionKey.OP_READ);
//                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NIOServer server = new NIOServer();
        server.start();
    }
}
