package GUI.Login;

import GUI.Common.CustomColors;
import GUI.Download.DownloadSuperFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginSuperFrame extends JFrame {

    public LoginSuperFrame() {
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel CONTENTPANEL = new JPanel();
        setContentPane(CONTENTPANEL);

        JPanel CENTERPANEL = new JPanel();
        CENTERPANEL.setLayout(new BorderLayout());
        CONTENTPANEL.setBackground(CustomColors.MAIN_BLUE);

        //TODO: 이미지 블럭
        JPanel logoPanel = new LogoPanel();

//        JPanel ImagePanel = new JPanel();
//        ImagePanel.setLayout(new BoxLayout(ImagePanel, BoxLayout.Y_AXIS));
//        ImagePanel.setBackground(CustomColors.MAIN_BLUE);
//        ImageIcon originalIcon = new ImageIcon("setting/test_client/DaouLogo.png");
//        Image image = originalIcon.getImage();
//        Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
//        ImageIcon scaledIcon = new ImageIcon(scaledImage);
//        JLabel imageLabel = new JLabel(scaledIcon);
//
//        JLabel appName = new JLabel("DAOU LOADER");
//        appName.setFont(appName.getFont().deriveFont(20f));
//        appName.setPreferredSize(new Dimension(200, 100));
//
//        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        appName.setAlignmentX(Component.CENTER_ALIGNMENT);
//        ImagePanel.add(Box.createVerticalStrut(80));
//        ImagePanel.add(imageLabel);
//        ImagePanel.add(Box.createVerticalStrut(25));
//        ImagePanel.add(appName);


        JPanel loginPanel = new LoginPanel();
//        //TODO: 로그인 블럭
//        JPanel loginPanel = new JPanel();
//        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
//        loginPanel.setBackground(CustomColors.MAIN_BLUE);
//
//        //TODO: 아이디 레이블
//        JLabel idLabel = new JLabel("아이디");
//        idLabel.setFont(idLabel.getFont().deriveFont(16f));
//        idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        JTextField idTextField = new JTextField();
//        idTextField.setPreferredSize(new Dimension(200, 30));
//        idTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        loginPanel.add(Box.createVerticalStrut(75));
//        loginPanel.add(idLabel);
//        loginPanel.add(idTextField);
//        loginPanel.add(Box.createVerticalStrut(60));
//
//        JLabel passwordLabel = new JLabel("비밀번호");
//        passwordLabel.setFont(passwordLabel.getFont().deriveFont(16f));
//        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        JPasswordField passwordField = new JPasswordField();
//        passwordField.setPreferredSize(new Dimension(200, 30));
//        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        loginPanel.add(passwordLabel);
//        loginPanel.add(passwordField);
//        loginPanel.add(Box.createVerticalStrut(60));
//
//        JPanel buttonSet = new JPanel();
//        buttonSet.setLayout(new BorderLayout());
//        buttonSet.setBackground(CustomColors.MAIN_BLUE);
//
//        JButton loginButton = new JButton("로그인");
//        loginButton.setPreferredSize(new Dimension(100, 30));
//        JButton signUpButon = new JButton("회원가입");
//        signUpButon.setPreferredSize(new Dimension(100, 30));
//        buttonSet.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        buttonSet.add(loginButton, BorderLayout.WEST);
//        buttonSet.add(Box.createHorizontalStrut(160));
//        buttonSet.add(signUpButon, BorderLayout.EAST);
//
//        loginPanel.add(buttonSet);
//        loginPanel.add(Box.createVerticalStrut(100));




        JPanel upperPadding = new JPanel();
        upperPadding.setPreferredSize(new Dimension(400, 180));
        upperPadding.setBackground(CustomColors.MAIN_BLUE);

        JPanel centerPadding = new JPanel();
        centerPadding.setPreferredSize(new Dimension(120, 100));
        centerPadding.setBackground(CustomColors.MAIN_BLUE);

        CENTERPANEL.add(upperPadding, BorderLayout.NORTH);
//        CENTERPANEL.add(ImagePanel, BorderLayout.WEST);
        CENTERPANEL.add(logoPanel, BorderLayout.WEST);
        CENTERPANEL.add(centerPadding, BorderLayout.CENTER);
        CENTERPANEL.add(loginPanel, BorderLayout.EAST);

        CONTENTPANEL.add(CENTERPANEL, BorderLayout.CENTER);
        setVisible(true);
    }
}

/*
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 현재 프레임 닫기
                DownloadSuperFrame downloadFrame = new DownloadSuperFrame();
                downloadFrame.setVisible(true);

                setVisible(false);

                // 다음 프레임 열기

            }
        });
 */