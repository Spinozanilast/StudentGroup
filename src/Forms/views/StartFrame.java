package Forms.views;

import CustomComponents.AddGroupPanel;
import CustomComponents.ThreeActionLabelsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

/**
 * Класс StartFrame представляет собой начальную форму создания учебной группы.
 * <p>
 * @author Будчанин В.А.
 * @version  1.4
 */
public class StartFrame extends JFrame {
    public static final Dimension STANDARD_ELEMENT_PREFFERED_SIZE = new Dimension(640,45);
    public static final Color BACKGROUND_COLOR = new Color(243,243,243);
    public static final Color GROUP_PANEL_BACKGROUND_COLOR = new Color(239,255,255);
    public static final Color GROUP_NON_EDITABLE_FOREGROUND = new Color(29,105,200);
    public static final Color TEXT_BOXES_BACKGROUND_COLOR = new Color(193,228,228, 179);
    public static final Font FONT_NON_EDITABLE = new Font("Montserrat", Font.BOLD, 20);
    private static final Dimension STANDARD_FRAME_SIZE = new Dimension(1500,750);
    private final ThreeActionLabelsPanel threeActionLabelsPanel = new ThreeActionLabelsPanel(Color.BLACK,
            StartFrame.BACKGROUND_COLOR, "Номер группы", "Номер курса", "ФИО старосты");
    private JPanel pnlLayoutGroups;

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
        setTitle("Студенческие группа");
        ImageIcon icon = new ImageIcon("assets/GroupIcon.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(STANDARD_FRAME_SIZE);
        setPreferredSize(STANDARD_FRAME_SIZE);
        setLocationRelativeTo(null);
        pnlLayoutGroups = new JPanel();
    }

    /**
     * Инициализирует панель макета.
     */
    private void initLayoutPanel(){
        setLayout(new BorderLayout());
        pnlLayoutGroups.setBackground(BACKGROUND_COLOR);
        pnlLayoutGroups.setLayout(new BoxLayout(pnlLayoutGroups, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(pnlLayoutGroups);
        scrollPane.createVerticalScrollBar();
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
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
            pnlLayoutGroups.add(Box.createVerticalStrut(15));
        }
        pnlLayoutGroups.add(component);
        if (hasVerticalStrutAfter) {
            pnlLayoutGroups.add(Box.createVerticalStrut(15));
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
        pnlLayoutGroups.remove(component);
        pnlLayoutGroups.revalidate();
        pnlLayoutGroups.repaint();
    }

    public JPanel getPnlLayoutGroups() {
        return pnlLayoutGroups;
    }

    public ThreeActionLabelsPanel getThreeActionLabelsPanel() {
        return threeActionLabelsPanel;
    }
}
