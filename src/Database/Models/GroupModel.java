package Database.Models;

public class GroupModel {
    public void setHeadmanFullName(String headmanFullName) {
        this.headmanFullName = headmanFullName;
    }

    private String groupNumber;
    private int courseNumber;
    private String headmanFullName;

    public GroupModel(String groupNumber, int courseNumber, String headmanFullName) {
        this.groupNumber = groupNumber;
        this.courseNumber = courseNumber;
        this.headmanFullName = headmanFullName;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getHeadmanFullName() {
        return headmanFullName;
    }
}
