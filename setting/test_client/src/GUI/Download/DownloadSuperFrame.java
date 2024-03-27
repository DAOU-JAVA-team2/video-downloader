package GUI.Download;

import GUI.Common.CustomColors;
import GUI.DummyController;

import javax.swing.*;
import java.awt.*;

public class DownloadSuperFrame extends JFrame {

    public DownloadSuperFrame() {
        //TODO 메인 프레임
        setSize(1200, 800);
        setLocationRelativeTo(null); //중앙에 창 뜨게
        setResizable(false);
        JPanel CONTENTPANEL = new JPanel();
        CONTENTPANEL.setLayout(new BorderLayout());
        setContentPane(CONTENTPANEL);

        //TODO 상단 패널(검색, 버튼)
        JPanel UPPERPANEL = new JPanel();
        UPPERPANEL.setLayout(new BorderLayout());
        UPPERPANEL.setBackground(CustomColors.MAIN_BLUE);

        JPanel searchPanel = new SearchPanel();
        JPanel buttonPanel = new ButtonPanel();

        UPPERPANEL.add(searchPanel, BorderLayout.WEST);
        UPPERPANEL.add(buttonPanel, BorderLayout.EAST);

        //TODO 반반 가르기
        JPanel DOWNLOADPANEL = new JPanel();
        DOWNLOADPANEL.setLayout(new GridBagLayout());
        DOWNLOADPANEL.setBackground(CustomColors.SEARCH_RESULT_BACKGROUND);

        HalfPanel leftPanel = new HalfPanel(HalfPanel.LEFT_PANEL);
        VideoSearchPanel panel = new VideoSearchPanel(DummyController.getInstance().getDtos());
        leftPanel.add(panel);

        //TODO 오른쪽 패널
        HalfPanel rightPanel = new HalfPanel(HalfPanel.RIGHT_PANEL);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        //TODO 다운로드 패널
        JPanel rightUpperPannel = new JPanel();

        rightUpperPannel.setLayout(new BoxLayout(rightUpperPannel, BoxLayout.Y_AXIS));
        rightUpperPannel.setPreferredSize(new Dimension(380, 280));
        rightUpperPannel.add(new DownloadWaitingPanel(DummyController.getInstance().getDtos()));

        rightPanel.add(rightUpperPannel);
        JPanel padding = new JPanel();
        padding.setBackground(CustomColors.PANEL_BLUE);
        padding.setPreferredSize(new Dimension(20, 15));

        rightPanel.add(padding);

        //TODO 받은 목록 리스트
        JPanel rightDownPannel = new JPanel();

        rightDownPannel.setLayout(new BoxLayout(rightDownPannel, BoxLayout.Y_AXIS));
        rightDownPannel.setPreferredSize(new Dimension(380, 280));
        rightDownPannel.add(new DownloadedListPanel(DummyController.getInstance().getDtos()));

        rightPanel.add(rightDownPannel);

        DOWNLOADPANEL.add(leftPanel, leftPanel.getConstraints());
        DOWNLOADPANEL.add(rightPanel, rightPanel.getConstraints());

        CONTENTPANEL.add(UPPERPANEL, BorderLayout.NORTH);
        CONTENTPANEL.add(DOWNLOADPANEL, BorderLayout.CENTER);

        setVisible(true);
    }
}
