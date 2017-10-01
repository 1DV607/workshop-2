package controller;

import model.Registry;
import view.UserInterface;

import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 * implements UserInteractionObserver
 * Receives User Input from the view and decides what view is to be shown,
 * Receives information from the View and Registry and provide information
 * to the User and Registry.
 */
public class UserInteractionController implements UserInteractionObserver {

    JsonArray members;
    UserInterface ui;
    Registry registry;
    InputDecoder decoder;
    Object [] lastCommands;
    boolean listChoise;

    public UserInteractionController(UserInterface ui, Registry registry) {
        listChoise = true;
        this.ui = ui;
        this.registry = registry;

        decoder = new InputDecoder();
        members = registry.getAllMembersInfo();
    }

    /**
     * launch the welcome message and shows a verbose list of all members
     */
    public void launch() {
        ui.displayWelcome();
        ui.displayVerboseList(members); 
    }

    /**
     * Takes user input and activates the view chosen by the user,
     * also collects information from Registry if needed
     * Shows error message if the command was not valid
     * @param userInput - String, containing <command> <member nr> <boat nr>
     */
    @Override
    public void onCommandSelected(String userInput) {
        try {
            if (userInput.isEmpty()) {
                throw new IllegalArgumentException("Empty input");
            }

            lastCommands = decoder.getUserCommands(userInput, members);
        }
        catch (Exception e) {
            ui.displayError("Invalid command, please try again");
            return;
        }

        switch ((UserCommand)lastCommands[0]) {

            case AddMember: {
                ui.displayAddMember();
                break;
            }
            case ViewMember: {
                if (isValidCommand()) {
                    long memberID = getMemberID();
                    ui.displayMemberInformation(registry.getMember(memberID), registry.getMemberBoats(memberID));
                }
                else {
                    ui.displayError("Invalid command, please try again.");
                }
                break;
            }
            case EditMember: {

                if (isValidCommand()) {
                    ui.displayEditMember(registry.getMember(getMemberID()));
                }
                else {
                    ui.displayError("Invalid command, please try again.");
                }
                break;
            }

            case RemoveMember: {

                if (isValidCommand()) {
                    registry.removeMember(getMemberID());
                    members = registry.getAllMembersInfo();
                    chooseCorrectListVerbosity();
                }
                else {
                    ui.displayError("Invalid command, please try again.");
                }
                break;
            }
            case AddBoat: {
                if (isValidCommand()) {
                    ui.displayAddBoat(registry.getMember(getMemberID()));
                }
                else {
                    ui.displayError("Invalid command, please try again.");
                }
                break;
            }
            case EditBoat: {
                if (!listChoise) {
                    ui.displayError("Not possible to edit boat in compact list view");
                }
                else if (isValidCommandWithBoat()) {
                    ui.displayEditBoat(registry.getMember(getMemberID()), registry.getBoat(getMemberID(), getBoatID()));

                }
                else {
                    ui.displayError("Invalid command, please try again.");
                }


                break;
            }
            case RemoveBoat: {

                if (!listChoise) {
                    ui.displayError("Not possible to remove boat in compact list view");
                }
                else if (isValidCommandWithBoat()) {
                    registry.removeBoat(getMemberID(), getBoatID());
                    members = registry.getAllMembersInfo();
                    chooseCorrectListVerbosity();
                }
                else{
                    ui.displayError("Invalid command, please try again.");
                }


                break;
            }
            case ChangeList: {
                listChoise = !listChoise;
                chooseCorrectListVerbosity();
                break;
            }
            case Exit: {
                System.exit(0);
            }
        }


    }

    /**
     * Takes a JsonObject containing new information and sends this to the Registry, and
     * updates the Member List with the new information.
     * Shows error message if the desired task could not be completed
     * @param information - JsonObject, containing the new information
     */
    @Override
    public void onSubmitted(JsonObject information) {

        switch ((UserCommand)lastCommands[0]) {

            case AddMember: {

                if (registry.addMember(information)) {
                    updateMemberList();
                }
                else {
                    ui.displayError("Unable to add member");
                }
                break;
            }
            case ViewMember: {
                break;
            }
            case EditMember: {

                if (registry.editMember(getMemberID(), information)) {
                    updateMemberList();
                }
                else {
                    ui.displayError("Unable to edit member");
                }
                break;
            }
            case RemoveMember: {

                if (registry.removeMember(getMemberID())) {
                    updateMemberList();
                }
                else {
                    ui.displayError("Unable to remove member");
                }
                break;
            }
            case AddBoat: {

                if (registry.addBoat(getMemberID(), information)) {
                    updateMemberList();
                }
                else {
                    ui.displayError("Unable to add boat");
                }
                break;
            }
            case EditBoat: {

                if (registry.editBoat(getMemberID(), getBoatID(), information)) {
                    updateMemberList();
                }
                else {
                    ui.displayError("Unable to edit boat");
                }
                break;
            }
            case RemoveBoat: {

                if (!listChoise) {
                    ui.displayError("Not possible to edit boat in compact list view");
                }
                else if (registry.removeBoat(getMemberID(), getBoatID())) {
                    updateMemberList();
                }
                else {
                    ui.displayError("Unable to remove boat");
                }
                break;
            }
            case ChangeList: {
                break;
            }
        }
        chooseCorrectListVerbosity();
    }


    @Override
    public void onContinue() {
        chooseCorrectListVerbosity();
    }

    /**
     * calls different display methods depending on boolean - listChoice
     * Allways the list option latest chosen by the User
     */
    private void chooseCorrectListVerbosity() {
        if (listChoise) {
            ui.displayVerboseList(members);
        }
        else {
            ui.displayCompactList(members);
        }
    }

    /**
     * collects the member ID from the User Command
     * @return long, memberID
     */
    private long getMemberID() {

        long memberID = Long.parseLong((String) lastCommands[1]);
        return memberID;
    }

    /**
     * collects the boat ID from the User Command
     * @return long, boatID
     */
    private long getBoatID() {
        long boatID = Long.parseLong((String) lastCommands[2]);
        return boatID;
    }

    /**
     * Check if the <member nr> input and <boat nr> input is valid
     * @return true if valid otherwise false
     */
    private boolean isValidCommandWithBoat() {
        long memberID;
        long boatID;
        try {
            memberID = getMemberID();
            boatID = getBoatID();
            return true;
        }
        catch (Exception e) {
            return false;

        }
    }

    /**
     * Check if the <member nr> input is valid
     * @return true if valid otherwise false
     */
    private boolean isValidCommand() {
        long memberID;
        try {
            memberID = getMemberID();
            return true;
        }
        catch (Exception e) {
            return false;

        }
    }

    /**
     * collect members from Registry
     */
    private void updateMemberList() {
        members = registry.getAllMembersInfo();
    }
}
