package map;

import java.util.HashMap;
import java.util.Map;

public class MapTest {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap();
        map.put("asd","asd");
        map.put("qwe", null);
        System.out.println(map.get("qwe"));
        map.forEach((k, v) -> System.out.println(v));
    }
}
