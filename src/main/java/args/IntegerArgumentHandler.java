package args;

public class IntegerArgumentHandler implements ArgumentHandler {
    private Integer value;

    @Override
    public void parse(String s) {
        value = Integer.parseInt(s);
    }

    @Override
    public Object getValue() {
        return value;
    }
}
