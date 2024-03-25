package controller;

import network.Request;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SendHandler implements Runnable{
    // setting
    private final Socket socket;
    private final ObjectOutputStream clientOut;
    private Request request;
    Scanner scanner = new Scanner(System.in);

    // constructor
    public SendHandler(Socket socket) throws Exception {
        this.socket = socket;
        this.clientOut = new ObjectOutputStream(socket.getOutputStream());
        this.request = new Request();
        System.out.println("  -> send thread open");
    }

    // run
    @Override
    public void run() {
        Boolean run_send = true;
        // send logic loop
        while(run_send) {
            try {
                Integer select = scanner.nextInt();
                if(select == 1) {
                    userInsertControl();
                }
                else if(select == 2) {
                    userDeleteControl();
                }
                else if(select == 3) {
                    userUpdateControl();
                }
                else if(select == 4) {
                    userListControl();
                }
            }
            catch (Exception e) {
                System.out.println("send error");
                e.printStackTrace();
                break;
            }
        }
        // send logic end -> close socket
        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void userInsertControl() throws IOException {
        request = new Request();
        request.put("select", "1");
        // send
        clientOut.writeObject(request);
        clientOut.flush();
    }
    public void userDeleteControl() throws IOException {
        request = new Request();
        request.put("select", "2");
        // send
        clientOut.writeObject(request);
        clientOut.flush();
    }
    public void userUpdateControl() throws IOException {
        request = new Request();
        request.put("select", "3");
        // send
        clientOut.writeObject(request);
        clientOut.flush();
    }
    public void userListControl() throws IOException {
        request = new Request();
        request.put("select", "4");
        request.put("select2", "4");
        // send
        clientOut.writeObject(request);
        clientOut.flush();
    }
}
