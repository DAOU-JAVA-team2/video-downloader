package project;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class project extends JFrame {

    private JTextField songNameField;

    public project() {
        setTitle("Youtube 뮤비 다운로드");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel label = new JLabel("곡 이름:");
        songNameField = new JTextField();
        JButton downloadButton = new JButton("다운로드");

        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downloadVideo();
            }
        });

        panel.add(label);
        panel.add(songNameField);
        panel.add(downloadButton);

        add(panel);
        setVisible(true);
    }

    private void downloadVideo() {
        String songName = songNameField.getText();
        try {
            String encodedSongName = URLEncoder.encode(songName, "UTF-8");
            String videoUrl = searchYoutubeVideo(encodedSongName);

            if (videoUrl != null) {
                downloadWithYoutubeDL(videoUrl);
            } else {
                JOptionPane.showMessageDialog(this, "해당 곡을 찾을 수 없습니다.");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "곡 이름을 인코딩하는 중 오류가 발생했습니다.");
        }
    }

    private String searchYoutubeVideo(String encodedSongName) {
        try {
            String url = "https://www.youtube.com/results?search_query=" + encodedSongName;
            //System.out.println("YouTube Search URL: " + url); // 디버그용 출력
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select("ytInitialData");

            // 모든 script 태그를 가져옴
            for (Element script : doc.getElementsByTag("script")) {
                String scriptContent = script.html();
                // script 내용에 ytInitialData가 포함되어 있는지 확인
                if (scriptContent.contains("ytInitialData")) {
                    // ytInitialData가 포함된 script 태그를 찾았으면 해석
                    String jsonData = scriptContent.substring(scriptContent.indexOf("{"), scriptContent.lastIndexOf("}") + 1);
                    JSONParser parser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) parser.parse(jsonData);

                    JSONArray contentsArray = (JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject.get("contents"))
                            .get("twoColumnSearchResultsRenderer"))
                            .get("primaryContents"))
                            .get("sectionListRenderer"))
                            .get("contents");
                    JSONObject contentsObject = (JSONObject) contentsArray.get(0);
                    JSONArray result = (JSONArray) ((JSONObject) contentsObject.get("itemSectionRenderer")).get("contents");

                    for (Object obj : result) {
                        // 요소를 JSONObject로 변환 후 videoRenderer 오브젝트 고르기
                        JSONObject item = (JSONObject) obj;
                        JSONObject videoRenderer = (JSONObject) item.get("videoRenderer");

                        // videoRenderer가 null이 아니고 videoId가 존재하는 경우에 출력.
                        if (videoRenderer != null && videoRenderer.containsKey("videoId")) {
                            String videoId = (String) videoRenderer.get("videoId");
                            // 콘솔에 URL을 출력하고 바로 반환
                            System.out.println("Video ID: " + videoId);
                            return "https://www.youtube.com/watch?v=" + videoId;
                        }
                    }
                    break;
                }
            }

            // elements가 비어있는 경우에 대한 처리 추가
            System.out.println("Number of elements found: " + elements.size()); // 디버그용 출력
            if (!elements.isEmpty()) {
                Element firstVideo = elements.first();
                System.out.println("First video element: " + firstVideo); // 디버그용 출력
                return "https://www.youtube.com" + firstVideo.attr("href");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    private void downloadWithYoutubeDL(String videoUrl) {
        try {
            // youtube-dlp의 전체 경로 지정
            String youtubeDLPPath = "C:\\java_project\\yt-dlp";
            // youtube-dlp 실행 명령어와 옵션을 따로 분리하여 전달
            // 직접 URL을 사용하여 다운로드
            String[] command = {"cmd", "/c", youtubeDLPPath + ".exe", "-o", "%(title)s.%(ext)s", videoUrl};

            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            Process process = builder.start();

            // 이하 코드는 그대로 유지
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                JOptionPane.showMessageDialog(this, "다운로드가 완료되었습니다.");
            } else {
                JOptionPane.showMessageDialog(this, "다운로드 중 오류가 발생했습니다.");
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "다운로드 중 오류가 발생했습니다.");
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new project();
            }
        });
    }
}
