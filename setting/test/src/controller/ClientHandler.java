package controller;

import dao.UserDAO;
import network.Request;
import network.Response;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private Request request;
    private Response response;
    private ObjectInputStream serverIn;
    private ObjectOutputStream serverOut;

    public ClientHandler(Socket socket) throws Exception {
        this.socket = socket;
        this.response = new Response();
        this.serverOut = new ObjectOutputStream(socket.getOutputStream());
        this.serverIn = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        Boolean run_server = true;
        while(run_server) {
            try {
                // request
                request = (Request)serverIn.readObject();
                System.out.println("\nREQ\n"+socket + request.toString());

                // response
                switch (request.get("select")){
                    case "1":
                        break;
                    case "2":
                        break;
                    case "3":
                        break;
                    case "4":
                        break;
                    case "5":
                        break;
                    case "6":
                        break;
                    case "7":
                        break;
                    default:
                        break;
                }
                response.put("msg", "Success");
                serverOut.writeObject(response);
                serverOut.flush();
            }
            catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}