package service;

import dao.FavoriteDAOImpl;
import dto.FavoriteDTO;
import dto.VideoDTO;
import network.Request;
import network.Response;
import com.google.gson.Gson;

import java.util.List;

public class FavoriteService {
    // settings
    private Response response;
    private final FavoriteDTO favoriteDTO;
    private final FavoriteDAOImpl dbUtil;
    private final UserService userService;

    // constructor
    public FavoriteService() throws Exception {
        this.favoriteDTO = new FavoriteDTO();
        this.dbUtil = new FavoriteDAOImpl();
        this.userService = new UserService();
    }

    // service methods
    public Response favoriteAdd(Request request) throws Exception {
        Response res_userId = userService.userGetId(request);
        favoriteDTO.setUser_id(Integer.valueOf(res_userId.get("user_id")));
        favoriteDTO.setVideo_id(Integer.valueOf(request.get("video_id")));
        boolean result = dbUtil.addFavorite(favoriteDTO);

        response = new Response();
        response.put("msg", result ? "Success" : "Failed");
        response.put("select", "favorite/add");

        return response;
    }
    public Response favoriteDelete(Request request) throws Exception {
        Response res_userId = userService.userGetId(request);
        favoriteDTO.setUser_id(Integer.valueOf(res_userId.get("user_id")));
        favoriteDTO.setVideo_id(Integer.valueOf(request.get("video_id")));
        boolean result = dbUtil.deleteFavorite(favoriteDTO);

        response = new Response();
        response.put("msg", result ? "Success" : "Failed");
        response.put("select", "favorite/delete");

        return response;
    }
    public Response favoriteGetList(Request request) throws Exception {
        response = new Response();
        response.put("select", "favorite/getList");

        try{
            Response res_userId = userService.userGetId(request);
            favoriteDTO.setUser_id(Integer.valueOf(res_userId.get("userId")));
            List<VideoDTO> result = dbUtil.getFavoriteList(favoriteDTO);

            response.put("msg", result != null ? "Success" : "Failed");
            if (result != null){
                Gson gson = new Gson();
                String jsonList = gson.toJson(result);
                response.put("list", jsonList);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("List get Error");
        }

        return response;
    }
}
