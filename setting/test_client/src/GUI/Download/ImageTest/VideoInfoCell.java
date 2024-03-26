package GUI.Download.ImageTest;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class VideoInfoCell extends JPanel {
    private JLabel imageLabel;
    private JLabel titleLabel;
    private JLabel viewCount;
    private JLabel uploader;

    private JButton playButton;

    public VideoInfoCell(DummyDTO dto) {
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        //TODO 이미지
        imageLabel = new JLabel();
        try {
            URL url = new URL(dto.imageURL);
            System.out.println("URL 생성 완");
            Image image = ImageIO.read(url);
            Image scaledImage = image.getScaledInstance(200,150,Image.SCALE_SMOOTH);
            System.out.println("이미지 리드 완");
            imageLabel.setIcon(new ImageIcon(scaledImage));
            System.out.println("아이콘 적용 완");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
        }

        add(imageLabel);
        add(Box.createHorizontalStrut(50));

        // 정보
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        titleLabel = new JLabel("Title: " + dto.videoTitle);
        viewCount = new JLabel("viewCount: " + dto.viewCount);
        uploader = new JLabel("uploader: " + dto.uploader);

        infoPanel.add(titleLabel);
        infoPanel.add(viewCount);
        infoPanel.add(uploader);
        infoPanel.add(Box.createVerticalStrut(70)); // 수직 간격

        // 재생 버튼
        playButton = new JButton("Play");
        infoPanel.add(playButton);

        add(infoPanel);
    }





    public static void main(String[] args) {
        // 예시를 위한 프레임 생성
        JFrame frame = new JFrame("Video Info Cell Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 150);

        VideoInfoCell videoCell = new VideoInfoCell(
                new DummyDTO("https://img.youtube.com/vi/YwC0m0XaD2E/maxresdefault.jpg",
                        "침투부",100,"침착맨"));
        frame.add(videoCell);

        frame.setVisible(true);
    }
}

