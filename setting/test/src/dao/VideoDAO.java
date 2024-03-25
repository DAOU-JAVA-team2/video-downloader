package dao;

import dto.VideoDTO;

public interface VideoDAO {
    void insertVideo(VideoDTO videoDTO);
    VideoDTO getVideoById(int videoId);
    void updateVideo(VideoDTO videoDTO);
    void deleteVideo(int videoId);
}
