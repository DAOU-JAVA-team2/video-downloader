package dao;

import db.DatabaseUtil;
import dto.VideoDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VideoDAOImpl extends DatabaseUtil implements VideoDAO {
    // settings
    PreparedStatement pStmtInsert;
    PreparedStatement pStmtSelect;
    PreparedStatement pStmtDelete;
    public VideoDAOImpl() throws Exception {
        pStmtInsert = conn.prepareStatement("INSERT into Video values (?, ?)");
        pStmtSelect = conn.prepareStatement("SELECT * from Video WHERE videoId=?");
        pStmtDelete = conn.prepareStatement("DELETE FROM Video WHERE title=? and url=?");
    }

    @Override
    public VideoDTO getVideoById(int videoId) throws SQLException {
        pStmtSelect.setInt(1, videoId);
        rs = pStmtSelect.executeQuery();

        VideoDTO dto = new VideoDTO();
        dto.setVideo_id(videoId);
        dto.setTitle(rs.getString("title"));
        dto.setUrl(rs.getString("url"));
        return dto;
    }

    @Override
    public int insertVideo(VideoDTO dto) throws SQLException {
        pStmtInsert.setString(1, dto.getTitle());
        pStmtInsert.setString(1, dto.getUrl());
        try{
            pStmtInsert.executeQuery();
            ResultSet generatedKeys = pStmtInsert.getGeneratedKeys();
            if (generatedKeys.next()) {
                // video_id를 반환합니다.
                return generatedKeys.getInt(1);
            } else {
                // 오류 처리: 생성된 키를 가져오지 못한 경우
                throw new SQLException("Failed to get generated key for video.");
            }
        }
        catch (SQLException e){
            e.getStackTrace();
            return -1;
        }
    }
    @Override
    public boolean deleteVideo(VideoDTO dto) throws SQLException {
        pStmtDelete.setString(1, dto.getTitle());
        pStmtDelete.setString(1, dto.getUrl());
        try{
            pStmtDelete.executeQuery();
            return true;
        }
        catch (SQLException e){
            e.getStackTrace();
            return false;
        }
    }
}
