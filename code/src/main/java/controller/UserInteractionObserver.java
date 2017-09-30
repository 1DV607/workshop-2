package controller;

import javax.json.JsonObject;
import javax.json.JsonString;

/**
 *
 */
public interface UserInteractionObserver {

    public void onCommandSelected(String userInput);

    public void onSubmitted(JsonObject information);

    public void onErrorDismissed();
}
