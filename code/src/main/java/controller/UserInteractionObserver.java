package controller;

import javax.json.JsonString;

/**
 * Created by Caroline Nilsson on 2017-09-30.
 */
public interface UserInteractionObserver {

    public void onCommandSelected(String userInput);

    public void onSubmitted(JsonString information);
}
