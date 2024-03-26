package GUI.Download;

import GUI.Common.DummyDTO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class VideoInfoCell extends JPanel {
    private final JLabel imageLabel;
    private final JLabel titleLabel;
    private final JLabel viewCount;
    private final JLabel uploader;

    private final JButton addToDownloadButton;

    public VideoInfoCell(DummyDTO dto) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        //TODO 이미지

        imageLabel = new JLabel();
        try {
            URL url = new URL(dto.imageURL);
            System.out.println("URL 생성 완");
            Image image = ImageIO.read(url);
            Image scaledImage = image.getScaledInstance(270, 150, Image.SCALE_SMOOTH);



            System.out.println("이미지 리드 완");
            imageLabel.setIcon(new ImageIcon(scaledImage));
            System.out.println("아이콘 적용 완");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
        }
        add(Box.createHorizontalStrut(20));
        add(imageLabel);
        add(Box.createHorizontalStrut(90));

        // 정보
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        titleLabel = new JLabel("제목: " + dto.videoTitle);
        viewCount = new JLabel("조회수: " + dto.viewCount);
        uploader = new JLabel("업로더: " + dto.uploader);

        infoPanel.add(titleLabel);
        infoPanel.add(viewCount);
        infoPanel.add(uploader);
        infoPanel.add(Box.createVerticalStrut(70)); // 수직 간격

        // 재생 버튼

        addToDownloadButton = new JButton("다운 목록에 추가");
        addToDownloadButton.setPreferredSize(new Dimension(100, 30));
        infoPanel.add(addToDownloadButton);

        add(infoPanel);
//        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
    }
}

