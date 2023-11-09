package CustomComponents;

import CustomComponents.CustomTableActionCells.TableActionCellEditor;
import CustomComponents.CustomTableActionCells.TableActionCellRender;
import CustomComponents.CustomTableActionCells.TableActionCellEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
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
    private static Color HEADER_GRID_COLOR = new Color(69, 83, 93, 47);
    public static final Color SELECTED_EVEN_COLOR = new Color(151, 151, 234, 139);
    public static final Color SELECTED_ODD_COLOR = new Color(83, 134, 128, 139);
    public static final Color EVEN_COLOR = Color.WHITE;
    public static final Color ODD_COLOR = new Color(244, 247, 252);;

    /**
     * Конструктор CustomLightJTableWithActionColumn с указанными данными таблицы и именами столбцов.
     *
     * @param tableModel   данные, которые будут отображаться в таблице
     */
    public CustomLightJTableWithActionColumn(DefaultTableModel tableModel) {
        super(tableModel);
        setShowGrid(true);
        super.setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
        setGridColor(HEADER_GRID_COLOR);
        getTableHeader().setDefaultRenderer(new TableLightHeader());
        getTableHeader().setReorderingAllowed(false);
        setDefaultRenderer(Object.class, new TableLightCell());
        getTableHeader().setPreferredSize(PREFFERED_HEADER_SIZE);
        setRowHeight(40);
    }

    public void addActionColumn(TableActionCellEvent tableActionCellEvents) {
        TableColumn actionColumn = new TableColumn();
        actionColumn.setCellRenderer(new TableActionCellRender());
        actionColumn.setCellEditor(new TableActionCellEditor(tableActionCellEvents));
        actionColumn.setHeaderValue("ДЕЙСТВИЯ");
        addColumn(actionColumn);
    }

    /**
     * Устанавливает пользовательский отрисовщик для столбцов с логическими значениями.
     *
     * @param booleanColumnsIndexes индексы столбцов с логическими значениями
     */
    public void setCustomBooleanIntegerRenderers(int[] booleanColumnsIndexes, int[] integerColumnsIndexes) {
        for (int columnIndex : booleanColumnsIndexes) {
            getColumnModel().getColumn(columnIndex).setCellRenderer(new CheckBoxRenderer());
        }

        for (int columnIndex : integerColumnsIndexes) {
            getColumnModel().getColumn(columnIndex).setCellRenderer(new IntegerRenderer());
        }
    }

    public static class IntegerRenderer extends DefaultTableCellRenderer {
        IntegerRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column).
                    setFont(new Font("Montserrat", Font.ITALIC, 12));
            Color selectedColor;
            Color evenRowColor;
            Color oddRowColor;
            if (isSelected) {
                selectedColor = row % 2 == 0 ? SELECTED_EVEN_COLOR : SELECTED_ODD_COLOR;
                setBackground(selectedColor);
            } else {
                evenRowColor = EVEN_COLOR;
                oddRowColor = ODD_COLOR;
                setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);
            }

            setText(value != null ? value.toString() : "");
            return this;
        }
    }

    public static class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
        CheckBoxRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {

            Color selectedColor;
            Color evenRowColor;
            Color oddRowColor;

            if (isSelected) {
                selectedColor = row % 2 == 0 ? SELECTED_EVEN_COLOR : SELECTED_ODD_COLOR;
                setBackground(selectedColor);
            } else {
                evenRowColor = EVEN_COLOR;
                oddRowColor = ODD_COLOR;
                setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);
            }

            setSelected((value != null && (Boolean) value));
            return this;
        }
    }

    /**
     * TableLightHeader - это пользовательский отрисовщик заголовка таблицы.
     */
    private static class TableLightHeader extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable jtable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel component = (JLabel) super.getTableCellRendererComponent(jtable, value, isSelected, hasFocus, row, column);
            component.setBackground(new Color(244, 247, 252));
            component.setForeground(new Color(108, 121, 146));
            component.setFont(new Font("Montserrat", Font.BOLD, 12));
            component.setBorder(BorderFactory.createMatteBorder(0,0,0, 1, HEADER_GRID_COLOR));
            component.setHorizontalAlignment(JLabel.CENTER);
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
            selectedColor = row % 2 == 0 ? SELECTED_EVEN_COLOR : SELECTED_ODD_COLOR;
            component.setBackground(selectedColor);
        } else {
            evenRowColor = EVEN_COLOR;
            oddRowColor = ODD_COLOR;
            component.setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);
        }

        return component;
    }

}