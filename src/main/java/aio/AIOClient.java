package aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;

public class AIOClient {
    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    public void start() {
        try {
            AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
            channel.connect(new InetSocketAddress(8848)).get();
            Future<Integer> read = channel.read(buffer);
            buffer.flip();
            System.out.println(StandardCharsets.UTF_8.decode(buffer));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AIOClient().start();
    }
}
