package view;

import controller.UserInteractionObserver;

import javax.json.JsonArray;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ConsoleView implements UserInterface {

    List<View> views;
    UserInteractionObserver interactionObserver;

    public ConsoleView() {
        views = new ArrayList<>();
    }

    public void addObserver(UserInteractionObserver interactionObserver) {
        this.interactionObserver = interactionObserver;
    }

    public void addView(View view) {
        views.add(view);
        view.addObserver(interactionObserver);
    }

    @Override
    public void displayVerboseList(JsonArray jsonArray) {

    }

    @Override
    public void displayCompactList(JsonArray jsonArray) {

    }

    @Override
    public void displayAddMember() {

    }

    @Override
    public void displayEditMember() {

    }

    @Override
    public void displayAddBoat() {

    }

    @Override
    public void displayEditBoat() {

    }
}
