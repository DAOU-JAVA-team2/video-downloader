package service;

import dto.VideoDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CrawlService {
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
                            title = title.replaceAll("[/]","_");

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

//    public ArrayList<VideoDTO> searchYoutubeVideos2(String encodedSongName) {
//        ArrayList<VideoDTO> videoList = new ArrayList<>();
//
//        try {
//            String url = "https://www.youtube.com/results?search_query=" + encodedSongName;
//            Document doc = Jsoup.connect(url).get();
//            Elements elements = doc.select("script");
//
//            int count = 0; // URL 개수 카운트
//
//            for (Element script : elements) {
//                String scriptContent = script.html();
//                if (scriptContent.contains("ytInitialData")) {
//                    int startIndex = scriptContent.indexOf("ytInitialData");
//                    int endIndex = scriptContent.lastIndexOf("};") + 1;
//                    String jsonData = scriptContent.substring(startIndex + 16, endIndex);
//                    JSONParser parser = new JSONParser();
//                    JSONObject jsonObject = (JSONObject) parser.parse(jsonData);
//
//                    JSONArray contentsArray = (JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject.get("contents"))
//                            .get("twoColumnSearchResultsRenderer"))
//                            .get("primaryContents"))
//                            .get("sectionListRenderer"))
//                            .get("contents");
//
//                    for (Object content : contentsArray) {
//                        JSONObject contentObject = (JSONObject) content;
//                        if (contentObject.containsKey("itemSectionRenderer")) {
//                            JSONArray itemArray = (JSONArray) ((JSONObject)contentObject.get("itemSectionRenderer")).get("contents");
//                            for (Object obj : itemArray) {
//                                JSONObject item = (JSONObject) obj;
//                                JSONObject videoRenderer = (JSONObject) item.get("videoRenderer");
//
//                                if (videoRenderer != null && videoRenderer.containsKey("videoId")) {
//                                    String videoId = (String) videoRenderer.get("videoId");
//                                    String title = ((JSONObject) ((JSONArray) ((JSONObject) videoRenderer.get("title")).get("runs")).get(0)).get("text").toString();
//                                    String uploader = ((JSONObject) ((JSONArray) ((JSONObject) videoRenderer.get("ownerText")).get("runs")).get(0)).get("text").toString();
//
//                                    Long viewCount = Long.parseLong(((JSONObject) videoRenderer.get("viewCountText")).get("simpleText").toString().replaceAll("[^0-9]", ""));
//                                    String thumbnailUrl = "https://img.youtube.com/vi/" + videoId + "/0.jpg";
//                                    String videoUrl = "https://www.youtube.com/watch?v=" + videoId;
//                                    VideoDTO videoInfo = new VideoDTO();
//                                    title = title.replaceAll("[/]","_");
//
//                                    videoInfo.setTitle(title);
//                                    videoInfo.setViewCount(formatViewCount(viewCount));
//                                    videoInfo.setUploader(uploader);
//                                    videoInfo.setThumbnailUrl(thumbnailUrl);
//                                    videoInfo.setUrl(videoUrl);
//
//                                    videoList.add(videoInfo);
//                                    count++;
//
//                                    if (count >= 10) {
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                        if (count >= 10) {
//                            break;
//                        }
//                    }
//                    break;
//                }
//            }
//        } catch (IOException | ParseException e) {
//            e.printStackTrace();
//        }
//        return videoList;
//    }







    private String formatViewCount(Long viewCount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedCount = decimalFormat.format(viewCount);
        return formattedCount + "회";
    }

    public boolean downloadWithYoutubeDL(VideoDTO dto) {
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
//            String[] command = {
//                    "cmd", "/c", youtubeDLPPath + ".exe", "-f", "bestvideo+bestaudio/best", "--merge-output-format", "mp4", "-o", dto.getTitle(), dto.getUrl()};
//                                                                                                        //            yt-dlp -o 'funny_cats.%(ext)s' 동영상_링크

//            String title = dto.getTitle().replaceAll("[/]", "_"); // 유효하지 않은 문자를 '_'로 대체
            String[] command = {"cmd", "/c", youtubeDLPPath + ".exe", "-f", "bestvideo+bestaudio/best", "--merge-output-format", "mp4", "-o", dto.getTitle() + ".mp4", dto.getUrl()};

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
                System.out.println("서비스의 진행도: "+ downloadProgress);
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


//    public static void playDownloadedVideo(VideoDTO dto) {
//        // 다운로드된 영상 파일의 경로 설정
//        String name = dto.getTitle();
//        System.out.println("타이틀: "+name);
//
//        String rootDirectory = System.getProperty("user.dir");
//        String filePath = "\""+rootDirectory + File.pathSeparator + "downloadList" + File.separator + name+".mp4" +"\"";
////        String filePath = rootDirectory+"\\downloadList\\"+name+".mp4";
//        System.out.println("파일 경로: "+filePath);
////        File file = new File(filePath);
////        Desktop desktop = Desktop.getDesktop();
////        try {
////            desktop.open(file);
////
////        }catch (Exception e) {
////            System.out.println(e.getLocalizedMessage());
////            e.printStackTrace();
////        }
//
//        try {
////            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "start", filePath);
//
////            ProcessBuilder builder = new ProcessBuilder(filePath);
////            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "start", filePath);
//            ProcessBuilder builder = new ProcessBuilder(filePath);
//            builder.start();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }

    public static void playDownloadedVideo(VideoDTO dto) {
        String name = dto.getTitle();
        System.out.println("타이틀: " + name);

        String rootDirectory = System.getProperty("user.dir");
//        String filePath = "\"" + rootDirectory + File.separator + "downloadList" + File.separator + name + ".mp4" +"\"";
        String filePath = new StringBuilder().append(rootDirectory).append(File.separator).append("downloadList").append(File.separator).append(name).append(".mp4").toString();

        try {
            File videoFile = new File(filePath);
            if (!videoFile.exists()) {
                System.err.println("파일을 찾을 수 없습니다: " + filePath);
                return; // 파일이 존재하지 않으면 종료
            }

            Desktop.getDesktop().open(videoFile); // 파일 열기
            System.out.println("되는 파일: "+ filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

//    downloadButton.addActionListener(new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            downloadVideo();
//        }
//    });

}
//C:\Users\김병준\Desktop\video-downloader\client\downloadList\아이유(IU) - Love wins all [가사/Lyrics].mp4
//"C:\Users\김병준\Desktop\video-downloader\client\downloadList\아이유(IU) - Love wins all [가사⧸Lyrics].mp4"

//"C:\Users\김병준\Desktop\video-downloader\client\downloadList\ITZY ＂마.피.아. In the morning＂ M⧸V @ITZY.mp4"
//"C:\Users\김병준\Desktop\video-downloader\client\downloadList\ITZY(있지) "마.피.아. In the morning" M/V @ITZY.mp4"

//"C:\Users\김병준\Desktop\video-downloader\client\downloadList\STAYC(스테이씨) 'Bubble' MV.mp4"

//"C:\Users\김병준\Desktop\video-downloader\client\downloadList\STAYC(스테이씨) 'Bubble' MV.mp4"