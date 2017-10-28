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

    public void onEditMemberSubmitted(long memberID, String socialSecurityNumber,
            String firstName, String lastName, String address);

    public void onRemoveMemberSubmitted(int memberID);

    public void onAddBoatSubmitted(BoatType type, int size);

    public void onEditBoatSubmitted(long boatID, BoatType type, int size);

    public void onRemoveBoatSubmitted(long boatID);

    public void onContinue();                               //called after Error or when program is waiting for
                                                            //the user to continue
}
