package dto;

public class UserDTO extends DTO{
    public Integer userId;
    public String userName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static void newDTO() {
         new UserDTO();
    }
}
