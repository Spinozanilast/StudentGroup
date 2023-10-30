package Forms.models;

import CustomComponents.InputGroupPanel;

import java.util.ArrayList;
import java.util.List;

public class GroupsModel {
    private List<InputGroupPanel> groupPanels;

    /**
     * Конструктор класса GroupsModel.
     * Создает новый экземпляр GroupsModel и инициализирует список groupPanels.
     */
    public GroupsModel() {
        groupPanels = new ArrayList<>();
    }

    /**
     * Возвращает список панелей групп.
     *
     * @return список панелей групп
     */
    public List<InputGroupPanel> getGroupPanels() {
        return groupPanels;
    }

    /**
     * Добавляет панель группы в список.
     *
     * @param groupPanel панель группы для добавления
     */
    public void addGroupPanel(InputGroupPanel groupPanel) {
        groupPanels.add(groupPanel);
    }

    /**
     * Очищает список панелей групп.
     */
    public void clearGroupPanels() {
        groupPanels.clear();
    }
}
