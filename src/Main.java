import Forms.controllers.StartFrameController;
import Forms.models.GroupsModel;


public class Main {
    public static void main(String[] args) {
        StartFrameController controller = new StartFrameController(new GroupsModel());
        controller.showStartForm();
//        GroupForm groupForm = new GroupForm("10702221", "3", "Будчанин В.А.", "31");
//        groupForm.setVisible(true);
    }
}
