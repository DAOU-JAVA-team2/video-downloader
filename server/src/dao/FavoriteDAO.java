package dao;

import dto.FavoriteDTO;
import dto.VideoDTO;

import java.sql.SQLException;
import java.util.List;

public interface FavoriteDAO {
    Boolean addFavorite(FavoriteDTO dto) throws SQLException;
    Boolean deleteFavorite(FavoriteDTO dto) throws SQLException;
    List<VideoDTO> getFavoriteList(FavoriteDTO dto) throws SQLException;
}
