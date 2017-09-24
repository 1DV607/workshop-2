package controller;

import javax.json.JsonObject;

/**
 *
 */
public interface Controller {

    public void onSubmit(String userInput);

    public void onShow();

    public void onSelect(int selection);
}
