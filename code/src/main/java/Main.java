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
        ConsoleView consoleView = new ConsoleView();


        controller = new MenuController(consoleView, registry);
        view = new MenuView(controller);
        consoleView.addView(view);

        controller = new AddMemberController(consoleView, registry);
        view = new AddMemberView(controller);
        consoleView.addView(view);

        controller = new EditMemberController(consoleView, registry);
        view = new EditMemberView(controller);
        consoleView.addView(view);

        controller = new RemoveMemberController(consoleView, registry);
        view = new RemoveMemberView(controller);
        consoleView.addView(view);

        controller = new AddBoatController(consoleView, registry);
        view = new AddBoatView(controller);
        consoleView.addView(view);

        controller = new EditBoatController(consoleView, registry);
        view = new EditBoatView(controller);
        consoleView.addView(view);

        controller= new RemoveBoatController(consoleView, registry);
        view = new RemoveBoatView(controller);
        consoleView.addView(view);

        System.out.println("completed creating views");

    }

}
