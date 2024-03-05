package Frames;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Objects;

/**
 * Класс AboutAuthorFrame представляет собой окно с информацией об авторе.
 * Окно содержит фотографию автора, его имя, адрес электронной почты и номер группы.
 *
 * @author Будчанин В.А.
 * @version 1.1
 */
public class AboutAuthorFrame extends JFrame {

    /**
     * Минимальный размер окна.
     */
    private final Dimension MINIMUM_FRAME_SIZE = new Dimension(500, 800);

    /**
     * Цвет фона.
     */
    private final Color BACKGROUND_COLOR = new Color(243, 243, 243);

    /**
     * Пустая граница.
     */
    private final Border EMPTY_BORDER = BorderFactory.createEmptyBorder(10, 0, 10, 0);

    /**
     * Граница кнопки "Выход".
     */
    private final Border MATTE_BORDER = BorderFactory.createMatteBorder(10, 10, 5, 10, BACKGROUND_COLOR);

    /**
     * Метка для фотографии автора.
     */
    private JLabel lblPhoto;

    /**
     * Метка для имени автора.
     */
    private JLabel lblName;

    /**
     * Метка для адреса электронной почты автора.
     */
    private JLabel lblEmail;

    /**
     * Метка для номера группы автора.
     */
    private JLabel lblGroup;

    /**
     * Метка для текста "Автор".
     */
    private JLabel lblAuthor;

    /**
     * Кнопка для выхода из окна.
     */
    private JButton jbtnExit;

    /**
     * Конструктор класса AboutAuthorFrame.
     * Инициализирует окно и его компоненты.
     */
    public AboutAuthorFrame() {
        setMinimumSize(MINIMUM_FRAME_SIZE);
        ImageIcon icon = new ImageIcon(getClass().getResource("/assets/AuthorFrameIcon.png"));
        setIconImage(icon.getImage());

        setTitle("Об Авторе");
        setSize(MINIMUM_FRAME_SIZE.getSize().width, MINIMUM_FRAME_SIZE.getSize().height);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        createComponents();
        addComponents();

        setVisible(true);
    }

    /**
     * Метод для создания компонентов.
     */
    private void createComponents() {
        lblPhoto = new JLabel();
        setIcon(lblPhoto);
        lblPhoto.setHorizontalAlignment(SwingConstants.CENTER);
        lblPhoto.setBorder(EMPTY_BORDER);

        lblAuthor = new JLabel("Автор");
        configureLabel(lblAuthor);

        lblName = new JLabel("студент группы 10702221");
        configureLabel(lblName);

        lblEmail = new JLabel("Будчанин Вадим Александрович");
        configureLabel(lblEmail);

        lblGroup = new JLabel("vadik.trolla@gmail.com");
        configureLabel(lblGroup);

        jbtnExit = new JButton("Назад");
        jbtnExit.setBorder(MATTE_BORDER);

        jbtnExit.addActionListener(e -> {
            dispose();
        });
    }

    /**
     * Метод для добавления компонентов.
     */
    private void addComponents() {
        // Создаем панель для текстовых меток
        JPanel textPanel = new JPanel();

        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(BACKGROUND_COLOR);

        textPanel.add(lblAuthor);
        textPanel.add(lblName);
        textPanel.add(lblEmail);
        textPanel.add(lblGroup);

        add(lblPhoto, BorderLayout.NORTH);

        add(textPanel, BorderLayout.CENTER);

        add(jbtnExit, BorderLayout.SOUTH);
        getContentPane().setBackground(BACKGROUND_COLOR);
    }

    /**
     * Метод для настройки метки.
     * @param label Метка для настройки.
     */
    private void configureLabel(JLabel label) {
        label.setFont(new Font("Montserrat", Font.BOLD, 14));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * Метод для установки иконки метки из файла.
     *
     * @param label Метка, для которой устанавливается иконка.
     */
    private void setIcon(JLabel label) {

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/AuthorPhoto.jpg")));

        Image image = icon.getImage();
        // Масштабируем изображение с сохранением качества
        Image newimg = image.getScaledInstance(
                MINIMUM_FRAME_SIZE.getSize().width - 20,
                MINIMUM_FRAME_SIZE.getSize().height - 180,
                java.awt.Image.SCALE_SMOOTH);

        icon = new ImageIcon(newimg);

        label.setIcon(icon);
    }
}
