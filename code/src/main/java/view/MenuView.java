package view;

import controller.UserInteractionObserver;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.Scanner;

/**
 *
 */
public class MenuView extends View {

    private UserInteractionObserver interactionObserver;
    private Scanner scanner;
    private boolean verbose;

    public MenuView() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void addObserver(UserInteractionObserver observer) {
        interactionObserver = observer;
    }
}
