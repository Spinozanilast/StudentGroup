package CustomComponents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PillButton extends RoundedPanel {
    private Color activatedBackgroundColor;
    private Color notActivatedBackgroundColor;
    private Color activatedforeground;
    private Color notActivatedforeground;
    private String text;
    JLabel jlbPillText;
    boolean isActivated = true;

    public PillButton(String text, Color activatedBackgroundColor, Color notActivatedBackgroundColor,
                      Color isActivatedforeground, Color notActivatedforeground){
        setShady(false);
        this.text = text;
        this.activatedBackgroundColor = activatedBackgroundColor;
        this.notActivatedBackgroundColor = notActivatedBackgroundColor;
        this.activatedforeground = isActivatedforeground;
        this.notActivatedforeground = notActivatedforeground;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isActivated = !isActivated;
                Color backgroundColor = isActivated ? activatedBackgroundColor : notActivatedBackgroundColor;
                Color foreground = isActivated ? activatedforeground : notActivatedforeground;
                setBackground(backgroundColor);
                jlbPillText.setForeground(foreground);
            }
        });
        initView(text);
    }

    public void setLblFont(Font font){
        jlbPillText.setFont(font);
    }

    public boolean getPillActivated(){
        return isActivated;
    }

    private void initView(String text) {
        setLayout(new BorderLayout());
        jlbPillText = new JLabel(text, JLabel.CENTER);
        jlbPillText.setForeground(activatedforeground);
        setBackground(activatedBackgroundColor);
        add(jlbPillText, BorderLayout.CENTER);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getActivatedBackgroundColor() {
        return activatedBackgroundColor;
    }

    public void setActivatedBackgroundColor(Color activatedBackgroundColor) {
        this.activatedBackgroundColor = activatedBackgroundColor;
    }

    public Color getNotActivatedBackgroundColor() {
        return notActivatedBackgroundColor;
    }

    public void setNotActivatedBackgroundColor(Color notActivatedBackgroundColor) {
        this.notActivatedBackgroundColor = notActivatedBackgroundColor;
    }

    @Override
    public Color getForeground() {
        return activatedforeground;
    }

    @Override
    public void setForeground(Color foreground) {
        this.activatedforeground = foreground;
    }
}
