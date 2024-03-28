package GUI.Download;


import dto.VideoDTO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class DownloadWaitingCell extends JPanel {
    private final JLabel imageLabel;
//    private final JLabel titleLabel;
    private final JTextArea titleArea;

    private final JLabel viewCount;
    private final JLabel uploader;
    private final JButton addToDownloadButton;

    public DownloadWaitingCell(VideoDTO dto) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        //TODO 이미지
        imageLabel = new JLabel();
        try {
            URL url = new URL(dto.getThumbnailUrl());

            Image image = ImageIO.read(url);
            Image scaledImage = image.getScaledInstance(160, 100, Image.SCALE_SMOOTH);

            imageLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        add(Box.createHorizontalStrut(20));
        add(imageLabel);
        add(Box.createHorizontalStrut(60));
        // 정보
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        titleArea = new JTextArea(dto.getTitle());

        titleArea.setBackground(Color.green);
        titleArea.setLineWrap(true);
        titleArea.setWrapStyleWord(true);
        titleArea.setEditable(false);
        titleArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleArea.setFont(titleArea.getFont().deriveFont(12f));

        viewCount = new JLabel("조회수: " + dto.getViewCount());
        uploader = new JLabel("업로더: " + dto.getUploader());

        infoPanel.add(titleArea);
        infoPanel.add(viewCount);
        infoPanel.add(uploader);
        infoPanel.add(Box.createVerticalStrut(20)); // 수직 간격

        // 재생 버튼
        addToDownloadButton = new JButton("삭제");
        addToDownloadButton.setPreferredSize(new Dimension(60, 30));
        infoPanel.add(addToDownloadButton);

        add(infoPanel);
    }
}
