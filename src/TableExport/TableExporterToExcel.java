package TableExport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;

public class TableExporterToExcel implements TableExporter {
    /**
     * Метод, выполняющий экспорт содержания JTable в Excel
     *
     * @param table    JTable, содержащая данные для экспорта
     * @param filePath путь к файлу, в который нужно экспортировать данные
     */
    @Override
    public void export(JTable table, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Книга1");

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < table.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(table.getColumnName(i));
                cell.setCellStyle(workbook.createCellStyle());

                //Устанавливаем границы для клеток шапки таблицы
                CellStyle headerCellStyle = cell.getCellStyle();
                headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
                headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
                headerCellStyle.setBorderLeft(BorderStyle.THIN);
                headerCellStyle.setBorderRight(BorderStyle.THIN);

                if (i == 0){
                    headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);
                }
                else if (i == table.getColumnCount() - 1) {
                    headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
                }
            }

            for (int i = 0; i < table.getRowCount(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                for (int j = 0; j < table.getColumnCount(); j++) {
                    Cell cell = dataRow.createCell(j);
                    String value = table.getValueAt(i, j).toString();
                    cell.setCellValue(value);
                }
            }

            for (int i = 0; i < table.getColumnCount(); i++) {
                sheet.setColumnWidth(i, 5000);
            }

            try (FileOutputStream out = new FileOutputStream(filePath)) {
                workbook.write(out);
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
                File excelFile = new File(filePath);
                java.awt.Desktop.getDesktop().open(excelFile);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Произошла ошибка, невозможно открыть файл");
            }
        }
    }
}
