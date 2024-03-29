package controller;

import GUI.Auth.LoginSuperFrame;
import GUI.Common.CompNames;
import GUI.Download.DownloadSuperFrame;
import GUI.Download.DownloadWaitingPanel;
import GUI.Download.DownloadedListPanel;
import GUI.Download.VideoSearchPanel;
import dto.UserDTO;
import dto.VideoDTO;
import service.CrawlService;
import service.ServerService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class ViewController {
    public static String access = null;
    public static JFrame loginFrame = null;
    public static JFrame downFrame = null;
    public static ObjectOutputStream clientOut;
    public static ServerService serverService;
    public static CrawlService testYoutubeService2 = new CrawlService();
    //TODO: 검색, 다운대기, 다운로드용 리스트
    public static ArrayList<VideoDTO> videoSearchList = new ArrayList<>();
    public static LinkedList<VideoDTO> downloadWaitingList = new LinkedList<>();
    public static ArrayList<VideoDTO> downloadedList = new ArrayList<>();
    // TODO: 소켓 통신 settings
    Socket socket;
    Thread recvThread;
    Thread sendThread;

    //TODO: 뷰컨 생성자
    public ViewController(Socket socket) throws Exception {
        this.socket = socket;
        clientOut = new ObjectOutputStream(socket.getOutputStream());
        /** frame setting **/
        loginFrame = new LoginSuperFrame();
        addAuthListener();
        /** get service **/
        serverService = new ServerService();
        /** recv thread open **/
        this.recvThread = new Thread(new RecvController(socket));
        recvThread.start();
        /** send test -> will delete **/
        sendThread = new Thread(new SendTest(socket));
        sendThread.start();
    }

    //TODO: 화면 전환
    public static void switcher() {
        if (downFrame == null) {
            downFrame = new DownloadSuperFrame();
            addDownloadListener();
            loginFrame.setVisible(false);

            //TODO: 초기 화면 검색어 (랜덤하게 나옴)
            JPanel videoSearchPanel = (JPanel) ViewController.findComponentByName(downFrame.getContentPane(), CompNames.videoSearchPanel_l);
            ArrayList<VideoDTO> initData = testYoutubeService2.searchYoutubeVideos(getRandomSong());
            ((VideoSearchPanel) videoSearchPanel).updatePanel(initData);
            loginFrame.dispose();
            loginFrame = null;
        } else if (loginFrame == null) {
            loginFrame = new LoginSuperFrame();
            addAuthListener();
            downFrame.dispose();
            downFrame = null;
        }
    }

    // TODO: 로그인 화면 액션 리스너
    public static void addAuthListener() {
        // all components
        JTextField idTextField = ((JTextField) ViewController.findComponentByName(loginFrame.getContentPane(), "idTextField"));
        JTextField passwordField = ((JTextField) ViewController.findComponentByName(loginFrame.getContentPane(), "passwordField"));
        JButton loginButton = ((JButton) ViewController.findComponentByName(loginFrame.getContentPane(), "loginButton"));
        JButton signUpButton = ((JButton) ViewController.findComponentByName(loginFrame.getContentPane(), "signUpButton"));
        // TODO: signUpButton
        signUpButton.addActionListener(e -> {
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
        // TODO: loginButton
        loginButton.addActionListener(e -> {
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

    // TODO 다운로드 화면 리스너
    public static void addDownloadListener() {
        JButton logOutButton = ((JButton) ViewController.findComponentByName(downFrame.getContentPane(), CompNames.logOutButton_u));
        JTextField searchField = (JTextField) ViewController.findComponentByName(downFrame.getContentPane(), CompNames.searchField_u);
        JButton searchButton = (JButton) ViewController.findComponentByName(downFrame.getContentPane(), CompNames.searchButton_u);
        JPanel videoSearchPanel = (JPanel) ViewController.findComponentByName(downFrame.getContentPane(), CompNames.videoSearchPanel_l);
        JButton addToDownloadButton = (JButton) ViewController.findComponentByName(downFrame.getContentPane(), CompNames.addToDownloadButton_l);
        JPanel downloadWaitingPanel = (JPanel) ViewController.findComponentByName(downFrame.getContentPane(), CompNames.downloadWaitingPanel_r);
        JButton downloadButton = (JButton) ViewController.findComponentByName(downFrame.getContentPane(), CompNames.downloadButton_r);
        JPanel downloadedListPanel = (JPanel) ViewController.findComponentByName(downFrame.getContentPane(), CompNames.downloadedListPanel_r);
        JPanel downloadedListCell = (JPanel) ViewController.findComponentByName(downFrame.getContentPane(), CompNames.downloadedListCell_r);
        JButton playButton = (JButton) ViewController.findComponentByName(downFrame.getContentPane(), CompNames.downloadButton_r);

        // TODO: 로그아웃 버튼 (여분 버튼은 숨겨뒀습니다.)
        logOutButton.addActionListener(e -> {
            System.out.println("Click logOutButton");
            try {
                UserDTO dto = new UserDTO();
                dto.setAccess(access);
                serverService.userLogout(dto);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        //TODO: 검색 텍스트 필드
        searchButton.addActionListener(e -> {
            String songName = searchField.getText();

            SwingWorker<ArrayList<VideoDTO>, Void> worker = new SwingWorker<>() {
                @Override
                protected ArrayList<VideoDTO> doInBackground() throws Exception {
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
        downloadButton.addActionListener(e -> {
            // 다운로드 버튼 비활성화
            downloadButton.setEnabled(false);

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    Iterator<VideoDTO> iterator = downloadWaitingList.iterator();
                    while (iterator.hasNext()) {
                        VideoDTO dto = iterator.next();
                        boolean result = testYoutubeService2.downloadWithYoutubeDL(dto);
                        if (result) {
                            System.out.println("다운로드 성공!");
                            iterator.remove();
                            downloadedList.add(dto);
                            /** Junha Add Start **/
                            // Request Server
                            try {
                                serverService.videoAdd(dto);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            /** Junha Add End **/

                            ((DownloadWaitingPanel) downloadWaitingPanel).updatePanel(downloadWaitingList);
                            ((DownloadedListPanel) downloadedListPanel).updatePanel(downloadedList);
                        } else {
                            System.out.println("다운로드 실패 ㅜ");
                        }
                    }
                    return null;
                }

                @Override
                protected void done() {
                    downloadButton.setEnabled(true);
                }
            };

            worker.execute();
        });

    }

    public static void updateDownloadWaitingPanel() {
        System.out.println("대기 패널 업데이트를 시작합니다.");
        SwingWorker<Void, Void> panelUpdater = new SwingWorker<Void, Void>() {
            final JPanel downloadWaitingPanel = (JPanel) ViewController.findComponentByName(downFrame.getContentPane(), CompNames.downloadWaitingPanel_r);

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

    public static String getRandomSong() {
        String[] initSongs = {"데이식스", "아이유", "쏜애플", "백예린"};
        Random random = new Random();
        return initSongs[random.nextInt(initSongs.length)];
    }
}
