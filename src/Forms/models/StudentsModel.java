package Forms.models;
import Database.Models.StudentModel;
import javax.swing.table.DefaultTableModel;
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
    private List<StudentModel> studentsData = new ArrayList<>();

    /**
     * Добавляет студента в список студентов.
     *
     * @param studentModel модель студента, которую необходимо добавить.
     */
    private void AddStudentsList(StudentModel studentModel){
        studentsData.add(studentModel);
    }

    /**
     * Устанавливает данные студентов.
     *
     * @param studentsData список студентов.
     */
    public void setStudentsData(List<StudentModel> studentsData) {
        this.studentsData = studentsData;
    }

    /**
     * Возвращает студента по индексу.
     *
     * @param index индекс студента.
     * @return модель студента или null, если индекс недействителен.
     */
    public StudentModel getStudentByIndex(int index){
        if (index < 0 || index >= studentsData.size()) return null;
        return studentsData.get(index);
    }

    /**
     * Возвращает представление таблицы в виде DefaultTableModel.
     *
     * @return DefaultTableModel, представляющая таблицу.
     */
    public static DefaultTableModel getRepresentationalTableModelWithoutGroup() {
        return new DefaultTableModel(new Object[]{
                "Номер студента",
                "Имя",
                "Фамилия",
                "Отчество",
                "Является платником",
                "Домашний адрес",
                "Текущий адрес",
                "Местный ли"
        }, 0);
    }
}