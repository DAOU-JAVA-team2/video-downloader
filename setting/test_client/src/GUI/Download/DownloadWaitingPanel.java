package GUI.Download;

import GUI.Common.DummyDTO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DownloadWaitingPanel extends JPanel {
    private final JLabel waitingLabel;
    private final JPanel contentPane;
    private final JScrollPane scrollPane;

    public DownloadWaitingPanel(ArrayList<DummyDTO> dtos) {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(600, 500));

        waitingLabel = new JLabel("다운로드 대기목록");

        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(600, 500));

        contentPane.add(Box.createVerticalStrut(15));

        for (DummyDTO dto : dtos) {
            DownloadWaitingCell cell = new DownloadWaitingCell(dto);
            contentPane.add(cell);
            //셀간 간격
            contentPane.add(Box.createVerticalStrut(15));
        }

        scrollPane = new JScrollPane(contentPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JLabel progressbar = new JLabel("프로그래스바아아ㅏ");
        JButton downloadButton = new JButton("다운로드 시자아아각ㄱ");

        add(waitingLabel);
        add(scrollPane, BorderLayout.CENTER);
    }
}
