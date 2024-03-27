package controller;

import network.Response;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Objects;

import static controller.ViewController.loginFrame;
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

                switch (response.get("select")){
                    case "user/signUp":
                        if (Objects.equals(response.get("msg"), "Success")){
                            JOptionPane.showMessageDialog(loginFrame, "회원가입 성공");
                        }
                        else {
                            JOptionPane.showMessageDialog(loginFrame, "회원가입 실패");
                        }
                        break;
                    case "user/signOut":
                        if (Objects.equals(response.get("msg"), "Success")){
                            JOptionPane.showMessageDialog(loginFrame, "회원탈퇴 성공");
                        }
                        else {
                            JOptionPane.showMessageDialog(loginFrame, "회원탈퇴 실패");
                        }
                        break;
                    case "user/login":
                        if (Objects.equals(response.get("msg"), "Success")){
                            JOptionPane.showMessageDialog(loginFrame, "로그인 성공");
                            switcher();
                        }
                        else {
                            JOptionPane.showMessageDialog(loginFrame, "로그인 실패");
                        }
                        break;
                    case "user/logout":
                        if (Objects.equals(response.get("msg"), "Success")){
                            JOptionPane.showMessageDialog(loginFrame, "로그아웃 성공");
                            switcher();
                        }
                        else {
                            JOptionPane.showMessageDialog(loginFrame, "로그인 실패");
                        }
                        break;
                    case "video/add":
                        if (Objects.equals(response.get("msg"), "Success")){

                        }
                        else {

                        }
                        break;
                    case "video/delete":
                        if (Objects.equals(response.get("msg"), "Success")){

                        }
                        else {

                        }
                        break;
                    case "favorite/getList":
                        if (Objects.equals(response.get("msg"), "Success")){

                        }
                        else {

                        }
                        break;
                    case "favorite/add":
                        if (Objects.equals(response.get("msg"), "Success")){

                        }
                        else {
                            JOptionPane.showMessageDialog(loginFrame, "다운로드 실패");
                        }
                        break;
                    case "favorite/delete":
                        if (Objects.equals(response.get("msg"), "Success")){

                        }
                        else {
                        }
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
