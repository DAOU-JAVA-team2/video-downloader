// video controller
package service;

import dao.FavoriteDAOImpl;
import dao.UserDAOImpl;
import dao.VideoDAOImpl;
import dto.FavoriteDTO;
import dto.VideoDTO;
import network.Request;
import network.Response;

public class VideoService {
    // settings
    private Response response;
    private final VideoDTO videoDTO;
    private final VideoDAOImpl dbUtil;
    private final FavoriteDAOImpl fDao;
    private final UserDAOImpl uDao;

    // constructor
    public VideoService() throws Exception {
        this.dbUtil = new VideoDAOImpl();
        this.fDao = new FavoriteDAOImpl();
        this.uDao = new UserDAOImpl();
        this.videoDTO = new VideoDTO();
        this.response = new Response();
    }

    // service methods
    public Response videoAdd(Request request) throws Exception {
        videoDTO.setTitle(request.get("title"));
        videoDTO.setUrl(request.get("url"));
        int video_id = dbUtil.insertVideo(videoDTO);

        response = new Response();
        response.put("msg", video_id != -1 ? "Success" : "Failed");
        try {
            if(video_id != -1){
                response.put("videoId", String.valueOf(video_id));
                FavoriteDTO fDto = new FavoriteDTO();
                Integer user_id = uDao.getUserIdByAccess(request.get("access")).getUser_id();
                fDto.setUser_id(user_id);
                fDto.setVideo_id(video_id);
                fDao.addFavorite(fDto);
            }
            response.put("select", "video/add");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response videoDelete(Request request) throws Exception {
        videoDTO.setTitle(request.get("title"));
        videoDTO.setUrl(request.get("url"));
        boolean result = dbUtil.deleteVideo(videoDTO);

        response = new Response();
        response.put("msg", result ? "Success" : "Failed");
        response.put("select", "video/delete");

        return response;
    }
}
