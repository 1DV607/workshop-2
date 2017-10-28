package controller;

import java.util.List;

import model.Registry;
import view.UserInterface;

/**
 * implements UserInteractionObserver
 * Receives User Input from the view and decides what view is to be shown,
 * Receives information from the View and Registry and provide information
 * to the User and Registry.
 */
public class UserInteractionController implements UserInteractionObserver {

    List<Member> members;
    UserInterface ui;
    Registry registry;
    InputDecoder decoder;
    Object [] lastCommands;
    boolean listChoice;

    public UserInteractionController(UserInterface ui, Registry registry) {
        listChoice = true;
        this.ui = ui;
        this.registry = registry;

        decoder = new InputDecoder();
        members = registry.getAllMembers();
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
            isValidCommand();
            if (lastCommands[3] != null) {
                isValidCommandWithBoat();
            }
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
                    members = registry.getAllMembers();
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
                    ui.displayEditBoat(registry.getMember(getMemberID()), registry.getBoat(getMemberID(), getBoatID()));

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
                listChoice = !listChoice;
                chooseCorrectListVerbosity();
                break;
            }
            case Exit: {
                System.exit(0);
            }
        }
    }

    @Override
    public void onAddMemberSubmitted(String socialSecurityNumber, String firstName,
            String lastName, String address) {
        if (registry.addMember(socialSecurityNumber, firstName, lastName, address)) {
            updateMemberList();
        }
        else {
            ui.displayError("Unable to add member");
        }

        chooseCorrectListVerbosity();
    }

    @Override
    public void onEditMemberSubmitted(long memberID, String socialSecurityNumber,
            String firstName, String lastName, String address) {
        
        Member newMemberInfo = new Member(memberID, socialSecurityNumber, 
                firstName, lastName, address);

        if (registry.editMember(getMemberID(), newMemberInfo)) {
            updateMemberList();
        }
        else {
            ui.displayError("Unable to edit member");
        }

        chooseCorrectListVerbosity();
    }

    @Override
    public void onRemoveMemberSubmitted(int memberID) {
        if (registry.removeMember(getMemberID())) {
            updateMemberList();
        }
        else {
            ui.displayError("Unable to remove member");
        }

        chooseCorrectListVerbosity();
    }

    @Override
    public void onAddBoatSubmitted(BoatType type, int size) {
        Member member = registry.getMember(getMemberID());
        member.addBoat(type, size);
        updateMemberList();
        chooseCorrectListVerbosity();
    }

    @Override
    public void onEditBoatSubmitted(long boatID, BoatType type, int size) {
        Member member = registry.getMember(getMemberID());
        member.editBoat(getBoatID(), type, size);
        updateMemberList();
        chooseCorrectListVerbosity();
    }

    @Override
    public void onRemoveBoatSubmitted(int boatID) {
        if (!listChoice) {
            ui.displayError("Not possible to edit boat in compact list view");
        }
        else {
            Member member = registry.getMember(getMemberID());
            member.removeBoat(getBoatID());
            updateMemberList();
            chooseCorrectListVerbosity();
        }
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
        if (listChoice) {
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
