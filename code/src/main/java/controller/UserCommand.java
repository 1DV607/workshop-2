package controller;

/**
 * Enum that represents the different Actions the User can chose from.
 *
 */
public enum UserCommand {

    AddMember,              // = 0
    ViewMember,             // = 1
    EditMember,             // = 2
    RemoveMember,           // = 3
    AddBoat,                // = 4
    EditBoat,               // = 5
    RemoveBoat,             // = 6
    ChangeList,             // = 7
    Exit;                   // = 8

    /**
     * Returns the value of an integer -1 (to get the correct value from the array)
     * @param i - int, Command
     * @return the value of the command integer - 1
     * @throws ArrayIndexOutOfBoundsException
     */
    public static UserCommand getCommand(int i) throws ArrayIndexOutOfBoundsException {
        return UserCommand.values()[i-1];
    }
}
