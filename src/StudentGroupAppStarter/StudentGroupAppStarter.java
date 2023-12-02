package StudentGroupAppStarter;

import Frames.SplashFrame;
import com.formdev.flatlaf.FlatIntelliJLaf;


/**
 * Главный класс приложения.
 *
 * @author Будчанин В.А.
 * @version 1.0
 */
public class StudentGroupAppStarter {
    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        // Установка темы FlatIntelliJLaf
        FlatIntelliJLaf.setup();

        // Создание и отображение начального экрана
        SplashFrame splashFrame = new SplashFrame();
        splashFrame.setVisible(true);
    }
}
