package builder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TbAccount {
    private String accountId;
    private String password;
}
