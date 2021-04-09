package responsive;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ResponsiveTest {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        Respond respond = RespondImpl.of(() -> new Thread(() -> {
            List<Integer> data = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
            for (Object d : data) {
                try {
                    TimeUnit.NANOSECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("produce " + d);
                RespondImpl.pipeline().product(d);
            }
        }).start(), () -> new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.NANOSECONDS.sleep(1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Object o = RespondImpl.pipeline().consumer();
                System.out.println("consume " + o);
            }
        }).start()).onError(() -> {});
        respond.run();
    }
}
