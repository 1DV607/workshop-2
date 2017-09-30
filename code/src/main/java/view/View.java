package view;

import controller.UserInteractionObserver;

/**
 *
 */
public abstract class View {

    public abstract void addObserver(UserInteractionObserver observer);
}
