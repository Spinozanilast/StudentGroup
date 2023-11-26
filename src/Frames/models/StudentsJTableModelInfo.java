package Frames.models;

/**
 * Модель студентов, реализующая интерфейс TableModelRepresentation.
 * Этот класс представляет модель данных для отображения студентов в JTable.
 *
 * @author Будчанин В.А.
 * @version 1.0
 */
public class StudentsJTableModelInfo {

    /**
     * Возвращает представление таблицы в виде DefaultTableModel.
     *
     * @return DefaultTableModel, представляющая таблицу.
     */
    public static String[] getTableColumnsNamesWithoutGroup() {
        return new String[]{
                "НОМЕР СТУДЕНТА*",
                "ИМЯ*",
                "ФАМИЛИЯ*",
                "ОТЧЕСТВО*",
                "ПЛАТНИК*",
                "ДОМАШНИЙ АДРЕС",
                "ТЕКУЩИЙ АДРЕС",
                "МЕСТНЫЙ",
                "ТЕЛЕФОН",
        };
    }

    /**
     * Этот метод возвращает индексы столбцов, которые содержат булевы значения.
     *
     * @return Массив индексов столбцов, содержащих булевы значения.
     */
    public static int[] getBooleanColumnsIndexes(){
        int isPayerColumnNumber = 4;
        int isLocalColumnNumber = 7;
        return new int[] {isPayerColumnNumber, isLocalColumnNumber};
    }

    /**
     * Этот метод возвращает индексы столбцов, которые содержат целочисленные значения.
     *
     * @return Массив индексов столбцов, содержащих целочисленные значения.
     */
    public static int[] getIntegerColumnsIndexes(){
        return new int[] { 0 };
    }
}