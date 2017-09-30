package view;

import controller.UserInteractionObserver;

/**
 *
 */
public class AddMemberView extends View {

    UserInteractionObserver interactionObserver;

    @Override
    public void addObserver(UserInteractionObserver observer) {
        interactionObserver = observer;
    }


}

