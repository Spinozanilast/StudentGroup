package Forms.views;

import javax.swing.*;
import java.awt.*;

public class GroupForm extends JFrame {
    private final JPanel mainPanel = new JPanel();
    private JPanel innerLeftLayoutPanel = new JPanel();
    private JPanel innerUpLayoutPanel = new JPanel();
    private JPanel contentLayoutPanel = new JPanel();
    public GroupForm(String groupNumber, String courseNumber, String headmanFullName){
        setTitle("Студенческая группа");
        ImageIcon icon = new ImageIcon("assets/AloneGroupIcon.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setLayouts();
    }

    private void setLayouts(){
        mainPanel.setLayout(new BorderLayout());
        innerLeftLayoutPanel.setLayout(new BoxLayout(innerLeftLayoutPanel, BoxLayout.Y_AXIS));
        innerUpLayoutPanel.setLayout(new BoxLayout(innerUpLayoutPanel, BoxLayout.X_AXIS));
        contentLayoutPanel.setLayout(new BoxLayout(contentLayoutPanel, BoxLayout.Y_AXIS));

        mainPanel.add(innerUpLayoutPanel, BorderLayout.NORTH);
        mainPanel.add(innerLeftLayoutPanel, BorderLayout.EAST);
        innerLeftLayoutPanel.setBackground(Color.RED);
        innerUpLayoutPanel.setBackground(Color.BLACK);
        contentLayoutPanel.setBackground(Color.CYAN);
        this.add(mainPanel);
        this.setContentPane(mainPanel);
    }

    private void clearLayout(Container layoutComponent, boolean isRepaint){
        layoutComponent.removeAll();
        layoutComponent.revalidate();
        layoutComponent.repaint();
    }
}
