package controller;

import GUI.Common.CompNames;
import GUI.Download.DownloadWaitingPanel;
import GUI.Download.DownloadedListPanel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.UserDTO;
import dto.VideoDTO;
import network.Response;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

import static controller.ViewController.*;

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
        boolean run_recv = true;
        // recv logic loop
        while(run_recv) {
            try {
                Response response = (Response) clientIn.readObject();
                System.out.println("\nRES: " + response.toString());

                switch (response.get("select")){
                    case "user/signUp":
                        if (Objects.equals(response.get("msg"), "Success")){
                            String msg = "회원가입 성공" ;
                            JOptionPane.showMessageDialog(loginFrame, msg);
                        }
                        else {
                            String msg = "회원가입 실패\n에러 : " + response.get("error");
                            JOptionPane.showMessageDialog(loginFrame, msg);
                        }
                        break;
                    case "user/signOut":
                        if (Objects.equals(response.get("msg"), "Success")){
                            String msg = "회원탈퇴 성공" ;
                            JOptionPane.showMessageDialog(loginFrame, msg);
                        }
                        else {
                            String msg = "회원탈퇴 실패" ;
                            JOptionPane.showMessageDialog(loginFrame, msg);
                        }
                        break;
                    case "user/login":
                        if (Objects.equals(response.get("msg"), "Success")){
                            String msg = "로그인 성공" ;
                            JOptionPane.showMessageDialog(loginFrame, msg);
                            access = response.get("access");
                            switcher();
                            System.out.println("리스트요청");
                            serverService.favoriteGetList(new UserDTO());
                        }
                        else {
                            String msg = "로그인 실패\n에러 : " + response.get("error");
                            JOptionPane.showMessageDialog(loginFrame, msg);
                        }
                        break;
                    case "user/logout":
                        if (Objects.equals(response.get("msg"), "Success")){
                            String msg = "로그아웃 성공" ;
                            JOptionPane.showMessageDialog(loginFrame, msg);
                            access = null;
                            switcher();
                        }
                        else {
                            String msg = "로그아웃 실패" ;
                            JOptionPane.showMessageDialog(loginFrame, msg);
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
                            String jsonList = (String) response.get("list"); // 응답에서 JSON 형식의 문자열을 가져옵니다.

                            // JSON 문자열을 List<VideoDTO>로 변환합니다.
                            Gson gson = new Gson();
                            Type videoListType = new TypeToken<List<VideoDTO>>(){}.getType();
                            List<VideoDTO> video_list = gson.fromJson(jsonList, videoListType);

                            // 변환된 List<VideoDTO>를 출력합니다.
                            for (VideoDTO videoDTO : video_list) {
                                System.out.println("Title: " + videoDTO.getTitle() + ", URL: " + videoDTO.getUrl());
                            }
                            System.out.println(video_list);
                            downloadedList = (ArrayList<VideoDTO>)video_list;

                            // redown
                            Iterator<VideoDTO> iterator = downloadedList.iterator();
                            try{
                                while (iterator.hasNext()) {
                                    VideoDTO dto = iterator.next();
                                    System.out.println(dto.toString());
                                    boolean result = testYoutubeService2.downloadWithYoutubeDL(dto);
                                    if (result) {
                                        System.out.println("다운로드 성공!");
                                    } else {
                                        System.out.println("다운로드 실패 ㅜ");
                                    }
                                }
                            }
                            catch (Exception e){
                                System.out.println("reDown end error");
                            }
                            // reload
                            JPanel downloadedListPanel = (JPanel) ViewController.findComponentByName(downFrame.getContentPane(), CompNames.downloadedListPanel_r);
                            ((DownloadedListPanel) downloadedListPanel).updatePanel(downloadedList);
                        }
                        else {
                            System.out.println("리스트 확인 실패");
                        }
                        break;
                    case "favorite/add":
                        if (Objects.equals(response.get("msg"), "Success")){

                        }
                        else {
                            String msg = "다운로드 실패" ;
                            JOptionPane.showMessageDialog(loginFrame, msg);
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
            catch (SocketException e){
                System.out.println("Socket disConnected");
                break;
            }
            catch (Exception e) {
                System.out.println("recv error");
                e.printStackTrace();
            }
        }
        // recv logic end -> close socket
        try {
            socket.close();
        } catch (Exception e) {
            System.out.println("socket close error");
        }
    }

}
