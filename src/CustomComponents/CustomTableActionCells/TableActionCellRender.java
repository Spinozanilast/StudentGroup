package CustomComponents.CustomTableActionCells;

import java.awt.Color;
import java.awt.Component;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import static CustomComponents.CustomLightJTableWithActionColumn.*;

/**
 * Класс TableActionCellRender представляет собой рендерер ячеек таблицы,
 * который отображает действия в виде панели.
 *
 * @author Будчанин В.А.
 * @version  1.0
 */
public class TableActionCellRender extends DefaultTableCellRenderer {
    /**
     * Цвет сетки.
     */
    private static Color GRID_COLOR = new Color(69, 83, 93, 47);
    /**
     * Метод получает компонент, который будет использоваться для отображения ячейки таблицы.
     *
     * @param jtable таблица, для которой выполняется рендеринг
     * @param o значение ячейки
     * @param isSelected флаг, указывающий, является ли ячейка выбранной
     * @param bln1 флаг, не используется
     * @param row индекс строки ячейки
     * @param column индекс столбца ячейки
     * @return компонент, используемый для отображения ячейки
     */
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

        panelAction.setBorder(BorderFactory.createLineBorder(GRID_COLOR));
        return panelAction;
    }
}
