package Frames.models;

/**
 * Модель студента, т.е. сведения о его номере, группе, ФИО, местах жительства, а также о том, местный ли и обучается ли
 * на платной основе
 *
 * @author Будчанин В.А.
 * @version  1.1
 */
public class Student {
    /**
     * Идентификатор студента.
     */
    private final int studentID;

    /**
     * Номер группы студента.
     */
    private final String groupNumber;

    /**
     * Фамилия студента.
     */
    private final String surname;

    /**
     * Отчество студента.
     */
    private final String middleName;

    /**
     * Имя студента.
     */
    private final String firstName;

    /**
     * Показывает, является ли студент плательщиком.
     */
    private final boolean isPayer;

    /**
     * Домашний адрес студента.
     */
    private String homaAddress;

    /**
     * Текущий адрес студента.
     */
    private String currentAddress;

    /**
     * Показывает, является ли студент местным.
     */
    private boolean isLocal;

    /**
     * Номер телефона студента.
     */
    private String phoneNumber;


    /**
     * Конструктор класса Student
     *
     * @param magazineStudentNumber номер студента в журнале
     * @param groupNumber           номер группы
     * @param surname               фамилия студента
     * @param middleName            отчество студента
     * @param firstName             имя студента
     * @param isPayer               является ли студент плательщиком
     */
    public Student(int magazineStudentNumber, String groupNumber, String surname, String middleName, String firstName, boolean isPayer) {
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
     * Конструктор класса Student
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
     * @param phoneNumber           номер телефона студента
     */
    public Student(int magazineStudentNumber, String groupNumber, String firstName, String surname, String middleName, boolean isPayer, String homaAddress, String currentAddress, boolean isLocal, String phoneNumber) {
        this(magazineStudentNumber, groupNumber, surname, middleName, firstName, isPayer);
        this.homaAddress = homaAddress;
        this.currentAddress = currentAddress;
        this.isLocal = isLocal;
        this.phoneNumber = phoneNumber;
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
     * Метод для получения номера группы
     *
     * @return номер группы
     */
    public String getGroupNumber() {
        return groupNumber;
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
     * Метод для получения отчества студента
     *
     * @return отчество студента
     */
    public String getMiddleName() {
        return middleName;
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
     * Метод для получения информации о том, является ли студент плательщиком
     *
     * @return true, если студент является плательщиком, иначе false
     */
    public boolean getIsPayer() {
        return isPayer;
    }

    /**
     * Метод для получения домашнего адреса студента
     *
     * @return домашний адрес студента
     */
    public String getHomeAddress() {
        return homaAddress;
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
     * Метод для получения информации о том, является ли студент местным
     *
     * @return true, если студент является местным, иначе false
     */
    public boolean getIsLocal() {
        return isLocal;
    }

    /**
     * Получить номер телефона.
     * @return номер телефона
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Этот класс представляет модель данных для отображения студентов в JTable.
     *
     * @author Будчанин В.А.
     * @version 1.0
     */
    public static class StudentsJTableModelInfo {

        /**
         * Возвращает представление таблицы в виде DefaultTableModel.
         *
         * @return DefaultTableModel, представляющая таблицу.
         */
        public static String[] getTableColumnsNamesWithoutGroup() {
            return new String[]{
                    "НОМЕР СТУДЕНТА*",
                    "ИМЯ*",
                    "ФАМИЛИЯ*",
                    "ОТЧЕСТВО*",
                    "ПЛАТНИК*",
                    "ДОМАШНИЙ АДРЕС",
                    "ТЕКУЩИЙ АДРЕС",
                    "МЕСТНЫЙ",
                    "ТЕЛЕФОН",
            };
        }

        /**
         * Этот метод возвращает индексы столбцов, которые содержат булевы значения.
         *
         * @return Массив индексов столбцов, содержащих булевы значения.
         */
        public static int[] getBooleanColumnsIndexes(){
            int isPayerColumnNumber = 4;
            int isLocalColumnNumber = 7;
            return new int[] {isPayerColumnNumber, isLocalColumnNumber};
        }

        /**
         * Этот метод возвращает индексы столбцов, которые содержат целочисленные значения.
         *
         * @return Массив индексов столбцов, содержащих целочисленные значения.
         */
        public static int[] getIntegerColumnsIndexes(){
            return new int[] { 0 };
        }
    }
}