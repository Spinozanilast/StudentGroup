package CustomComponents;

import javax.swing.*;
import java.awt.*;

/**
 * Класс HighResolutionImagePanel представляет панель с высокоразрешенным изображением.
 *
 * @author Будчанин В.А.
 * @version 1.0
 */
public class HighResolutionImagePanel extends JPanel {
    /**
     * Метка с изображением высокого разрешения.
     */
    private HighResolutionImageLabel label;

    /**
     * Размер панели.
     */
    private Dimension panelSize;

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