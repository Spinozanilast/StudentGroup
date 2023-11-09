package Forms.views;

import CustomComponents.PillButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

/**
 * Класс GroupFrame представляет форму для отображения информации о студенческой группе.
 *
 * @author Будчанин В.А.
 * @version 1.1 04.11.2023
 */
public class GroupFrame extends JFrame {
    public static final Dimension BUTTON_PREFFERED_SIZE = new Dimension(200,40);
    public static final Dimension PILLS_PREFFERED_SIZE = new Dimension(100,30);
    private final Color LABEL_FOREGROUND = new Color(29,105,200);
    private final Color PANEL_BACKGROUND = new Color(242,250,255);
    private final Color BUTTON_BACKGROUND = new Color(0,95,184);
    private final JPanel pnlMain = new JPanel();
    private final JPanel pnlInnerMenu = new JPanel();
    private final JPanel pnlInnerAttributes = new JPanel();
    private final JPanel pnlInnerUp = new JPanel();
    private final JPanel pnlContentLayout = new JPanel();
    private JLabel lblStudentsNumValue;
    private JButton jbtShowStudentsList;
    private String groupNumber;
    private String studentsNum;
    private JButton jbtToWordExport;
    private JButton jbtToExcelExport;

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
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        pnlInnerMenu.setLayout(new BoxLayout(pnlInnerMenu, BoxLayout.X_AXIS));
        pnlInnerAttributes.setLayout(new BoxLayout(pnlInnerAttributes, BoxLayout.X_AXIS));
        pnlInnerUp.setLayout(new BoxLayout(pnlInnerUp, BoxLayout.X_AXIS));
        pnlContentLayout.setLayout(new BoxLayout(pnlContentLayout, BoxLayout.Y_AXIS));
        pnlMain.add(pnlInnerUp);
        pnlMain.add(pnlInnerMenu);
        pnlMain.add(pnlInnerAttributes);
        pnlMain.add(pnlContentLayout);
        add(pnlMain);
        setContentPane(pnlMain);
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

        JLabel lblCourse = new JLabel("Курс: ");
        JLabel lblStudentsNum = new JLabel("Количество студентов: ");
        JLabel lblHeadmanFullName = new JLabel("Староста: ");
        JLabel lblCourseValue = new JLabel(course);
        JLabel lblHeadmanValue = new JLabel(headmanFullName);
        lblStudentsNumValue = new JLabel(studentsCount);

        pnlInnerUp.setLayout(new BoxLayout(pnlInnerUp, BoxLayout.X_AXIS));

        stylizeLabels(LABEL_FOREGROUND, lblCourse, lblStudentsNum, lblHeadmanFullName);
        stylizeLabels(Color.BLACK, lblCourseValue, lblStudentsNumValue, lblHeadmanValue);

        pnlInnerUp.add(Box.createHorizontalStrut(strutWidth));
        pnlInnerUp.add(lblCourse);

        pnlInnerUp.add(Box.createHorizontalStrut(strutWidth));
        pnlInnerUp.add(lblCourseValue);

        pnlInnerUp.add(Box.createHorizontalStrut(strutWidth));
        pnlInnerUp.add(Box.createHorizontalGlue());
        pnlInnerUp.add(lblStudentsNum);

        pnlInnerUp.add(Box.createHorizontalStrut(strutWidth));
        pnlInnerUp.add(lblStudentsNumValue);

        pnlInnerUp.add(Box.createHorizontalStrut(strutWidth));
        pnlInnerUp.add(Box.createHorizontalGlue());
        pnlInnerUp.add(lblHeadmanFullName);

        pnlInnerUp.add(Box.createHorizontalStrut(strutWidth));
        pnlInnerUp.add(lblHeadmanValue);
        pnlInnerUp.add(Box.createHorizontalStrut(strutWidth));

        pnlInnerUp.setBackground(PANEL_BACKGROUND);
    }

    public JButton getExitButton(){
        JButton jbtExit = new JButton("Закрыть окно");
        jbtExit.addActionListener(e -> {
            JFrame jFrame = (JFrame) jbtExit.getTopLevelAncestor();
            jFrame.dispose();
        });
        return jbtExit;
    }

    /**
     * Метод setUpButtonsPanel устанавливает панель кнопок.
     */
    private void setUpButtonsPanel(){
        jbtShowStudentsList = new JButton("Список студентов");
        jbtShowStudentsList.setToolTipText("Вывести список всех студентов для данной группы");

        JButton jbtExit = getExitButton();
        jbtExit.setToolTipText("Закрывает окно редактирования данной группы");

//        jbtToWordExport = new JButton("Экспорт", new ImageIcon(Objects.requireNonNull(getClass().
//                getResource("/CustomComponents/Icons/WordIcon.png"))));
//        jbtToWordExport.setToolTipText("Экспортирует таблицу со студентами в Word");
//
//        jbtToExcelExport = new JButton("Экспорт", new ImageIcon(Objects.requireNonNull(getClass().
//                getResource("/CustomComponents/Icons/ExcelIcon.png"))));
//        jbtToExcelExport.setToolTipText("Экспортирует таблицу со студентами в Excel");

        stylizeButtons(Color.WHITE, BUTTON_BACKGROUND, jbtShowStudentsList);
        stylizeButtons(Color.WHITE, Color.RED, jbtExit);
//        stylizeButtons(Color.WHITE, new Color(24, 90, 189), jbtToWordExport);
//        stylizeButtons(Color.WHITE, new Color(16, 124, 65), jbtToExcelExport);

        pnlInnerMenu.setBackground(PANEL_BACKGROUND);
        pnlInnerAttributes.setBackground(PANEL_BACKGROUND);
        addButton(jbtShowStudentsList, false);
        addButton(jbtExit, true);
    }

    /**
     * Метод addButton добавляет кнопку на панель.
     *
     * @param button        Кнопка.
     * @param isEndButton   Флаг, указывающий, является ли кнопка последней на панели.
     */
    private void addButton(JButton button, boolean isEndButton){
        pnlInnerMenu.add(button);
        if (isEndButton) return;
        pnlInnerMenu.add(Box.createHorizontalGlue());
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
            jButton.setFont(new Font("Montserrat", Font.BOLD | Font.ITALIC, 14));
            jButton.setBorder(null);
            jButton.setBorder(new EmptyBorder(10,10,10,10));
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
    public JPanel getPnlContentLayout() {
        return pnlContentLayout;
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
        lblStudentsNumValue.setText(studentsNum);
    }

    public JPanel getPnlInnerAttributes() {
        return pnlInnerAttributes;
    }
}