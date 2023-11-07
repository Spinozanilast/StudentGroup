package CustomComponents.CustomTableActionCells;

import java.awt.Component;
import javax.swing.*;

/**
 *
 * @author RAVEN
 */
public class TableActionCellEditor extends DefaultCellEditor {

    private TableActionEvent event;

    public TableActionCellEditor(TableActionEvent event) {
        super(new JTextField());
        this.event = event;
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        super.getTableCellEditorComponent(jtable, o, bln, row, column);
        PanelAction action = new PanelAction();
        action.initEvent(event, row, jtable);
        action.setBackground(jtable.getSelectionBackground());
        return action;
    }
}
