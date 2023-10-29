package Database.Models;

public class StudentModel {
    private int studentID;
    private String groupNumber;
    private String surname;
    private String middleName;
    private String firstName;
    private boolean isPayer;
    private String homaAddress;
    private String currentAddress;
    private boolean isLocal;

    public StudentModel(int magazineStudentNumber, String groupNumber, String surname, String middleName, String firstName, boolean
            isPayer){
        studentID = magazineStudentNumber;
        this.groupNumber = groupNumber;
        this.surname = surname;
        this.middleName = middleName;
        this.firstName = firstName;
        this.isPayer = isPayer;
        this.homaAddress = null;
        this.currentAddress = null;
        this.isLocal = false;
    }

    public StudentModel(int magazineStudentNumber, String groupNumber, String surname, String middleName, String firstName, boolean
            isPayer, String homaAddress, String currentAddress, boolean isLocal){
        this(magazineStudentNumber, groupNumber, surname, middleName, firstName, isPayer);
        this.homaAddress = homaAddress;
        this.currentAddress = currentAddress;
        this.isLocal = isLocal;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean getIsPayer() {
        return isPayer;
    }

    public void setIsPayer(boolean isPayer) {
        this.isPayer = isPayer;
    }

    public String getHomaAddress() {
        return homaAddress;
    }

    public void setHomaAddress(String homaAddress) {
        this.homaAddress = homaAddress;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public boolean getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }
}
