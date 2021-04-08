package builder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {
    private String userId;
    private String userName;
    private String accountId;
    private String password;
}
