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
//        contentPane.setBackground(Color.cyan);
        for (VideoDTO dto : dtos) {
            VideoInfoCell cell = new VideoInfoCell(dto);
            contentPane.add(cell);
            //셀간 간격
            contentPane.add(Box.createVerticalStrut(15));
        }

        scrollPane = new JScrollPane(contentPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setBackground(Color.BLUE);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void updatePanel(ArrayList<VideoDTO> dtos) {
        contentPane.removeAll(); // 이전에 추가된 모든 컴포넌트 제거

        for (VideoDTO dto : dtos) {
            VideoInfoCell cell = new VideoInfoCell(dto);
            contentPane.add(cell);
            // 셀간 간격
            contentPane.add(Box.createVerticalStrut(15));
        }

        contentPane.revalidate(); // 패널을 다시 그리기 위해 호출
        contentPane.repaint();

        //스크롤바 최상단 재설정
        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(0);
        });
    }
}