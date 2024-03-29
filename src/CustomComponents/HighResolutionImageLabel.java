package CustomComponents;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Класс HighResolutionImageLabel представляет метку с высокоразрешенным изображением.
 *
 * @author Будчанин В.А.
 * @version 1.0
 */
public class HighResolutionImageLabel extends JLabel {
    /**
     * Изображение в формате BufferedImage.
     */
    private BufferedImage image;

    /**
     * Ширина изображения.
     */
    private int imageWidth;

    /**
     * Высота изображения.
     */
    private int imageHeight;

    /**
     * Создает новую экземпляр класса HighResolutionImageLabel с указанным путем к изображению и размером изображения.
     *
     * @param imagePath  Путь к изображению.
     * @param imageSize  Размер изображения.
     */
    public HighResolutionImageLabel(String imagePath, int imageSize) {
        imageWidth = imageSize;
        imageHeight = imageSize;
        setImage(imagePath);
    }

    /**
     * Создает новую экземпляр класса HighResolutionImageLabel с указанным путем к изображению и размерами изображения.
     *
     * @param imagePath   Путь к изображению.
     * @param imageWidth  Ширина изображения.
     * @param imageHeight Высота изображения.
     */
    public HighResolutionImageLabel(String imagePath, int imageWidth, int imageHeight) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        setImage(imagePath);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            int x = (getWidth() - image.getWidth()) / 2;
            int y = (getHeight() - image.getHeight()) / 2;
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(image, x, y, imageWidth, imageHeight, null);
            g2d.dispose();
        }
    }

    private void setImage(String imagePath) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}