package dao;

import db.DatabaseUtil;
import dto.FavoriteDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDAOImpl extends DatabaseUtil implements FavoriteDAO {
    // settings
    PreparedStatement pStmtInsert;
    PreparedStatement pStmtSelect;
    PreparedStatement pStmtDelete;

    // constructor
    public FavoriteDAOImpl() throws Exception {
        pStmtInsert = conn.prepareStatement("INSERT into Favorite values (?, ?)");
        pStmtSelect = conn.prepareStatement("SELECT * from Favorite WHERE userId=?");
        pStmtDelete = conn.prepareStatement("DELETE FROM Favorite WHERE userId=? and videoId=?");
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

    @Override
    public List<FavoriteDTO> getFavoriteList(FavoriteDTO dto) throws SQLException {
        List<FavoriteDTO> result = new ArrayList<FavoriteDTO>();
        pStmtSelect.setInt(1, dto.getUser_id());
        try {
            rs = pStmtSelect.executeQuery();
            while(rs.next()) {
                FavoriteDTO fDto = new FavoriteDTO();
                fDto.setUser_id(rs.getInt("userId"));
                fDto.setVideo_id(rs.getInt("videoId"));
                result.add(fDto);
            }
            return result;
        }
        catch(Exception e) {
            e.getStackTrace();
            return null;
        }
    }
}
