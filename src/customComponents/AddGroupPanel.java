package customComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddGroupPanel extends RoundedPanel {
    private Color labelsForeground;
    private Color backPanelColor;
    private final Color MOUSE_OVER_COLOR = new Color(201,255,255);
    private final Color NOT_ENABLED_BACK_COLOR = new Color(171, 171, 171);
    private Dimension standardSize;
    private HighResolutionImageLabel highResolutionImageLabel;
    public AddGroupPanel(Color foregroundColor, Color backgroundColor){
        standardSize = new Dimension(640,45);
        labelsForeground = foregroundColor;
        backPanelColor = backgroundColor;
        JLabel addLabel = new JLabel("+");
        addLabel.setHorizontalAlignment(SwingConstants.CENTER);
        initLabelsViews(addLabel);
        setStrokeSize(0);

        setMaximumSize(standardSize);
        setBackground(backgroundColor);

        setLayout(new BorderLayout());

        highResolutionImageLabel = new HighResolutionImageLabel("Add-Group.png",40, 20);
        add(highResolutionImageLabel, BorderLayout.CENTER);

        highResolutionImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(MOUSE_OVER_COLOR);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(backgroundColor);
                setShady(true);
            }
        });
    }

    public void setMouseClickEvent(MouseAdapter mouseAdapter){
        highResolutionImageLabel.addMouseListener(mouseAdapter);
    }

    private void initLabelsViews(JLabel label) {
        label.setFont(new Font("Montserrat", Font.BOLD, 20));
        setBackground(backPanelColor);
        label.setForeground(labelsForeground);
    }

    public Color getLabelsForeground() {
        return labelsForeground;
    }

    public void setLabelsForeground(Color labelsForeground) {
        this.labelsForeground = labelsForeground;
    }

    public Color getBackPanelColor() {
        return backPanelColor;
    }

    public void setBackPanelColor(Color backPanelColor) {
        this.backPanelColor = backPanelColor;
    }

    public Dimension getStandardSize() {
        return standardSize;
    }

    public void setStandardSize(Dimension standardSize) {
        this.standardSize = standardSize;
    }
}
