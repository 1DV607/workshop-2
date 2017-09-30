import controller.*;
import io.Dao;
import model.JsonParser;
import model.Registry;
import view.*;

public class Main {

    public static void main(String[] args) {

        View view;


        Dao dao = new Dao();
        JsonParser parser = new JsonParser();
        Registry registry = new Registry(dao, parser);
        ConsoleView consoleView = new ConsoleView();

        UserInteractionController controller = new UserInteractionController(consoleView, registry);
        consoleView.addObserver(controller);

        view = new MenuView();
        consoleView.addView(view);

        view = new AddMemberView();
        consoleView.addView(view);

        view = new EditMemberView();
        consoleView.addView(view);

        view = new AddBoatView();
        consoleView.addView(view);

        view = new EditBoatView();
        consoleView.addView(view);



    }

}
