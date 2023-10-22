package customComponents;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.geom.RoundRectangle2D;

/**
 * A custom JTextField with a round shape.
 */
public class RoundJTextField extends JTextField {
    private Shape shape;

    /**
     * Constructs a RoundJTextField with the specified size.
     *
     * @param size the number of columns to display.
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
    //    protected void paintBorder(Graphics g) {
    //        g.setColor(getForeground());
//        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
//    }
    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 5, 5);
        }
        return shape.contains(x, y);
    }
}