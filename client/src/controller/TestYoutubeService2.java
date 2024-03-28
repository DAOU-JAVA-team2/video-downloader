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
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TestYoutubeService2 {
    private static volatile int downloadProgress = 0; // volatile 키워드 추가

    public static synchronized void incrementDownloadProgress() {
        downloadProgress++;
    }

    public static int getDownloadProgress() {
        return downloadProgress;
    }

    public static void resetDownloadProgress() {
        downloadProgress = 0;
    }

    public ArrayList<VideoDTO> searchYoutubeVideos(String name) {

        ArrayList<VideoDTO> videoList = new ArrayList<>();
        String songName = name;
        try {
            String url = "https://www.youtube.com/results?search_query=" + songName;
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select("script");

            int count = 0; // URL 개수 카운트

            for (Element script : elements) {
                String scriptContent = script.html();
                if (scriptContent.contains("ytInitialData")) {
                    int startIndex = scriptContent.indexOf("ytInitialData");
                    int endIndex = scriptContent.lastIndexOf("};") + 1;
                    String jsonData = scriptContent.substring(startIndex + 16, endIndex);
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
                            String title = ((JSONObject)  ((JSONArray) ((JSONObject)videoRenderer.get("title")).get("runs")).get(0)).get("text").toString();
                            String uploader = ((JSONObject) ((JSONArray) ((JSONObject) videoRenderer.get("ownerText")).get("runs")).get(0)).get("text").toString();

                            Long viewCount = Long.parseLong(((JSONObject) videoRenderer.get("viewCountText")).get("simpleText").toString().replaceAll("[^0-9]", ""));
                            String thumbnailUrl = "https://img.youtube.com/vi/" + videoId + "/0.jpg";;
                            String videoUrl = "https://www.youtube.com/watch?v=" + videoId;
                            VideoDTO videoInfo = new VideoDTO();
                            videoInfo.setTitle(title);
                            videoInfo.setViewCount(formatViewCount(viewCount));
                            videoInfo.setUploader(uploader);
                            videoInfo.setThumbnailUrl(thumbnailUrl);
                            videoInfo.setUrl(videoUrl);

                            videoList.add(videoInfo);
                            count++;

                            if (count >= 10) {
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return videoList;
    }

    private String formatViewCount(Long viewCount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedCount = decimalFormat.format(viewCount);
        return formattedCount + "회";
    }

    public boolean downloadWithYoutubeDL(String videoUrl) {
        try {
            // youtube-dlp의 전체 경로 지정
            String rootDirectory = System.getProperty("user.dir");
            String youtubeDLPPath = rootDirectory + "/yt-dlp";

            //TODO: 저장 경로
//            File downloadList = new File("C:\\Users\\김병준\\Desktop\\video-downloader\\client\\downloadList");
            File downloadList = new File(rootDirectory+"/downloadList");
//            File downloadList = new File(System.getProperty("user.dir") + "\\client\\downloadList");

            if (!downloadList.exists()) {
                downloadList.mkdirs(); // 디렉토리가 존재하지 않으면 생성합니다.
            }


            // youtube-dlp 실행 명령어와 옵션을 따로 분리하여 전달
            // 직접 URL을 사용하여 다운로드
            String[] command = {"cmd", "/c", youtubeDLPPath + ".exe", "-f", "bestvideo+bestaudio/best", "--merge-output-format", "mp4", "-o", "%(title)s.mp4", videoUrl};

            ProcessBuilder builder = new ProcessBuilder(command);

            builder.redirectErrorStream(true);

            builder.directory(downloadList);

            Process process = builder.start();
            // 이하 코드는 그대로 유지
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("이게 다운 속도인가?");
                downloadProgress++;
                System.out.println(line);
                System.out.println("서비스의 진행도: "+downloadProgress);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                downloadProgress = 0;
                return true;
            } else {
                downloadProgress = 0;
                return false;
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            downloadProgress = 0;
            return false;
        }
    }
}
