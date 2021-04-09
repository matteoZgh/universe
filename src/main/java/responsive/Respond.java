package responsive;

public interface Respond {
    void main();
    void consumer();
    void success();
    void block();
    void error();
    Respond onSuccess(Runnable runnable);
    Respond onBlock(Runnable runnable);
    Respond onError(Runnable runnable);
    void run();
}
