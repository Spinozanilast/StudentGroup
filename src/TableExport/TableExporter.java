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

    // Common code for opening file

    /**
     * Метод, производящий открытие созданного или перезаписанного файла
     *
     * @param filePath путь к файлу
     * @param dialogResult целочисленный результат выбора опции диалога
     */
    public void openFileChooseResult(String filePath, int dialogResult);
}
