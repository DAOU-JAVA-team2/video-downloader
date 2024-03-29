package GUI.Download;

import GUI.Common.CompNames;
import GUI.Common.CustomColors;
import dto.VideoDTO;
import service.CrawlService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class DownloadedListCell extends JPanel {
    private final JLabel imageLabel;
    private final JTextArea titleArea;
    private final JLabel viewCount;
    private final JLabel uploader;
    private final JButton playButton;
    private final VideoDTO dto;

    public DownloadedListCell(VideoDTO dto) {
        this.dto = dto;
        setName(CompNames.downloadedListCell_r);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(new Dimension(200, 120));

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

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        titleArea = new JTextArea(dto.getTitle());
        titleArea.setBackground(CustomColors.DEFAULT_GRAY);
        titleArea.setLineWrap(true);
        titleArea.setWrapStyleWord(true);
        titleArea.setEditable(false);
        titleArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleArea.setFont(new Font(titleArea.getFont().getName(), Font.BOLD, 12));

        viewCount = new JLabel("조회수: " + dto.getViewCount());
        viewCount.setFont(viewCount.getFont().deriveFont(10f));
        viewCount.setForeground(Color.gray);

        uploader = new JLabel("업로더: " + dto.getUploader());
        uploader.setFont(uploader.getFont().deriveFont(10f));
        uploader.setForeground(Color.gray);

        infoPanel.add(titleArea);

        infoPanel.add(viewCount);
        infoPanel.add(uploader);
        infoPanel.add(Box.createVerticalStrut(20)); // 수직 간격

        playButton = new JButton("재생");
        playButton.setPreferredSize(new Dimension(100, 30));
        playButton.setName(CompNames.playButton_r);
        playButton.addActionListener(e -> {
            CrawlService.playDownloadedVideo(dto);
        });

        infoPanel.add(playButton);
        add(infoPanel);
    }

    public VideoDTO getDto() {
        return this.dto;
    }
}
