package controller;

import model.Registry;
import view.UserInterface;

/**
 * Created by Caroline Nilsson on 2017-09-30.
 */
public class UserInteractionController implements UserInteractionObserver {

    UserInterface ui;
    Registry registry;

    public UserInteractionController(UserInterface ui, Registry registry) {
        this.ui = ui;
        this.registry = registry;
    }

    @Override
    public void onUserInteract(String userInput) {

    }
}
