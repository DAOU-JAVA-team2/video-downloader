package GUI;



import dto.VideoDTO;

import java.util.ArrayList;

public class DummyController {
    private static DummyController instance;
    private ArrayList<VideoDTO> dtos = new ArrayList<>();

    private DummyController() {
        initDtos();
    }

    public static DummyController getInstance() {
        if (instance == null) {
            instance = new DummyController();
        }
        return instance;
    }

    private void initDtos() {
//        VideoDTO test1 = new VideoDTO();
//        test1.setThumbnailUrl();
//        test1.setTitle();
//        test1.setUploader();
//        test1.setViewCount();
//        test1.setVideo_id();
//        test1.set
//        dtos.add(new VideoDTO("https://img.youtube.com/vi/YwC0m0XaD2E/maxresdefault.jpg", "Uploader 1", 1, "A"));
//        dtos.add(new VideoDTO("https://img.youtube.com/vi/YwC0m0XaD2E/maxresdefault.jpg", "Uploader 2", 2, "B"));
//        dtos.add(new VideoDTO("https://img.youtube.com/vi/YwC0m0XaD2E/maxresdefault.jpg", "Uploader 3", 3, "C"));
//        dtos.add(new VideoDTO("https://img.youtube.com/vi/YwC0m0XaD2E/maxresdefault.jpg", "Uploader 3", 4, "C"));
//        dtos.add(new VideoDTO("https://img.youtube.com/vi/YwC0m0XaD2E/maxresdefault.jpg", "Uploader 3", 5, "C"));
    }

    public ArrayList<VideoDTO> getDtos() {
        return dtos;
    }
}