package dto;

public class UserDTO {
    private Integer user_id;
    private String username;
    private String id;
    private String password;
    private String access;

    // 생성자, getter, setter 메서드 등이 포함됩니다.
    public Integer getUser_id() {return user_id;}
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccess() {return access;}
    public void setAccess(String access) {this.access = access;}
}
