package Database.Models;

/**
 * Модель группы.
 *
 * @author Будчанин В.А.
 * @version 1.0
 */
public class GroupDatabaseModel {
    /**
     * Номер группы.
     */
    private String groupNumber;

    /**
     * Номер курса.
     */
    private int courseNumber;

    /**
     * Полное имя старосты.
     */
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
     * Возвращает номер курса.
     *
     * @return номер курса
     */
    public int getCourseNumber() {
        return courseNumber;
    }

    /**
     * Возвращает полное имя старосты.
     *
     * @return полное имя старосты
     */
    public String getHeadmanFullName() {
        return headmanFullName;
    }

}