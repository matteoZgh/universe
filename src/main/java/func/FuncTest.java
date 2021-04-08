package func;

import java.util.function.BiFunction;

public class FuncTest {
    public static void main(String[] args) {
        test();
    }

    public static Object test() {
        return $.tryCatch(func, eFunc);
    }

    public static ResultFunc func = ($) -> {
        System.out.println("asd");
        int a = 1 / 0;
        return "asd";
    };

    public static ExceptionResultFunc eFunc = (e) -> {
        e.printStackTrace();
        return null;
    };
}
