// user controller
package controller;

import dto.UserDTO;

public class UserController {
    private UserDTO userDTO;
    public UserController(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    // 사용자 관련 요청 처리 메서드들...
}
