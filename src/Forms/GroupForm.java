package Forms;

import customComponents.*;

import javax.swing.*;
import java.awt.*;

public class GroupForm extends JFrame {

    public GroupForm(){

    }

    private void setLayout(){

    }

    private void setInLayouts(){

    }

    private void clearLayout(Container layoutComponent, boolean isRepaint){
        layoutComponent.removeAll();
        layoutComponent.revalidate();
        layoutComponent.repaint();
    }
}
