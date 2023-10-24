import Forms.controllers.StartFormController;
import Forms.models.GroupModel;


public class Main {
    public static void main(String[] args) {
        StartFormController controller = new StartFormController(new GroupModel());
        controller.showStartForm();
    }
}
