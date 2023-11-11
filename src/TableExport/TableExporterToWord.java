package TableExport;

import org.apache.poi.xwpf.usermodel.*;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
            XWPFParagraph paragraph = document.createParagraph();

            XWPFTable wordTable = document.createTable(table.getRowCount(), table.getColumnCount());

            for (int i = 0; i < table.getColumnCount(); i++) {
                wordTable.getRow(0).getCell(i).setText(table.getColumnName(i));
            }

            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    String value = table.getValueAt(i, j).toString();
                    wordTable.getRow(i + 1).getCell(j).setText(value);
                }
            }

            try (FileOutputStream out = new FileOutputStream(filePath)) {
                document.write(out);
            }

            // Запрашиваем у пользователя, хочет ли он открыть файл
            int dialogResult = JOptionPane.showConfirmDialog (null,
                    "Запись таблицы в файл" + filePath + " произошла успешно" +
                            "\nВы хотите открыть его ?","Подтверждение", JOptionPane.YES_NO_OPTION);

            openFileChooseResult(filePath, dialogResult);

        } catch (Exception e) {
            e.printStackTrace();
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
