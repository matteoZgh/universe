package builder;

public class BuilderTest {
    public static void main(String[] args) {
        TbUser tbUser = getTbUser();

        TbAccount tbAccount = getTbAccount();

        UserInfo userInfo = UserInfoFactory.create(tbUser, tbAccount);

        System.out.println(userInfo);
    }

    public static TbUser getTbUser() {
        return TbUser.builder().userId("123")
                .userName("asd")
                .build();
    }

    public static TbAccount getTbAccount() {
        return TbAccount.builder().accountId("a123")
                .password("123456")
                .build();
    }

}
