package CustomComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Класс RoundJTextField представляет текстовое поле с закругленными углами.
 *
 * @author Будчанин В.А.
 * @version 1.0
 */
public class RoundJTextField extends JTextField {
    /**
     * Форма для помещения на задний план
     */
    private Shape shape;

    /**
     * Создает новый экземпляр класса RoundJTextField с указанным размером.
     *
     * @param size Размер текстового поля.
     */
    public RoundJTextField(int size) {
        super(size);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        super.paintComponent(g);
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 5, 5);
        }
        return shape.contains(x, y);
    }
}