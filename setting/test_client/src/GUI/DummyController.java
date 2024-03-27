package GUI;

import GUI.Common.DummyDTO;

import java.util.ArrayList;

public class DummyController {
    private static DummyController instance;
    private final ArrayList<DummyDTO> dtos = new ArrayList<>();

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
        dtos.add(new DummyDTO("https://img.youtube.com/vi/YwC0m0XaD2E/maxresdefault.jpg", "Uploader 1", 1, "A"));
        dtos.add(new DummyDTO("https://img.youtube.com/vi/YwC0m0XaD2E/maxresdefault.jpg", "Uploader 2", 2, "B"));
        dtos.add(new DummyDTO("https://img.youtube.com/vi/YwC0m0XaD2E/maxresdefault.jpg", "Uploader 3", 3, "C"));
        dtos.add(new DummyDTO("https://img.youtube.com/vi/YwC0m0XaD2E/maxresdefault.jpg", "Uploader 3", 4, "C"));
        dtos.add(new DummyDTO("https://img.youtube.com/vi/YwC0m0XaD2E/maxresdefault.jpg", "Uploader 3", 5, "C"));
    }

    public ArrayList<DummyDTO> getDtos() {
        return dtos;
    }
}
