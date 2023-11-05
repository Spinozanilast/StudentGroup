package Database.Models;

/**
 * Модель группы.
 * <p>
 * @author Будчанин В.А.
 * @version 1.0
 */
public class GroupDatabaseModel {
    private String groupNumber;
    private int courseNumber;
    private String headmanFullName;

    /**
     * Создает новую модель группы.
     *
     * @param groupNumber      номер группы
     * @param courseNumber     номер курса
     * @param headmanFullName  полное имя старосты
     */
    public GroupDatabaseModel(String groupNumber, int courseNumber, String headmanFullName) {
        this.groupNumber = groupNumber;
        this.courseNumber = courseNumber;
        this.headmanFullName = headmanFullName;
    }

    /**
     * Возвращает номер группы.
     *
     * @return номер группы
     */
    public String getGroupNumber() {
        return groupNumber;
    }

    /**
     * Устанавливает номер группы.
     *
     * @param groupNumber номер группы
     */
    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    /**
     * Возвращает номер курса.
     *
     * @return номер курса
     */
    public int getCourseNumber() {
        return courseNumber;
    }

    /**
     * Устанавливает номер курса.
     *
     * @param courseNumber номер курса
     */
    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    /**
     * Возвращает полное имя старосты.
     *
     * @return полное имя старосты
     */
    public String getHeadmanFullName() {
        return headmanFullName;
    }

    /**
     * Устанавливает полное имя старосты.
     *
     * @param headmanFullName полное имя старосты
     */
    public void setHeadmanFullName(String headmanFullName) {
        this.headmanFullName = headmanFullName;
    }
}