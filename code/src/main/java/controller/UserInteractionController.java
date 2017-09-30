package controller;

import model.JsonParser;
import model.Registry;
import view.UserInterface;

import javax.json.JsonArray;
import javax.json.JsonString;

/**
 * Created by Caroline Nilsson on 2017-09-30.
 */
public class UserInteractionController implements UserInteractionObserver {

    JsonArray members;
    UserInterface ui;
    Registry registry;
    JsonParser jsonParser;
    InputDecoder decoder;
    Object [] lastCommands;

    public UserInteractionController(UserInterface ui, Registry registry) {
        this.ui = ui;
        this.registry = registry;
        jsonParser = new JsonParser();
        decoder = new InputDecoder();
        members = registry.getAllMembersInfo();

    }

    @Override
    public void onCommandSelected(String userInput) {
        lastCommands = decoder.getUserCommands(userInput, members);


    }

    @Override
    public void onSubmitted(JsonString information) {

    }

    /**
     * Takes a String and check if it contains Json
     * @param string - String
     * @return true if the String contains Json, otherwise false
     */
    private boolean isJsonString(String string) {

        if (string.contains("memberID") || string.contains("boatID")) {
            return true;
        }
        else {
            return false;
        }
    }
}
