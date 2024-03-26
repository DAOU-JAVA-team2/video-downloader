package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewController extends JFrame {
    // settings
    public JTextField songNameField;
    public JTextArea resultArea; // 검색 결과를 출력할 텍스트 영역
    public JPanel videoPanel;
    public YtService ytService;

    public ViewController(){
        // service load
        ytService = new YtService(this);
        // 1. frame setting - 여기서 모든 뷰 넣기
        setTitle("Youtube 뮤비 다운로드");
        setSize(600, 400); // 크기 조정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 상단 입력 패널
        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel label = new JLabel("곡 이름:");
        songNameField = new JTextField(20); // 필드 크기 지정
        songNameField.setName("songNameField");
        JButton downloadButton = new JButton("검색");

        // 검색 버튼에 액션 리스너 추가
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ytService.searchAndDisplayResults(); // 검색 및 결과 출력 메서드 호출
            }
        });

        inputPanel.add(label);
        inputPanel.add(songNameField);
        inputPanel.add(downloadButton);

        // 결과 출력을 위한 텍스트 영역
        resultArea = new JTextArea();
        resultArea.setEditable(false); // 편집 불가능하도록 설정

        // 각 영상 정보와 썸네일 이미지를 표시할 패널
        videoPanel = new JPanel();
        videoPanel.setLayout(new GridLayout(0, 1)); // 수직적으로 배열

        // JScrollPane을 사용하여 videoPanel을 감싸기
        JScrollPane scrollPane = new JScrollPane(videoPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // 패널에 추가
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(resultArea, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH); // JScrollPane 추가

        add(panel);
        setVisible(true);
    }
}
