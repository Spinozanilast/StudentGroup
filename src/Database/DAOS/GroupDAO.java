package Database.DAOS;

import Forms.models.GroupModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupDAO {

    private Connection connectionDB;

    public GroupDAO (Connection connectionDB){
        this.connectionDB = connectionDB;
    }

//    public GroupModel getAllGroups(){
//        GroupModel groupModel = new GroupModel();
//        String query = "SELECT * FROM StudentGroups";
//
//        try(PreparedStatement statement = connectionDB.prepareStatement(query)){
//            ResultSet resultSet = statement.executeQuery();
//            while(resultSet.next()){
//                String groupNumber = resultSet.getString("groupNumber");
//                int course = resultSet.getInt("course");
//                String headmanFullName = resultSet.getString("headmanFullName");
//
//                //TODO: Add group model to models
//
//            }
//        }
//        catch(SQLException e){
//            e.printStackTrace();
//        }
//    }

//    public void deleteGroup(String groupNumber) {
//        String query = "DELETE FROM students WHERE groupNumber = ?";
//
//        try (PreparedStatement statement = connectionDB.prepareStatement(query)) {
//            statement.setString(1, groupNumber);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
