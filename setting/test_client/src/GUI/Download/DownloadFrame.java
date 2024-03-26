package GUI.Download;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

//final class Padding {
//    static final upperPanel
//}

class CustomColors {
    public static final Color MAIN_BLUE = new Color(193, 204, 222);
    public static final Color BUTTON_GREEN = new Color(136,179,197);
}

public class DownloadFrame extends JFrame {

    public DownloadFrame() {
        //TODO 메인 프레임
        setSize(1200,800);
        setLocationRelativeTo(null); //중앙에 창 뜨게

        JPanel CONTENTPANEL = new JPanel();
        CONTENTPANEL.setLayout(new BorderLayout());
        CONTENTPANEL.setBackground(Color.GREEN);
        setContentPane(CONTENTPANEL);

        //TODO 상단 패널(검색, 버튼)
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30,20));
        searchPanel.setBackground(CustomColors.MAIN_BLUE);
        JTextField searchField = new JTextField(50);
        searchField.setPreferredSize(new Dimension(300, 30));

        JButton searchButton = new JButton("검색");
        searchButton.setPreferredSize(new Dimension(100, 30));
        searchButton.setBackground(CustomColors.BUTTON_GREEN);
        searchButton.setForeground(Color.WHITE);

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30,20));
        buttonPanel.setBackground(CustomColors.MAIN_BLUE);
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
        UPPERPANEL.setBackground(CustomColors.MAIN_BLUE);

        UPPERPANEL.add(searchPanel,BorderLayout.WEST);
        UPPERPANEL.add(buttonPanel,BorderLayout.EAST);


        //TODO 반반 가르기
        JPanel downloadPanel = new JPanel();
        downloadPanel.setLayout(new GridBagLayout());
        downloadPanel.setBackground(new Color(193,204,222));

        GridBagConstraints leftPanelConstraints = new GridBagConstraints();
        leftPanelConstraints.fill = GridBagConstraints.BOTH;
        leftPanelConstraints.weightx = 1.0;
        leftPanelConstraints.weighty = 1.0;

        leftPanelConstraints.insets = new Insets(0, 30, 0, 0);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(new Color(220, 230, 236));
        leftPanel.setPreferredSize(new Dimension(570, 100));

        downloadPanel.add(leftPanel,leftPanelConstraints);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(222, 222, 222));
        rightPanel.setPreferredSize(new Dimension(430, 100));

        GridBagConstraints rightPanelConstraints = new GridBagConstraints();
        rightPanelConstraints.fill = GridBagConstraints.BOTH;
        rightPanelConstraints.weightx = 1.0;
        rightPanelConstraints.weighty = 1.0;
        rightPanelConstraints.insets = new Insets(0, 0, 0, 30);
        downloadPanel.add(rightPanel, rightPanelConstraints);





        CONTENTPANEL.add(UPPERPANEL,BorderLayout.NORTH);
        CONTENTPANEL.add(downloadPanel,BorderLayout.CENTER);
        setVisible(true);
    }
}

/*
HStack {
Image,
VStack {
Label
label
label
label
padding(.vertical)
Button
}

 */
