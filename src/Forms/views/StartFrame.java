package Forms.views;

import CustomComponents.AddGroupPanel;
import CustomComponents.ThreeActionLabelsPanel;

import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;

/**
 * Класс StartFrame, являющийся начальной стадией создания учебной группы.
 */
public class StartFrame extends JFrame {
    public static final Color BACKGROUND_COLOR = new Color(243,243,243);
    public static final Color GROUP_PANEL_BACKGROUND_COLOR = new Color(239,255,255);
    public static final Color GROUP_NON_EDITABLE_FOREGROUND = new Color(29,105,200);
    public static final Color TEXT_BOXES_BACKGROUND_COLOR = new Color(193,228,228, 179);
    public static final Font FONT_NON_EDITABLE = new Font("Montserrat", Font.BOLD, 20);
    private final ThreeActionLabelsPanel threeActionLabelsPanel = new ThreeActionLabelsPanel(Color.BLACK, StartFrame.BACKGROUND_COLOR, "Номер группы", "Номер курса", "ФИО старосты");
    private JPanel layoutGroupsPanel;

    /**
     * Конструктор класса StartForm.
     */
    public StartFrame() {
        initFormState();
        initLayoutPanel();
    }

    /**
     * Инициализирует состояние формы.
     */
    private void initFormState(){
        setTitle("Студенческая группа");
        ImageIcon icon = new ImageIcon("assets/GroupIcon.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        layoutGroupsPanel = new JPanel();
    }

    /**
     * Инициализирует панель макета.
     */
    private void initLayoutPanel(){
        layoutGroupsPanel.setBackground(BACKGROUND_COLOR);
        layoutGroupsPanel.setLayout(new BoxLayout(layoutGroupsPanel, BoxLayout.Y_AXIS));
        layoutGroupsPanel.add(Box.createVerticalStrut(15));
        JScrollPane scrollPane = new JScrollPane(layoutGroupsPanel);
        add(layoutGroupsPanel);
    }

    /**
     * Добавляет компонент в макет.
     *
     * @param component компонент
     * @param hasVerticalStrutBefore флаг указывающий на необходимость добавления вертикального промежутка перед компонентом
     * @param hasVerticalStrutAfter флаг указывающий на необходимость добавления вертикального промежутка после компонента
     */
    public void addComponent(Component component, boolean hasVerticalStrutBefore, boolean hasVerticalStrutAfter) {
        if (hasVerticalStrutBefore) {
            layoutGroupsPanel.add(Box.createVerticalStrut(15));
        }
        layoutGroupsPanel.add(component);
        if (hasVerticalStrutAfter) {
            layoutGroupsPanel.add(Box.createVerticalStrut(15));
        }
    }

    /**
     * Добавляет слушатель событий мыши для панели добавления группы.
     *
     * @param addGroupPanel панель добавления группы
     * @param mouseAdapter слушатель событий мыши
     */
    public void addComponentMouseListener(AddGroupPanel addGroupPanel,
                                          MouseAdapter mouseAdapter) {
        addComponent(addGroupPanel, false, true);
        addGroupPanel.setMouseClickEvent(mouseAdapter);
    }

    /**
     * Безопасно удаляет компонент из макета.
     *
     * @param component компонент
     */
    public void safelyDeleteComponent(Component component){
        layoutGroupsPanel.remove(component);
        layoutGroupsPanel.revalidate();
        layoutGroupsPanel.repaint();
    }

    public JPanel getLayoutGroupsPanel() {
        return layoutGroupsPanel;
    }

    public ThreeActionLabelsPanel getThreeActionLabelsPanel() {
        return threeActionLabelsPanel;
    }
}
