// video controller
package service;

import dao.VideoDAOImpl;
import dto.VideoDTO;
import network.Request;
import network.Response;

import java.net.Socket;

public class VideoService {
    // settings
    private Response response;
    private final VideoDTO videoDTO;
    private final VideoDAOImpl dbUtil;

    // constructor
    public VideoService(Socket socket) throws Exception {
        this.dbUtil = new VideoDAOImpl();
        this.videoDTO = new VideoDTO();
        this.response = new Response();
    }

    // service methods
    public Response videoAdd(Request request) throws Exception {
        videoDTO.setTitle(request.get("title"));
        videoDTO.setUrl(request.get("url"));
        int result = dbUtil.insertVideo(videoDTO);

        response = new Response();
        response.put("msg", result != -1 ? "Success" : "Failed");
        if(result != -1){
            response.put("videoId", String.valueOf(result));
        }
        response.put("select", "video/add");

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
