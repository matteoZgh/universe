import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import lombok.var;
import netty.rpc.RpcTest;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) throws Exception {
//        System.out.println(Arrays.toString("\n".getBytes()));
//        System.out.println(Arrays.toString(" ".getBytes()));
//        Class<?>[] classes = {String.class, Integer.class};
        String[] classes = {String.class.getName()};
        Joiner joiner = Joiner.on(',');
        System.out.println(joiner.join(classes));
    }

    public static String test() {
        try {
            return "try";
        } catch (Exception e) {
            return "catch";
        } finally {
            return "finally";
        }
    }
}
