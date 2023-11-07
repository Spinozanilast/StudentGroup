package CustomComponents.CustomTableActionCells;

import javax.swing.*;

/**
 *
 * @author RAVEN
 */
public interface TableActionEvent {

    public void onAddRow(int rowIndex, JTable jTable);

    public void onDelete(int rowIndex, JTable jTable);

    public void onUpdateDB(int rowIndex, JTable jTable);
}
