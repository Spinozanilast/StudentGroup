package Frames;

import Frames.models.GroupViews;
import javax.swing.*;
import java.awt.*;

/**
 * Класс SplashFrame, представляющий титульный лист курсовой работы.
 *
 * @author Будчанин В.А.
 * @version 1.0
 */
public class SplashFrame extends JFrame {
    /**
     * Конструктор класса SplashFrame.
     */
    public SplashFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension minimumSize = new Dimension(850, 730);
        setMinimumSize(minimumSize);

        Timer timer = new Timer(60000, e -> dispose());
        timer.start();
        setTitle("Титульный лист");
        ImageIcon icon = new ImageIcon("assets/TitleFrameIcon.png");
        setIconImage(icon.getImage());
        setLocationRelativeTo(null);
        initView();
    }

    /**
     * Метод для инициализации интерфейса.
     */
    private void initView() {
        setupLayout();
        addNames();
        addTexts();
        addInfo();
        addBottomPanel();
    }

    /**
     * Метод для настройки макета.
     */
    private void setupLayout() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setBackground(new Color(243, 243, 243));
    }

    /**
     * Метод для добавления названий.
     */
    private void addNames() {
        String[] names = {"МИНИСТЕРСТВО ОБРАЗОВАНИЯ РЕСПУБЛИКИ БЕЛАРУСЬ",
                "БЕЛОРУССКИЙ НАЦИОНАЛЬНЫЙ ТЕХНИЧЕСКИЙ УНИВЕРСИТЕТ",
                "Факультет информационных технологий и робототехники",
                "Кафедра программного обеспечения информационных систем и технологий"};
        Font font = new Font("Calibri", Font.BOLD, 14);
        for (String name : names) {
            JLabel label = new JLabel(name);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setFont(font);
            addComponentsBoxes(10, label);
        }
    }

    /**
     * Метод для добавления текстовых меток.
     */
    private void addTexts() {
        add(Box.createVerticalStrut(100));

        String[] texts = {"Курсовая работа",
                "По дисциплине \"Программирование на языке Java\"",
                "Студенческая группа"};

        int[] sizes = {16, 14, 20};

        for (int i = 0; i < texts.length; i++) {
            JLabel label = new JLabel(texts[i]);
            label.setFont(new Font("Calibri", Font.BOLD, sizes[i]));
            addComponentsBoxes(i == 0 ? 20 : 10, label);
        }
    }

    /**
     * Метод для добавления информационных меток.
     */
    private void addInfo() {
        String[] info = {"Выполнил: студент группы 10702221",
                "Будчанин Вадим Александрович",
                "Руководитель: к.ф.-м.н., доц",
                "Сидорик Валерий Владимирович"};

        JPanel pnlLabelsWithIcon = new JPanel(new FlowLayout(FlowLayout.CENTER, 150, 10));
        ImageIcon icon = new ImageIcon("assets/BigGroupIcon.png");
        JLabel iconLabel = new JLabel(icon);
        JPanel jPanelLeft = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanelLeft.add(iconLabel, gbc);
        pnlLabelsWithIcon.add(jPanelLeft);

        JPanel jPanelRight = new JPanel();
        jPanelRight.setLayout(new BoxLayout(jPanelRight, BoxLayout.Y_AXIS));

        for (int i = 0; i < info.length; i++) {
            JLabel label = new JLabel(info[i]);
            label.setFont(new Font("Calibri", Font.BOLD, 14));
            jPanelRight.add(label);

            if (i == 1) {
                jPanelRight.add(Box.createVerticalStrut(20));
            }
        }

        pnlLabelsWithIcon.add(jPanelRight);
        add(pnlLabelsWithIcon);
    }

    /**
     * Метод для добавления текста о местоположении и годе.
     */
    private JPanel addLocationYearText() {
        JLabel jlblLocationYearText = new JLabel("Минск, 2023");
        jlblLocationYearText.setFont(new Font("Calibri", Font.BOLD, 14));

        JPanel bottomOutYearPanel = new JPanel(new BorderLayout());

        JPanel pnlForLabel = new JPanel();
        pnlForLabel.add(jlblLocationYearText);
        bottomOutYearPanel.add(pnlForLabel, BorderLayout.NORTH);

        return bottomOutYearPanel;
    }

    /**
     * Метод для добавления кнопок с действиями.
     */
    private JPanel addButtonsWithActions() {
        JButton jbtExit = new JButton("Выйти");
        JButton jbtNext = new JButton("Далее");

        jbtExit.addActionListener(e -> System.exit(0));

        jbtNext.addActionListener(e -> {
            GroupsFrame groupsFrame = new GroupsFrame(new GroupViews());
            groupsFrame.setVisible(true);
            dispose();
        });

        JPanel buttonsPanel = new JPanel();
        JPanel bottomOutPanel = new JPanel(new BorderLayout());
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(jbtExit);
        buttonsPanel.add(jbtNext);

        bottomOutPanel.add(buttonsPanel, BorderLayout.SOUTH);
        return bottomOutPanel;
    }


    /**
     * Метод для добавления нижней панели.
     * Этот метод создает новую панель с BorderLayout.
     * Затем он добавляет результаты методов addLocationYearText() и addButtonsWithActions()
     * в северную и южную части этой панели соответственно.
     * Наконец, он добавляет эту панель в южную часть текущего компонента.
     */
    private void addBottomPanel() {
        JPanel pnlGridBottom = new JPanel(new BorderLayout());
        JPanel pnlGridContent = new JPanel(new GridLayout(2, 1));

        pnlGridContent.add(addLocationYearText());
        pnlGridContent.add(addButtonsWithActions());

        pnlGridBottom.add(new JPanel(), BorderLayout.CENTER);
        pnlGridBottom.add(pnlGridContent, BorderLayout.SOUTH);

        add(pnlGridBottom, BorderLayout.SOUTH);
    }

    /**
     * Метод для добавления компонентов в контейнеры Box.
     *
     * @param verticalStrut Размер вертикального пространства между компонентами.
     * @param components    Компоненты для добавления.
     */
    private void addComponentsBoxes(int verticalStrut, Component... components) {
        for (Component component : components) {
            Box componentBox = Box.createHorizontalBox();
            componentBox.add(Box.createHorizontalGlue());
            componentBox.add(component);
            componentBox.add(Box.createHorizontalGlue());
            add(componentBox);
            add(Box.createVerticalStrut(verticalStrut));
        }
    }

}