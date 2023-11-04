package Database.Managers;

import Database.DAOS.StudentDAO;
import Database.Models.StudentModel;
import Forms.models.StudentsModel;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.util.List;

public class SQLiteDBManager {
    public static DefaultTableModel getStudentsTableModel(Connection connection, String groupNumber) {
        DefaultTableModel studentsTableModel = StudentsModel.getRepresentationalTableModelWithoutGroup();
        StudentDAO studentDAO = new StudentDAO(connection);
        List<StudentModel> studentModelList = studentDAO.getAllGroupStudents(groupNumber);
        int columnsNumber = studentsTableModel.getColumnCount();

        for(StudentModel studentModel: studentModelList){
            Object[] rowElements = new Object[columnsNumber];
            rowElements[0] = studentModel.getStudentID();
            rowElements[1] = studentModel.getFirstName();
            rowElements[2] = studentModel.getSurname();
            rowElements[3] = studentModel.getMiddleName();
            rowElements[4] = studentModel.getIsPayer();
            rowElements[5] = studentModel.getHomaAddress();
            rowElements[6] = studentModel.getCurrentAddress();
            rowElements[7] = studentModel.getIsLocal();
            studentsTableModel.addRow(rowElements);
        }

        return studentsTableModel;
    }
}
