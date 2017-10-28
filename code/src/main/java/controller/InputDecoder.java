package controller;

import model.Boat;
import model.Member;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.List;

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
        Member memberObject;
        Boat boatObject;

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
            int memberIndex = Integer.parseInt(member);
            memberObject = members.get(memberIndex-1);
            userCommands [1] = memberObject.getMemberID();
        }

        if (boat.length() != 0) {
            int memberIndex = Integer.parseInt(member);
            int boatIndex = Integer.parseInt(boat);
            memberObject = members.get(memberIndex - 1);
            List<Boat> boats = memberObject.getAllBoats();
            boatObject = boats.get(boatIndex-1);
            userCommands [2] = boatObject.getBoatID();
        }

        return userCommands;

    }
}
