package Forms.views;

import javax.swing.*;
import java.awt.*;

/**
 * Класс GroupFrame представляет форму для отображения информации о студенческой группе.
 */
public class GroupFrame extends JFrame {
    private Color LABEL_FOREGROUND = new Color(29,105,200);
    private Color PANEL_BACKGROUND = new Color(242,250,255);
    private final JPanel mainPanel = new JPanel();
    private JPanel innerMenuPanel = new JPanel();
    private JPanel innerAttributesPanel = new JPanel();
    private JPanel innerUpPanel = new JPanel();
    private JPanel contentLayoutPanel = new JPanel();

    /**
     * Создает новый экземпляр класса GroupFrame с указанными номером группы, номером курса и ФИО старосты.
     *
     * @param groupNumber       Номер группы.
     * @param courseNumber      Номер курса.
     * @param headmanFullName   ФИО старосты.
     */
    public GroupFrame(String groupNumber, String courseNumber, String headmanFullName, String studentsCount) {
        setTitle("Данные группы " + groupNumber);
        ImageIcon icon = new ImageIcon("assets/AloneGroupIcon.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setLayouts();
        setUpPanelViewComponents(courseNumber, studentsCount, headmanFullName);
    }

    private void setLayouts() {
        mainPanel.setLayout(new BorderLayout());
        innerMenuPanel.setLayout(new BoxLayout(innerMenuPanel, BoxLayout.X_AXIS));
        innerUpPanel.setLayout(new BoxLayout(innerUpPanel, BoxLayout.X_AXIS));
        contentLayoutPanel.setLayout(new BoxLayout(contentLayoutPanel, BoxLayout.Y_AXIS));

        mainPanel.add(innerUpPanel, BorderLayout.NORTH);
        mainPanel.add(innerMenuPanel);
        mainPanel.add(contentLayoutPanel);

        innerMenuPanel.setBackground(Color.RED);

        this.add(mainPanel);
        this.setContentPane(mainPanel);
    }

    private void setUpPanelViewComponents(String course, String studentsCount, String headmanFullName){
        JPanel panelForLabels = new JPanel();
        int strutWidth = 17;

        JLabel courseLabel = new JLabel("Курс: ");
        JLabel studentsNumLabel = new JLabel("Количество студентов: ");
        JLabel headmanFullNameLabel = new JLabel("Староста: ");

        JLabel courseValueLabel = new JLabel(course);
        JLabel studentsNumValueLabel = new JLabel(studentsCount);
        JLabel headmanValueLabel = new JLabel(headmanFullName);

        panelForLabels.setLayout(new BoxLayout(panelForLabels, BoxLayout.X_AXIS));
        stylizeLabels(LABEL_FOREGROUND, courseLabel, studentsNumLabel, headmanFullNameLabel);
        stylizeLabels(Color.BLACK, courseValueLabel, studentsNumValueLabel, headmanValueLabel);

        panelForLabels.add(Box.createHorizontalStrut(strutWidth));
        panelForLabels.add(courseLabel);
        panelForLabels.add(Box.createHorizontalStrut(strutWidth));
        panelForLabels.add(courseValueLabel);
        panelForLabels.add(Box.createHorizontalGlue());
        panelForLabels.add(studentsNumLabel);
        panelForLabels.add(Box.createHorizontalStrut(strutWidth));
        panelForLabels.add(studentsNumValueLabel);
        panelForLabels.add(Box.createHorizontalGlue());
        panelForLabels.add(headmanFullNameLabel);
        panelForLabels.add(Box.createHorizontalStrut(strutWidth));
        panelForLabels.add(headmanValueLabel);

        panelForLabels.setBackground(PANEL_BACKGROUND);

        innerUpPanel.add(panelForLabels);
    }

    private void setUpButtonsPanel(){
        JButton jbtShowStudentsList = new JButton("Список студентов");
        JButton jbtShowStatistics = new JButton("Статистика");

        innerMenuPanel.add(jbtShowStudentsList);
        innerMenuPanel.add(jbtShowStatistics);
    }

    private void stylizeLabels(Color foreground, JLabel ...labels){
        for(JLabel label: labels) {
            label.setFont(new Font("Montserrat", Font.BOLD | Font.ITALIC, 24));
            label.setForeground(foreground);
        }
    }

    private void stylizeButtons(JButton ...jButtons){
        for(JButton jButton: jButtons){

        }
    }

    private void clearLayout(Container layoutComponent, boolean isRepaint) {
        layoutComponent.removeAll();
        layoutComponent.revalidate();
        if (isRepaint) {
            layoutComponent.repaint();
        }
    }
}