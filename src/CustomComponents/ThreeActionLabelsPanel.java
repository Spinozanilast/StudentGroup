package CustomComponents;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

/**
 * ThreeActionLabelsPanel - это пользовательский класс JPanel, который содержит три метки действий.
 *
 * @author Будчанин В.А.
 * @version 1.0
 */
public class ThreeActionLabelsPanel extends JPanel {

    /**
     * Путь к иконке стрелки вниз.
     */
    public static URL DOWN_ARROW_ICON;

    /**
     * Путь к иконке стрелки вверх.
     */
    public static URL UP_ARROW_ICON;

    /**
     * Цвет переднего плана меток.
     */
    private Color labelsForeground;

    /**
     * Цвет фона задней панели.
     */
    private Color backPanelColor;

    /**
     * Левая метка.
     */
    private JLabel leftLabel;

    /**
     * Центральная метка.
     */
    private JLabel centerLabel;

    /**
     * Правая метка.
     */
    private JLabel rightLabel;

    /**
     * Флаг, указывающий, направлена ли вниз левая стрелка.
     */
    private boolean isLeftArrowDown = false;

    /**
     * Флаг, указывающий, направлена ли вниз центральная стрелка.
     */
    private boolean isCenterArrowDown = false;

    /**
     * Флаг, указывающий, направлена ли вниз правая стрелка.
     */
    private boolean isRightArrowDown = false;


    /**
     * Конструктор класса ThreeActionLabelsPanel.
     *
     * @param foregroundColor Цвет переднего плана.
     * @param backgroundColor Цвет фона.
     * @param leftLabelText   Текст левой метки.
     * @param centerLabelText Текст центральной метки.
     * @param rightLabelText  Текст правой метки.
     */
    public ThreeActionLabelsPanel(Color foregroundColor, Color backgroundColor, String leftLabelText, String centerLabelText, String rightLabelText) {
        DOWN_ARROW_ICON = getClass().getResource("src/assets/DownArrowIcon.png");
        UP_ARROW_ICON = getClass().getResource("src/assets/UpArrowIcon.png");

        /**
         * Стандартный размер.
         */
        Dimension standardSize = new Dimension(640, 45);
        labelsForeground = foregroundColor;
        backPanelColor = backgroundColor;
        setLabelsTexts(leftLabelText, centerLabelText, rightLabelText);
        setMaximumSize(standardSize);
        setBackground(backgroundColor);
        setPanelLayout();
        initLablesViews(leftLabel, centerLabel, rightLabel);
    }

    /**
     * Устанавливает тексты меток.
     *
     * @param leftLabelText   Текст левой метки.
     * @param centerLabelText Текст центральной метки.
     * @param rightLabelText  Текст правой метки.
     */
    public void setLabelsTexts(String leftLabelText, String centerLabelText, String rightLabelText) {
        leftLabel = new JLabel(leftLabelText);
        leftLabel.setHorizontalAlignment(SwingConstants.LEFT);
        centerLabel = new JLabel(centerLabelText);
        centerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightLabel = new JLabel(rightLabelText);
        rightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    }

    /**
     * Устанавливает компоновку панели.
     */
    private void setPanelLayout() {
        setLayout(new BorderLayout());
        add(leftLabel, BorderLayout.WEST);
        add(centerLabel, BorderLayout.CENTER);
        add(rightLabel, BorderLayout.EAST);
    }


    /**
     * Инициализирует представления меток.
     *
     * @param labels Метки для инициализации.
     */
    private void initLablesViews(JLabel... labels) {
        for (JLabel label : labels) {
            label.setFont(new Font("Montserrat", Font.ITALIC, 16));
            setBackground(backPanelColor);
            label.setForeground(labelsForeground);

            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/DownArrowIcon.png")));
            label.setIcon(icon);
        }
    }

    /**
     * Устанавливает иконку для левой метки.
     *
     * @param filename Имя файла иконки.
     */
    public void setLeftLabelIcon(URL filename) {
        isLeftArrowDown = !isLeftArrowDown;
        ImageIcon icon = new ImageIcon(filename);
        leftLabel.setIcon(icon);
    }

    /**
     * Устанавливает иконку для центральной метки.
     *
     * @param filename Имя файла иконки.
     */
    public void setCenterLabelIcon(URL filename) {
        isCenterArrowDown = !isCenterArrowDown;
        ImageIcon icon = new ImageIcon(filename);
        centerLabel.setIcon(icon);
    }

    /**
     * Устанавливает иконку для правой метки.
     *
     * @param filename Имя файла иконки.
     */
    public void setRightLabelIcon(URL filename) {
        isRightArrowDown = !isRightArrowDown;
        ImageIcon icon = new ImageIcon(filename);
        rightLabel.setIcon(icon);
    }

    /**
     * Возвращает левую метку.
     *
     * @return Левая метка.
     */
    public JLabel getLeftLabel() {
        return leftLabel;
    }

    /**
     * Возвращает центральную метку.
     *
     * @return Центральная метка.
     */
    public JLabel getCenterLabel() {
        return centerLabel;
    }

    /**
     * Возвращает правую метку.
     *
     * @return Правая метка.
     */
    public JLabel getRightLabel() {
        return rightLabel;
    }

    /**
     * Проверяет, направлена ли вниз левая стрелка.
     *
     * @return true, если левая стрелка направлена вниз, иначе false.
     */
    public boolean isLeftArrowDown() {
        return isLeftArrowDown;
    }

    /**
     * Проверяет, направлена ли вниз центральная стрелка.
     *
     * @return true, если центральная стрелка направлена вниз, иначе false.
     */
    public boolean isCenterArrowDown() {
        return isCenterArrowDown;
    }

    /**
     * Проверяет, направлена ли вниз правая стрелка.
     *
     * @return true, если правая стрелка направлена вниз, иначе false.
     */
    public boolean isRightArrowDown() {
        return isRightArrowDown;
    }

    /**
     * Перечисление типов меток.
     */
    public enum LabelType {
        // Левая метка
        LEFT,
        // Центральная метка
        CENTER,
        // Правая метка
        RIGHT,
    }
}
