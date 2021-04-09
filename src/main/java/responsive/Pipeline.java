package responsive;

import com.google.common.collect.Queues;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Pipeline {
    private final BlockingQueue<Object> data = Queues.newLinkedBlockingQueue();
    private volatile long speed = 0;

    public void product(Object o) {
        try {
            TimeUnit.NANOSECONDS.sleep(speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        data.offer(o);
    }

    public Object consumer() {
        try {
            long start = System.currentTimeMillis();
            Object o = data.take();
            long end = System.currentTimeMillis();
            speed = end - start;
            return o;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
