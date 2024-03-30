package GUI.Download;

import GUI.Common.CompNames;
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
        setName(CompNames.downloadedListPanel_r);
//        setName("downloadedListPanel_r");
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
            contentPane.add(Box.createVerticalStrut(15));
        }

        scrollPane = new JScrollPane(contentPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel buttonSet = new JPanel();
        buttonSet.setLayout(new BorderLayout());

        add(waitingLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updatePanel(ArrayList<VideoDTO> dtos) {
        contentPane.removeAll();

//        System.out.println(" dtos의 길이는 다음과 같습니다: "  +dtos.size());
        for (VideoDTO dto : dtos) {
            if (dtos.size() == 1) {
//                System.out.println("셀이 하나인 경우 시작합니다.");
                DownloadedListCell cell = new DownloadedListCell(dto);
                contentPane.add(cell);
                JPanel dummy = new JPanel();
//                System.out.println("더미 패널이 생겼습니다.");
                dummy.setPreferredSize(new Dimension(200, 140));
                dummy.setOpaque(true);
//                dummy.setBackground(Color.black);
                contentPane.add(dummy);
//                System.out.println("더미 패널이 추가되었습니다.");
            } else {
//                System.out.println("셀 업데이트를 시작합니다.");
                DownloadedListCell cell = new DownloadedListCell(dto);
                contentPane.add(cell);
                // 셀간 간격
                contentPane.add(Box.createVerticalStrut(15));
            }
        }
        contentPane.revalidate();
        contentPane.repaint();

        //스크롤바 최상단 재설정
//        SwingUtilities.invokeLater(() -> {
//            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
//            verticalScrollBar.setValue(0);
//        });
    }
}
