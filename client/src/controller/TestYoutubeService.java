package controller;

import dto.VideoDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TestYoutubeService {

    public ArrayList<VideoDTO> searchAndDisplayResults(String name) {
//        String songName = songNameField.getText();
        String songName = name;
        //TODO: 초기화 로직
//        resultArea.setText(""); // 이전 결과 지우기
//        videoPanel.removeAll(); // 이전 영상 정보 패널 지우기
         ArrayList<VideoDTO> videoSearchList = new ArrayList<>();

        try {
            //tf String encoding 후 searchYoutubeVideos로 String 배열 받아옴

            String encodedSongName = URLEncoder.encode(songName, "UTF-8");
            String[] videoUrls = searchYoutubeVideos(encodedSongName);

            if (videoUrls != null) {
                //각 url마다 반복
                for (String videoUrl : videoUrls) {
                    // 동영상 정보 json으로 가져오기
                    String videoInfoUrl = "https://www.youtube.com/oembed?url=" + videoUrl + "&format=json";
                    Document videoInfoDoc = Jsoup.connect(videoInfoUrl).ignoreContentType(true).get();
                    String videoInfoJson = videoInfoDoc.body().text();
                    JSONParser parser = new JSONParser();
                    JSONObject videoInfoObject = (JSONObject) parser.parse(videoInfoJson);
                    // 영상 제목
                    String title = (String) videoInfoObject.get("title");
                    // 조회수
                    Long viewCount = getViewCount(videoUrl);
                    String formattedViewCount = formatViewCount(viewCount);
                    // 업로드한 사람
                    String uploader = (String) videoInfoObject.get("author_name");
                    // videoUrl에서 VIDEO_ID 추출
                    String videoId = videoUrl.substring(videoUrl.indexOf("v=") + 2);
                    // 썸네일 이미지 URL 조합
                    String thumbnailUrl = "https://img.youtube.com/vi/" + videoId + "/0.jpg";

                    // 영상 정보와 썸네일 이미지를 VideoInfo 객체로 묶음
                    VideoDTO videoInfo = new VideoDTO();
                    videoInfo.setTitle(title);
                    videoInfo.setViewCount(formattedViewCount);
                    videoInfo.setUploader(uploader);
                    videoInfo.setThumbnailUrl(thumbnailUrl);
                    videoInfo.setUrl(videoUrl);

                    videoSearchList.add(videoInfo);
                    //Video DTO 배열을 리턴하도록 수정
                    //이후 순회하면서 createVideoPanel

                    // VideoInfo 객체를 사용하여 영상 정보 패널 생성
//                    createVideoPanel(videoInfo);
                }
            } else {
//                resultArea.append("해당 곡을 찾을 수 없습니다.");
            }


            // 패널 갱신
//            videoPanel.revalidate();
//            videoPanel.repaint();

        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "곡 이름을 인코딩하는 중 오류가 발생했습니다.");
        } catch (IOException | ParseException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return videoSearchList;

    }





    public static long getViewCount(String youtubeUrl) throws IOException {
        Document doc = Jsoup.connect(youtubeUrl).get();

        // 유튜브의 조회수는 <meta> 태그에 og:description 속성으로 들어가 있음
        Element metaTag = doc.select("meta[itemprop=interactionCount]").first();
        if (metaTag != null) {
            String viewCountText = metaTag.attr("content");
            return Long.parseLong(viewCountText);
        } else {
            throw new IOException("조회수를 찾을 수 없습니다.");
        }
    }

    public static String formatViewCount(long viewCount) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(viewCount);
    }

    // VideoInfo 객체를 사용하여 영상 정보 패널을 생성하는 메서드
    private void createVideoPanel(VideoDTO videoInfo) {
        JPanel videoInfoPanel = new JPanel(new BorderLayout());

        // 썸네일 이미지를 표시하기 위해 JLabel을 생성하여 패널에 추가
        JLabel thumbnailLabel = new JLabel(new ImageIcon(getResizedImage(videoInfo.getThumbnailUrl(), 120, 90)));
        videoInfoPanel.add(thumbnailLabel, BorderLayout.WEST);

        // 영상 정보를 텍스트 영역에 표시하기 위해 JTextArea를 생성하여 패널에 추가
        JTextArea textArea = new JTextArea();
        textArea.append("Title: " + videoInfo.getTitle() + "\n");
        textArea.append("View Count: " + videoInfo.getViewCount() + "\n");
        textArea.append("Uploader: " + videoInfo.getUploader() + "\n");
        textArea.append("Video URL: " + videoInfo.getUrl() + "\n");
        textArea.setEditable(false); // 수정 불가능하도록 설정
        videoInfoPanel.add(textArea, BorderLayout.CENTER);

        // 다운로드 버튼 생성 및 패널에 추가
        JButton downloadButton = new JButton("다운로드");
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downloadWithYoutubeDL(videoInfo.getUrl()); // 다운로드 메서드 호출
            }
        });
        videoInfoPanel.add(downloadButton, BorderLayout.EAST); // EAST로 배치

        // 영상 정보 패널을 전체 패널에 추가
//        videoPanel.add(videoInfoPanel);
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

    private boolean downloadWithYoutubeDL(String videoUrl) {
        try {
            // youtube-dlp의 전체 경로 지정
            String rootDirectory = System.getProperty("user.dir");
            String youtubeDLPPath = rootDirectory + "/yt-dlp";
            // youtube-dlp 실행 명령어와 옵션을 따로 분리하여 전달
            // 직접 URL을 사용하여 다운로드
            String[] command = {"cmd", "/c", youtubeDLPPath + ".exe", "-f", "bestvideo+bestaudio/best", "--merge-output-format", "mp4", "-o", "%(title)s.mp4", videoUrl};

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
                return true;
            } else {
                return false;
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            return false;
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

}
