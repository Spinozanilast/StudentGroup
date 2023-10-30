package CustomComponents;

import javax.swing.*;
import java.awt.*;

/**
 * Класс RoundedPanel представляет панель с закругленными углами.
 */
public class RoundedPanel extends JPanel {
    /** Толщина границы */
    protected int strokeSize = 1;
    /** Цвет тени */
    protected Color shadowColor = Color.black;
    /** Устанавливается в true, если отбрасывает тень */
    protected boolean shady = true;
    /** Устанавливается в true, если отображается в высоком качестве */
    protected boolean highQuality = true;
    /** Устанавливается размеры закруглений */
    protected Dimension arcs = new Dimension(20, 20);
    /** Расстояние между границей тени и границы панели */
    protected int shadowGap = 5;
    /** Выступ тени.  */
    protected int shadowOffset = 4;
    /** Значение прозрачности для тени (0 - 255) */
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
     * Проверяет, включено ли высокое качество отображения компонента.
     *
     * @return <b>true</b>, если включено высокое качество, иначе <b>false</b>.
     */
    public boolean isHighQuality() {
        return highQuality;
    }

    /**
     * Устанавливает высокое качество отображения компонента.
     *
     * @param highQuality <b>true</b>, чтобы включить высокое качество, <b>false</b> в противном случае.
     */
    public void setHighQuality(boolean highQuality) {
        this.highQuality = highQuality;
    }

    /**
     * Возвращает цвет тени.
     *
     * @return объект Color.
     */
    public Color getShadowColor() {
        return shadowColor;
    }

    /**
     * Устанавливает цвет тени.
     *
     * @param shadowColor Цвет тени.
     */
    public void setShadowColor(Color shadowColor) {
        this.shadowColor = shadowColor;
    }

    /**
     * Проверяет, отображается ли тень.
     *
     * @return <b>true</b>, если тень отображается, иначе <b>false</b>.
     */
    public boolean isShady() {
        return shady;
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
     * Возвращает размер границы.
     *
     * @return значение размера границы.
     */
    public float getStrokeSize() {
        return strokeSize;
    }

    /**
     * Устанавливает значение размера границы.
     *
     * @param strokeSize Значение размера границы.
     */
    public void setStrokeSize(int strokeSize) {
        this.strokeSize = strokeSize;
    }

    /**
     * Возвращает размеры закруглений.
     *
     * @return размеры закруглений.
     */
    public Dimension getArcs() {
        return arcs;
    }

    /**
     * Устанавливает размеры закруглений.
     *
     * @param arcs новые размеры закруглений.
     */
    public void setArcs(Dimension arcs) {
        this.arcs = arcs;
    }

    /**
     * Возвращает смещение тени.
     *
     * @return значение смещения тени.
     */
    public int getShadowOffset() {
        return shadowOffset;
    }

    /**
     * Устанавливает смещение тени.
     *
     * @param shadowOffset новое значение смещения тени.
     */
    public void setShadowOffset(int shadowOffset) {
        if (shadowOffset >= 1) {
            this.shadowOffset = shadowOffset;
        } else {
            this.shadowOffset = 1;
        }
    }

    /**
     * Возвращает расстояние между границей тени и границей панели.
     *
     * @return значение расстояния.
     */
    public int getShadowGap() {
        return shadowGap;
    }

    /**
     * Устанавливает расстояние между границей тени и границей панели.
     *
     * @param shadowGap новое значение расстояния.
     */
    public void setShadowGap(int shadowGap) {
        if (shadowGap >= 1) {
            this.shadowGap = shadowGap;
        } else {
            this.shadowGap = 1;
        }
    }

    /**
     * Возвращает значение прозрачности для тени.
     *
     * @return значение прозрачности для тени.
     */
    public int getShadowAlpha() {
        return shadowAlpha;
    }

    /**
     * Устанавливает значение прозрачности для тени.
     *
     * @param shadowAlpha новое значение прозрачности для тени.
     */
    public void setShadowAlpha(int shadowAlpha) {
        if (shadowAlpha >= 0 && shadowAlpha <= 255) {
            this.shadowAlpha = shadowAlpha;
        } else {
            this.shadowAlpha = 255;
        }
    }
}