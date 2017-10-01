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

    public void onSubmitted(JsonObject information);        //called when the User gives new information

    public void onContinue();                               //called after Error or when program is waiting for
                                                            //the user to continue
}
