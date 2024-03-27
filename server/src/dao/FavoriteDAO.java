package dao;

import dto.FavoriteDTO;

import java.sql.SQLException;
import java.util.List;

public interface FavoriteDAO {
    Boolean addFavorite(FavoriteDTO dto) throws SQLException;
    Boolean deleteFavorite(FavoriteDTO dto) throws SQLException;
    List<FavoriteDTO> getFavoriteList(FavoriteDTO dto) throws SQLException;
}
