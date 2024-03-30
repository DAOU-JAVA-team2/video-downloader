package GUI.Download;


import dto.VideoDTO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VideoSearchPanel extends JPanel {
    private final JScrollPane scrollPane;
    private final JPanel contentPane;

    public VideoSearchPanel(ArrayList<VideoDTO> dtos) {
        setName("videoSearchPanel_l");
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(550, 650));

        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(Box.createVerticalStrut(15));

        for (VideoDTO dto : dtos) {
            VideoInfoCell cell = new VideoInfoCell(dto);
            contentPane.add(cell);
            contentPane.add(Box.createVerticalStrut(15));
        }

        scrollPane = new JScrollPane(contentPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void updatePanel(ArrayList<VideoDTO> dtos) {
        contentPane.removeAll();

        for (VideoDTO dto : dtos) {
            VideoInfoCell cell = new VideoInfoCell(dto);
            contentPane.add(cell);
            contentPane.add(Box.createVerticalStrut(15));
        }

        contentPane.revalidate();
        contentPane.repaint();

        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(0);
        });
    }
}