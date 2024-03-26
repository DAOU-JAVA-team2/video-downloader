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

        //더미 컨트롤러에서 데이터 받아옴
        VideoSearchPanel panel = new VideoSearchPanel(DummyController.getInstance().getDtos());
        leftPanel.add(panel);


        HalfPanel rightPanel = new HalfPanel(HalfPanel.RIGHT_PANEL);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JPanel testPanel = new JPanel();
        testPanel.setLayout(new BoxLayout(testPanel,BoxLayout.Y_AXIS));
        testPanel.setPreferredSize(new Dimension(380,265));
        testPanel.add(new DownloadWaitingPanel(DummyController.getInstance().getDtos()));

        rightPanel.add(testPanel);

        //TODO 받은 목록 리스트
        JPanel testPanel2 = new JPanel();

        testPanel2.setLayout(new BoxLayout(testPanel2,BoxLayout.Y_AXIS));
        testPanel2.setPreferredSize(new Dimension(380,225));
        testPanel2.add(new DownloadWaitingPanel(DummyController.getInstance().getDtos()));

        rightPanel.add(testPanel2);


        DOWNLOADPANEL.add(leftPanel, leftPanel.getConstraints());
        DOWNLOADPANEL.add(rightPanel, rightPanel.getConstraints());

        CONTENTPANEL.add(UPPERPANEL, BorderLayout.NORTH);
        CONTENTPANEL.add(DOWNLOADPANEL, BorderLayout.CENTER);

        setVisible(true);
    }
}
