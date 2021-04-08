package load;

public class Test {
    static {
        System.out.println("Test: Static");
    }

    {
        System.out.println("TesT: Normal");
    }

    public Inner getInner() {
        return new Inner();
    }

    public class Inner {
        {
            System.out.println("Inner: Normal");
        }
    }
}
