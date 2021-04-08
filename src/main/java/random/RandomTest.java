package random;

import java.math.BigDecimal;
import java.util.*;

public class RandomTest {
    private static <T> List<T> getRandomList(List<T> list, int size) {
        Collections.shuffle(list);

//        int start = new Random().nextInt(list.size() - size + 1);
//        int end = start + size;

        return list.subList(0, size);
    }

    public static void main(String[] args) {
        List list = new ArrayList();
        for (int i = 0; i < 47; i++) {
            list.add(i);
        }

        double res = 0.0;

        for (int j = 0; j < 10; j++) {
            Map<String, Integer> map = new HashMap<>();

            for (int i = 0; i < 100000; i++) {
                List l = getRandomList(list, 4);
                String key = l.toString();
//                System.out.println(key);
                if (map.containsKey(key)) {
                    int n = map.get(key);
                    map.put(key, n + 1);
                }
                else
                    map.put(key, 0);
            }

            int times = 0;

            for (String key : map.keySet()) {
                if (map.get(key) > 0) {
                    times++;
                }
            }

            double p = (double) times / (double) map.size();

            System.out.println(times + "/" + map.size() + " p=" + p);

            res += p;
        }

        System.out.println("ap =" + res / 10.0);
    }

    private static BigDecimal C(int a, int b) {
        BigDecimal res = new BigDecimal(1);
        for (int i = a; i > b; i--) {
            res = res.multiply(new BigDecimal(String.valueOf(i)));
        }
        return res;
    }
}
