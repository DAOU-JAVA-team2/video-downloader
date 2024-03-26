package project;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class project3 extends JFrame {

    private JTextField songNameField;
    private JTextArea resultArea; // 검색 결과를 출력할 텍스트 영역
    private JPanel videoPanel; // 각 영상 정보와 썸네일 이미지를 표시할 패널

    // 영상 정보와 썸네일 이미지를 담는 내부 클래스
    private class VideoInfo {
        String title;
        String viewCount;
        String uploader;
        String thumbnailUrl;
        String videoUrl;

        public VideoInfo(String title, String viewCount, String uploader, String thumbnailUrl, String videoUrl) {
            this.title = title;
            this.viewCount = viewCount;
            this.uploader = uploader;
            this.thumbnailUrl = thumbnailUrl;
            this.videoUrl = videoUrl;
        }
    }

    public project3() {
        setTitle("Youtube 뮤비 다운로드");
        setSize(600, 400); // 크기 조정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 상단 입력 패널
        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel label = new JLabel("곡 이름:");
        songNameField = new JTextField(20); // 필드 크기 지정
        JButton downloadButton = new JButton("검색");

        // 검색 버튼에 액션 리스너 추가
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchAndDisplayResults(); // 검색 및 결과 출력 메서드 호출
            }
        });

        inputPanel.add(label);
        inputPanel.add(songNameField);
        inputPanel.add(downloadButton);

        // 결과 출력을 위한 텍스트 영역
        resultArea = new JTextArea();
        resultArea.setEditable(false); // 편집 불가능하도록 설정

        // 각 영상 정보와 썸네일 이미지를 표시할 패널
        videoPanel = new JPanel();
        videoPanel.setLayout(new GridLayout(0, 1)); // 수직적으로 배열

        // JScrollPane을 사용하여 videoPanel을 감싸기
        JScrollPane scrollPane = new JScrollPane(videoPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // 패널에 추가
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(resultArea, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH); // JScrollPane 추가

        add(panel);
        setVisible(true);
    }

    // 검색 및 결과 출력 메서드
    private void searchAndDisplayResults() {
        String songName = songNameField.getText();
        resultArea.setText(""); // 이전 결과 지우기
        videoPanel.removeAll(); // 이전 영상 정보 패널 지우기

        try {
            String encodedSongName = URLEncoder.encode(songName, "UTF-8");
            String[] videoUrls = searchYoutubeVideos(encodedSongName);

            if (videoUrls != null) {
                for (String videoUrl : videoUrls) {
                    // 동영상 정보 가져오기
                    String videoInfoUrl = "https://www.youtube.com/oembed?url=" + videoUrl + "&format=json";
                    Document videoInfoDoc = Jsoup.connect(videoInfoUrl).ignoreContentType(true).get();
                    String videoInfoJson = videoInfoDoc.body().text();
                    JSONParser parser = new JSONParser();
                    JSONObject videoInfoObject = (JSONObject) parser.parse(videoInfoJson);

                    // 영상 제목
                    String title = (String) videoInfoObject.get("title");

                    // 조회수
                    String viewCount = (String) videoInfoObject.get("view_count");

                    // 업로드한 사람
                    String uploader = (String) videoInfoObject.get("author_name");

                    // videoUrl에서 VIDEO_ID 추출
                    String videoId = videoUrl.substring(videoUrl.indexOf("v=") + 2);

                    // 썸네일 이미지 URL 조합
                    String thumbnailUrl = "https://img.youtube.com/vi/" + videoId + "/0.jpg";

                    // 영상 정보와 썸네일 이미지를 VideoInfo 객체로 묶음
                    VideoInfo videoInfo = new VideoInfo(title, viewCount, uploader, thumbnailUrl, videoUrl);

                    // VideoInfo 객체를 사용하여 영상 정보 패널 생성
                    createVideoPanel(videoInfo);
                }
            } else {
                resultArea.append("해당 곡을 찾을 수 없습니다.");
            }

            // 패널 갱신
            videoPanel.revalidate();
            videoPanel.repaint();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "곡 이름을 인코딩하는 중 오류가 발생했습니다.");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    // VideoInfo 객체를 사용하여 영상 정보 패널을 생성하는 메서드
    private void createVideoPanel(VideoInfo videoInfo) {
        JPanel videoInfoPanel = new JPanel(new BorderLayout());

        // 썸네일 이미지를 표시하기 위해 JLabel을 생성하여 패널에 추가
        JLabel thumbnailLabel = new JLabel(new ImageIcon(getResizedImage(videoInfo.thumbnailUrl, 120, 90)));
        videoInfoPanel.add(thumbnailLabel, BorderLayout.WEST);

        // 영상 정보를 텍스트 영역에 표시하기 위해 JTextArea를 생성하여 패널에 추가
        JTextArea textArea = new JTextArea();
        textArea.append("Title: " + videoInfo.title + "\n");
        textArea.append("View Count: " + videoInfo.viewCount + "\n");
        textArea.append("Uploader: " + videoInfo.uploader + "\n");
        textArea.append("Video URL: " + videoInfo.videoUrl + "\n");
        textArea.setEditable(false); // 수정 불가능하도록 설정
        videoInfoPanel.add(textArea, BorderLayout.CENTER);

        // 다운로드 버튼 생성 및 패널에 추가
        JButton downloadButton = new JButton("다운로드");
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downloadWithYoutubeDL(videoInfo.videoUrl); // 다운로드 메서드 호출
            }
        });
        videoInfoPanel.add(downloadButton, BorderLayout.EAST); // EAST로 배치

        // 영상 정보 패널을 전체 패널에 추가
        videoPanel.add(videoInfoPanel);
    }

    // 이미지를 리사이즈하는 메서드
    private Image getResizedImage(String imageUrl, int width, int height) {
        try {
            URL url = new URL(imageUrl);
            BufferedImage originalImage = ImageIO.read(url);
            Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return resizedImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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

    private String[] searchYoutubeVideos(String encodedSongName) {
        try {
            String url = "https://www.youtube.com/results?search_query=" + encodedSongName;
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select("ytInitialData");

            String[] videoUrls = new String[10]; // 최대 10개의 비디오 URL 저장
            int count = 0; // URL 개수 카운트

            for (Element script : doc.getElementsByTag("script")) {
                String scriptContent = script.html();
                if (scriptContent.contains("ytInitialData")) {
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
                        JSONObject item = (JSONObject) obj;
                        JSONObject videoRenderer = (JSONObject) item.get("videoRenderer");

                        if (videoRenderer != null && videoRenderer.containsKey("videoId")) {
                            String videoId = (String) videoRenderer.get("videoId");
                            String videoUrl = "https://www.youtube.com/watch?v=" + videoId;
                            videoUrls[count++] = videoUrl;

                            if (count >= 10) {
                                return videoUrls;
                            }
                        }
                    }
                    break;
                }
            }

            if (!elements.isEmpty()) {
                for (Element firstVideo : elements) {
                    String videoUrl = "https://www.youtube.com" + firstVideo.attr("href");
                    videoUrls[count++] = videoUrl;

                    if (count >= 10) {
                        return videoUrls;
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new project3();
            }
        });
    }
}

