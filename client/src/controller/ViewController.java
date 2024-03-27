package controller;

import GUI.Auth.AuthSuperFrame;
import GUI.Download.DownloadSuperFrame;
import GUI.Login.LoginSuperFrame;
import GUI.SampleFrame;
import dto.UserDTO;
import service.CrawlService;
import service.ServerService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

public class ViewController {
    // settings
    Socket socket;
    Thread recvThread;
    Thread sendThread;
    public static JFrame loginFrame = null;
    public static JFrame downFrame = null;
    public static JFrame sampleFrame;
    public static ObjectOutputStream clientOut;
    public static ServerService serverService;
    public static CrawlService crawlService;

    // constructor
    public ViewController(Socket socket) throws Exception {
        this.socket = socket;
        clientOut = new ObjectOutputStream(socket.getOutputStream());
        /** frame setting **/
//        downFrame = new DownloadSuperFrame();
        loginFrame = new LoginSuperFrame();
        addAuthListener();
        sampleFrame = new SampleFrame();
        /** get service **/
        serverService = new ServerService();
        crawlService = new CrawlService();
        /** recv thread open **/
        this.recvThread = new Thread(new RecvController(socket));
        recvThread.start();
        /** send test -> will delete **/
        sendThread = new Thread(new SendTest(socket));
        sendThread.start();
    }

    // functions
    public static void switcher() {
        if(downFrame == null) {
            downFrame = new DownloadSuperFrame();
            addDownloadListener();
            loginFrame.dispose();
            loginFrame = null;
        }
        else if(loginFrame == null) {
            loginFrame = new LoginSuperFrame();
            addAuthListener();
            downFrame.dispose();
            downFrame = null;
        }
    }

    public static void addAuthListener(){
        // all components
        JTextField idTextField = ((JTextField)ViewController.findComponentByName(loginFrame.getContentPane(), "idTextField"));
        JTextField passwordField = ((JTextField)ViewController.findComponentByName(loginFrame.getContentPane(), "passwordField"));
        JButton loginButton = ((JButton)ViewController.findComponentByName(loginFrame.getContentPane(), "loginButton"));
        // loginButton
        loginButton.addActionListener(e->{
            System.out.println("얄루");
            try {
                UserDTO dto = new UserDTO();
                dto.setId(idTextField.getText());
                dto.setPassword(passwordField.getText());
                serverService.userLogin(dto);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    public static void addDownloadListener(){
        // all components
        // searchButton
        JButton searchButton = ((JButton)ViewController.findComponentByName(downFrame.getContentPane(), "searchButton"));
        searchButton.addActionListener(e->{
            System.out.println("얄루");
        });
    }

    // 이름으로 컴포넌트 찾기 메서드
    public static Component findComponentByName(Container container, String componentName) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (Objects.equals(component.getName(), componentName)) {
                return component; // 이름이 일치하는 컴포넌트 반환
            }
            if (component instanceof Container) {
                Component foundComponent = findComponentByName((Container) component, componentName);
                if (foundComponent != null) {
                    return foundComponent; // 하위 컨테이너에서 찾은 컴포넌트 반환
                }
            }
        }
        return null; // 이름이 일치하는 컴포넌트가 없는 경우
    }
}
