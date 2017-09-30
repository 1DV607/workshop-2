package view;

import controller.UserInteractionObserver;

/**
 *
 */
public class EditBoatView extends View {

    UserInteractionObserver interactionObserver;

    @Override
    public void addObserver(UserInteractionObserver observer) {
        interactionObserver = observer;
    }

}
