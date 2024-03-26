package GUI.Download;

import javax.swing.*;
import java.awt.*;

//final class Padding {
//    static final upperPanel
//}


public class DownloadFrame extends JFrame {

    public DownloadFrame() {
        //TODO 메인 프레임
        setSize(1200,800);
        getContentPane().setBackground(new Color(88, 121, 208));
        setLocationRelativeTo(null); //중앙에 창 뜨게


        //TODO 상단 패널(검색, 버튼)
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30,20));

        JTextField searchField = new JTextField(50);
        searchField.setPreferredSize(new Dimension(300, 30));

        JButton searchButton = new JButton("검색");
        searchButton.setPreferredSize(new Dimension(100, 30));
        searchButton.setBackground(new Color(136,179,197));
        searchButton.setForeground(Color.WHITE);

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30,20));

        JButton extraButton = new JButton("여분 버튼");
        extraButton.setBackground(Color.gray);
        extraButton.setForeground(Color.WHITE);
        searchButton.setPreferredSize(new Dimension(100, 30));

        JButton logOutButton = new JButton("로그 아웃");
        logOutButton.setBackground(Color.gray);
        logOutButton.setForeground(Color.WHITE);
        searchButton.setPreferredSize(new Dimension(100, 30));

        buttonPanel.add(extraButton);
        buttonPanel.add(logOutButton);

        JPanel UPPERPANEL = new JPanel();
        UPPERPANEL.setLayout(new BorderLayout());
//        UPPERPANEL.setLayout(new FlowLayout(FlowLayout.LEFT,100,10));
        UPPERPANEL.add(searchPanel,BorderLayout.WEST);
        UPPERPANEL.add(buttonPanel,BorderLayout.EAST);


        //TODO 반반 가르기









        add(UPPERPANEL);

        setVisible(true);
    }
}

