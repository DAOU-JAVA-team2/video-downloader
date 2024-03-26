package GUI.Download;
import GUI.Common.CustomColors;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


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
        JPanel UPPERPANEL = new JPanel();
        UPPERPANEL.setLayout(new BorderLayout());
        UPPERPANEL.setBackground(CustomColors.MAIN_BLUE);

        JPanel searchPanel = new SearchPanel();
        JPanel buttonPanel = new ButtonPanel();

        UPPERPANEL.add(searchPanel,BorderLayout.WEST);
        UPPERPANEL.add(buttonPanel,BorderLayout.EAST);


        //TODO 반반 가르기
        JPanel DOWNLOADPANEL = new JPanel();
        DOWNLOADPANEL.setLayout(new GridBagLayout());
        DOWNLOADPANEL.setBackground(CustomColors.SEARCH_RESULT_BACKGROUND);

        HalfPanel leftPanel = new HalfPanel(HalfPanel.LEFT_PANEL);
        
        HalfPanel rightPanel = new HalfPanel(HalfPanel.RIGHT_PANEL);

        DOWNLOADPANEL.add(leftPanel,leftPanel.getConstraints());
        DOWNLOADPANEL.add(rightPanel,rightPanel.getConstraints());





        CONTENTPANEL.add(UPPERPANEL,BorderLayout.NORTH);
        CONTENTPANEL.add(DOWNLOADPANEL,BorderLayout.CENTER);

        setVisible(true);
    }
}
