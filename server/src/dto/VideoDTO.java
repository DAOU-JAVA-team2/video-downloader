package dto;

public class VideoDTO {
    private Integer video_id;
    private String title;
    private String viewCount;
    private String videoUrl;
    private String uploader;
    private String thumbnailUrl;

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
        return videoUrl;
    }
    public void setUrl(String url) {
        this.videoUrl = url;
    }

    public String getUploader() { return uploader; }
    public void setUploader(String uploader) { this.uploader = uploader;}

    public String getThumbnailUrl() {return thumbnailUrl;}
    public void setThumbnailUrl(String thumbnailUrl) {this.thumbnailUrl = thumbnailUrl;}

    public String getViewCount() {
        return viewCount;
    }
    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }
}