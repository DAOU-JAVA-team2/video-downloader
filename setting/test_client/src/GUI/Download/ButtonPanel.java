package GUI.Download;

import GUI.Common.CustomColors;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {
    private JButton extraButton;
    private JButton logOutButton;

    public ButtonPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 30, 20));
        setBackground(CustomColors.MAIN_BLUE);

        extraButton = new JButton("여분 버튼");
        extraButton.setBackground(Color.gray);
        extraButton.setForeground(Color.WHITE);
        extraButton.setPreferredSize(new Dimension(100, 30));

        logOutButton = new JButton("로그 아웃");
        logOutButton.setBackground(Color.gray);
        logOutButton.setForeground(Color.WHITE);
        logOutButton.setPreferredSize(new Dimension(100, 30));

        add(extraButton);
        add(logOutButton);
    }

    public JButton getExtraButton() {
        return extraButton;
    }

    public JButton getLogOutButton() {
        return logOutButton;
    }
}
