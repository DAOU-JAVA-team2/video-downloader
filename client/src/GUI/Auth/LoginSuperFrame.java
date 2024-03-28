package GUI.Auth;

import GUI.Common.CustomColors;

import javax.swing.*;
import java.awt.*;

public class LoginSuperFrame extends JFrame {

    public LoginSuperFrame() {
        setTitle("DAOU LOADER");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel CONTENTPANEL = new JPanel();
        setContentPane(CONTENTPANEL);
        CONTENTPANEL.setName("CONTENTPANEL");

        JPanel CENTERPANEL = new JPanel();
        CENTERPANEL.setLayout(new BorderLayout());
        CONTENTPANEL.setBackground(CustomColors.MAIN_BLUE);
        CENTERPANEL.setName("CENTERPANEL");

        JPanel logoPanel = new LogoPanel();
        JPanel loginPanel = new LoginPanel();
        logoPanel.setName("logoPanel");
        loginPanel.setName("loginPanel");

        CENTERPANEL.add(getPadding(400, 180), BorderLayout.NORTH);
        CENTERPANEL.add(logoPanel, BorderLayout.WEST);
        CENTERPANEL.add(getPadding(120, 100), BorderLayout.CENTER);
        CENTERPANEL.add(loginPanel, BorderLayout.EAST);

        CONTENTPANEL.add(CENTERPANEL, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel getPadding(int width, int height) {
        JPanel padding = new JPanel();
        padding.setPreferredSize(new Dimension(width, height));
        padding.setBackground(CustomColors.MAIN_BLUE);
        return padding;
    }
}