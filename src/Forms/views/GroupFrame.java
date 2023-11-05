package Forms.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Класс GroupFrame представляет форму для отображения информации о студенческой группе.
 *
 * @author Будчанин В.А.
 * @version 1.1 04.11.2023
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
    private JLabel lblStudentsNumValue;
    private JButton jbtShowStudentsList;
    private  JButton jbtShowStatistics;
    private String groupNumber;
    private String studentsNum;

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
        this.groupNumber = groupNumber;
        this.studentsNum = studentsCount;
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

    /**
     * Метод setLayouts устанавливает компоновки для панелей.
     */
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

    /**
     * Метод setUpPanelViewComponents устанавливает компоненты представления панели.
     *
     * @param course           Курс.
     * @param studentsCount    Количество студентов.
     * @param headmanFullName  ФИО старосты.
     */
    private void setUpPanelViewComponents(String course, String studentsCount, String headmanFullName){
        int strutWidth = 17;

        JLabel courseLabel = new JLabel("Курс: ");
        JLabel studentsNumLabel = new JLabel("Количество студентов: ");
        JLabel headmanFullNameLabel = new JLabel("Староста: ");
        JLabel courseValueLabel = new JLabel(course);
        JLabel headmanValueLabel = new JLabel(headmanFullName);
        lblStudentsNumValue = new JLabel(studentsCount);

        innerUpPanel.setLayout(new BoxLayout(innerUpPanel, BoxLayout.X_AXIS));

        stylizeLabels(LABEL_FOREGROUND, courseLabel, studentsNumLabel, headmanFullNameLabel);
        stylizeLabels(Color.BLACK, courseValueLabel, lblStudentsNumValue, headmanValueLabel);

        innerUpPanel.add(Box.createHorizontalStrut(strutWidth));
        innerUpPanel.add(courseLabel);

        innerUpPanel.add(Box.createHorizontalStrut(strutWidth));
        innerUpPanel.add(courseValueLabel);

        innerUpPanel.add(Box.createHorizontalGlue());
        innerUpPanel.add(studentsNumLabel);

        innerUpPanel.add(Box.createHorizontalStrut(strutWidth));
        innerUpPanel.add(lblStudentsNumValue);

        innerUpPanel.add(Box.createHorizontalGlue());
        innerUpPanel.add(headmanFullNameLabel);

        innerUpPanel.add(Box.createHorizontalStrut(strutWidth));
        innerUpPanel.add(headmanValueLabel);

        innerUpPanel.setBackground(PANEL_BACKGROUND);
    }

    /**
     * Метод setUpButtonsPanel устанавливает панель кнопок.
     */
    private void setUpButtonsPanel(){
        jbtShowStudentsList = new JButton("Список студентов");
        jbtShowStatistics = new JButton("Статистика");
        stylizeButtons(Color.WHITE, BUTTON_BACKGROUND, jbtShowStudentsList, jbtShowStatistics);
        innerMenuPanel.setBackground(PANEL_BACKGROUND);
        addButton(jbtShowStudentsList, false);
        addButton(jbtShowStatistics, true);
    }

    /**
     * Метод addButton добавляет кнопку на панель.
     *
     * @param button        Кнопка.
     * @param isEndButton   Флаг, указывающий, является ли кнопка последней на панели.
     */
    private void addButton(JButton button, boolean isEndButton){
        innerMenuPanel.add(button);
        if (isEndButton) return;
        innerMenuPanel.add(Box.createHorizontalGlue());
    }

    /**
     * Метод stylizeLabels задает стиль для надписей.
     *
     * @param foreground    Цвет переднего плана.
     * @param labels        Надписи.
     */
    private void stylizeLabels(Color foreground, JLabel ...labels){
        for(JLabel label: labels) {
            label.setFont(new Font("Montserrat", Font.BOLD | Font.ITALIC, 24));
            label.setForeground(foreground);
        }
    }

    /**
     * Метод stylizeButtons задает стиль для кнопок.
     *
     * @param foreground    Цвет переднего плана.
     * @param background    Цвет заднего плана.
     * @param jButtons      Кнопки.
     */
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

    /**
     * Метод stylizeButtons задает стиль для кнопок.
     *
     * @param jButtons      Кнопки.
     */
    private void stylizeButtons(JButton ...jButtons){
        for(JButton jButton: jButtons){
        }
    }

    /**
     * Метод clearLayout очищает компоновку контейнера.
     *
     * @param layoutComponent   Компонент компоновки.
     * @param isRepaint         Флаг, указывающий, нужно ли перерисовать компонент после очистки.
     */
    private void clearLayout(Container layoutComponent, boolean isRepaint) {
        layoutComponent.removeAll();
        layoutComponent.revalidate();
        if (isRepaint) {
            layoutComponent.repaint();
        }
    }

    /**
     * Метод getStudentsNumValueLabel возвращает метку с количеством студентов.
     *
     * @return Метка с количеством студентов.
     */
    public JLabel getLblStudentsNumValue() {
        return lblStudentsNumValue;
    }

    /**
     * Метод setStudentsNumValueLabel устанавливает метку с количеством студентов.
     *
     * @param lblStudentsNumValue    Метка с количеством студентов.
     */
    public void setLblStudentsNumValue(JLabel lblStudentsNumValue) {
        this.lblStudentsNumValue = lblStudentsNumValue;
    }

    /**
     * Метод getContentLayoutPanel возвращает панель содержимого.
     *
     * @return Панель содержимого.
     */
    public JPanel getContentLayoutPanel() {
        return contentLayoutPanel;
    }

    /**
     * Метод getJbtShowStudentsList возвращает кнопку "Список студентов".
     *
     * @return Кнопка "Список студентов".
     */
    public JButton getJbtShowStudentsList() {
        return jbtShowStudentsList;
    }

    /**
     * Метод getJbtShowStatistics возвращает кнопку "Статистика".
     *
     * @return Кнопка "Статистика".
     */
    public JButton getJbtShowStatistics() {
        return jbtShowStatistics;
    }

    /**
     * Метод getGroupNumber возвращает номер группы.
     *
     * @return Номер группы.
     */
    public String getGroupNumber() {
        return groupNumber;
    }

    /**
     * Метод setGroupNumber устанавливает номер группы.
     *
     * @param groupNumber   Номер группы.
     */
    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    /**
     * Метод getStudentsNum возвращает количество студентов.
     *
     * @return Количество студентов.
     */
    public String getStudentsNum() {
        return studentsNum;
    }

    /**
     * Метод setStudentsNum устанавливает количество студентов.
     *
     * @param studentsNum   Количество студентов.
     */
    public void setStudentsNum(String studentsNum) {
        this.studentsNum = studentsNum;
    }
}