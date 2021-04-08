package splitter;

import com.google.common.base.Splitter;

import java.util.List;

public class SplitterTest {
    public static void main(String[] args) {
        String str = "1,,  3,4,5,6,7,8,9";
        List<String> strings = Splitter.on(',')
                .omitEmptyStrings()
                .trimResults()
                .splitToList(str);
        System.out.println(strings);
    }
}
