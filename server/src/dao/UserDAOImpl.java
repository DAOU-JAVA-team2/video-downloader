package dao;

import db.DatabaseUtil;
import dto.UserDTO;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class UserDAOImpl extends DatabaseUtil implements UserDAO{
    // settings
    PreparedStatement pStmtInsert;
    PreparedStatement pStmtSelect;
    PreparedStatement pStmtDelete;

    // constructor
    public UserDAOImpl() throws Exception {
        pStmtInsert = conn.prepareStatement("INSERT into User (id, password) values (?, ?)");
        pStmtSelect = conn.prepareStatement("SELECT * from User WHERE access=?");
        pStmtDelete = conn.prepareStatement("DELETE FROM User WHERE id=? and password=?");
    }

    // user Dao
    @Override
    public Boolean insertUser(UserDTO dto) throws SQLException {
        try{
        pStmtInsert.setString(1, dto.getId());
        pStmtInsert.setString(2, dto.getPassword());
            pStmtInsert.executeQuery();
            return true;
        }
        catch (SQLException e){
            e.getStackTrace();
            throw e;
        }
    }

    @Override
    public Boolean deleteUser(UserDTO dto) throws SQLException {
        pStmtDelete.setString(1, dto.getId());
        pStmtDelete.setString(2, dto.getPassword());
        try{
            pStmtDelete.executeQuery();
            return true;
        }
        catch (SQLException e){
            e.getStackTrace();
            return false;
        }
    }

    @Override
    public UserDTO getUserIdByAccess(String access) throws SQLException {
        pStmtSelect.setString(1, access);
        rs = pStmtSelect.executeQuery();

        UserDTO dto = new UserDTO();
        dto.setUser_id(rs.getInt("user_id"));
        return dto;
    }

    @Override
    public String loginUser(UserDTO dto) throws SQLException {
        String sqlSelectUser = "SELECT COUNT(*) AS count FROM User WHERE id = ? AND password = ?";
        String sqlSelectAccessToken = "SELECT access FROM User WHERE id = ? AND password = ?";
        String sqlUpdateAccessToken = "UPDATE User SET access = ? WHERE id = ? AND password = ?";
        String accessToken;

        try {
            // 해당 아이디와 패스워드를 가진 유저가 존재하는지 확인
            PreparedStatement userSelect = conn.prepareStatement(sqlSelectUser);
            userSelect.setString(1, dto.getId());
            userSelect.setString(2, dto.getPassword());
            ResultSet userRs = userSelect.executeQuery();
            userRs.next();
            int userCount = userRs.getInt("count");

            if (userCount == 1) {
                do {
                    accessToken = randomAccessToken();
                    PreparedStatement accessSelect = conn.prepareStatement(sqlSelectAccessToken);
                    PreparedStatement accessUpdate = conn.prepareStatement(sqlUpdateAccessToken);
                    accessSelect.setString(1, dto.getId());
                    accessSelect.setString(2, dto.getPassword());
                    ResultSet accessRs = accessSelect.executeQuery();

                    if (accessRs.next()) {
                        String currentAccessToken = accessRs.getString("access");

                        // 현재 액세스 토큰과 새로 생성한 액세스 토큰이 다를 경우에만 업데이트
                        if (!accessToken.equals(currentAccessToken)) {
                            // 새로 생성한 액세스 토큰으로 업데이트
                            accessUpdate.setString(1, accessToken);
                            accessUpdate.setString(2, dto.getId());
                            accessUpdate.setString(3, dto.getPassword());
                            accessUpdate.executeUpdate();
                            break;
                        }
                    }
                } while (true);
                return accessToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    @Override
    public Boolean logoutUser(UserDTO dto) {
        String sqlUpdate = "UPDATE User SET access = null WHERE access = ?";
        try{
            PreparedStatement accessDelete = conn.prepareStatement(sqlUpdate);
            accessDelete.setString(1, dto.getAccess());
            accessDelete.executeQuery();
            return true;
        }
        catch (SQLException e) {
            e.getStackTrace();
            return false;
        }
    }

    // etc
    public String randomAccessToken(){
        // 랜덤한 바이트 배열 생성
        byte[] randomBytes = new byte[40];
        new SecureRandom().nextBytes(randomBytes);
        // 바이트 배열을 Base64 인코딩하여 문자열로 변환
        String accessToken = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        accessToken = accessToken.substring(0, 30);

        return accessToken;
    }
}
