package Frames.models;

import CustomComponents.GroupView;

import java.util.ArrayList;
import java.util.List;

public class GroupViews {
    /**
     * Список групп студентов-видов
     */
    private final List<GroupView> groupPanels;

    /**
     * Конструктор класса GroupViews.
     * Создает новый экземпляр GroupViews и инициализирует список groupPanels.
     */
    public GroupViews() {
        groupPanels = new ArrayList<>();
    }

    /**
     * Возвращает список панелей групп.
     *
     * @return список панелей групп
     */
    public List<GroupView> getGroupPanels() {
        return groupPanels;
    }

    /**
     * Добавляет панель группы в список.
     *
     * @param groupPanel панель группы для добавления
     */
    public void addGroupPanel(GroupView groupPanel) {
        groupPanels.add(groupPanel);
    }

}
