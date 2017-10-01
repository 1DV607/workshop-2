package controller;

import model.JsonParser;
import model.Registry;
import view.UserInterface;

import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 *
 */
public class UserInteractionController implements UserInteractionObserver {

    JsonArray members;
    UserInterface ui;
    Registry registry;
    InputDecoder decoder;
    Object [] lastCommands;
    boolean listChooise;

    public UserInteractionController(UserInterface ui, Registry registry) {
        listChooise = true;
        this.ui = ui;
        this.registry = registry;

        decoder = new InputDecoder();
        members = registry.getAllMembersInfo();
    }

    public void launch() {
        ui.displayWelcome();
        ui.displayVerboseList(members); 
    }

    @Override
    public void onCommandSelected(String userInput) {
        try {
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
                if (!listChooise) {
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

                if (!listChooise) {
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
                listChooise = !listChooise;
                chooseCorrectListVerbosity();
                break;
            }
            case Exit: {
                System.exit(0);
            }
        }


    }

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

                if (!listChooise) {
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
    public void onErrorDismissed() {
        chooseCorrectListVerbosity();
    }

    private void chooseCorrectListVerbosity() {
        if (listChooise) {
            ui.displayVerboseList(members);
        }
        else {
            ui.displayCompactList(members);
        }
    }

    private long getMemberID() {

        long memberID = Long.parseLong((String) lastCommands[1]);
        return memberID;
    }

    private long getBoatID() {
        long boatID = Long.parseLong((String) lastCommands[2]);
        return boatID;
    }

    private boolean isValidCommandWithBoat() {
        long memberID;
        long boatID;
        System.out.println("boat id = " + (String) lastCommands[2]);
        try {
            memberID = getMemberID();
            boatID = getBoatID();
            return true;
        }
        catch (Exception e) {
            return false;

        }
    }

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

    private void updateMemberList() {
        members = registry.getAllMembersInfo();
    }
}
