package view;

import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 * interface that connects the Controller and the View
 *
 * Implementing Classes: ConsoleView
 */
public interface UserInterface {

    public void displayVerboseList(List<Member> members);                                //called to show a verbose list of members

    public void displayCompactList(List<Member> members);                                //called to show a compact list of members

    public void displayAddMember();                                                     //called to add a member

    public void displayMemberInformation(Member member);   //called to view a specific members info

    public void displayEditMember(Member member);                               //called to edit a members information

    public void displayAddBoat(Member member);                                  //called to add a boat to a member

    public void displayEditBoat(Member member, Boat boat);            //called to edit a boats information

    public void displayError(String message);                                           //called to display error messages

    public void displayWelcome();                                                       //called to display welcome message
}
