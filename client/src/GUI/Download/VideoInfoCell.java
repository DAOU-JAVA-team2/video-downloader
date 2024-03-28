package GUI.Download;


import GUI.Common.CustomColors;
import controller.ViewController;
import dto.VideoDTO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class VideoInfoCell extends JPanel {
    private final JLabel imageLabel;
//    private final JLabel titleLabel;
    private final JTextArea titleArea;
    private final JLabel viewCount;
    private final JLabel uploader;
    private final JButton addToDownloadButton;

    private final VideoDTO dto;

    public VideoInfoCell(VideoDTO dto) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//        setBackground(Color.yellow);
        this.dto = dto;

        //TODO 이미지
        imageLabel = new JLabel();
        try {
            URL url = new URL(dto.getThumbnailUrl());
            Image image = ImageIO.read(url);
            Image scaledImage = image.getScaledInstance(200, 130, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
        }
        add(Box.createHorizontalStrut(20));
        add(imageLabel);
        add(Box.createHorizontalStrut(60));
        // 정보
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
//        titleLabel = new JLabel("제목: " + dto.getTitle());

        titleArea = new JTextArea(dto.getTitle());
        titleArea.setBackground(CustomColors.DEFAULT_GRAY);
        titleArea.setLineWrap(true);
        titleArea.setWrapStyleWord(true);
        titleArea.setEditable(false);
        titleArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleArea.setFont(new Font("맑은 고딕",Font.BOLD,12));

        viewCount = new JLabel("조회수: " + dto.getViewCount());
        viewCount.setAlignmentX(Component.LEFT_ALIGNMENT);
        viewCount.setForeground(Color.gray);

        uploader = new JLabel("업로더: " + dto.getUploader());
        uploader.setAlignmentX(Component.LEFT_ALIGNMENT);
        uploader.setForeground(Color.gray);

        infoPanel.add(titleArea);
        infoPanel.add(viewCount);
        infoPanel.add(uploader);
        infoPanel.add(Box.createVerticalStrut(30)); // 수직 간격

        // 재생 버튼
        addToDownloadButton = new JButton("다운 목록에 추가");
        addToDownloadButton.setName("addToDownloadButton_l");
        addToDownloadButton.setPreferredSize(new Dimension(100, 30));
        addToDownloadButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(addToDownloadButton);

        addToDownloadButton.addActionListener(e -> {
            ViewController.downloadWaitingList.add(dto);
            System.out.println("다운로드 대기리스트에 dto가 추가되었습니다.");
            ViewController.updateDownloadWaitingPanel();
        });

        add(infoPanel);
    }
}

