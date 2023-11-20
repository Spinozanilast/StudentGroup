package Forms.views;

import Forms.controllers.StartFrameController;
import Forms.models.GroupsModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Класс SplashFrame, который наследуется от JFrame.
 *
 * @author Будчанин В.А.
 * @version 1.0
 */
public class SplashFrame extends JFrame {
    /**
     * Название министерства образования.
     */
    private final String EDUCATION_MINISTRY_NAME = "МИНИСТЕРСТВО ОБРАЗОВАНИЯ РЕСПУБЛИКИ БЕЛАРУСЬ";

    /**
     * Название университета.
     */
    private final String UNIVERSITY_NAME = "БЕЛОРУССКИЙ НАЦИОНАЛЬНЫЙ ТЕХНИЧЕСКИЙ УНИВЕРСИТЕТ";

    /**
     * Название факультета.
     */
    private final String FACULTY_NAME = "Факультет информационных технологий и робототехники";

    /**
     * Название кафедры.
     */
    private final String DEPARTMENT_NAME = "Кафедра программного обеспечения информационных систем и технологий";

    /**
     * Конструктор класса SplashFrame.
     */
    public SplashFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        initView();
    }

    /**
     * Метод для инициализации интерфейса.
     */
    private void initView() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel jlblMinistryName = new JLabel(EDUCATION_MINISTRY_NAME);
        jlblMinistryName.setHorizontalAlignment(JLabel.CENTER);
        JLabel jlblUniversityName = new JLabel(UNIVERSITY_NAME);
        jlblUniversityName.setHorizontalAlignment(JLabel.CENTER);
        JLabel jlblFacultyName = new JLabel(FACULTY_NAME);
        jlblFacultyName.setHorizontalAlignment(JLabel.CENTER);
        JLabel jlblDepartmentName = new JLabel(DEPARTMENT_NAME);
        jlblDepartmentName.setHorizontalAlignment(JLabel.CENTER);
        addComponentsBoxes(10, jlblMinistryName, jlblUniversityName, jlblFacultyName, jlblDepartmentName);
        add(Box.createVerticalStrut(100));


        JLabel jlblCourseWorkText = new JLabel("Курсовая работа");
        JLabel jlblDisciplineText = new JLabel("По дисциплине \"Программирование на языке Java\"");
        JLabel jlblTitleText = new JLabel("на тему \"Студенческая группа\"");
        addComponentsBoxes(50, jlblCourseWorkText);
        addComponentsBoxes(10, jlblDisciplineText, jlblTitleText);

        JLabel jlblStudentStart = new JLabel("Выполнил:");
        JLabel jlblStudentGroupText = new JLabel("студент группы 10702221");
        JLabel jlblStudentNameText = new JLabel("Будчанин Вадим Александрович");
        addComponentsBordered(jlblStudentStart, jlblStudentGroupText, false);
        addComponentsBordered(null, jlblStudentNameText, true);


        JLabel jlblLeaderStart = new JLabel("Руководитель: к.ф.-м.н., доц");
        JLabel jlblLeaderText = new JLabel("к.ф.-м.н., доц");
        JLabel jlblLeaderNameText = new JLabel("Сидорик Валерий Владимирович");
        addComponentsBordered(jlblLeaderStart, jlblLeaderText, false);
        addComponentsBordered(null, jlblLeaderNameText, true);

        JLabel jlblLocationYearText = new JLabel("Минск 2023");
        addComponentsBoxes(40, jlblLocationYearText);

        addButtonsWithActions();
    }

    /**
     * Метод для добавления кнопок с действиями.
     */
    private void addButtonsWithActions() {
        JButton jbtExit = new JButton("Выйти");
        JButton jbtNext = new JButton("Далее");

        jbtExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        jbtNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartFrameController controller = new StartFrameController(new GroupsModel());
                controller.showStartForm();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(jbtExit);
        panel.add(jbtNext);

        add(panel);

    }

    /**
     * Метод для добавления компонентов в контейнеры Box.
     *
     * @param verticalStrut Размер вертикального пространства между компонентами.
     * @param components Компоненты для добавления.
     */
    private void addComponentsBoxes(int verticalStrut, Component... components){
        for(Component component: components){
            Box componentBox = Box.createHorizontalBox();
            componentBox.add(Box.createHorizontalGlue());
            componentBox.add(component);
            componentBox.add(Box.createHorizontalGlue());
            add(componentBox);
            add(Box.createVerticalStrut(verticalStrut));
        }
    }

    /**
     * Метод для добавления компонентов с границами.
     *
     * @param componentLeft Компонент для добавления слева.
     * @param componentRight Компонент для добавления справа.
     * @param isOneElement Флаг, указывающий, является ли компонент единственным элементом.
     */
    private void addComponentsBordered(Component componentLeft, Component componentRight, boolean isOneElement){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        if (!isOneElement) {
            panel.add(componentLeft, BorderLayout.WEST);
        }
        panel.add(componentRight, BorderLayout.EAST);

        add(panel);
    }
}
