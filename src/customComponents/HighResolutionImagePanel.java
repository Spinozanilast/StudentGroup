package customComponents;

import javax.swing.*;
import java.awt.*;

public class HighResolutionImagePanel extends JPanel {
    HighResolutionImageLabel label;
    Dimension panelSize;
    public HighResolutionImagePanel(HighResolutionImageLabel highResolutionImageLabel, int panelWidth, int panelHeight){
        panelSize = new Dimension(panelWidth, panelHeight);
        label = highResolutionImageLabel;
        setMaximumSize(panelSize);
        setBorder(null);
        setOpaque(false);
        initPanelLayout();
    }

    private void initPanelLayout(){
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
    }
}
