package Forms.models;
import Database.Models.StudentDatabaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Модель студентов, реализующая интерфейс TableModelRepresentation.
 * Этот класс представляет модель данных для отображения студентов в JTable.
 *
 * @author Будчанин В.А.
 * @version 1.0
 */
public class StudentsModel{
    private List<StudentDatabaseModel> studentsData = new ArrayList<>();

    /**
     * Добавляет студента в список студентов.
     *
     * @param studentDatabaseModel модель студента, которую необходимо добавить.
     */
    private void AddStudentsList(StudentDatabaseModel studentDatabaseModel){
        studentsData.add(studentDatabaseModel);
    }

    /**
     * Устанавливает данные студентов.
     *
     * @param studentsData список студентов.
     */
    public void setStudentsData(List<StudentDatabaseModel> studentsData) {
        this.studentsData = studentsData;
    }

    /**
     * Возвращает студента по индексу.
     *
     * @param index индекс студента.
     * @return модель студента или null, если индекс недействителен.
     */
    public StudentDatabaseModel getStudentByIndex(int index){
        if (index < 0 || index >= studentsData.size()) return null;
        return studentsData.get(index);
    }

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

    public static int[] getBooleanColumnsIndexes(){
        int isPayerColumnNumber = 4;
        int isLocalColumnNumber = 7;
        return new int[] {isPayerColumnNumber, isLocalColumnNumber};
    }

    public static int[] getIntegerColumnsIndexes(){
        return new int[] { 0 };
    }
}