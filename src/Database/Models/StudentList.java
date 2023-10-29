package Database.Models;

import Database.Models.StudentModel;

import java.util.ArrayList;
import java.util.List;

public class StudentList {
    private List<StudentModel> studentsData = new ArrayList<StudentModel>();

    private void AddStudentsList(StudentModel studentModel){
        studentsData.add(studentModel);
    }

    public void setStudentsData(List<StudentModel> studentsData) {
        this.studentsData = studentsData;
    }

    public StudentModel getStudentByIndex(int index){
        if (index < 0 || index >= studentsData.size()) return null;
        return studentsData.get(index);
    }
}
