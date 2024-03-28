package GUI.Download;

import GUI.Common.CustomColors;
import dto.VideoDTO;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class DownloadWaitingPanel extends JPanel {
    private final JLabel waitingLabel;
    private final JPanel contentPane;
    private final JScrollPane scrollPane;
    private final JProgressBar progressBar;
    private final JButton downloadButton;

    public DownloadWaitingPanel(LinkedList<VideoDTO> dtos) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(380, 265));
        setName(DownloadCompNames.downloadWaitingPanel_r);

        waitingLabel = new JLabel("다운로드 대기목록");
        waitingLabel.setFont(waitingLabel.getFont().deriveFont(16f));
        waitingLabel.setForeground(CustomColors.FONT_GRAY);
        waitingLabel.setOpaque(true);
        waitingLabel.setBackground(CustomColors.PANEL_BLUE);

        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(Box.createVerticalGlue());
        contentPane.add(Box.createVerticalStrut(15));


        for (VideoDTO dto : dtos) {
            DownloadWaitingCell cell = new DownloadWaitingCell(dto);
            contentPane.add(cell);
            // 셀간 간격
            contentPane.add(Box.createVerticalStrut(15));
        }

        scrollPane = new JScrollPane(contentPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel buttonSet = new JPanel();
        buttonSet.setLayout(new BorderLayout());

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setForeground(CustomColors.PROGRESS_GREEN);

        downloadButton = new JButton("다운로드 시작");
        buttonSet.add(progressBar, BorderLayout.NORTH);
        buttonSet.add(downloadButton, BorderLayout.SOUTH);
        downloadButton.addActionListener(e -> startDownload());

        add(waitingLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonSet, BorderLayout.SOUTH);
    }

    public void updatePanel(LinkedList<VideoDTO> dtos) {
        contentPane.removeAll(); // 이전에 추가된 모든 컴포넌트 제거
        System.out.println(" dtos의 길이는 다음과 같습니다: "  +dtos.size());
        for (VideoDTO dto : dtos) {
            if (dtos.size() == 1) {
                System.out.println("셀이 하나인 경우 시작합니다.");
                DownloadWaitingCell cell = new DownloadWaitingCell(dto);
                contentPane.add(cell);
                JPanel dummy = new JPanel();
                System.out.println("더미 패널이 생겼습니다.");
                dummy.setPreferredSize(new Dimension(200,140));
                dummy.setOpaque(true);
//                dummy.setBackground(Color.black);
                contentPane.add(dummy);
                System.out.println("더미 패널이 추가되었습니다.");
            }else{
                System.out.println("셀 업데이트를 시작합니다.");
                DownloadWaitingCell cell = new DownloadWaitingCell(dto);
                contentPane.add(cell);
                // 셀간 간격
                contentPane.add(Box.createVerticalStrut(15));
            }
        }

        contentPane.revalidate(); // 패널을 다시 그리기 위해 호출
        contentPane.repaint();

        //스크롤바 최상단 재설정
//        SwingUtilities.invokeLater(() -> {
//            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
//            verticalScrollBar.setValue(0);
//        });
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
