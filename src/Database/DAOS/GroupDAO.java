package Database.DAOS;

import Database.Models.GroupModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс GroupDAO предоставляет методы для взаимодействия с таблицей групп в базе данных.
 * <p>
 * Автор: Будчанин В.А.
 * Версия: 1.1
 */
public class GroupDAO {
    private Connection connectionDB;

    /**
     * Конструктор класса GroupDAO
     *
     * @param connectionDB объект Connection для подключения к базе данных
     */
    public GroupDAO(Connection connectionDB) {
        this.connectionDB = connectionDB;
    }

    /**
     * Метод для добавления группы в базу данных
     *
     * @param groupNumber      номер группы
     * @param courseNumber     номер курса
     * @param headmanFullName  полное имя старосты
     */
    public void addGroup(String groupNumber, int courseNumber, String headmanFullName) {
        String query = "INSERT INTO StudentGroups (groupNumber, course, headmanFullName) VALUES (?, ?, ?)";
        try (PreparedStatement insertStatement = connectionDB.prepareStatement(query)) {
            insertStatement.setString(1, groupNumber);
            insertStatement.setInt(2, courseNumber);
            insertStatement.setString(3, headmanFullName);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для редактирования данных группы в базе данных
     *
     * @param oldGroupNumber    старый номер группы
     * @param newGroupNumber    новый номер группы
     * @param courseNumber      номер курса
     * @param headmanFullName   полное имя старосты
     */
    public void editGroupData(String oldGroupNumber, String newGroupNumber, int courseNumber, String headmanFullName) {
        int stringEquals = oldGroupNumber.compareTo(newGroupNumber);
        try {
            if (stringEquals == 0) {
                String updateQuery = "UPDATE StudentGroups SET course = ?, headmanFullName = ? WHERE groupNumber = ?";
                PreparedStatement statement = connectionDB.prepareStatement(updateQuery);
                statement.setInt(1, courseNumber);
                statement.setString(2, headmanFullName);
                statement.setString(3, newGroupNumber);
                statement.executeUpdate();
            } else {
                deleteGroup(oldGroupNumber);
                addGroup(newGroupNumber, courseNumber, headmanFullName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для получения списка всех групп из базы данных
     *
     * @return список объектов GroupsModel, представляющих группы
     */
    public List<GroupModel> getAllGroups() {
        List<GroupModel> groupModelList = new ArrayList<>();
        String query = "SELECT * FROM StudentGroups";
        try (PreparedStatement statement = connectionDB.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String groupNumber = resultSet.getString("groupNumber");
                int course = resultSet.getInt("course");
                String headmanFullName = resultSet.getString("headmanFullName");
                GroupModel groupModel = new GroupModel(groupNumber, course, headmanFullName);
                groupModelList.add(groupModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupModelList;
    }

    /**
     * Метод для удаления группы из базы данных
     *
     * @param groupNumber номер группы
     */
    public void deleteGroup(String groupNumber) {
        String query = "DELETE FROM StudentGroups WHERE groupNumber = ?";
        try (PreparedStatement statement = connectionDB.prepareStatement(query)) {
            statement.setString(1, groupNumber);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для проверки, пуста ли таблица "StudentGroups" в базе данных.
     *
     * @return true, если таблица пуста, иначе false.
     *
     * @throws SQLException если возникает ошибка при выполнении запроса к базе данных.
     */
    public boolean isTableEmpty() {
        boolean isEmpty = false;
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM StudentGroups");
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                isEmpty = (count == 0);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isEmpty;
    }
}