// user Service
package service;

import dao.UserDAOImpl;
import dto.UserDTO;
import network.Request;
import network.Response;

import java.net.Socket;

public class UserService {
    // settings
    private Response response;
    private UserDTO userDTO;
    private UserDAOImpl dbUtil;

    // constructor
    public UserService(Socket socket) throws Exception {
        try{
            this.dbUtil = new UserDAOImpl();
            this.userDTO = new UserDTO();
            this.response = new Response();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    // service methods
    public Response userSignup(Request request) throws Exception {
        userDTO.setId(request.get("id"));
        userDTO.setPassword(request.get("password"));
        userDTO.setUsername(request.get("name"));
        boolean result = dbUtil.insertUser(userDTO);

        response = new Response();
        response.put("msg", result ? "Success" : "Failed");
        response.put("select", "user/signup");

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
    public Response userLogin(Request request) throws Exception {
        userDTO.setId(request.get("id"));
        userDTO.setPassword(request.get("password"));
        String accessToken = dbUtil.loginUser(userDTO);

        response = new Response();
        response.put("msg", accessToken != null ? "Success" : "Failed");
        response.put("select", "user/login");
        response.put("access", accessToken);

        return response;
    }
    public Response userLogout(Request request) throws Exception {
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
}

