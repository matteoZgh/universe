package responsive;

public class RespondImpl implements Respond {
    private final Runnable main;
    private final Runnable consumer;
    private Runnable success;
    private Runnable error;
    private static final Pipeline pipeline = new Pipeline();

    static public Pipeline pipeline() {
        return pipeline;
    }

    private RespondImpl(Runnable main, Runnable consumer) {
        this.main = main;
        this.consumer = consumer;
    }

    public static Respond of(Runnable main, Runnable consumer) {
        return new RespondImpl(main, consumer);
    }

    @Override
    public Respond onSuccess(Runnable runnable) {
        success = runnable;
        return this;
    }

    @Override
    public Respond onBlock(Runnable runnable) {
        return this;
    }

    @Override
    public Respond onError(Runnable runnable) {
        error = runnable;
        return this;
    }

    public void run() {
        try {
            main();
            consumer();
            success();
        } catch (Exception e) {
            error();
        }
    }

    @Override
    public void main() {
        if (main != null) {
            main.run();
        }
    }

    @Override
    public void consumer() {
        if (consumer != null) {
            consumer.run();
        }
    }

    @Override
    public void success() {
        if (success != null) {
            success.run();
        }
    }

    @Override
    public void block() {

    }

    @Override
    public void error() {
        if (error != null) {
            error.run();
        }
    }
}
