package Database.DAOS;

import Database.Models.StudentDatabaseModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс StudentDAO предоставляет методы для взаимодействия с таблицей студентов в базе данных.
 * <p>
 * @author Будчанин В.А.
 * @version  1.0
 */
public class StudentDAO {
    private Connection connection;

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
     * @return список объектов StudentDatabaseModel, представляющих студентов
     */
    public List<StudentDatabaseModel> getAllGroupStudents(String groupNumber) {
        List<StudentDatabaseModel> students = new ArrayList<>();
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
                    StudentDatabaseModel student = new StudentDatabaseModel(studentID, groupNumber, firstName, surname, middleName, isPayer,
                            currentAddress, homeAddress, isLocal);
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
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

    /**
     * Метод для удаления нескольких студентов из базы данных.
     *
     * @param studentIDs список идентификаторов студентов
     */
    public void deleteMultipleStudents(List<String> studentIDs) {
        String query = "DELETE FROM students WHERE studentID IN (";
        for (int i = 0; i < studentIDs.size(); i++) {
            query += "?";
            if (i < studentIDs.size() - 1) {
                query += ",";
            }
        }
        query += ")";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < studentIDs.size(); i++) {
                statement.setString(i + 1, studentIDs.get(i));
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}