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
 *
 * @author Будчанин В.А.
 * @version 1.0
 */
public class CustomLightJTableWithActionColumn extends JTable {

    /**
     * Предпочтительный размер заголовка.
     */
    private Dimension PREFFERED_HEADER_SIZE = new Dimension(0, 35);

    /**
     * Цвет сетки заголовка.
     */
    private static Color HEADER_GRID_COLOR = new Color(69, 83, 93, 47);

    /**
     * Цвет выбранной четной строки.
     */
    public static final Color SELECTED_EVEN_COLOR = new Color(151, 151, 234, 139);

    /**
     * Цвет выбранной нечетной строки.
     */
    public static final Color SELECTED_ODD_COLOR = new Color(83, 134, 128, 139);

    /**
     * Цвет четной строки.
     */
    public static final Color EVEN_COLOR = Color.WHITE;

    /**
     * Цвет нечетной строки.
     */
    public static final Color ODD_COLOR = new Color(244, 247, 252);

    /**
     * Конструктор CustomLightJTableWithActionColumn с указанными данными таблицы и именами столбцов.
     *
     * @param tableModel   данные, которые будут отображаться в таблице
     */
    public CustomLightJTableWithActionColumn(DefaultTableModel tableModel) {
        super(tableModel);
        super.setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
        getTableHeader().setDefaultRenderer(new TableLightHeader());
        getTableHeader().setReorderingAllowed(false);
        setDefaultRenderer(Object.class, new TableLightCell());
        getTableHeader().setPreferredSize(PREFFERED_HEADER_SIZE);
        setRowHeight(40);
    }

    /**
     * Добавляет столбец действий в таблицу.
     *
     * @param tableActionCellEvents Объект, содержащий события для ячеек действий.
     */
    public void addActionColumn(TableActionCellEvent tableActionCellEvents) {
        TableColumn actionColumn = new TableColumn();
        actionColumn.setCellRenderer(new TableActionCellRender());
        actionColumn.setCellEditor(new TableActionCellEditor(tableActionCellEvents));
        actionColumn.setHeaderValue("ДЕЙСТВИЯ");
        addColumn(actionColumn);
    }

    /**
     * Устанавливает пользовательский отрисовщик для столбцов с логическими и целочисленными значениями.
     *
     * @param booleanColumnsIndexes Индексы столбцов с логическими значениями.
     * @param integerColumnsIndexes Индексы столбцов с целочисленными значениями.
     */
    public void setCustomBooleanIntegerRenderers(int[] booleanColumnsIndexes, int[] integerColumnsIndexes) {
        for (int columnIndex : booleanColumnsIndexes) {
            getColumnModel().getColumn(columnIndex).setCellRenderer(new CheckBoxRenderer());
        }

        for (int columnIndex : integerColumnsIndexes) {
            getColumnModel().getColumn(columnIndex).setCellRenderer(new IntegerRenderer());
        }
    }

    /**
     * IntegerRenderer - это пользовательский отрисовщик ячеек таблицы для целочисленных значений.
     *
     *  @author Будчанин В.А.
     *  @version 1.0
     */
    public static class IntegerRenderer extends DefaultTableCellRenderer {
        IntegerRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column).
                    setFont(new Font("Montserrat", Font.ITALIC, 12));
            super.setBorder(BorderFactory.createLineBorder(HEADER_GRID_COLOR));
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

    /**
     * CheckBoxRenderer - это пользовательский отрисовщик ячеек таблицы для логических значений.
     *
     * @author Будчанин В.А.
     * @version 1.0
     */
    public static class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
        CheckBoxRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
            setOpaque(true);
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
            setBorder(BorderFactory.createLineBorder(HEADER_GRID_COLOR));
            setSelected((value != null && (Boolean) value));
            return this;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(HEADER_GRID_COLOR);
            g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            g2.dispose();
        }
    }

    /**
     * TableLightHeader - это пользовательский отрисовщик заголовка таблицы.
     *
     * @author Будчанин В.А.
     * @version 1.0
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
            component.setBorder(BorderFactory.createLineBorder(HEADER_GRID_COLOR));
            return  component;
        }
    }


    /**
     * TableLightCell - это пользовательский отрисовщик ячеек таблицы.
     *
     * @author Будчанин В.А.
     * @version 1.0
     */
    private static class TableLightCell extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            return CustomLightJTableWithActionColumn.getCustomTableComponent(isSelected, row, component);
        }
    }

    /**
     * Возвращает пользовательский компонент таблицы на основе заданных параметров.
     *
     * @param isSelected Определяет, выбрана ли ячейка.
     * @param row Номер строки ячейки.
     * @param component Компонент, который нужно настроить.
     * @return Настроенный компонент.
     */
    private static Component getCustomTableComponent(boolean isSelected, int row, Component component) {
        JLabel resultComponent = (JLabel) component;
        resultComponent.setHorizontalAlignment(JLabel.CENTER);
        resultComponent.setFont(new Font("Montserrat", Font.ITALIC, 12));

        Color selectedColor;
        Color evenRowColor;
        Color oddRowColor;

        if (isSelected) {
            selectedColor = row % 2 == 0 ? SELECTED_EVEN_COLOR : SELECTED_ODD_COLOR;
            resultComponent.setBackground(selectedColor);
        } else {
            evenRowColor = EVEN_COLOR;
            oddRowColor = ODD_COLOR;
            resultComponent.setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);
        }
        resultComponent.setBorder(BorderFactory.createLineBorder(HEADER_GRID_COLOR));
        return resultComponent;
    }

}