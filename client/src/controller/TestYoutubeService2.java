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

public class TestYoutubeService2 {
//    public void searchAndDisplayResults() {
//        String songName = songNameField.getText();
//        resultArea.setText(""); // 이전 결과 지우기
//        videoPanel.removeAll(); // 이전 영상 정보 패널 지우기
//
//        try {
//            String encodedSongName = URLEncoder.encode(songName, "UTF-8");
//            List<VideoDTO> videoLists = searchYoutubeVideos(encodedSongName);
//
//            if (videoLists != null) {
//                for (VideoDTO videoInfo : videoLists) {
//                    createVideoPanel(videoInfo);
//                }
//            } else {
//                resultArea.append("해당 곡을 찾을 수 없습니다.");
//            }
//
//            // 패널 갱신
//            videoPanel.revalidate();
//            videoPanel.repaint();
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "곡 이름을 인코딩하는 중 오류가 발생했습니다.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

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

}
