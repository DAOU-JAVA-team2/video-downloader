package GUI.Download;

import GUI.Common.CompNames;
import GUI.Common.CustomColors;
import controller.ViewController;
import dto.VideoDTO;
import service.CrawlService;

import javax.swing.*;
import java.awt.*;
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
        setName(CompNames.downloadWaitingPanel_r);

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
            contentPane.add(Box.createVerticalStrut(15));
        }

        scrollPane = new JScrollPane(contentPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel buttonSet = new JPanel();
        buttonSet.setLayout(new BorderLayout());

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setForeground(CustomColors.PROGRESS_GREEN);
        progressBar.setName("progressbar_r");

        downloadButton = new JButton("다운로드 시작");
        downloadButton.setName(CompNames.downloadButton_r);
        downloadButton.addActionListener(
                e -> progressbarUpdate()
        );

        buttonSet.add(progressBar, BorderLayout.NORTH);
        buttonSet.add(downloadButton, BorderLayout.SOUTH);

        add(waitingLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonSet, BorderLayout.SOUTH);
    }

    public void updatePanel(LinkedList<VideoDTO> dtos) {
        contentPane.removeAll();
//        System.out.println(" dtos의 길이는 다음과 같습니다: "  +dtos.size());
        for (VideoDTO dto : dtos) {
            if (dtos.size() == 1) {
//                System.out.println("셀이 하나인 경우 시작합니다.");
                DownloadWaitingCell cell = new DownloadWaitingCell(dto);
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
                DownloadWaitingCell cell = new DownloadWaitingCell(dto);
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


//TODO: 프로그레스바 나중에 수정하자 ㅎ
//    private void progressbarUpdate() {
//        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
//            @Override
//            protected Void doInBackground() throws Exception {
//                progressBar.setValue(0);
//
//                while (progressBar.getValue() < 100) {
//                    int progress = TestYoutubeService2.getDownloadProgress();
//                    System.out.println("진행도::::::" + progress);
//                    publish(progress);
//                    Thread.sleep(50);
//                }
//                return null;
//            }
//
//            @Override
//            protected void process(java.util.List<Integer> chunks) {
//                for (int value : chunks) {
//                    progressBar.setValue(value);
//                }
//            }
//
//            @Override
//            protected void done() {
//                progressBar.setValue(100);
//            }
//        };
//        worker.execute();
//    }


    private void progressbarUpdate() {
        SwingUtilities.invokeLater(() -> {
            progressBar.setValue(0);
            Thread workerThread = new Thread(() -> {
                int buffer = 0;

                while (!ViewController.downloadWaitingList.isEmpty()) {
                    int progress = CrawlService.getDownloadProgress();
                    progressBar.setValue(progress);

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                progressBar.setValue(100);
            });

            workerThread.start();
        });
    }
}
