package CustomComponents.CustomTableActionCells;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import static CustomComponents.CustomLightJTableWithActionColumn.*;

/**
 *
 * @author Будчанин Вадим
 */
public class TableActionCellRender extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSelected, boolean bln1, int row, int column) {
        PanelAction panelAction = new PanelAction();

        Color selectedColor;
        Color evenRowColor;
        Color oddRowColor;

        if (isSelected) {
            selectedColor = row % 2 == 0 ? SELECTED_EVEN_COLOR : SELECTED_ODD_COLOR;
            panelAction.setBackground(selectedColor);
        } else {
            evenRowColor = EVEN_COLOR;
            oddRowColor = ODD_COLOR;
            panelAction.setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);
        }

        return panelAction;
    }
}
