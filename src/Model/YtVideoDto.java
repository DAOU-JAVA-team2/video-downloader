package Model;

public class YtVideoDto {
    String title;
    String viewCount;
    String uploader;
    String thumbnailUrl;
    String videoUrl;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getViewCount() {
        return viewCount;
    }
    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getUploader() {
        return uploader;
    }
    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

//    public VtVideoDto(String title, String viewCount, String uploader, String thumbnailUrl, String videoUrl) {
//        this.title = title;
//        this.viewCount = viewCount;
//        this.uploader = uploader;
//        this.thumbnailUrl = thumbnailUrl;
//        this.videoUrl = videoUrl;
//    }
}
