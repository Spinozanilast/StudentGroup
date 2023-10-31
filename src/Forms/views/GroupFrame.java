package Forms.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Класс GroupFrame представляет форму для отображения информации о студенческой группе.
 */
public class GroupFrame extends JFrame {
    private Dimension BUTTON_PREFFERED_SIZE = new Dimension(200,40);
    private Color LABEL_FOREGROUND = new Color(29,105,200);
    private Color PANEL_BACKGROUND = new Color(242,250,255);
    private Color BUTTON_BACKGROUND = new Color(0,95,184);
    private final JPanel mainPanel = new JPanel();
    private JPanel innerMenuPanel = new JPanel();
    private JPanel innerAttributesPanel = new JPanel();
    private JPanel innerUpPanel = new JPanel();
    private JPanel contentLayoutPanel = new JPanel();
    private JLabel studentsNumValueLabel;
    private JButton jbtShowStudentsList;
    private  JButton jbtShowStatistics;

    /**
     * Создает новый экземпляр класса GroupFrame с указанными номером группы, номером курса и ФИО старосты вместе с
     * кол-вом студентов в группе.
     *
     * @param groupNumber       Номер группы.
     * @param courseNumber      Номер курса.
     * @param headmanFullName   ФИО старосты.
     * @param studentsCount     Количество студентов.
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
        setUpButtonsPanel();
    }

    /**
     * Создает новый экземпляр класса GroupFrame с указанными данными о группе (группа, курс и ФИО старосты) вместе с
     * кол-вом студентов в группе.
     *
     * @param groupData         Массив данных о группе (группа, курс и ФИО старосты).
     * @param studentsCount     Количество студентов.
     */
    public GroupFrame(String[] groupData, String studentsCount) {
        this(groupData[0], groupData[1], groupData[2], studentsCount);
    }

    private void setLayouts() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        innerMenuPanel.setLayout(new BoxLayout(innerMenuPanel, BoxLayout.X_AXIS));
        innerUpPanel.setLayout(new BoxLayout(innerUpPanel, BoxLayout.X_AXIS));
        contentLayoutPanel.setLayout(new BoxLayout(contentLayoutPanel, BoxLayout.Y_AXIS));

        mainPanel.add(innerUpPanel);
        mainPanel.add(innerMenuPanel);
        mainPanel.add(contentLayoutPanel);

        add(mainPanel);
        setContentPane(mainPanel);
    }

    private void setUpPanelViewComponents(String course, String studentsCount, String headmanFullName){
        int strutWidth = 17;

        JLabel courseLabel = new JLabel("Курс: ");
        JLabel studentsNumLabel = new JLabel("Количество студентов: ");
        JLabel headmanFullNameLabel = new JLabel("Староста: ");

        JLabel courseValueLabel = new JLabel(course);
        JLabel headmanValueLabel = new JLabel(headmanFullName);
        studentsNumValueLabel = new JLabel(studentsCount);

        innerUpPanel.setLayout(new BoxLayout(innerUpPanel, BoxLayout.X_AXIS));
        stylizeLabels(LABEL_FOREGROUND, courseLabel, studentsNumLabel, headmanFullNameLabel);
        stylizeLabels(Color.BLACK, courseValueLabel, studentsNumValueLabel, headmanValueLabel);

        innerUpPanel.add(Box.createHorizontalStrut(strutWidth));
        innerUpPanel.add(courseLabel);
        innerUpPanel.add(Box.createHorizontalStrut(strutWidth));
        innerUpPanel.add(courseValueLabel);
        innerUpPanel.add(Box.createHorizontalGlue());
        innerUpPanel.add(studentsNumLabel);
        innerUpPanel.add(Box.createHorizontalStrut(strutWidth));
        innerUpPanel.add(studentsNumValueLabel);
        innerUpPanel.add(Box.createHorizontalGlue());
        innerUpPanel.add(headmanFullNameLabel);
        innerUpPanel.add(Box.createHorizontalStrut(strutWidth));
        innerUpPanel.add(headmanValueLabel);

        innerUpPanel.setBackground(PANEL_BACKGROUND);
    }

    private void setUpButtonsPanel(){
        jbtShowStudentsList = new JButton("Список студентов");
        jbtShowStatistics = new JButton("Статистика");

        stylizeButtons(Color.WHITE, BUTTON_BACKGROUND, jbtShowStudentsList, jbtShowStatistics);

        innerMenuPanel.setBackground(PANEL_BACKGROUND);
        addButton(jbtShowStudentsList, false);
        addButton(jbtShowStatistics, true);
    }

    private void setUpContentPanel(){
        JTable table = new JTable();

    }

    private void addButton(JButton button, boolean isEndButton){
        innerMenuPanel.add(button);

        if (isEndButton) return;
        innerMenuPanel.add(Box.createHorizontalGlue());
    }

    private void stylizeLabels(Color foreground, JLabel ...labels){
        for(JLabel label: labels) {
            label.setFont(new Font("Montserrat", Font.BOLD | Font.ITALIC, 24));
            label.setForeground(foreground);
        }
    }

    private void stylizeButtons(Color foreground, Color background, JButton ...jButtons){
        for (JButton jButton: jButtons){
            jButton.setPreferredSize(BUTTON_PREFFERED_SIZE);
            jButton.setForeground(foreground);
            jButton.setBackground(background);
            jButton.setFont(new Font("Montserrat", Font.TRUETYPE_FONT, 18));
            jButton.setBorder(null);
            jButton.setBorder(new EmptyBorder(10,10,10,10));
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

    public JLabel getStudentsNumValueLabel() {
        return studentsNumValueLabel;
    }

    public void setStudentsNumValueLabel(JLabel studentsNumValueLabel) {
        this.studentsNumValueLabel = studentsNumValueLabel;
    }

    public JPanel getContentLayoutPanel() {
        return contentLayoutPanel;
    }

    public JButton getJbtShowStudentsList() {
        return jbtShowStudentsList;
    }

    public JButton getJbtShowStatistics() {
        return jbtShowStatistics;
    }
}