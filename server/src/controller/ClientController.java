package controller;

import network.Request;
import network.Response;
import service.FavoriteService;
import service.UserService;
import service.VideoService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientController implements Runnable {
    private final Socket socket;
    private final ObjectInputStream serverIn;
    private final ObjectOutputStream serverOut;
    private final UserService userService;
    private final VideoService videoService;
    private final FavoriteService favoriteService;
    String result;

    // constructor
    public ClientController(Socket socket) throws Exception {
        this.socket = socket;
        this.serverOut = new ObjectOutputStream(socket.getOutputStream());
        this.serverIn = new ObjectInputStream(socket.getInputStream());
        this.userService = new UserService(socket);
        this.videoService = new VideoService(socket);
        this.favoriteService = new FavoriteService(socket);
    }

    // run
    @Override
    public void run() {
        boolean run_server = true;
        while(run_server) {
            try {
                // request
                Request request = (Request)serverIn.readObject();
                System.out.println("\nREQ\n"+socket + request.toString());

                // request parse
                // setting
                Response response = new Response();
                switch (request.get("select")){
                    case "user/signUp":
                        response = userService.userSignup(request);
                        break;
                    case "user/signOut":
                        response = userService.userSignout(request);
                        break;
                    case "user/login":
                        response = userService.userLogin(request);
                        break;
                    case "user/logout":
                        response = userService.userLogout(request);
                        break;
                    case "video/add":
                        response = videoService.videoAdd(request);
                        break;
                    case "video/delete":
                        response = videoService.videoDelete(request);
                        break;
                    case "favorite/getList":
                        response = favoriteService.favoriteGetList(request);
                        break;
                    case "favorite/add":
                        response = favoriteService.favoriteAdd(request);
                        break;
                    case "favorite/delete":
                        response = favoriteService.favoriteDelete(request);
                        break;
                    case "exit":
                        run_server = false;
                        break;
                    default:
                        break;
                }
                // response send
                response.put("select", request.get("select"));
                serverOut.writeObject(response);
                serverOut.flush();
            }
            catch (Exception e) {
                System.out.println("  client 해제 -> " + socket);
                e.getStackTrace();
                return;
            }
        }
    }
}
