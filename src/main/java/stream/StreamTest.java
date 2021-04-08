package stream;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

public class StreamTest {
    public static void main(String[] args) {
        List<String> list = Lists.newArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        VO newList = list.stream()
                .map(Integer::valueOf)
                .map((o) -> VO.builder().id(o).build())
                .reduce((a, b) -> VO.builder().id(a.id + b.id).build())
                .get();
        System.out.println(list.equals(newList));
        System.out.println(newList);
    }

    @Data
    @Builder
    static class VO {
        private Integer id;
    }
}
