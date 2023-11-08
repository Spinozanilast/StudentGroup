package CustomComponents.CustomTableActionCells;

import javax.swing.JTable;

/**
 * Интерфейс, объявляющий соответствующие методы для исполнения
 * соответствующему ActionButton в последнем столбце таблицы
 *
 * @author  Будчанин В.А.
 * @version 1.0
 */
public interface TableActionCellEvent {
    /**
     * Метод вызывается при добавлении строки.
     *
     * @param rowIndex индекс строки
     * @param jTable таблица, к которой применяется действие
     */
    public void onAddRow(int rowIndex, JTable jTable);

    /**
     * Метод вызывается при удалении строки.
     *
     * @param rowIndex индекс строки
     * @param jTable таблица, к которой применяется действие
     */
    public void onDelete(int rowIndex, JTable jTable);

    /**
     * Метод вызывается при обновлении базы данных.
     *
     * @param rowIndex индекс строки
     * @param jTable таблица, к которой применяется действие
     */
    public void onUpdateDB(int rowIndex, JTable jTable);
}