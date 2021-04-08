package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class NIOClient {
    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    public void start() {
        try {
            Selector selector = Selector.open();
            SocketChannel channel = SocketChannel.open();
            channel.connect(new InetSocketAddress(8848));
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);

            Scanner scanner = new Scanner(System.in);
            Thread thread = new Thread(() -> {
                while (true) {
                    if (scanner.hasNext()) {
                        try {
                            channel.write(StandardCharsets.UTF_8.encode(scanner.nextLine()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.setDaemon(true);
            thread.start();
            while (true) {
                if (selector.select() == 0) continue;
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        if (sc.read(buffer) > 0) {
                            buffer.flip();
                            System.out.println(StandardCharsets.UTF_8.decode(buffer));
                            buffer.clear();
                        }
                        sc.register(selector, SelectionKey.OP_READ);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NIOClient client = new NIOClient();
        client.start();
    }
}
