package controller;

/**
 * Enum that represents the different Actions the User can chose from.
 *
 */
public enum UserCommand {


    AddMember,
    EditMember,
    RemoveMember,
    AddBoat,
    EditBoat,
    RemoveBoat,
    ChangeList;


    public static UserCommand getCommand(int i) {
        return UserCommand.values()[i-1];
    }
}
