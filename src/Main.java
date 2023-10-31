import Forms.controllers.StartFrameController;
import Forms.models.GroupsModel;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;


public class Main {
    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        StartFrameController controller = new StartFrameController(new GroupsModel());
        controller.showStartForm();
    }
}
