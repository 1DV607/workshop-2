package view;

import javax.json.JsonArray;

/**
 * Created by Caroline Nilsson on 2017-09-30.
 */
public interface UserInterface {



    public void displayVerboseList(JsonArray jsonArray);

    public void displayCompactList(JsonArray jsonArray);

    public void displayAddMember();

    public void displayEditMember();

    public void displayAddBoat();

    public void displayEditBoat();
}
