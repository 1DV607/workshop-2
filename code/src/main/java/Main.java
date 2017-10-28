import controller.*;
import io.Dao;
import model.Registry;
import view.*;

public class Main {

    public static void main(String[] args) {
        Dao dao = new Dao();
        Registry registry = new Registry(dao);
        ConsoleView consoleView = new ConsoleView();

        UserInteractionController controller = new UserInteractionController(consoleView, registry);
        consoleView.setObserver(controller);
        controller.launch();
    }
}
