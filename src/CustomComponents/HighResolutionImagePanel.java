package CustomComponents;

import javax.swing.*;
import java.awt.*;

/**
 * Класс HighResolutionImagePanel представляет панель с высокоразрешенным изображением.
 */
public class HighResolutionImagePanel extends JPanel {
    HighResolutionImageLabel label;
    Dimension panelSize;

    /**
     * Создает новый экземпляр класса HighResolutionImagePanel с заданным высокоразрешенным изображением и размерами панели.
     *
     * @param highResolutionImageLabel Высокоразрешенная метка изображения.
     * @param panelWidth               Ширина панели.
     * @param panelHeight              Высота панели.
     */
    public HighResolutionImagePanel(HighResolutionImageLabel highResolutionImageLabel, int panelWidth, int panelHeight) {
        panelSize = new Dimension(panelWidth, panelHeight);
        label = highResolutionImageLabel;
        setMaximumSize(panelSize);
        setBorder(null);
        setOpaque(false);
        initPanelLayout();
    }

    /**
     * Задаёт компоновку для панели.
     */
    private void initPanelLayout() {
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
    }
}