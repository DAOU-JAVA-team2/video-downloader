package GUI.Auth;

import GUI.Common.CustomColors;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    public LoginPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(CustomColors.MAIN_BLUE);

        JLabel idLabel = new JLabel("아이디");
        idLabel.setFont(idLabel.getFont().deriveFont(16f));
        idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        idLabel.setName("idLabel");

        JTextField idTextField = new JTextField();
        idTextField.setPreferredSize(new Dimension(200, 30));
        idTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        idTextField.setName("idTextField");

        add(Box.createVerticalStrut(75));
        add(idLabel);
        add(idTextField);
        add(Box.createVerticalStrut(60));

        JLabel passwordLabel = new JLabel("비밀번호");
        passwordLabel.setFont(passwordLabel.getFont().deriveFont(16f));
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordLabel.setName("passwordLabel");

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordField.setName("passwordField");

        add(passwordLabel);
        add(passwordField);
        add(Box.createVerticalStrut(60));

        JPanel buttonSet = new JPanel();
        buttonSet.setLayout(new BorderLayout());
        buttonSet.setBackground(CustomColors.MAIN_BLUE);
        buttonSet.setName("buttonSet");

        JButton loginButton = new JButton("로그인");
        loginButton.setPreferredSize(new Dimension(100, 30));
        loginButton.setName("loginButton");

        JButton signUpButton = new JButton("회원가입");
        signUpButton.setPreferredSize(new Dimension(100, 30));
        signUpButton.setName("signUpButton");

        buttonSet.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonSet.add(loginButton, BorderLayout.WEST);
        buttonSet.add(Box.createHorizontalStrut(160));
        buttonSet.add(signUpButton, BorderLayout.EAST);

        add(buttonSet);
        add(Box.createVerticalStrut(100));
    }
}

