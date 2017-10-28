package view;

import java.util.Scanner;

import controller.UserInteractionObserver;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 *  Implements a console/terminal user interface for the application. 
 *
 *  Responsible for displaying information received from the model and 
 *  forwarding user input to the controller.
 */
public class ConsoleView implements UserInterface {

    private boolean verbose;
    private UserInteractionObserver interactionObserver;
    private StringFormatter formatter = new StringFormatter();

    /**
     *  Sets the observer to notify when a user interaction occurs.
     */
    public void setObserver(UserInteractionObserver interactionObserver) {
        this.interactionObserver = interactionObserver;
    }

    /**
     * Displays a verbose list of members.
     *
     * Information displayed for each member in the list is name, member ID,
     * social security number and a list of boats.
     * 
     * Information displayed for each boat is boat ID, size in meters and boat type.
     *
     * @param jsonArray - json array with member/boat information
     */
    @Override
    public void displayVerboseList(List<Member> members) {
        verbose = true;
        displayMemberList(members);
    }

    /**
     * Displays a compact list of members.
     *
     * Information displayed for each member in the list is name, member ID,
     * social security number and number of registered boats.
     * 
     * @param jsonArray - json array with member/boat information
     */
    @Override
    public void displayCompactList(List<Member> members) {
        verbose = false;
        displayMemberList(members);
    }

    /**
     *  Helper method for displayVerboseList and displayCompactList.
     *
     *  Passes Json to StringFormatter which formats the list, then
     *  outputs the list, displays the menu, waits for input and finally
     *  notifies the observer of the input.
     *
     *  @param jsonArray - json array with member/boat information.
     */
    private void displayMemberList(List<Member> members) {
        String list;
        
        if (verbose) {
            list = formatter.getMemberListVerbose(members);
        } else {
            list = formatter.getMemberListCompact(members);
        }

        System.out.println(list);
        displayMenu();
        String selection = getInput("> ");
        interactionObserver.onCommandSelected(selection);
    }

    /**
     *  Called when user has requested to add a member. Prompts user
     *  for information about the new member and submits the user input
     *  to the observer.
     */
    @Override
    public void displayAddMember() {
        System.out.println();
        System.out.println("Please enter the new member's information.");
        System.out.println("");
        String socialSecurityNumber = getInput("Social security number: ");
        String firstName = getInput("First name: ");
        String lastName = getInput("Last name: ");
        String address = getInput("Address: ");

        interactionObserver.onAddMemberSubmitted(socialSecurityNumber, firstName,
            lastName, address);
    }

    /**
     *  Display all available information about a specific member.
     *
     *  The information shown about the member is: first name, last name,
     *  address, member ID, social security number and a list of all boats
     *  registered to the user.
     *
     *  Information shown about each boat registered to the member is:
     *  boat ID, size in meters and boat type.
     *
     *  @param jsonMember - member information in JSON format
     *  @param jsonBoats - list of boats registered to the user in JSON format
     */
    @Override
    public void displayMemberInformation(JsonObject jsonMember, JsonArray jsonBoats) {
        System.out.println(formatter.getMember(jsonMember, jsonBoats));
        System.out.println();
        getInput("Press 'Enter' to continue to Menu Options!");
        interactionObserver.onContinue();
    }

    /**
     *  Called when user has requested to edit a member. Prompts user
     *  to input the new information about the member and sends the 
     *  new information to the observer.
     *
     *  @param jsonMember - current information about the member in JSON format
     */
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

    /**
     *  Called when user has requested to add a boat. Prompts user
     *  to input the information about the new boat and sends the 
     *  user input to the observer.
     *
     *  @param jsonMember - Information about the owner of the boat in
     *                      JSON format.
     */
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

    /**
     *  Called when user has requested to edit a boat. Prompts user
     *  to input the new information about the boat and sends the 
     *  user input to the observer.
     *
     *  @param jsonMember - Information about the owner of the boat to edit in
     *                      JSON format.
     *  @param jsonBoat - Current information about the boat to be edited
     */
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

    /**
     *  Displays an error message to the user and prompts the user to dismiss
     *  it when ready. When the message has been dismissed, the observer is 
     *  notified of this.
     *
     *  @param message - Error message to display.
     */
    @Override
    public void displayError(String message) {
        System.out.println();
        System.out.println("=======================================");
        System.out.println(message);
        System.out.println("=======================================");
        System.out.println();
        getInput("Press enter to continue");

        interactionObserver.onContinue();
    }

    /**
     *  Called when the application starts. Displays a welcome message and 
     *  prompts the user to continue by pressing 'Enter'.
     */
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

    /**
     *  Displays the menu of possible actions to the user.
     */
    private void displayMenu() {
        System.out.println(formatter.getMenu(verbose));
    }

    /**
     *  Outputs the provided prompt text and waits for the 
     *  user to input a line on the console, then returns it.
     *
     *  @param prompt - text to be displayed before getting input
     *
     *  @return The line of text that the user entered.
     */
    private String getInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print(prompt);
        return scanner.nextLine();
    }

}
