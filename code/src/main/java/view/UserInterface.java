package view;

import model.Boat;
import model.Member;

import java.util.List;

/**
 * interface that connects the Controller and the View
 *
 * Implementing Classes: ConsoleView
 */
public interface UserInterface {

    void displayVerboseList(List<Member> members);                              //called to show a verbose list of members

    void displayCompactList(List<Member> members);                              //called to show a compact list of members

    void displayAddMember();                                                    //called to add a member

    void displayMemberInformation(Member member);                               //called to view a specific members info

    void displayEditMember(Member member);                                      //called to edit a members information

    void displayAddBoat(Member member);                                         //called to add a boat to a member

    void displayEditBoat(Boat boat);                                            //called to edit a boats information

    void displayError(String message);                                          //called to display error messages

    void displayWelcome();                                                      //called to display welcome message
}
