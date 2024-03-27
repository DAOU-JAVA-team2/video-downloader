package GUI.Common;

public class VideoDTO {

    public String imageURL;
    public String videoTitle;
    public int viewCount;
    public String uploader;

    public VideoDTO(String imageURL, String videoTitle, int viewCount, String uploader) {
        this.imageURL = imageURL;
        this.videoTitle = videoTitle;
        this.viewCount = viewCount;
        this.uploader = uploader;
    }
}
