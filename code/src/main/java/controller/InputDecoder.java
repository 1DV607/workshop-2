package controller;

import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 * Decodes the user command and get an Member ID/Boat ID from an JsonArray if the
 * User asks to perform an Comand where this is necessary
 */
public class InputDecoder {

    private Object [] userCommands;

    public InputDecoder() {

    }

    /**
     * Takes user input and a JsonArray and transform it to a
     * command, memberID (if present) and boatID (if present)
     * The userInput choices has to be separated by space.
     * @param userInput - String, the command choices made by the user
     * @param members - List of Members
     * @return Object [], [0] = command, [1] = memberID, [2] = boatID
     */
    public Object [] getUserCommands(String userInput, List<Member> members) {
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
            JsonObject jsonMember = json.getJsonObject((Integer.parseInt(member))-1).getJsonObject("member");
            String memberID = jsonMember.getString("memberID");
            userCommands [1] = memberID;
        }

        if (boat.length() != 0) {
            JsonObject jsonBoat = json.getJsonObject((Integer.parseInt(member))-1).getJsonArray("boats")
                    .getJsonObject((Integer.parseInt(boat))-1);
            String boatID = jsonBoat.getString("boatID");
            userCommands [2] = boatID;
        }

        return userCommands;

    }
}
