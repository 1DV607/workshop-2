package view;

import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 * interface that connects the Controller and the View
 *
 * Implementing Classes: ConsoleView
 */
public interface UserInterface {

    public void displayVerboseList(JsonArray jsonArray);                                //called to show a verbose list of members

    public void displayCompactList(JsonArray jsonArray);                                //called to show a compact list of members

    public void displayAddMember();                                                     //called to add a member

    public void displayMemberInformation(JsonObject jsonMember, JsonArray jsonBoats);   //called to view a specific members info

    public void displayEditMember(JsonObject jsonMember);                               //called to edit a members information

    public void displayAddBoat(JsonObject jsonMember);                                  //called to add a boat to a member

    public void displayEditBoat(JsonObject jsonMember, JsonObject jsonBoat);            //called to edit a boats information

    public void displayError(String message);                                           //called to display error messages

    public void displayWelcome();                                                       //called to display welcome message
}
