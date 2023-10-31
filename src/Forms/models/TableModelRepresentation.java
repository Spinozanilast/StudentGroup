package Forms.models;

import javax.swing.table.DefaultTableModel;

/**
 * Модель представления таблицы.
 * Реализует интерфейс TableModelRepresentation.
 * <p>
 * Автор: Будчанин В.А.
 * Версия: 1.0
 */
public interface TableModelRepresentation {
    /**
     * Возвращает представление таблицы в виде DefaultTableModel.
     *
     * @return DefaultTableModel, представляющая таблицу.
     */
    public DefaultTableModel getRepresentationalTableModel();
}