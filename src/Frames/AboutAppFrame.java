package Frames;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.Objects;

/**
 * Класс AboutAppFrame представляет собой окно с информацией о приложении.
 * Окно информация с титульного листа курсовой.
 *
 * @author Будчанин В.А.
 * @version 1.1
 */
public class AboutAppFrame extends JFrame {
    /**
     * Версия приложения.
     */
    private static final String APP_VERSION = "1.0.0";

    /**
     * Минимальный размер окна.
     */
    private final Dimension MINIMUM_FRAME_SIZE = new Dimension(750, 600);

    /**
     * Цвет фона.
     */
    private final Color BACKGROUND_COLOR = new Color(243,243,243);

    /**
     * Описание приложения.
     */
    private final String APP_DESCRIPTION = """
            Приложение "Студенческая группа" предоставляет следующие возможности:
            
            1. Создание группы: Пользователь может создать новую группу, указав уникальное имя группы.
            2. Добавление студентов в группу: Пользователь может добавлять новых студентов в каждую группу, указывая их данные, такие как имя, фамилия, номер зачетной книжки и т.д.
            3. Редактирование данных студента: Пользователь может изменять данные студента, выбрав студента и внося необходимые изменения.
            4. Удаление студента из группы: Пользователь может удалить студента из группы, выбрав студента и нажав кнопку удаления.
            5. Экспорт данных группы: Пользователь может экспортировать данные группы в формате Excel или Word. Это может быть полезно для создания отчетов или анализа данных.
            
            Эта программа предоставляет удобный и эффективный способ управления студенческими группами. Она обеспечивает гибкость и контроль над данными студентов, позволяя пользователю легко добавлять, редактировать и удалять студентов, а также экспортировать данные для дальнейшего анализа или отчетности.""";

    /**
     * Конструктор класса AboutAppFrame.
     */
    public AboutAppFrame() {
        setupFrame();
        addComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Метод для настройки основных параметров окна.
     */
    private void setupFrame() {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/AboutAppIcon.png")));
        setBackground(BACKGROUND_COLOR);
        getContentPane().setBackground(BACKGROUND_COLOR);
        setMinimumSize(MINIMUM_FRAME_SIZE);
        setSize(MINIMUM_FRAME_SIZE);
        setTitle("О программе");
        setIconImage(icon.getImage());
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    }

    /**
     * Метод для добавления компонентов на панель окна.
     */
    private void addComponents() {
        JPanel pnlBottom = new JPanel(new FlowLayout());
        pnlBottom.add(createVersionLabel());
        pnlBottom.add(createExitButton());
        add(createTopPanel());
        add(pnlBottom);
    }

    /**
     * Метод для создания верхней панели окна.
     * @return JPanel объект.
     */
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        topPanel.add(createImageLabel());
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(createTextArea());

        return topPanel;
    }

    /**
     * Метод для создания метки изображения.
     * @return JLabel объект.
     */
    private JLabel createImageLabel() {
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/AppDiagram.png")));
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(500/2, 650/2 - 50,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);

        return new JLabel(imageIcon);
    }

    /**
     * Метод для создания текстовой области.
     * @return JTextArea объект.
     */
    private JTextPane createTextArea() {
        JTextPane textPane = new JTextPane();
        textPane.setBackground(BACKGROUND_COLOR);
        textPane.setText(APP_DESCRIPTION);
        textPane.setEditable(false);

        // Center align the text
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        return textPane;
    }

    /**
     * Метод для создания метки версии.
     * @return JLabel объект.
     */
    private JLabel createVersionLabel() {
        return new JLabel("Версия программы: " + APP_VERSION);
    }

    /**
     * Метод для создания кнопки выхода.
     * @return JButton объект.
     */
    private JButton createExitButton() {
        JButton exitButton = new JButton("Выход");
        exitButton.addActionListener(e -> dispose());

        return exitButton;
    }

    public static void main(String[] args) {
        AboutAppFrame frame = new AboutAppFrame();
        frame.setVisible(true);
    }
}
