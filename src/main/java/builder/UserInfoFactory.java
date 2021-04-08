package builder;

public class UserInfoFactory {

    public static UserInfo create(TbUser tbUser, TbAccount tbAccount) {
        return UserInfo.builder().userId(tbUser.getUserId())
                .userName(tbUser.getUserName())
                .accountId(tbAccount.getAccountId())
                .password(tbAccount.getPassword())
                .build();
    }

}
