package controller;

import network.Response;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Objects;

import static controller.ViewController.switcher;

public class RecvController implements Runnable {
    // setting
    private final Socket socket;
    private final ObjectInputStream clientIn;

    // constructor
    public RecvController(Socket socket) throws Exception {
        this.socket = socket;
        System.out.println("  -> recv thread open");
        this.clientIn = new ObjectInputStream(socket.getInputStream());
    }

    // run
    @Override
    public void run() {
        Boolean run_recv = true;
        // recv logic loop
        while(run_recv) {
            try {
                Response response = (Response) clientIn.readObject();
                System.out.println("\nRES: " + response.toString());
                if (Objects.equals(response.get("select"), "user/login")) {
                    switcher();
                }
                switch (response.get("select")){
                    case "user/signUp":
                        break;
                    case "user/signOut":
                        break;
                    case "user/login":
                        break;
                    case "user/logout":
                        break;
                    case "video/add":
                        break;
                    case "video/delete":
                        break;
                    case "favorite/getList":
                        break;
                    case "favorite/add":
                        break;
                    case "favorite/delete":
                        break;
                    case "exit":
                        break;
                    default:
                        break;
                }
            }
            catch (Exception e) {
                System.out.println("recv error");
                e.getStackTrace();
                break;
            }
        }
        // recv logic end -> close socket
        try {
            socket.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

}
