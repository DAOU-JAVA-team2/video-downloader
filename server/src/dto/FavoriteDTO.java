package dto;

public class FavoriteDTO {
    private Integer user_id;
    private Integer video_id;

    // 생성자, getter, setter 메서드 등이 포함됩니다.
    public Integer getUser_id() {
        return user_id;
    }
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getVideo_id() {
        return video_id;
    }
    public void setVideo_id(Integer video_id) {
        this.video_id = video_id;
    }
}
