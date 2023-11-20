package CustomComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Класс AddGroupPanel представляет панель для добавления группы.
 */
public class AddGroupPanel extends RoundedPanel {
    /**
     * Цвет переднего плана меток.
     */
    private Color labelsForeground;

    /**
     * Цвет фона задней панели.
     */
    private Color backPanelColor;

    /**
     * Цвет при наведении мыши.
     */
    private final Color MOUSE_OVER_COLOR = new Color(201,255,255);

    /**
     * Стандартный размер.
     */
    private Dimension standardSize;

    /**
     * Метка с изображением высокого разрешения.
     */
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

}