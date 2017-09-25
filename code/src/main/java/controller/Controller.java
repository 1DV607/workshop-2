package controller;

import model.Registry;
import view.View;

/**
 *
 */
public abstract class Controller {

    String argument;
    Registry registry;
    View view;
    ConsoleController consoleController;

    public Controller(View view, ConsoleController consoleController, Registry registry) {
        this.view = view;
        this.consoleController = consoleController;
        this.registry = registry;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public abstract void onSubmit(String userInput);

    public abstract void onShow();

}
