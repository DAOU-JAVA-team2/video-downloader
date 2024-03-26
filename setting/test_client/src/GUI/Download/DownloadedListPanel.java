package GUI.Download;

import GUI.Common.DummyDTO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DownloadedListPanel extends JPanel {
    private final JLabel waitingLabel;
    private final JPanel contentPane;
    private final JScrollPane scrollPane;

    public DownloadedListPanel(ArrayList<DummyDTO> dtos) {
//        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(380, 265));
        waitingLabel = new JLabel("다운로드 보관함");
        waitingLabel.setFont(waitingLabel.getFont().deriveFont(16f));
        waitingLabel.setForeground(new Color(70, 75, 75));
        waitingLabel.setOpaque(true);
        waitingLabel.setBackground(new Color(220, 230, 236));

        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        for (DummyDTO dto : dtos) {
            DownloadedListCell cell = new DownloadedListCell(dto);
            contentPane.add(cell);
            //셀간 간격
            contentPane.add(Box.createVerticalStrut(15));
        }

        scrollPane = new JScrollPane(contentPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel buttonSet = new JPanel();
        buttonSet.setLayout(new BorderLayout());


        add(waitingLabel,BorderLayout.NORTH);
        add(scrollPane,BorderLayout.CENTER);

    }
}
