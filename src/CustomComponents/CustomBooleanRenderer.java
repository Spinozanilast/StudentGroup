package CustomComponents;

import javax.swing.table.DefaultTableCellRenderer;

class CustomBooleanRenderer extends DefaultTableCellRenderer {
    @Override
    protected void setValue(Object value) {
        if (value instanceof Boolean) {
            Boolean boolValue = (Boolean) value;
            if (boolValue) {
                setText("Действительно");
            } else {
                setText("Нет");
            }
        }
    }
}