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
    ChangeList,
    Exit;

    public static UserCommand getCommand(int i) throws ArrayIndexOutOfBoundsException {
        return UserCommand.values()[i-1];
    }
}
