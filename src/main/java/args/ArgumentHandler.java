package args;

public interface ArgumentHandler {
    void parse(String s);
    Object getValue();
}
