package CustomComponents;

import CustomComponents.CustomTableActionCells.TableActionCellEditor;
import CustomComponents.CustomTableActionCells.TableActionCellRender;
import CustomComponents.CustomTableActionCells.TableActionEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * CustomLightJTableWithActionColumn - это подкласс JTable, который предоставляет возможности для настройки внешнего вида таблицы.
 * Он расширяет JTable и реализует пользовательскую отрисовку для столбцов с логическими значениями в заголовке и ячейках данных таблицы.
 * <p>
 * @author Будчанин В.А.
 * @version 1.0
 */
public class CustomLightJTableWithActionColumn extends JTable {

    private Dimension PREFFERED_HEADER_SIZE = new Dimension(0, 35);

    /**
     * Конструктор CustomLightJTableWithActionColumn с указанными данными таблицы и именами столбцов.
     *
     * @param tableModel   данные, которые будут отображаться в таблице
     */
    public CustomLightJTableWithActionColumn(DefaultTableModel tableModel) {
        super(tableModel);
        getTableHeader().setDefaultRenderer(new TableLightHeader());
        setDefaultRenderer(Object.class, new TableLightCell());
        getTableHeader().setPreferredSize(PREFFERED_HEADER_SIZE);
        setRowHeight(40);
    }

    public void addActionColumn(TableActionEvent tableActionEvents) {
        TableColumn actionColumn = new TableColumn();
        actionColumn.setCellRenderer(new TableActionCellRender());
        actionColumn.setCellEditor(new TableActionCellEditor(tableActionEvents));
        actionColumn.setHeaderValue("ДЕЙСТВИЯ");
        addColumn(actionColumn);
    }

    /**
     * Устанавливает пользовательский отрисовщик для столбцов с логическими значениями.
     *
     * @param booleanColumnsIndexes индексы столбцов с логическими значениями
     */
    public void setCustomBooleanColumnRenderer(int[] booleanColumnsIndexes) {
        for (int columnIndex : booleanColumnsIndexes) {
            getColumnModel().getColumn(columnIndex).setCellRenderer(new CustomBooleanRenderer());
        }
    }

    /**
     * CustomBooleanRenderer - это отрисовщик ячеек для логических значений в таблице.
     */
    private static class CustomBooleanRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return  getCustomTableComponent(isSelected, row, component);
        }

        @Override
        protected void setValue(Object value) {
            if (value instanceof Boolean boolValue) {
                if (boolValue) {
                    setText("Действительно");
                } else {
                    setText("Нет");
                }
            }
        }
    }

    /**
     * TableLightHeader - это пользовательский отрисовщик заголовка таблицы.
     */
    private static class TableLightHeader extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable jtable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(jtable, value, isSelected, hasFocus, row, column);
            component.setBackground(new Color(244, 247, 252));
            component.setForeground(new Color(108, 121, 146));
            component.setFont(new Font("Montserrat", Font.BOLD, 12));
            return  component;
        }
    }

    private static class TableLightCell extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return CustomLightJTableWithActionColumn.getCustomTableComponent(isSelected, row, component);
        }
    }

    private static Component getCustomTableComponent(boolean isSelected, int row, Component component) {
        component.setFont(new Font("Montserrat", Font.ITALIC, 12));

        Color selectedColor;
        Color evenRowColor;
        Color oddRowColor;

        if (isSelected) {
            selectedColor = row % 2 == 0 ? new Color(174, 174, 204, 139) : new Color(114, 118, 116);
            component.setBackground(selectedColor);
        } else {
            evenRowColor = Color.WHITE;
            oddRowColor = new Color(244, 247, 252);
            component.setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);
        }

        return component;
    }
}