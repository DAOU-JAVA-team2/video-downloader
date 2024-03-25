package dao;

import dto.UserDTO;

public interface UserDAO {
    void insertUser(UserDTO userDTO);
    UserDTO getUserById(int userId);
    void updateUser(UserDTO userDTO);
    void deleteUser(int userId);
}
