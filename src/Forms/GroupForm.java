package Forms;

import javax.swing.*;
import java.awt.*;

public class GroupForm extends JFrame {
    private JPanel mainPanel = new JPanel();
    private JPanel innerLeftLayoutPanel = new JPanel();
    private JPanel innerUpLayoutPanel = new JPanel();
    private JPanel contentLayoutPanel = new JPanel();
    public GroupForm(){
        setLayouts();
        setStartFormState();
    }

    private void setLayouts(){
        mainPanel.setLayout(new BorderLayout());
        innerLeftLayoutPanel.setLayout(new BoxLayout(innerLeftLayoutPanel, BoxLayout.Y_AXIS));
        innerUpLayoutPanel.setLayout(new BoxLayout(innerUpLayoutPanel, BoxLayout.X_AXIS));
        contentLayoutPanel.setLayout(new BoxLayout(contentLayoutPanel, BoxLayout.Y_AXIS));

        mainPanel.add(innerUpLayoutPanel, BorderLayout.NORTH);
        mainPanel.add(innerLeftLayoutPanel, BorderLayout.EAST);
        mainPanel.add(contentLayoutPanel, BorderLayout.CENTER);
    }

    private void setStartFormState(){

    }

    private void clearLayout(Container layoutComponent, boolean isRepaint){
        layoutComponent.removeAll();
        layoutComponent.revalidate();
        layoutComponent.repaint();
    }
}
