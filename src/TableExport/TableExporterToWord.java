package TableExport;

import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.xwpf.usermodel.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * Класс TableExporterToWord, который реализует интерфейс TableExporter и выполняет экспорт таблицы в Word.
 *
 * @author Будчанин В.А.
 * @version 1.0
 */
public class TableExporterToWord implements TableExporter {

    /**
     * Метод, выполняющий экспорт содержания JTable в Word
     *
     * @param table    JTable, содержащая данные для экспорта
     * @param filePath путь к файлу, в который нужно экспортировать данные
     */
    @Override
    public void export(JTable table, String filePath) {
        try (XWPFDocument document = new XWPFDocument()) {

            XWPFTable wordTable = document.createTable(table.getRowCount(), table.getColumnCount());
            int rowColumnFilterIndex = 0;
            for (int i = 0; i < table.getColumnCount(); i++) {
                if (table.getColumnModel().getColumn(i).getMaxWidth() == 0){
                    continue;
                }
                //TODO: Сделать сдвиг влево по пропуску столбцов с нулевой шириной (не должны выводиться)
                XWPFTableCell headerCell = wordTable.getRow(0).getCell(rowColumnFilterIndex);
                headerCell.setText(table.getColumnName(i));
                rowColumnFilterIndex++;
            }

            for (int i = 0; i < table.getRowCount(); i++) {
                rowColumnFilterIndex = 0;
                for (int j = 0; j < table.getColumnCount(); j++) {
                    wordTable.createRow();
                    if (table.getColumnModel().getColumn(j).getMaxWidth() == 0){
                        continue;
                    }
                    String value = table.getValueAt(i, j).toString();
                    if (Objects.equals(value, "false") || Objects.equals(value, "true")) {
                        value = value.equals("false") ? "Нет" : "Да";
                    }
                    wordTable.getRow(i + 1).getCell(rowColumnFilterIndex).setText(value);
                    rowColumnFilterIndex++;
                }
            }

            try (FileOutputStream out = new FileOutputStream(filePath)) {
                document.write(out);
            }

            // Запрашиваем у пользователя, хочет ли он открыть файл
            int dialogResult = JOptionPane.showConfirmDialog(null,
                    "Запись таблицы в файл" + filePath + " произошла успешно" +
                            "\nВы хотите открыть его ?", "Подтверждение", JOptionPane.YES_NO_OPTION);

            openFileChooseResult(filePath, dialogResult);


        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Случились непредвиденные проблемы, быть может файл используется чем-то.");
        }
    }

    /**
     * Метод, производящий открытие созданного или перезаписанного файла
     *
     * @param filePath путь к файлу
     * @param dialogResult целочисленный результат выбора опции диалога
     */
    @Override
    public void openFileChooseResult(String filePath, int dialogResult) {
        if(dialogResult == JOptionPane.YES_OPTION){
            try {
                File wordFile = new File(filePath);
                java.awt.Desktop.getDesktop().open(wordFile);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Произошла ошибка, невозможно открыть файл");
            }
        }
    }
}
