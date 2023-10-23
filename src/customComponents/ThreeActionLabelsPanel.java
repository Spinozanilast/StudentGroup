package customComponents;

import javax.swing.*;
import java.awt.*;

public class ThreeActionLabelsPanel extends JPanel {
    private Color labelsForeground;
    private Color backPanelColor;
    private Dimension standardSize;
    private JLabel leftLabel;
    private JLabel centerLabel;
    private JLabel rightLabel;

    private boolean isLeftArrowDown = true;
    private boolean isCenterArrowDown = true;
    private boolean isRightArrowDown = true;

    public ThreeActionLabelsPanel(Color foregroundColor, Color backgroundColor, String leftLabelText, String centerLabelText, String rightLabelText){
        standardSize = new Dimension(640,45);
        labelsForeground = foregroundColor;
        backPanelColor = backgroundColor;
        setLabelsTexts(leftLabelText, centerLabelText, rightLabelText);
        setMaximumSize(standardSize);
        setBackground(backgroundColor);
        setPanelLayout();
        initLablesViews(leftLabel, centerLabel, rightLabel);
    }
    public void setLabelsTexts(String leftLabelText, String centerLabelText, String rightLabelText){
        leftLabel = new JLabel(leftLabelText);
        leftLabel.setHorizontalAlignment(SwingConstants.LEFT);
        centerLabel = new JLabel(centerLabelText);
        centerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightLabel = new JLabel(rightLabelText);
        rightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    }

    private void setPanelLayout(){
        setLayout(new BorderLayout());
        add(leftLabel,BorderLayout.WEST);
        add(centerLabel, BorderLayout.CENTER);
        add(rightLabel, BorderLayout.EAST);
    }

    private void initLablesViews(JLabel ... labels){
        for (JLabel label: labels) {
            label.setFont(new Font("Montserrat", Font.ITALIC, 16));
            setBackground(backPanelColor);
            label.setForeground(labelsForeground);

            ImageIcon icon = new ImageIcon("assets/DownArrowIcon.png");
            label.setIcon(icon);
        }
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

    public void setLeftLabelIcon(String filename) {
        isLeftArrowDown = !isLeftArrowDown;
        ImageIcon icon = new ImageIcon(filename);
        leftLabel.setIcon(icon);
    }

    public void setCenterLabelIcon(String filename) {
        isCenterArrowDown = !isCenterArrowDown;
        ImageIcon icon = new ImageIcon(filename);
        centerLabel.setIcon(icon);
    }

    public void setRightLabelIcon(String filename) {
        isRightArrowDown = !isRightArrowDown;
        ImageIcon icon = new ImageIcon(filename);
        rightLabel.setIcon(icon);
    }

    public void setLeftLabelText(String text) {
        leftLabel.setText(text);
    }

    public void setCenterLabelText(String text) {
        centerLabel.setText(text);
    }

    public void setRightLabelText(String text) {
        rightLabel.setText(text);
    }

    public JLabel getLeftLabel() {
        return leftLabel;
    }

    public JLabel getCenterLabel() {
        return centerLabel;
    }

    public JLabel getRightLabel() {
        return rightLabel;
    }
}
