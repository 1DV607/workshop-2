import controller.*;
import io.Dao;
import model.JsonParser;
import model.Registry;
import view.*;

public class Main {

    public static void main(String[] args) {

        View view;
        Controller controller;

        Dao dao = new Dao();
        JsonParser parser = new JsonParser();
        Registry registry = new Registry(dao, parser);
        ConsoleController consoleController = new ConsoleController();

        controller = new MenuController(new MenuView(), consoleController, registry);
        consoleController.addController(controller);

        controller = new AddMemberController(new AddMemberView(), consoleController, registry);
        consoleController.addController(controller);

        controller = new EditMemberController(new EditMemberView(), consoleController, registry);
        consoleController.addController(controller);

        controller = new RemoveMemberController(new RemoveMemberView(), consoleController, registry);
        consoleController.addController(controller);

        controller = new AddBoatController(new AddBoatView(), consoleController, registry);
        consoleController.addController(controller);

        controller = new EditBoatController(new EditBoatView(), consoleController, registry);
        consoleController.addController(controller);

        controller = new RemoveBoatController(new RemoveBoatView(), consoleController, registry);
        consoleController.addController(controller);

        consoleController.showView(0);
    }

}
