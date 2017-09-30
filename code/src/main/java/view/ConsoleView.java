package view;

import controller.UserInteractionObserver;

import javax.json.JsonArray;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ConsoleView implements UserInterface {

    private MenuView menuView;
    private AddMemberView addMemberView;
    private EditMemberView editMemberView;
    private AddBoatView addBoatView;
    private EditBoatView editBoatView;

    private List<View> views;
    private UserInteractionObserver interactionObserver;

    private StringFormatter formatter = new StringFormatter();

    public ConsoleView() {
        views = new ArrayList<>();

        menuView = new MenuView();
        addMemberView = new AddMemberView();
        editMemberView = new EditMemberView();
        addBoatView = new AddBoatView();
        editBoatView = new EditBoatView();

        views.add(menuView);
        views.add(addMemberView);
        views.add(editMemberView);
        views.add(addBoatView);
        views.add(editBoatView);
    }

    public void addObserver(UserInteractionObserver interactionObserver) {
        this.interactionObserver = interactionObserver;

        for (View view : views) {
            view.addObserver(interactionObserver);
        }
    }

    @Override
    public void displayVerboseList(JsonArray jsonArray) {
        displayMemberList(true);
    }

    @Override
    public void displayCompactList(JsonArray jsonArray) {
        displayMemberList(false);
    }

    private void displayMemberList(boolean verbose) {
        String list;
        
        if (verbose) {
            list = formatter.getMemberListCompact(jsonArray);
        } else {
            list = formatter.getMemberListVerbose(jsonArray);
        }

        System.out.println(listOutput);
        displayMenu();
        String selection = getInput("> ");
        interactionObserver.onCommandSelected(selection);
    }

    @Override
    public void displayAddMember() {
        System.out.println("Please enter the new member's information.");
        String socialSecurityNumber = getInput("Social security number: ");
        String firstName = getInput("First name: ");
        String lastName = getInput("Last name: ");
        String address = getInput("Address: ");

        JsonObject info = new Json.createJsonBuilder()
            .add("socialSecurityNumber", socialSecurityNumber)
            .add("firstName", firstName)
            .add("lastName", lastName)
            .add("address", address)
            .build();

        interactionObserver.onSubmitted(info);
    }

    @Override
    public void displayEditMember(JsonObject jsonMember) {

    }

    @Override
    public void displayAddBoat(JsonObject jsonMember) {
        System.out.printf("Please enter the information about %s's new boat.",
                member.getString("firstName"));
        String size = getInput("Enter length (meters): ");
        String boatType = getInput("Enter type (SailBoat/Motorsailer/Canoe/Kayak/Other): ");

        JsonObject info = new Json.createJsonBuilder()
            .add("size", size)
            .add("boatType", boatType)
            .build();

        interactionObserver.onSubmitted(info);
    }

    @Override
    public void displayEditBoat(JsonObject jsonMember, JsonObject jsonBoat) {

    }

    @Override
    public void displayError(String message) {

    }

    private void displayMenu() {
        System.out.println("MENU");
        System.out.println("==========");
        System.out.println("1. Add member");
        System.out.println("2. Edit member <member nr>");
        System.out.println("3. Remove member <member nr>");
        System.out.println("4. Add boat <member nr>");

        System.out.println("5. Edit boat <boat nr>");
        System.out.println("6. Remove boat <boat nr>");
        System.out.println("5. Edit boat <boat nr> (NOT AVAILABLE)");
        System.out.println("6. Remove boat <boat nr> (NOT AVAILABLE)");

        System.out.println("7. Change list verbosity.");
        System.out.println();
        System.out.println("Enter selection nr (Ex: '5 2.1') ");          
    }

    private String getInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print(prompt);
        return scanner.nextLine();
    }

}
