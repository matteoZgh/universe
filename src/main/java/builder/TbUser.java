package builder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TbUser {
    private String userId;
    private String userName;
}
