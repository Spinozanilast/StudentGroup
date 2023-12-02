package Database.DAOS;

import Frames.models.Student;
import org.sqlite .SQLiteErrorCode;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс StudentDAO предоставляет методы для взаимодействия с таблицей студентов в базе данных.
 *
 * @author Будчанин В.А.
 * @version  1.0
 */
public class StudentDAO {
    /**
     * Соединение с базой данных
     */
    private final Connection connection;

    /**
     * Конструктор класса StudentDAO.
     *
     * @param connection объект Connection для подключения к базе данных
     */
    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Метод для получения списка всех студентов из конкретной группы из базы данных.
     *
     * @param groupNumber номер группы
     * @return список объектов Student, представляющих студентов
     */
    public List<Student> getAllGroupStudents(String groupNumber) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM Students WHERE groupNumber = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, groupNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int studentID = resultSet.getInt("studentID");
                    String firstName = resultSet.getString("firstname");
                    String surname = resultSet.getString("surname");
                    String middleName = resultSet.getString("middleName");
                    boolean isPayer = resultSet.getBoolean("isPayer");
                    String currentAddress = resultSet.getString("currentAddress");
                    String homeAddress = resultSet.getString("homeAddress");
                    boolean isLocal = resultSet.getBoolean("isLocal");
                    String phoneNumber = resultSet.getString("phoneNumber");
                    Student student = new Student(studentID, groupNumber, firstName, surname, middleName, isPayer,
                            homeAddress, currentAddress, isLocal, phoneNumber);
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    /**
     * Метод для добавления студента в таблицу.
     *
     * @param student модель студента, содержащая данные для добавления
     */
    public void addStudentToDB(Student student) {
        String query = "INSERT INTO Students (studentID, groupNumber, firstname, surname, middleName, isPayer, homeAddress, currentAddress, isLocal, phoneNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, student.getStudentID());
            statement.setString(2, student.getGroupNumber());
            statement.setString(3, student.getFirstName());
            statement.setString(4, student.getSurname());
            statement.setString(5, student.getMiddleName());
            statement.setBoolean(6, student.getIsPayer());
            statement.setString(7, student.getHomeAddress());
            statement.setString(8, student.getCurrentAddress());
            statement.setBoolean(9, student.getIsLocal());
            statement.setString(10, student.getPhoneNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            String message;
            if (e.getErrorCode() == SQLiteErrorCode.SQLITE_CONSTRAINT.code){
                message = "Вероятно, уже есть студент с таким номером (номером телефона или номером-идентификатором)" +
                        ", удалите данного студента или перепроверьте номер.";
            }
            else {
                message = "Произошли непредвиденные проблемы при добавлении записи в базу данных";
            }
            JOptionPane.showMessageDialog(null, message);
        }
    }

    /**
     * Обновляет информацию о студенте в базе данных.
     *
     * @param student Объект студента, содержащий обновленную информацию о студенте.
     */
    public void updateStudentInDB(Student student) {
        String query = "UPDATE Students SET groupNumber = ?, firstname = ?, surname = ?, middleName = ?, isPayer = ?, currentAddress = ?, homeAddress = ?, isLocal = ?, phoneNumber = ? WHERE studentID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, student.getGroupNumber());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getSurname());
            statement.setString(4, student.getMiddleName());
            statement.setBoolean(5, student.getIsPayer());
            statement.setString(6, student.getCurrentAddress());
            statement.setString(7, student.getHomeAddress());
            statement.setBoolean(8, student.getIsLocal());
            statement.setString(9, student.getPhoneNumber());
            statement.setInt(10, student.getStudentID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверяет наличие студента с указанным идентификатором.
     *
     * @param studentID идентификатор студента для проверки
     * @param groupNumber номер группы
     * @return true, если студент с указанным идентификатором существует; в противном случае - false
     */
    public boolean isStudentExists(String studentID, String groupNumber) {
        String query = "SELECT COUNT(*) AS count FROM Students WHERE studentID = ? AND groupNumber = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, studentID);
            statement.setString(2, groupNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Возвращает количество студентов в определенной группе.
     *
     * @param groupNumber номер группы, для которой нужно получить количество студентов
     * @return количество студентов в группе
     */
    public int getCountOfGroupStudents(String groupNumber) {
        int count = 0;
        String query = "SELECT COUNT(*) as count FROM Students WHERE groupNumber = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, groupNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    /**
     * Метод для удаления студента из базы данных.
     *
     * @param studentID идентификатор студента
     */
    public void deleteStudent(String studentID) {
        String query = "DELETE FROM Students WHERE studentID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, studentID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}