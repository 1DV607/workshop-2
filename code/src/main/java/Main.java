import controller.*;
import io.Dao;
import model.JsonParser;
import model.Registry;
import view.*;

public class Main {

    public static void main(String[] args) {
        Dao dao = new Dao();
        JsonParser parser = new JsonParser();
        Registry registry = new Registry(dao, parser);
        ConsoleView consoleView = new ConsoleView();

        UserInteractionController controller = new UserInteractionController(consoleView, registry);
        consoleView.setObserver(controller);
        controller.launch();
    }
}
