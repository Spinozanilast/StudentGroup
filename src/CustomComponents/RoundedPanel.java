package CustomComponents;

import javax.swing.*;
import java.awt.*;

/**
 * Класс RoundedPanel представляет панель с закругленными углами.
 *
 * @author Будчанин В.А.
 * @version 1.0
 */
public class RoundedPanel extends JPanel {
    /**
     * Толщина границы
     */
    protected int strokeSize = 1;
    /**
     * Цвет тени
     */
    protected Color shadowColor = Color.black;
    /**
     * Устанавливается в true, если отбрасывает тень
     */
    protected boolean shady = true;
    /**
     Устанавливается в true, если отображается в высоком качестве
     */
    protected boolean highQuality = true;
    /**
     * Устанавливается размеры закруглений
     */
    protected Dimension arcs = new Dimension(20, 20);
    /**
     * Расстояние между границей тени и границы панели
     */
    protected int shadowGap = 5;
    /**
     * Выступ тени.
     */
    protected int shadowOffset = 4;
    /**
     * Значение прозрачности для тени (0 - 255)
     */
    protected int shadowAlpha = 150;

    /**
     * Создает новый экземпляр класса RoundedPanel.
     */
    public RoundedPanel() {
        super();
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int shadowGap = this.shadowGap;
        Color shadowColorA = new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha);
        Graphics2D graphics = (Graphics2D) g;

        if (highQuality) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        if (shady) {
            graphics.setColor(shadowColorA);
            graphics.fillRoundRect(
                    shadowOffset,// X позиция
                    shadowOffset,// Y позиция
                    width - strokeSize - shadowOffset, // ширина
                    height - strokeSize - shadowOffset, // высота
                    arcs.width, arcs.height);// закругления
        } else {
            shadowGap = 1;
        }

        // Рисуем закругленную непрозрачную панель с границами.
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - shadowGap, height - shadowGap, arcs.width, arcs.height);
        graphics.setColor(getForeground());
        graphics.setStroke(new BasicStroke(strokeSize));
        graphics.drawRoundRect(0, 0, width - shadowGap, height - shadowGap, arcs.width, arcs.height);

        // Восстанавливаем значения границ по умолчанию.
        graphics.setStroke(new BasicStroke());
    }

    /**
     * Устанавливает, отображается ли тень.
     *
     * @param shady <b>true</b>, чтобы отобразить тень, <b>false</b> в противном случае.
     */
    public void setShady(boolean shady) {
        this.shady = shady;
    }

    /**
     * Устанавливает значение размера границы.
     *
     * @param strokeSize Значение размера границы.
     */
    public void setStrokeSize(int strokeSize) {
        this.strokeSize = strokeSize;
    }
}