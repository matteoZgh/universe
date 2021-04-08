package set;

import java.util.*;

public class SetTest {
    public static void main(String[] args) {
        String[] nums = new String[]{"asd","cee","www","zzz"};
        Set<String> set = new HashSet<>();
        List<String> list = new ArrayList<>();


        for (String n : nums) {
            set.add(n);
            list.add(n);
        }

        System.out.println(set);
        System.out.println(list);
    }
}
