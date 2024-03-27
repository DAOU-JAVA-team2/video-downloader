package GUI.Login;

import GUI.Common.CustomColors;

import javax.swing.*;
import java.awt.*;

public class LogoPanel extends JPanel {
    public LogoPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(CustomColors.MAIN_BLUE);

//        ImageIcon originalIcon = new ImageIcon("setting/test_client/DaouLogo.png");
        ImageIcon originalIcon = new ImageIcon("src/GUI/DaouLogo.png");
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);

        JLabel appName = new JLabel("DAOU LOADER");
        appName.setFont(appName.getFont().deriveFont(20f));
        appName.setPreferredSize(new Dimension(200, 100));

        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        appName.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(80));
        add(imageLabel);
        add(Box.createVerticalStrut(25));
        add(appName);
    }
}

