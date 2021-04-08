package check;

public class Checker {

    public static Boolean iF(boolean expression, VoidFunc func) {
        return (Boolean) if4VoidFunc(expression, func, true, false);
    }

    public static Object iF(boolean expression, ResultFunc func, Object fair) {
        if (expression) {
            return func.run();
        }
        return fair;
    }

    private static Object if4VoidFunc(boolean expression, VoidFunc func, Object success, Object fair) {
        if (expression) {
            func.run();
            return success;
        }
        return fair;
    }

    @FunctionalInterface
    public interface ResultFunc {
        Object run();
    }

    @FunctionalInterface
    public interface VoidFunc {
        void run();
    }

}
