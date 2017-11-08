package controller;

import model.*;
import model.data.Boat;
import model.data.BoatType;
import model.data.Member;
import view.UserInterface;

/**
 * implements UserInteractionObserver
 * Receives User Input from the view and decides what view is to be shown,
 * Receives information from the View and Registry and provide information
 * to the User and Registry.
 */
public class UserInteractionController implements UserInteractionObserver {


    private UserInterface ui;
    private Registry registry;
    private InputDecoder decoder;
    private Object [] lastCommands;
    private boolean listChoice;

    public UserInteractionController(UserInterface ui, Registry registry) {
        listChoice = true;
        this.ui = ui;
        this.registry = registry;

        decoder = new InputDecoder();
    }

    /**
     * launch the welcome message and shows a verbose list of all members
     */
    public void launch() {
        ui.displayWelcome();
        ui.displayVerboseList(registry.getAllMembers());
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

            lastCommands = decoder.getUserCommands(userInput);
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
                    int memberID = getMemberID();
                    ui.displayMemberInformation(registry.getMember(memberID));
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
                if (!listChoice) {
                    ui.displayError("Not possible to edit boat in compact list view");
                }
                else if (isValidCommandWithBoat()) {
                    ui.displayEditBoat(registry.getMember(getMemberID()).getBoat(getBoatID()));

                }
                else {
                    ui.displayError("Invalid command, please try again.");
                }


                break;
            }
            case RemoveBoat: {

                if (!listChoice) {
                    ui.displayError("Not possible to remove boat in compact list view");
                }
                else if (isValidCommandWithBoat()) {
                    registry.getMember(getMemberID()).removeBoat(getBoatID());
                    chooseCorrectListVerbosity();
                }
                else{
                    ui.displayError("Invalid command, please try again.");
                }


                break;
            }
            case ChangeList: {
                listChoice = !listChoice;
                chooseCorrectListVerbosity();
                break;
            }
            case Exit: {
                registry.saveChanges();
                System.exit(0);
            }
        }
    }

    @Override
    public void onAddMemberSubmitted(String socialSecurityNumber, String firstName,
            String lastName, String address) {
        if (registry.addMember(new Member(socialSecurityNumber, firstName, lastName, address))) {
        }
        else {
            ui.displayError("Unable to add member");
        }

        chooseCorrectListVerbosity();
    }

    @Override
    public void onEditMemberSubmitted(String socialSecurityNumber,
            String firstName, String lastName, String address) {
        
        Member newMemberInfo = new Member(getMemberID(), socialSecurityNumber,
                firstName, lastName, address);

        if (registry.editMember(getMemberID(), newMemberInfo)) {
        }
        else {
            ui.displayError("Unable to edit member");
        }

        chooseCorrectListVerbosity();
    }

    @Override
    public void onAddBoatSubmitted(String type, String size) {
        Member member = registry.getMember(getMemberID());

        if (isValidBoatInput(type, size)) {

            member.addBoat(new Boat(Integer.parseInt(size), BoatType.fromString(type)));
            chooseCorrectListVerbosity();
        }
        else {
            ui.displayError("Invalid input, Unable to add boat");
        }
    }

    @Override
    public void onEditBoatSubmitted(String type, String size) {
        Member member = registry.getMember(getMemberID());

        if (isValidBoatInput(type, size)) {
            member.editBoat(getBoatID(), new Boat(Integer.parseInt(size), BoatType.fromString(type)));
            chooseCorrectListVerbosity();
        }
        else {
            ui.displayError("Invalid input, Unable to add boat");
        }
    }

    @Override
    public void onContinue() {
        chooseCorrectListVerbosity();
    }

    /**
     * calls different display methods depending on boolean - listChoice
     * Always the list option latest chosen by the User
     */
    private void chooseCorrectListVerbosity() {
        if (listChoice) {
            ui.displayVerboseList(registry.getAllMembers());
        }
        else {
            ui.displayCompactList(registry.getAllMembers());
        }
    }

    /**
     * collects the member ID from the User Command
     * @return long, memberID
     */
    private int getMemberID() {
        return (Integer) lastCommands[1];

    }

    /**
     * collects the boat ID from the User Command
     * @return long, boatID
     */
    private int getBoatID() {
        return (Integer) lastCommands[2];
    }

    /**
     * Check if the <member nr> input and <boat nr> input is valid
     * @return true if valid otherwise false
     */
    private boolean isValidCommandWithBoat() {

        try {
            int memberID = getMemberID();
            int boatID = getBoatID();

            Member member = registry.getMember(memberID);
            member.getBoat(boatID);
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
        try {
            int memberID = getMemberID();
            registry.getMember(memberID);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if the boat input is valid
     * @return true if valid otherwise false
     */
    private boolean isValidBoatInput(String type, String size) {
        try {
            BoatType.fromString(type);
            Integer.parseInt(size);
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

}
