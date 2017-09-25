package view;

import controller.Controller;

/**
 *
 */
public abstract class View {

    private Controller controller;

    public View(Controller cont) {
        controller = cont;
    }

    public abstract void show();
}
