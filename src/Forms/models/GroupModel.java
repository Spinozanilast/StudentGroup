package Forms.models;

import customComponents.InputGroupPanel;

import java.util.ArrayList;
import java.util.List;

public class GroupModel {
    private List<InputGroupPanel> groupPanels;

    public GroupModel() {
        groupPanels = new ArrayList<>();
    }

    public List<InputGroupPanel> getGroupPanels() {
        return groupPanels;
    }

    public void addGroupPanel(InputGroupPanel groupPanel) {
        groupPanels.add(groupPanel);
    }

    public void clearGroupPanels() {
        groupPanels.clear();
    }
}
