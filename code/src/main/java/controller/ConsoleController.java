package controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Main view for console window.
 * Contains a list of Views and is responsible for showing view selected by the controller.
 */
public class ConsoleController {

    public List<Controller> controllerList;

    public ConsoleController() {
        controllerList = new ArrayList<Controller>();
    }

    public void showView(int viewSelection) {
        controllerList.get(viewSelection).onShow();
    }

    public void showView(int viewSelection, String argument) {
        controllerList.get(viewSelection).setArgument(argument);
        showView(viewSelection);
    }

    public void addController(Controller controller) {
        controllerList.add(controller);
    }
}
