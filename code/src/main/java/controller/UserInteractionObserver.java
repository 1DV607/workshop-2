package controller;

import javax.json.JsonObject;
import javax.json.JsonString;

/**
 * interface that Connects the View and Controller
 *
 * Implementing Classes: UserInteractionController
 */
public interface UserInteractionObserver {

    void onCommandSelected(String userInput);        //called when a Command is selected by the User

    void onAddMemberSubmitted(String socialSecurityNumber, String firstName,
            String lastName, String address);

    void onEditMemberSubmitted( String socialSecurityNumber,
            String firstName, String lastName, String address);

    void onAddBoatSubmitted(String type, String size);

    void onEditBoatSubmitted( String type, String size);

    void onContinue();                               //called after Error or when program is waiting for
                                                            //the user to continue
}
