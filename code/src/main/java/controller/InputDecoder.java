package controller;

import javax.json.JsonArray;
import javax.json.JsonObject;

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
     * @param members - List of Members
     * @return Object [], [0] = command, [1] = memberID, [2] = boatID
     */
    public Object [] getUserCommands(String userInput, List<Member> members) {
        userCommands = new Object[3];

        String [] inputParts =  userInput.split(" ");
        String command = inputParts[0];
        int member = (inputParts.length >= 2)
                ? inputParts[1]
                : "";
        int boat = (inputParts.length >= 3)
                ? inputParts[2]
                : "";

        if (command.length() != 0) {
            UserCommand userCommand = UserCommand.getCommand(Integer.parseInt(command));
            userCommands [0] = userCommand;
        }

        if (member.length() != 0) {
            int memberIndex = Integer.parseInt(member);
            Member member = members.get(memberIndex - 1);
            userCommands [1] = member.getMemberID();
        }

        if (boat.length() != 0) {
            int memberIndex = Integer.parseInt(member);
            int boatIndex = Integer.parseInt(boat);
            Member member = members.get(memberIndex - 1);
            List<Boat> boats = member.getBoats();
            Boat boat = boats.get(boatIndex);
            userCommands [2] = boat.getBoatID();
        }

        return userCommands;

    }
}
