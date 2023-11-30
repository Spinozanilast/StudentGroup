package Database.Managers;

import Database.DAOS.StudentDAO;
import Database.Models.StudentDatabaseModel;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

/**
 * Класс-менеджер, осуществляющий периферийные действия между базой данных и контроллером.
 *
 * @author Будчанин В.А.
 * @version 1.1
 */
public class SQLiteDBHelper {

    /**
     * Получает данные таблицы студентов.
     *
     * @param connection   соединение с базой данных
     * @param groupNumber  номер группы
     * @param columnsNum   количество столбцов
     * @return двумерный массив объектов с данными таблицы студентов
     */
    public static Object[][] getStudentsTableData(Connection connection, String groupNumber, int columnsNum) {
        StudentDAO studentDAO = new StudentDAO(connection);
        List<StudentDatabaseModel> studentDatabaseModelList = studentDAO.getAllGroupStudents(groupNumber);
        Object[][] tableData = new Object[studentDatabaseModelList.size()][columnsNum];
        int rowIndex = 0;
        for (StudentDatabaseModel studentDatabaseModel : studentDatabaseModelList) {
            Object[] rowElements = new Object[columnsNum];
            rowElements[0] = studentDatabaseModel.getStudentID();
            rowElements[1] = studentDatabaseModel.getFirstName();
            rowElements[2] = studentDatabaseModel.getSurname();
            rowElements[3] = studentDatabaseModel.getMiddleName();
            rowElements[4] = studentDatabaseModel.getIsPayer();
            rowElements[5] = studentDatabaseModel.getHomeAddress();
            rowElements[6] = studentDatabaseModel.getCurrentAddress();
            rowElements[7] = studentDatabaseModel.getIsLocal();
            rowElements[8] = studentDatabaseModel.getPhoneNumber();
            tableData[rowIndex] = rowElements;
            rowIndex++;
        }
        return tableData;
    }

    /**
     * Класс, осуществляющий валидацию вводимых значений в строку о студента
     *
     * @author Будчанин В.А.
     * @version 1.3
     */
    public static class StudentsDataValidator {

        /**
         * Валидирует данные строки.
         *
         * @param dataRow      данные строки
         * @param connection   соединение с базой данных
         * @param groupNumber  номер группы
         * @return константа, указывающая на результат валидации
         */
        public static ValidityConstants validateData(Object[] dataRow, Connection connection, String groupNumber) {
            ValidityConstants validityResult = validateStudentID(dataRow[0], connection, groupNumber);
            if (validityResult == ValidityConstants.NOT_VALID_VALUES || !isValidString(dataRow[1]) || !isValidString(dataRow[2])
                    || !isValidString(dataRow[3]) || !isValidBoolean(dataRow[4])) {
                JOptionPane.showMessageDialog(null, "Не были введены обязательные данные.");
                return ValidityConstants.NOT_VALID_VALUES;
            }
            return validityResult;
        }

        /**
         * Проверяет, является ли значение допустимым идентификатором студента.
         *
         * @param value         значение, которое необходимо проверить
         * @param connection    соединение с базой данных
         * @param groupNumber   номер группы
         * @return константа, указывающая на результат проверки
         */
        private static ValidityConstants validateStudentID(Object value, Connection connection, String groupNumber) {
            if (value instanceof Integer) {
                StudentDAO studentDAO = new StudentDAO(connection);
                int studentID = (int) value;
                boolean isValid = studentID >= 0 && !studentDAO.isStudentExists(String.valueOf(studentID), groupNumber);
                if (!isValid) {
                    int choice = JOptionPane.showOptionDialog(null, "Студент с таким же номером существует. Что нужно сделать",
                            "Диалоговое окно с выбором", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                            null, new Object[]{"Обновить запись", "Отменить добавление новой записи"}, "Отменить добавление новой записи");
                    return switch (choice) {
                        /*
                        Обновить запись.
                        */
                        case 0 -> ValidityConstants.UPDATE_ROW;
                        /*
                        Закрыть редактирование.
                        */
                        case 1 -> ValidityConstants.CLOSE_EDIT;
                        /*
                        Недопустимые значения.
                        */
                        default -> ValidityConstants.NOT_VALID_VALUES;
                    };
                } else {
                    /*
                    Допустимая запись.
                    */
                    return ValidityConstants.VALID_ROW;
                }
            }
            /*
            Недопустимые значения.
            */
            return ValidityConstants.NOT_VALID_VALUES;
        }

        /**
         * Проверяет, является ли значение допустимой строкой.
         *
         * @param value  значение, которое необходимо проверить
         * @return true, если значение является допустимой строкой, иначе false
         */
        private static boolean isValidString(Object value) {
            return value instanceof String && !((String) value).isEmpty();
        }

        /**
         * Проверяет, является ли значение допустимым булевым значением.
         *
         * @param value  значение, которое необходимо проверить
         * @return true, если значение является допустимым булевым значением, иначе false
         */
        private static boolean isValidBoolean(Object value) {
            return value instanceof Boolean;
        }

        /**
         * Константы, указывающие на результат проверки.
         */
        public enum ValidityConstants {
            /**
             * Допустимая запись.
             */
            VALID_ROW,
            /**
             * Обновить запись.
             */
            UPDATE_ROW,
            /**
             * Закрыть редактирование.
             */
            CLOSE_EDIT,
            /**
             * Недопустимые значения.
             */
            NOT_VALID_VALUES,
        }
    }
}