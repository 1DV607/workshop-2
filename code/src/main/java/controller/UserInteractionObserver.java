package controller;

import model.data.Member;
import model.data.Boat;

/**
 * interface that Connects the View and Controller
 *
 * Implementing Classes: UserInteractionController
 */
public interface UserInteractionObserver {

    //called when a command is selected by the user
    void onCommandSelected(String userInput);

    //takes the member information given by the user and creates a new member
    void onAddMemberSubmitted(Member memberInfo);

    //takes the new member information given by the user and creates a new member
    void onEditMemberSubmitted(Member memberInfo);

    //takes the boat information given by the user and creates a new boat
    void onAddBoatSubmitted(String type, String size);

    //takes the new boat onformation given by the user and creates a new boat
    void onEditBoatSubmitted(String type, String size);

    //called after Error or when the program is waiting for the user to continue
    void onContinue();
    
}
