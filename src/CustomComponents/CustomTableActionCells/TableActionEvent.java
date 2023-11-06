package CustomComponents.CustomTableActionCells;

import javax.swing.*;

/**
 *
 * @author RAVEN
 */
public interface TableActionEvent {

    public void onEdit(int rowIndex, JTable jTable);

    public void onDelete(int rowIndex, JTable jTable);

    public void onView(int rowIndex, JTable jTable);
}
