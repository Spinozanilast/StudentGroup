package Forms.models;

import CustomComponents.InputGroupPanel;

import java.util.ArrayList;
import java.util.List;

public class GroupModel {
    private List<InputGroupPanel> groupPanels;

    /**
     * Конструктор класса GroupModel.
     * Создает новый экземпляр GroupModel и инициализирует список groupPanels.
     */
    public GroupModel() {
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
