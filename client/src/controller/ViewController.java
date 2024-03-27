package controller;

import GUI.Auth.AuthSuperFrame;
import GUI.Download.DownloadSuperFrame;
import GUI.Login.LoginSuperFrame;
import GUI.SampleFrame;
import service.CrawlService;
import service.ServerService;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
            loginFrame.dispose();
            loginFrame = null;
        }
        else if(loginFrame == null) {
            loginFrame = new LoginSuperFrame();
            downFrame.dispose();
            downFrame = null;
        }
    }

    public void addAuthListener(){
    }
    public void addDownloadListener(){

    }

    // 이름으로 컴포넌트 찾기 메서드
    public static Component findComponentByName(Container container, String name) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof Container) {
                Component found = findComponentByName((Container) component, name);
                if (found != null) {
                    return found;
                }
            }
            if (name.equals(component.getName())) {
                return component;
            }
        }
        return null;
    }
}
