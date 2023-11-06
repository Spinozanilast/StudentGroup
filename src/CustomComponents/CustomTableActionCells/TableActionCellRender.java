package CustomComponents.CustomTableActionCells;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

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
            jtable.setSelectionForeground(Color.WHITE);
            selectedColor = row % 2 == 0 ? new Color(174, 174, 204, 139) : new Color(114, 118, 116);
            panelAction.setBackground(selectedColor);
        } else {
            evenRowColor = Color.WHITE;
            oddRowColor = new Color(244, 247, 252);
            panelAction.setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);
        }

        return panelAction;
    }
}
