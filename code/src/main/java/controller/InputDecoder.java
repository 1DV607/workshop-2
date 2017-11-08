package controller;

/**
 * Decodes the user command and get an Member ID/Boat ID from a List of Members if the
 * User asks to perform an Comand where this is necessary
 */
public class InputDecoder {

    private Object [] userCommands;

    public InputDecoder() {

    }

    /**
     * Takes user input and a List of members and transform it to a
     * command, memberID (if present) and boatID (if present)
     * The userInput choices has to be separated by space.
     * @param userInput - String, the command choices made by the user
     * @return Object [], [0] = command, [1] = memberID, [2] = boatID
     */
    public Object [] getUserCommands(String userInput) {
        userCommands = new Object[3];

        String [] inputParts =  userInput.split(" ");
        String command = inputParts[0];
        String member = (inputParts.length >= 2)
                ? inputParts[1]
                : "";
        String boat = (inputParts.length >= 3)
                ? inputParts[2]
                : "";

        if (command.length() != 0) {
            UserCommand userCommand = UserCommand.getCommand(Integer.parseInt(command));
            userCommands [0] = userCommand;
        }

        if (member.length() != 0) {
            userCommands [1] = Integer.parseInt(member);
        }

        if (boat.length() != 0) {
            userCommands [2] = Integer.parseInt(boat);
        }

        return userCommands;

    }
}
