package GUI.Download;

import GUI.Common.CustomColors;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel {
    private final JTextField searchField;
    private final JButton searchButton;

    public SearchPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 30, 20));
        setBackground(CustomColors.MAIN_BLUE);

        searchField = new JTextField(50);
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setName("searchField_u");

        searchButton = new JButton("검색");
        searchButton.setPreferredSize(new Dimension(100, 30));

        searchButton.setBackground(CustomColors.BUTTON_GREEN);
        searchButton.setForeground(Color.WHITE);
        searchButton.setName("searchButton_u");

        add(searchField);
        add(searchButton);
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }
}
