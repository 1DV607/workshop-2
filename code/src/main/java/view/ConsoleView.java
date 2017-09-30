package view;

import java.util.Scanner;

import controller.UserInteractionObserver;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
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

    private boolean verbose;
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
        verbose = true;
        displayMemberList(jsonArray);
    }

    @Override
    public void displayCompactList(JsonArray jsonArray) {
        verbose = false;
        displayMemberList(jsonArray);
    }

    private void displayMemberList(JsonArray jsonArray) {
        String list;
        
        if (verbose) {
            list = formatter.getMemberListVerbose(jsonArray);
        } else {
            list = formatter.getMemberListCompact(jsonArray);
        }

        System.out.println(list);
        displayMenu();
        String selection = getInput("> ");
        interactionObserver.onCommandSelected(selection);
    }

    @Override
    public void displayAddMember() {
        System.out.println();
        System.out.println("Please enter the new member's information.");
        System.out.println("");
        String socialSecurityNumber = getInput("Social security number: ");
        String firstName = getInput("First name: ");
        String lastName = getInput("Last name: ");
        String address = getInput("Address: ");

        JsonObject info = Json.createObjectBuilder()
            .add("socialSecurityNumber", socialSecurityNumber)
            .add("firstName", firstName)
            .add("lastName", lastName)
            .add("address", address)
            .build();

        interactionObserver.onSubmitted(info);
    }

    @Override
    public void displayEditMember(JsonObject jsonMember) {
        System.out.println();
        System.out.println("Please enter the new information. Leave a field blank to keep current information.");
        System.out.println("");
        String firstName = getInput(String.format("First name (current: %s): ",
                    jsonMember.getString("firstName")));
        String lastName = getInput(String.format("Last name (current: %s): ",
                    jsonMember.getString("lastName")));
        String address = getInput(String.format("Address (current: %s): ",
                    jsonMember.getString("address")));

        if (firstName.isEmpty()) {
            firstName = jsonMember.getString("firstName");
        } 

        if (lastName.isEmpty()) {
            lastName = jsonMember.getString("lastName");
        }

        if (address.isEmpty()) {
            address = jsonMember.getString("address");
        }

        JsonObject info = Json.createObjectBuilder()
            .add("socialSecurityNumber", "")
            .add("firstName", firstName)
            .add("lastName", lastName)
            .add("address", address)
            .build();

        interactionObserver.onSubmitted(info);
    }

    @Override
    public void displayAddBoat(JsonObject jsonMember) {
        System.out.println();
        System.out.printf("Please enter the information about %s's new boat.",
                jsonMember.getString("firstName"));
        System.out.println("");
        String size = getInput("Enter length (meters): ");
        String boatType = getInput("Enter type (SailBoat/Motorsailer/Canoe/Kayak/Other): ");

        JsonObject info = Json.createObjectBuilder()
            .add("size", size)
            .add("boatType", boatType)
            .build();

        interactionObserver.onSubmitted(info);
    }

    @Override
    public void displayEditBoat(JsonObject jsonMember, JsonObject jsonBoat) {
        System.out.println();
        System.out.println("Please enter the new information. Leave a field blank to keep current information.");
        System.out.println("");
        String size = getInput(String.format("Boat length (meters) (current: %s): ",
                    jsonBoat.getString("size")));
        String boatType = getInput(String.format("Boat type (current: %s): ",
                    jsonBoat.getString("boatType")));

        if (size.isEmpty()) {
            size = jsonBoat.getString("size");
        } 

        if (boatType.isEmpty()) {
            boatType = jsonBoat.getString("boatType");
        }

        JsonObject info = Json.createObjectBuilder()
            .add("boatID", "")
            .add("size", size)
            .add("boatType", boatType)
            .build();

        interactionObserver.onSubmitted(info);
    }

    @Override
    public void displayError(String message) {
        System.out.println();
        System.out.println("=======================================");
        System.out.println(message);
        System.out.println("=======================================");
        System.out.println();
        getInput("Press enter to continue");

        interactionObserver.onErrorDismissed();
    }

    @Override
    public void displayWelcome() {
        System.out.println("" +
                           "                          |\"-,_                       \n" +
                           "                          I--(_                       \n" +
                           "                         ,I?8,                        \n" +
                           "                         d|`888.                      \n" +
                           "                        d8| 8888b                     \n" +
                           "                       ,88| ?8888b                    \n" +
                           "                      ,888| `88888b                   \n" +
                           "                     ,8888|  8888g8b                  \n" +
                           "                    ,88888|  888PX?8b                 \n" +
                           "                   ,888888|  8888bd88,                \n" +
                           "                  o8888888| ,888888888                \n" +
                           "                 d8888888P| d888888888b               \n" +
                           "              _.d888gggg8'| 8gg88888888,              \n" +
                           "             '\\==-,,,,,,,,|/;,,,,,-==;7               \n" +
                           "             _ \\__...____...__    __/ _                ");
        System.out.println();
        System.out.println("======================================================");
        System.out.println("||           Welcome to The Happy Pirate!           ||");
        System.out.println("======================================================");
        System.out.println();
        getInput("Press 'Enter' to start!");
    }

    private void displayMenu() {
        System.out.println(formatter.getMenu(verbose));
    }

    private String getInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print(prompt);
        return scanner.nextLine();
    }

}
