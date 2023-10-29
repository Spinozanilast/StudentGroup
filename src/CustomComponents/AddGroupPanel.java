package CustomComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Класс AddGroupPanel представляет панель для добавления группы.
 */
public class AddGroupPanel extends RoundedPanel {
    private Color labelsForeground;
    private Color backPanelColor;
    private final Color MOUSE_OVER_COLOR = new Color(201,255,255);
    private final Color NOT_ENABLED_BACK_COLOR = new Color(171, 171, 171);
    private Dimension standardSize;
    private HighResolutionImageLabel highResolutionImageLabel;

    /**
     * Создает новый экземпляр класса AddGroupPanel с указанными цветами переднего плана и фона.
     *
     * @param foregroundColor Цвет переднего плана.
     * @param backgroundColor Цвет фона.
     */
    public AddGroupPanel(Color foregroundColor, Color backgroundColor) {
        standardSize = new Dimension(640,45);
        labelsForeground = foregroundColor;
        backPanelColor = backgroundColor;

        JLabel addLabel = new JLabel("+");
        addLabel.setHorizontalAlignment(SwingConstants.CENTER);
        initLabelsViews(addLabel);

        setStrokeSize(0);
        setMaximumSize(standardSize);
        setBackground(backgroundColor);
        setLayout(new BorderLayout());

        highResolutionImageLabel = new HighResolutionImageLabel("Icons/Add-Group.png",40, 20);
        add(highResolutionImageLabel, BorderLayout.CENTER);

        highResolutionImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(MOUSE_OVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(backgroundColor);
                setShady(true);
            }
        });
    }

    /**
     * Устанавливает обработчик события щелчка мыши.
     *
     * @param mouseAdapter Обработчик события щелчка мыши.
     */
    public void setMouseClickEvent(MouseAdapter mouseAdapter) {
        highResolutionImageLabel.addMouseListener(mouseAdapter);
    }

    private void initLabelsViews(JLabel label) {
        label.setFont(new Font("Montserrat", Font.BOLD, 20));
        setBackground(backPanelColor);
        label.setForeground(labelsForeground);
    }

    /**
     * Возвращает цвет переднего плана.
     *
     * @return Цвет переднего плана.
     */
    public Color getLabelsForeground() {
        return labelsForeground;
    }

    /**
     * Устанавливает цвет переднего плана.
     *
     * @param labelsForeground Новый цвет переднего плана.
     */
    public void setLabelsForeground(Color labelsForeground) {
        this.labelsForeground = labelsForeground;
    }

    /**
     * Возвращает цвет фона.
     *
     * @return Цвет фона.
     */
    public Color getBackPanelColor() {
        return backPanelColor;
    }

    /**
     * Устанавливает цвет фона.
     *
     * @param backPanelColor Новый цвет фона.
     */
    public void setBackPanelColor(Color backPanelColor) {
        this.backPanelColor = backPanelColor;
    }

    /**
     * Возвращает стандартный размер панели.
     *
     * @return Размер панели.
     */
    public Dimension getStandardSize() {
        return standardSize;
    }

    /**
     * Устанавливает стандартный размер панели.
     *
     * @param standardSize Новый размер панели.
     */
    public void setStandardSize(Dimension standardSize) {
        this.standardSize = standardSize;
    }
}