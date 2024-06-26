package GUI.Download;


import GUI.Common.CustomColors;
import controller.ViewController;
import dto.VideoDTO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class DownloadWaitingCell extends JPanel {
    private final JLabel imageLabel;
    private final JTextArea titleArea;
    private final JLabel viewCount;
    private final JLabel uploader;
    private final JButton waitingDeleteButton;

    public DownloadWaitingCell(VideoDTO dto) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(new Dimension(200, 120));
        //TODO 이미지
        imageLabel = new JLabel();

        try {
            URL url = new URL(dto.getThumbnailUrl());
            Image image = ImageIO.read(url);
            Image scaledImage = image.getScaledInstance(160, 120, Image.SCALE_DEFAULT);

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
        titleArea.setBackground(CustomColors.DEFAULT_GRAY);
        titleArea.setLineWrap(true);
        titleArea.setWrapStyleWord(true);
        titleArea.setEditable(false);
        titleArea.setAlignmentX(Component.LEFT_ALIGNMENT);
//        titleArea.setFont(titleArea.getFont().deriveFont(10f));
        titleArea.setFont(new Font(titleArea.getFont().getName(), Font.BOLD, 12));

//        titleArea.setFont(new Font("맑은 고딕",Font.BOLD,12));


        viewCount = new JLabel("조회수: " + dto.getViewCount());
        viewCount.setFont(viewCount.getFont().deriveFont(10f));
        viewCount.setForeground(Color.gray);

        uploader = new JLabel("업로더: " + dto.getUploader());
        uploader.setFont(uploader.getFont().deriveFont(10f));
        uploader.setForeground(Color.gray);


        infoPanel.add(titleArea);
        infoPanel.add(viewCount);
        infoPanel.add(uploader);
        infoPanel.add(Box.createVerticalStrut(10)); // 수직 간격

        waitingDeleteButton = new JButton("삭제");
        waitingDeleteButton.setPreferredSize(new Dimension(60, 30));
        infoPanel.add(waitingDeleteButton);

        waitingDeleteButton.addActionListener(e -> {
            ViewController.downloadWaitingList.remove(dto);
//            System.out.println("dto가 제거되었습니다");
            ViewController.updateDownloadWaitingPanel();
        });

        add(infoPanel);
    }
}
