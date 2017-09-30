package controller;

import model.JsonParser;
import model.Registry;
import view.UserInterface;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;

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
    Boolean listChooise;

    public UserInteractionController(UserInterface ui, Registry registry) {
        this.ui = ui;
        this.registry = registry;
        jsonParser = new JsonParser();
        decoder = new InputDecoder();
        members = registry.getAllMembersInfo();

    }

    @Override
    public void onCommandSelected(String userInput) {
        lastCommands = decoder.getUserCommands(userInput, members);

        switch ((UserCommand)lastCommands[0]) {
            case AddMember: {
                ui.displayAddMember();
            }
            case EditMember: {
                ui.displayEditMember(registry.getMember(getMemberID()));
            }
            case RemoveMember: {
                registry.removeMember(getMemberID());
                members = registry.getAllMembersInfo();
                chooseCorrectListVerbosity();

            }
            case AddBoat: {
               ui.displayAddBoat(registry.getMember(getMemberID()));
            }
            case EditBoat: {
                ui.displayEditBoat(registry.getMember(getMemberID()), registry.getBoat(getMemberID(), getBoatID()));
            }
            case RemoveBoat: {
                registry.removeBoat(getMemberID(), getBoatID());
                members = registry.getAllMembersInfo();
                chooseCorrectListVerbosity();
            }
            case ChangeList: {
                listChooise = !listChooise;
                chooseCorrectListVerbosity();
            }
        }


    }

    @Override
    public void onSubmitted(JsonObject information) {

        switch ((UserCommand)lastCommands[0]) {
            case AddMember: {

                if (registry.addMember(information)) {
                    updateMemberList();
                    chooseCorrectListVerbosity();
                }
                else {
                    ui.displayError("Unable to add member");
                }

            }
            case EditMember: {

                if (registry.editMember(getMemberID(), information)) {
                    updateMemberList();
                    chooseCorrectListVerbosity();
                }
                else {
                    ui.displayError("Unable to edit member");
                }

            }
            case RemoveMember: {

                if (registry.removeMember(getMemberID())) {
                    updateMemberList();
                    chooseCorrectListVerbosity();
                }
                else {
                    ui.displayError("Unable to remove member");
                }

            }
            case AddBoat: {

                if (registry.addBoat(getMemberID(), information)) {
                    updateMemberList();
                    chooseCorrectListVerbosity();
                }
                else {
                    ui.displayError("Unable to add boat");
                }

            }
            case EditBoat: {

                if (registry.editBoat(getMemberID(), getBoatID(), information)) {
                    updateMemberList();
                    chooseCorrectListVerbosity();
                }
                else {
                    ui.displayError("Unable to edit boat");
                }

            }
            case RemoveBoat: {

                if (registry.removeBoat(getMemberID(), getBoatID())) {
                    updateMemberList();
                    chooseCorrectListVerbosity();
                }
                else {
                    ui.displayError("Unable to remove boat");
                }

            }
            case ChangeList: {

            }
        }
    }

    /**
     * Takes a String and check if it contains Json
     * @param string - String
     * @return true if the String contains Json, otherwise false
     */
    private boolean isJsonString(String string) {

        if (string.contains("memberID") || string.contains("boatID")) {
            return true;
        }
        else {
            return false;
        }
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
        return boatID
    }

    private void updateMemberList() {
        members = registry.getAllMembersInfo();
    }
}
