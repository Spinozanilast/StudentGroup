package CustomComponents.CustomTableActionCells;

import java.awt.Component;
import javax.swing.*;

/**
 * Класс, представляющий поведение ячейки таблицы для ActionPanel
 *
 * @author Будчанин В.А.
 * @version 1.1
 */
public class TableActionCellEditor extends DefaultCellEditor {
    /**
     * Совокупность событий по нажатию трёх кнопок.
     */
    private TableActionCellEvent event;

    /**
     * Конструктор класса TableActionCellEditor. 
     *
     * @param event объект, реализующий интерфейс TableActionCellEvent 
     */
    public TableActionCellEditor(TableActionCellEvent event) {
        super(new JTextField());
        this.event = event;
    }

    /**
     * Метод получает компонент, который будет использоваться в качестве редактора ячейки таблицы.
     *
     * @param jtable таблица, для которой выполняется редактирование
     * @param o значение ячейки
     * @param bln флаг, указывающий, является ли ячейка выбранной
     * @param row индекс строки ячейки
     * @param column индекс столбца ячейки
     * @return компонент, используемый в качестве редактора ячейки
     */
    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        super.getTableCellEditorComponent(jtable, o, bln, row, column);
        PanelAction action = new PanelAction();
        action.initEvent(event, row, jtable);
        action.setBackground(jtable.getSelectionBackground());
        return action;
    }
}