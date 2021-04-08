package func;

public class $ {
    public static void tryCatch(Func func, ExceptionFunc fair) {
        try {
            func.run();
        } catch (Exception e) {
            fair.run(e);
        }
    }

    public static Object tryCatch(ResultFunc func, ExceptionResultFunc fair) {
        try {
            return func.run();
        } catch (Exception e) {
            return fair.run(e);
        }
    }
}
