package controller;

import model.Registry;
import view.ConsoleView;
import view.View;

/**
 *
 */
public abstract class Controller {

    private Registry registry;
    private View view;
    private ConsoleView consoleView;

    public Controller(View view, ConsoleView consoleView, Registry registry) {
        this.view = view;
        this.consoleView = consoleView;
        this.registry = registry;
    }

    public Controller() {

    }

    public abstract void onSubmit(String userInput);

    public abstract void onShow();

    public void onSelect(int selection) {

    }
}
