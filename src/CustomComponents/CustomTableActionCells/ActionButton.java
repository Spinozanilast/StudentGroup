package CustomComponents.CustomTableActionCells;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

/**
 * Кнопка действия с пользовательским отображением.
 *
 * <p>Кнопка, которая отображает действие с пользовательским оформлением.
 * Изменяет свой вид при нажатии и отпускании мыши.
 * </p>
 *
 * @version 1.0
 * @author Будчанин В.А.
 */
public class ActionButton extends JButton {
    private boolean mousePressed;

    /**
     * Создает экземпляр кнопки действия.
     */
    public ActionButton() {
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(3, 3, 3, 3));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePressed = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mousePressed = false;
                repaint();
            }
        });
    }

    /**
     * Переопределенный метод для отрисовки компонента.
     *
     * <p>Отрисовывает компонент с использованием графического контекста g.
     * Устанавливает режимы сглаживания и интерполяции для улучшенного отображения.
     * Заливает компонент эллипсом определенного размера и цвета в зависимости от состояния нажатия мыши.
     * Отображает иконку внутри эллипса.
     * </p>
     *
     * @param g графический контекст для отрисовки компонента
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height);
        int x = (width - size) / 2;
        int y = (height - size) / 2;
        if (mousePressed) {
            g2.setColor(new Color(190, 244, 244));
        } else {
            g2.setColor(new Color(223, 250, 255));
        }
        g2.fill(new Ellipse2D.Double(x, y, size, size));
        g2.drawImage(((ImageIcon) getIcon()).getImage(), x + 3, y + 3, 18, 18, null);
        g2.dispose();
    }
}