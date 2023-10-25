import Forms.controllers.StartFrameController;
import Forms.models.GroupModel;


public class Main {
    public static void main(String[] args) {
        StartFrameController controller = new StartFrameController(new GroupModel());
        controller.showStartForm();
    }
}
