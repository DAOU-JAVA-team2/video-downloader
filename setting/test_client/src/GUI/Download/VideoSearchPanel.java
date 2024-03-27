package GUI.Download;

import GUI.Common.DummyDTO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VideoSearchPanel extends JPanel {
    private final JScrollPane scrollPane;
    private final JPanel contentPane;

    public VideoSearchPanel(ArrayList<DummyDTO> dtos) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(550, 650));
        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(Box.createVerticalStrut(15));
        for (DummyDTO dto : dtos) {
            VideoInfoCell cell = new VideoInfoCell(dto);
            contentPane.add(cell);
            //셀간 간격
            contentPane.add(Box.createVerticalStrut(15));
        }

        scrollPane = new JScrollPane(contentPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);

//        revalidate();
//        repaint();
    }
}