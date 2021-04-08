package list;

import java.util.ArrayList;
import java.util.List;

public class ListTest {
    public static void main(String[] args) {
        List <? super A> list = new ArrayList<>();
        list.add(new A());
        list.add(new B());
        list.add(new C());
        System.out.println(list);
    }

    static class A {}
    static class B extends A {}
    static class C extends B {}

}
