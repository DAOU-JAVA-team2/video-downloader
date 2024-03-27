package controller;

import dto.UserDTO;
import dto.VideoDTO;

import java.net.Socket;
import java.util.Scanner;

import static controller.ViewController.serverService;

public class SendTest implements Runnable{
    // setting
    private final Socket socket;
    Scanner scanner = new Scanner(System.in);

    // constructor
    public SendTest(Socket socket) throws Exception {
        this.socket = socket;

        System.out.println("  -> send thread open");
    }

    public void print(){
        System.out.println("test");
    }

    // run
    @Override
    public void run() {
        print();
        boolean run_send = true;
        // send logic loop
        while(run_send) {
            try {
                int select = scanner.nextInt();
                if(select == 1) {
                    serverService.userSignin(new UserDTO());
                }
                else if(select == 2) {
                    serverService.userSignout(new UserDTO());
                }
                else if(select == 3) {
                    serverService.userLogin(new UserDTO());
                }
                else if(select == 4) {
                    serverService.userLogout(new UserDTO());
                }
                else if(select == 5) {
                    serverService.videoAdd(new VideoDTO());
                }
                else if(select == 6) {
                    serverService.videoDelete(new VideoDTO());
                }
                else if(select == 7) {
                    serverService.favoriteAdd(new UserDTO(), new VideoDTO());
                }
                else if(select == 8) {
                    serverService.favoriteDelete(new UserDTO(), new VideoDTO());
                }
                else if(select == 9) {
                    serverService.favoriteGetList(new UserDTO());
                }
                // exit
                else if(select == 100) {
                    run_send = false;
                    break;
                }
            }
            catch (Exception e) {
                System.out.println("send error");
                e.getStackTrace();
                break;
            }
        }
        // send logic end -> close socket
        try {
            socket.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
