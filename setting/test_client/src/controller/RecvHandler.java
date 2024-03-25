package controller;

import network.Response;

import java.io.ObjectInputStream;
import java.net.Socket;

public class RecvHandler implements Runnable {
    // setting
    private Socket socket;
    private ObjectInputStream clientIn;
    private Response response;

    // constructor
    public RecvHandler(Socket socket) throws Exception {
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
                response = (Response)clientIn.readObject();
                System.out.println("\nRES " + response.toString());
            }
            catch (Exception e) {
                System.out.println("recv error");
                e.printStackTrace();
                break;
            }
        }
        // recv logic end -> close socket
        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
