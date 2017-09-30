package controller;

import model.JsonParser;
import model.Registry;
import view.UserInterface;

import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 * Created by Caroline Nilsson on 2017-09-30.
 */
public class UserInteractionController implements UserInteractionObserver {

    JsonArray members;
    UserInterface ui;
    Registry registry;
    JsonParser jsonParser;
    InputDecoder decoder;
    Object [] lastCommands;
    boolean listChooise;

    public UserInteractionController(UserInterface ui, Registry registry) {
        listChooise = true;
        this.ui = ui;
        this.registry = registry;
        jsonParser = new JsonParser();
        decoder = new InputDecoder();
        members = registry.getAllMembersInfo();
    }

    public void launch() {
        ui.displayVerboseList(members); 
    }

    @Override
    public void onCommandSelected(String userInput) {
        lastCommands = decoder.getUserCommands(userInput, members);

        switch ((UserCommand)lastCommands[0]) {
            case AddMember: {
                ui.displayAddMember();
                break;
            }
            case EditMember: {
                ui.displayEditMember(registry.getMember(getMemberID()));
                break;
            }

            case RemoveMember: {
                registry.removeMember(getMemberID());
                members = registry.getAllMembersInfo();
                chooseCorrectListVerbosity();
                break;
            }
            case AddBoat: {
               ui.displayAddBoat(registry.getMember(getMemberID()));
                break;
            }
            case EditBoat: {
                ui.displayEditBoat(registry.getMember(getMemberID()), registry.getBoat(getMemberID(), getBoatID()));
                break;
            }
            case RemoveBoat: {
                registry.removeBoat(getMemberID(), getBoatID());
                members = registry.getAllMembersInfo();
                chooseCorrectListVerbosity();
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
                System.out.println("Add Boat");
                if (registry.addBoat(getMemberID(), information)) {
                    System.out.println("Adding a boat");
                    updateMemberList();
                    System.out.println("done adding the boat");
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

                if (registry.removeBoat(getMemberID(), getBoatID())) {
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

    private void updateMemberList() {
        members = registry.getAllMembersInfo();
    }
}
