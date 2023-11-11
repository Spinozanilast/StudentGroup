package TableExport;

import javax.swing.JTable;

/**
 * Интерфейс, представляющий метод для экспорта данных
 *
 * @author Будчанин В.А.
 * @version 1.0
 */
public interface TableExporter {
    /**
     * Экспортирует данные из JTable в файл.
     *
     * @param table    JTable, содержащая данные для экспорта
     * @param filePath путь к файлу, в который нужно экспортировать данные
     */
    void export(JTable table, String filePath);
}
