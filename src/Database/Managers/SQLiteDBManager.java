package Database.Managers;

import Database.DAOS.StudentDAO;
import Database.Models.StudentDatabaseModel;

import java.sql.Connection;
import java.util.List;

public class SQLiteDBManager {
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
            rowElements[5] = studentDatabaseModel.getHomaAddress();
            rowElements[6] = studentDatabaseModel.getCurrentAddress();
            rowElements[7] = studentDatabaseModel.getIsLocal();
            tableData[rowIndex] = rowElements;
            rowIndex++;
        }
        return tableData;
    }
}
