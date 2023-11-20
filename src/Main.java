import Forms.controllers.StartFrameController;
import Forms.models.GroupsModel;
import com.formdev.flatlaf.FlatIntelliJLaf;


public class Main {
    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        StartFrameController controller = new StartFrameController(new GroupsModel());
        controller.showStartForm();
    }
}
