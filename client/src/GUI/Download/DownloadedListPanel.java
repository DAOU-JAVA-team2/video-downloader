package GUI.Download;

import GUI.Common.CustomColors;
import dto.VideoDTO;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DownloadedListPanel extends JPanel {
    private final JLabel waitingLabel;
    private final JPanel contentPane;
    private final JScrollPane scrollPane;

    public DownloadedListPanel(ArrayList<VideoDTO> dtos) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(380, 265));
        waitingLabel = new JLabel("다운로드 보관함");
        waitingLabel.setFont(waitingLabel.getFont().deriveFont(16f));
        waitingLabel.setForeground(CustomColors.FONT_GRAY);
        waitingLabel.setOpaque(true);
        waitingLabel.setBackground(CustomColors.PANEL_BLUE);

        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(Box.createVerticalStrut(15));

        for (VideoDTO dto : dtos) {
            DownloadedListCell cell = new DownloadedListCell(dto);
            contentPane.add(cell);
            //셀간 간격
            contentPane.add(Box.createVerticalStrut(15));
        }

        scrollPane = new JScrollPane(contentPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel buttonSet = new JPanel();
        buttonSet.setLayout(new BorderLayout());

        add(waitingLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
