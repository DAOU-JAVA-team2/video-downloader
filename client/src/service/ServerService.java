package service;

import dto.UserDTO;
import dto.VideoDTO;
import network.Request;

import java.io.IOException;

import static controller.ViewController.clientOut;

public class ServerService {
    public void userSignin(UserDTO dto) throws IOException {
        Request request = new Request();
        request.put("select", "user/signUp");
        // add data
        request.put("id", dto.getId());
        request.put("password", dto.getPassword());
        request.put("name", dto.getUsername());
        // return
        clientOut.writeObject(request);
        clientOut.flush();
    }
    public void userSignout(UserDTO dto) throws IOException {
        Request request = new Request();
        request.put("select", "user/signOut");
        // add data
        request.put("id", dto.getId());
        request.put("password", dto.getPassword());
        // return
        clientOut.writeObject(request);
        clientOut.flush();
    }
    public void userLogin(UserDTO dto) throws IOException {
        Request request = new Request();
        request.put("select", "user/login");
        // add data
        request.put("id", dto.getId());
        request.put("password", dto.getPassword());
        // return
        clientOut.writeObject(request);
        clientOut.flush();
    }

    public void userLogout(UserDTO dto) throws IOException {
        Request request = new Request();
        request.put("select", "user/logout");
        // add data
        request.put("access", dto.getAccess());
        // return
        clientOut.writeObject(request);
        clientOut.flush();
    }

    public void videoAdd(VideoDTO dto) throws IOException {
        Request request = new Request();
        request.put("select", "video/add");
        // add data
        request.put("title", dto.getTitle());
        request.put("url", dto.getUrl());
        // return
        clientOut.writeObject(request);
        clientOut.flush();
    }

    public void videoDelete(VideoDTO dto) throws IOException {
        Request request = new Request();
        request.put("select", "video/delete");
        // add data
        request.put("title", dto.getTitle());
        request.put("url", dto.getUrl());
        // return
        clientOut.writeObject(request);
        clientOut.flush();
    }

    public void favoriteGetList(UserDTO dto) throws IOException {
        Request request = new Request();
        request.put("select", "video/getList");
        // add data
        request.put("access", dto.getAccess());
        request.put("userId", String.valueOf(dto.getUser_id()));
        // return
        clientOut.writeObject(request);
        clientOut.flush();
    }

    public void favoriteAdd(UserDTO uDto, VideoDTO vDto) throws IOException {
        Request request = new Request();
        request.put("select", "video/add");
        // add data
        request.put("access", uDto.getAccess());
        request.put("userId", String.valueOf(uDto.getUser_id()));
        request.put("videoId", String.valueOf(vDto.getVideo_id()));
        // return
        clientOut.writeObject(request);
        clientOut.flush();
    }

    public void favoriteDelete(UserDTO uDto, VideoDTO vDto) throws IOException {
        Request request = new Request();
        request.put("select", "video/delete");
        // add data
        request.put("access", uDto.getAccess());
        request.put("userId", String.valueOf(uDto.getUser_id()));
        request.put("videoId", String.valueOf(vDto.getVideo_id()));
        // return
        clientOut.writeObject(request);
        clientOut.flush();
    }
}
