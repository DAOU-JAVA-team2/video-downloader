package controller;

import GUI.Download.*;
import GUI.Login.LoginSuperFrame;
import dto.UserDTO;
import dto.VideoDTO;
import service.CrawlService;
import service.ServerService;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;


public class ViewController {
    // settings
    Socket socket;
    Thread recvThread;
    Thread sendThread;
    public static String access = null;
    public static JFrame loginFrame = null;
    public static JFrame downFrame = null;
//    public static JFrame sampleFrame;

    public static ObjectOutputStream clientOut;
    public static ServerService serverService;
    public static CrawlService crawlService;

    public static TestYoutubeService testYoutubeService = new TestYoutubeService();
    public static TestYoutubeService2 testYoutubeService2 = new TestYoutubeService2();

    //검색용 리스트
    //장바구니 리스트
    //다운로드 리스트

    //검색 결과를 담아서 셀을 그릴 용도의 리스트
    public static ArrayList<VideoDTO> videoSearchList = new ArrayList<>();

    public static LinkedList<VideoDTO> downloadWaitingList = new LinkedList<>();

    public static ArrayList<VideoDTO> downloadedList = new ArrayList<>();

    // constructor
    public ViewController(Socket socket) throws Exception {

        this.socket = socket;
        clientOut = new ObjectOutputStream(socket.getOutputStream());
        /** frame setting **/
        loginFrame = new LoginSuperFrame();
        addAuthListener();
//        sampleFrame = new SampleFrame();
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

            JPanel videoSearchPanel = (JPanel) ViewController.findComponentByName(downFrame.getContentPane(),DownloadCompNames.videoSearchPanel_l);
            ArrayList<VideoDTO> initData = testYoutubeService2.searchYoutubeVideos("춘식이");
            ((VideoSearchPanel) videoSearchPanel).updatePanel(initData);

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

    // Auth Frame의 컴포넌트에 액션 넣기
    public static void addAuthListener(){
        // all components
        JTextField idTextField = ((JTextField)ViewController.findComponentByName(loginFrame.getContentPane(), "idTextField"));
        JTextField passwordField = ((JTextField)ViewController.findComponentByName(loginFrame.getContentPane(), "passwordField"));
        JButton loginButton = ((JButton)ViewController.findComponentByName(loginFrame.getContentPane(), "loginButton"));
        JButton signUpButton = ((JButton)ViewController.findComponentByName(loginFrame.getContentPane(), "signUpButton"));
        // signUpButton
        signUpButton.addActionListener(e->{
            System.out.println("Click signUpButton");
            try {
                UserDTO dto = new UserDTO();
                dto.setId(idTextField.getText());
                dto.setPassword(passwordField.getText());
                serverService.userSignin(dto);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        // loginButton
        loginButton.addActionListener(e->{
            System.out.println("Click loginButton");
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

    // Download Frame의 컴포넌트에 액션 넣기
    public static void addDownloadListener(){
        //TODO: 위 패널
        JButton logOutButton = ((JButton)ViewController.findComponentByName(downFrame.getContentPane(), DownloadCompNames.logOutButton_u));
        JTextField searchField = (JTextField)ViewController.findComponentByName(downFrame.getContentPane(), DownloadCompNames.searchField_u);
        JButton searchButton = (JButton)ViewController.findComponentByName(downFrame.getContentPane(),DownloadCompNames.searchButton_u);
        JPanel videoSearchPanel = (JPanel) ViewController.findComponentByName(downFrame.getContentPane(),DownloadCompNames.videoSearchPanel_l);
        JButton addToDownloadButton = (JButton) ViewController.findComponentByName(downFrame.getContentPane(),DownloadCompNames.addToDownloadButton_l);
        JPanel downloadWaitingPanel = (JPanel) ViewController.findComponentByName(downFrame.getContentPane(),DownloadCompNames.downloadWaitingPanel_r);
        JButton downloadButton = (JButton) ViewController.findComponentByName(downFrame.getContentPane(),DownloadCompNames.downloadButton_r);
        JPanel downloadedListPanel = (JPanel) ViewController.findComponentByName(downFrame.getContentPane(),DownloadCompNames.downloadedListPanel_r);

        // TODO: 상 단 패 널
        logOutButton.addActionListener(e->{
            System.out.println("Click logOutButton");
            try {
                UserDTO dto = new UserDTO();
                dto.setAccess(access);
                serverService.userLogout(dto);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        //TODO: 좌측 패널
        searchButton.addActionListener(e -> {
            String songName = searchField.getText();

            SwingWorker<ArrayList<VideoDTO>, Void> worker = new SwingWorker<>() {
                @Override
                protected ArrayList<VideoDTO> doInBackground() throws Exception {
                    // 백그라운드에서 실행될 작업 수행
//                    return testYoutubeService.searchAndDisplayResults(songName);
                    return testYoutubeService2.searchYoutubeVideos(songName);
                }

                @Override
                protected void done() {
                    try {
                        // 작업 완료 후 UI 업데이트
                        videoSearchList = get(); // doInBackground()의 반환값 가져오기
                        ((VideoSearchPanel) videoSearchPanel).updatePanel(videoSearchList);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };
            worker.execute();
        });

        //TODO: 우 상단 패널
        downloadButton.addActionListener(e-> {
            //다운로드 버튼 비활성화
            downloadButton.setEnabled(false);
            for(VideoDTO dto : downloadWaitingList) {
                boolean result = testYoutubeService2.downloadWithYoutubeDL(dto.getUrl());
                if (result) {

                    System.out.println("다운로드 성공!");

                    downloadWaitingList.remove(dto);
                    ((DownloadWaitingPanel)downloadWaitingPanel).updatePanel(downloadWaitingList);
                    downloadedList.add(dto);
                    ((DownloadedListPanel) downloadedListPanel).updatePanel(downloadedList);

                } else {
                    System.out.println("다운로드 실패 ㅜ");
                }
            }



            downloadButton.setEnabled(true);
        });



        //TODO: 우 하단 패널


    }

//    public static void updateDownloadWaitingPanel() {
////        ((VideoSearchPanel) videoSearchPanel).updatePanel(videoSearchList);
//
//        JPanel downloadWaitingPanel = (JPanel) ViewController.findComponentByName(downFrame.getContentPane(),DownloadCompNames.downloadWaitingPanel_r);
//        ((DownloadWaitingPanel) downloadWaitingPanel).updatePanel(downloadWaitingList);
//    }

    public static void updateDownloadWaitingPanel() {
        System.out.println("대기 패널 업데이트를 시작합니다.");
        SwingWorker<Void, Void> panelUpdater = new SwingWorker<Void, Void>() {
            JPanel downloadWaitingPanel = (JPanel) ViewController.findComponentByName(downFrame.getContentPane(), DownloadCompNames.downloadWaitingPanel_r);

            @Override
            protected Void doInBackground() throws Exception {
                ((DownloadWaitingPanel) downloadWaitingPanel).updatePanel(downloadWaitingList);
                return null;
            }
        };
        panelUpdater.execute();
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

    // 이름으로 컴포넌트 찾기 메서드
//    public static Component findComponentBySameName(Container container, String componentName) {
//        System.out.println("______________________");
//        Component[] components = container.getComponents();
//        System.out.println("test222222");
//        for (Component component : components) {
//            System.out.println("for" + component.toString());
//            if (Objects.equals(component.getName(), componentName)) {
//                System.out.println("______________________");
//                ((AddToDownloadButton)component).addActionListener(e->{
//                    downloadWaitingList.add(((AddToDownloadButton)component).getDto());
//                    System.out.println("끄아악");
//                });
//            }
////            if (component instanceof Container) {
////                Component foundComponent = findComponentByName((Container) component, componentName);
////                if (foundComponent != null) {
////                    return foundComponent; // 하위 컨테이너에서 찾은 컴포넌트 반환
////                }
////            }
//        }
//        System.out.println("없음");
//        return null; // 이름이 일치하는 컴포넌트가 없는 경우
//    }
}
