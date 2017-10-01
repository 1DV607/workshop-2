package view;

import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 *
 */
public interface UserInterface {

    public void displayVerboseList(JsonArray jsonArray);

    public void displayCompactList(JsonArray jsonArray);

    public void displayAddMember();

    public void displayMemberInformation(JsonObject jsonMember, JsonArray jsonBoats);

    public void displayEditMember(JsonObject jsonMember);

    public void displayAddBoat(JsonObject jsonMember);

    public void displayEditBoat(JsonObject jsonMember, JsonObject jsonBoat);

    public void displayError(String message);

    public void displayWelcome();
}
