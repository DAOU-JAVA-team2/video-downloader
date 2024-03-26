package GUI.Download;

import GUI.Common.DummyDTO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DownloadWaitingPanel extends JPanel {
    private final JLabel waitingLabel;
    private final JPanel contentPane;
    private final JScrollPane scrollPane;
    private final JProgressBar progressBar;
    private final JButton downloadButton;

    public DownloadWaitingPanel(ArrayList<DummyDTO> dtos) {
//        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(380, 265));

        waitingLabel = new JLabel("다운로드 대기목록");
        waitingLabel.setFont(waitingLabel.getFont().deriveFont(16f));
        waitingLabel.setForeground(new Color(70, 75, 75));
        waitingLabel.setOpaque(true);
        waitingLabel.setBackground(new Color(220, 230, 236));

        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(Box.createVerticalStrut(15));
        for (DummyDTO dto : dtos) {
            DownloadWaitingCell cell = new DownloadWaitingCell(dto);
            contentPane.add(cell);
            //셀간 간격
            contentPane.add(Box.createVerticalStrut(15));
        }

        scrollPane = new JScrollPane(contentPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel buttonSet = new JPanel();
        buttonSet.setLayout(new BorderLayout());

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(136, 222, 91, 255));

        downloadButton = new JButton("다운로드 시작");
        buttonSet.add(progressBar,BorderLayout.NORTH);
        buttonSet.add(downloadButton,BorderLayout.SOUTH);
        downloadButton.addActionListener(e -> startDownload());

        add(waitingLabel,BorderLayout.NORTH);
        add(scrollPane,BorderLayout.CENTER);
        add(buttonSet,BorderLayout.SOUTH);
    }


    private void startDownload() {
        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                // 다운로드 시작 전에 진행 상황을 0%로 초기화
                progressBar.setValue(0);

                // 다운로드 진행 상황을 업데이트하며 다운로드 작업을 수행
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(50); // 다운로드 작업 대신 시간 대기 (예시용)
                    publish(i); // 진행 상황을 업데이트하여 프로그래스바에 전달
                }

                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                // 다운로드 진행 상황에 따라 프로그래스바를 업데이트
                for (int value : chunks) {
                    progressBar.setValue(value);
                }
            }

            @Override
            protected void done() {
                // 다운로드가 완료되면 프로그래스바를 100%로 설정
                progressBar.setValue(100);
            }
        };

        // SwingWorker 실행
        worker.execute();
    }

}
