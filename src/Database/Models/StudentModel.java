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

    /**
     * Конструктор класса StudentModel
     *
     * @param magazineStudentNumber номер студента в журнале
     * @param groupNumber           номер группы
     * @param surname               фамилия студента
     * @param middleName            отчество студента
     * @param firstName             имя студента
     * @param isPayer               является ли студент плательщиком
     */
    public StudentModel(int magazineStudentNumber, String groupNumber, String surname, String middleName, String firstName, boolean isPayer) {
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

    /**
     * Конструктор класса StudentModel
     *
     * @param magazineStudentNumber номер студента в журнале
     * @param groupNumber           номер группы
     * @param surname               фамилия студента
     * @param middleName            отчество студента
     * @param firstName             имя студента
     * @param isPayer               является ли студент плательщиком
     * @param homaAddress           домашний адрес студента
     * @param currentAddress        текущий адрес студента
     * @param isLocal               является ли студент местным
     */
    public StudentModel(int magazineStudentNumber, String groupNumber, String surname, String middleName, String firstName, boolean isPayer, String homaAddress, String currentAddress, boolean isLocal) {
        this(magazineStudentNumber, groupNumber, surname, middleName, firstName, isPayer);
        this.homaAddress = homaAddress;
        this.currentAddress = currentAddress;
        this.isLocal = isLocal;
    }

    /**
     * Метод для получения номера студента в журнале
     *
     * @return номер студента в журнале
     */
    public int getStudentID() {
        return studentID;
    }

    /**
     * Метод для установки номера студента в журнале
     *
     * @param studentID номер студента в журнале
     */
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    /**
     * Метод для получения номера группы
     *
     * @return номер группы
     */
    public String getGroupNumber() {
        return groupNumber;
    }

    /**
     * Метод для установки номера группы
     *
     * @param groupNumber номер группы
     */
    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    /**
     * Метод для получения фамилии студента
     *
     * @return фамилия студента
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Метод для установки фамилии студента
     *
     * @param surname фамилия студента
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Метод для получения отчества студента
     *
     * @return отчество студента
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Метод для установки отчества студента
     *
     * @param middleName отчество студента
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Метод для получения имени студента
     *
     * @return имя студента
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Метод для установки имени студента
     *
     * @param firstName имя студента
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Метод для получения информации о том, является ли студент плательщиком
     *
     * @return true, если студент является плательщиком, иначе false
     */
    public boolean getIsPayer() {
        return isPayer;
    }

    /**
     * Метод для установки информации о том, является ли студент плательщиком
     *
     * @param isPayer true, если студент является плательщиком, иначе false
     */
    public void setIsPayer(boolean isPayer) {
        this.isPayer = isPayer;
    }

    /**
     * Метод для получения домашнего адреса студента
     *
     * @return домашний адрес студента
     */
    public String getHomaAddress() {
        return homaAddress;
    }

    /**
     * Метод для установки домашнего адреса студента
     *
     * @param homaAddress домашний адрес студента
     */
    public void setHomaAddress(String homaAddress) {
        this.homaAddress = homaAddress;
    }

    /**
     * Метод для получения текущего адреса студента
     *
     * @return текущий адрес студента
     */
    public String getCurrentAddress() {
        return currentAddress;
    }

    /**
     * Метод для установки текущего адреса студента
     *
     * @param currentAddress текущий адрес студента
     */
    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    /**
     * Метод для получения информации о том, является ли студент местным
     *
     * @return true, если студент является местным, иначе false
     */
    public boolean getIsLocal() {
        return isLocal;
    }

    /**
     * Метод для установки информации о том, является ли студент местным
     *
     * @param isLocal true, если студент является местным, иначе false
     */
    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }
}