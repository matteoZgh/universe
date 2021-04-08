package param;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class TestParam extends Param {
    private Integer a;
    private Integer b;
    private String c;

    public TestValidator validator() {
        return new TestValidator();
    }

    public class TestValidator extends Validator {
        public TestValidator a() {
            return (TestValidator) and(() -> a > 0);
        }

        public TestValidator b() {
            return (TestValidator) and(() -> b > 0);
        }

        public TestValidator c() {
            return (TestValidator) and(() -> StringUtils.isNotBlank(c));
        }
    }
}
