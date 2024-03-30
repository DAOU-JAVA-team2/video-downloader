package GUI.Download;

import GUI.Common.CustomColors;

import javax.swing.*;
import java.awt.*;

public class HalfPanel extends JPanel {

    public static final int LEFT_PANEL = 0;
    public static final int RIGHT_PANEL = 1;
    private GridBagConstraints constraints = null;

    public HalfPanel(int panelType) {
        setLayout(new GridBagLayout());
        setBackground(CustomColors.PANEL_BLUE);

        if (panelType == LEFT_PANEL) {
            setPreferredSize(new Dimension(550, 400));
            GridBagConstraints leftPanelConstraints = new GridBagConstraints();
            leftPanelConstraints.fill = GridBagConstraints.BOTH;
            leftPanelConstraints.weightx = 1.0;
            leftPanelConstraints.weighty = 1.0;
            leftPanelConstraints.insets = new Insets(0, 0, 30, 0);
            this.constraints = leftPanelConstraints;
        } else if (panelType == RIGHT_PANEL) {
            setPreferredSize(new Dimension(410, 400));
            GridBagConstraints rightPanelConstraints = new GridBagConstraints();
            rightPanelConstraints.fill = GridBagConstraints.BOTH;
            rightPanelConstraints.weightx = 1.0;
            rightPanelConstraints.weighty = 1.0;
            rightPanelConstraints.insets = new Insets(0, 0, 30, 30);
            this.constraints = rightPanelConstraints;
        }
    }

    public GridBagConstraints getConstraints() {
        return constraints;
    }
}

