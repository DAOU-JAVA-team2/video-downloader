package dto;

public class VideoDTO {
    private Integer video_id;
    private String title;
    private String url;

    // 생성자, getter, setter 메서드 등이 포함됩니다.
    public Integer getVideo_id() {
        return video_id;
    }
    public void setVideo_id(Integer video_id) {
        this.video_id = video_id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
