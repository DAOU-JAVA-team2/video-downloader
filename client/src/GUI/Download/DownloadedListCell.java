package GUI.Download;

import GUI.Common.DummyDTO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class DownloadedListCell extends JPanel {
    private final JLabel imageLabel;
    private final JLabel titleLabel;
    private final JLabel viewCount;
    private final JLabel uploader;

    private final JButton addToDownloadButton;

    public DownloadedListCell(DummyDTO dto) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        //TODO 이미지
        imageLabel = new JLabel();
        try {
            URL url = new URL(dto.imageURL);
            Image image = ImageIO.read(url);
            Image scaledImage = image.getScaledInstance(180, 100, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
        }

        add(Box.createHorizontalStrut(20));
        add(imageLabel);
        add(Box.createHorizontalStrut(150));
        // 정보
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        titleLabel = new JLabel("제목: " + dto.videoTitle);
        viewCount = new JLabel("조회수: " + dto.viewCount);
        uploader = new JLabel("업로더: " + dto.uploader);

        infoPanel.add(titleLabel);
        infoPanel.add(viewCount);
        infoPanel.add(uploader);
        infoPanel.add(Box.createVerticalStrut(20)); // 수직 간격

        // 재생 버튼
        addToDownloadButton = new JButton("재생");
        addToDownloadButton.setPreferredSize(new Dimension(100, 30));

        infoPanel.add(addToDownloadButton);
        add(infoPanel);
    }
}
