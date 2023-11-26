import Frames.SplashFrame;
import com.formdev.flatlaf.FlatIntelliJLaf;


public class Main {
    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        SplashFrame splashFrame = new SplashFrame();
        splashFrame.setVisible(true);
    }
}
