package controller;

import javax.json.JsonObject;
import javax.json.JsonString;

/**
 * interface that Connects the View and Controller
 *
 * Implementing Classes: UserInteractionController
 */
public interface UserInteractionObserver {

    public void onCommandSelected(String userInput);        //called when a Command is selected by the User

    public void onAddMemberSubmitted(String socialSecurityNumber, String firstName,
            String lastName, String address);

    public void onEditMemberSubmitted(int memberID, String socialSecurityNumber,
            String firstName, String lastName, String address);

    public void onRemoveMemberSubmitted(int memberID);

    public void onAddBoatSubmitted(String type, int size);

    public void onEditBoatSubmitted(int boatID, String type, int size);

    public void onRemoveBoatSubmitted(int boatID);

    public void onContinue();                               //called after Error or when program is waiting for
                                                            //the user to continue
}
