package controller;

import javax.json.JsonArray;
import javax.json.JsonObject;

import model.Registry;
import view.View;
import view.MenuView;

/**
 * Handles flow of operations and logic for user input for the Menu view
 */
public class MenuController extends Controller {

    private MenuView menuView;

    public MenuController(View view, ConsoleController consoleController, Registry registry) {
        super(view, consoleController, registry);
        menuView = (MenuView)view;
    }

    @Override
    public void onShow() {
        JsonArray memberInfo = registry.getAllMembersInfoTest();
        menuView.showMemberList(memberInfo);
        menuView.showMenu();
        onSubmit(menuView.getUserInput());
    }

    @Override
    public void onSubmit(String userInput) {
        String[] split = userInput.split(" "); 
        int command = Integer.parseInt(split[0]);
        String arguments = "";

        if (split.length > 1) {
            arguments = split[1];
        }

        if (command == 7) {
            menuView.toggleVerbosity();
            consoleController.showView(0);
        }

        consoleController.showView(command, argument);
    }
}
