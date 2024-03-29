package dao;

import db.DatabaseUtil;
import dto.FavoriteDTO;
import dto.VideoDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDAOImpl extends DatabaseUtil implements FavoriteDAO {
    // settings
    PreparedStatement pStmtInsert;
    PreparedStatement pStmtSelect;
    PreparedStatement pStmtDelete;
    PreparedStatement pStmtSelectVideo;

    // constructor
    public FavoriteDAOImpl() throws Exception {
        pStmtInsert = conn.prepareStatement("INSERT into Favorite (user_id , video_id) values (?, ?)");
        pStmtSelect = conn.prepareStatement("SELECT * from Favorite WHERE user_id=?");
        pStmtSelectVideo = conn.prepareStatement("SELECT * from Video WHERE video_id=?");
        pStmtDelete = conn.prepareStatement("DELETE FROM Favorite WHERE user_id=? and video_id=?");
    }

    @Override
    public Boolean addFavorite(FavoriteDTO dto) throws SQLException {
        pStmtInsert.setInt(1, dto.getUser_id());
        pStmtInsert.setInt(2, dto.getVideo_id());
        try{
            pStmtInsert.executeQuery();
            return true;
        }
        catch (Exception e){
            e.getStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteFavorite(FavoriteDTO dto) throws SQLException {
        pStmtDelete.setInt(1, dto.getUser_id());
        pStmtDelete.setInt(2, dto.getVideo_id());
        try{
            pStmtDelete.executeQuery();
            return true;
        }
        catch (Exception e){
            e.getStackTrace();
            return false;
        }
    }

    public List<VideoDTO> getFavoriteList(FavoriteDTO dto) throws SQLException {
        List<VideoDTO> video_list = new ArrayList<>();
        pStmtSelect.setInt(1, dto.getUser_id());
        try {
            rs = pStmtSelect.executeQuery();
            while(rs.next()) {
                FavoriteDTO fDto = new FavoriteDTO();
                VideoDTO vDto;
                vDto = getVideoById(rs.getInt("video_id"));

                vDto.setTitle(vDto.getTitle());
                vDto.setUrl(vDto.getUrl());
                video_list.add(vDto);
            }
            return video_list;
        }
        catch(Exception e) {
            System.out.println("리스트 요청 에러");
            e.printStackTrace();
            return null;
        }
    }

    public VideoDTO getVideoById(int videoId) throws SQLException {
        pStmtSelectVideo.setInt(1, videoId);
        rs = pStmtSelectVideo.executeQuery();
        System.out.println(rs);

        VideoDTO dto = new VideoDTO();
        while (rs.next()){
            System.out.println(rs.getString("VIDEO_ID"));
            dto.setVideo_id(videoId);
            dto.setUrl(rs.getString("URL"));
            dto.setTitle(rs.getString("TITLE"));
            break;
        }
        return dto;
    }
}
