// user Service
package service;

import dao.UserDAOImpl;
import dto.UserDTO;
import network.Request;
import network.Response;

import java.sql.SQLException;

public class UserService {
    // settings
    private Response response;
    private UserDTO userDTO;
    private UserDAOImpl dbUtil;

    // constructor
    public UserService(){
        try{
            this.dbUtil = new UserDAOImpl();
            this.userDTO = new UserDTO();
            this.response = new Response();
        }
        catch(Exception e) {
            System.out.println("userService Error: " + e.getMessage());
        }
    }

    // service methods
    public Response userSignup(Request request) {
        response = new Response();
        response.put("select", "user/signup");

        userDTO.setId(request.get("id"));
        userDTO.setPassword(request.get("password"));
        try{
            dbUtil.insertUser(userDTO);
            response.put("msg", "Success");
        }
        catch (SQLException e) {
            response.put("msg", "Failed");
            System.out.println(e.getSQLState());
            String errorMessage = getErrorMessage(e.getSQLState());
            response.put("error", errorMessage);
        }

        return response;
    }
    public Response userSignout(Request request) throws Exception {
        userDTO.setId(request.get("id"));
        userDTO.setPassword(request.get("password"));
        boolean result = dbUtil.deleteUser(userDTO);

        response = new Response();
        response.put("msg", result ? "Success" : "Failed");
        response.put("select", "user/signout");

        return response;
    }
    public Response userLogin(Request request) {
        response = new Response();
        response.put("select", "user/login");

        userDTO.setId(request.get("id"));
        userDTO.setPassword(request.get("password"));
        try {
            String accessToken = dbUtil.loginUser(userDTO);
            response.put("msg", accessToken != null ? "Success" : "Failed");
            response.put("access", accessToken);
            if(accessToken == null) {
                response.put("error", "유효하지 않은 사용자");
            }
        }
        catch (SQLException e) {
            response.put("msg", "Failed");
            System.out.println(e.getSQLState());
            String errorMessage = getErrorMessage(e.getSQLState());
            response.put("error", errorMessage);
        }

        return response;
    }
    public Response userLogout(Request request) {
        userDTO.setAccess(request.get("access"));
        boolean result = dbUtil.logoutUser(userDTO);

        response = new Response();
        response.put("msg", result ? "Success" : "Failed");
        response.put("select", "user/logout");

        return response;
    }

    public Response userGetId(Request request) throws Exception {
        UserDTO dto = dbUtil.getUserIdByAccess(request.get("access"));
        Integer result = dto.getUser_id();

        response = new Response();
        response.put("msg", result != null ? "Success" : "Failed");
        response.put("userId", String.valueOf(result));
        response.put("select", "user/getId");

        return response;
    }

    // etc
    private String getErrorMessage(String sqlState) {
        String errorMessage;
        switch (sqlState) {
            case "23000":
                errorMessage = "이미 존재하는 값";
                break;
            case "22001":
                errorMessage = "너무 긴 입력";
                break;
            // 다른 SQL 오류 코드에 대한 처리를 여기에 추가합니다.
            default:
                errorMessage = "알 수 없는 오류";
                break;
        }
        return errorMessage;
    }
}

