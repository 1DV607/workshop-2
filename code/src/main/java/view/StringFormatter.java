package view;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

/**
 * Formats an JsonValue (Array) into a String: Verbose List, Compact List
 */
public class StringFormatter {

    private StringBuilder stringBuilder;

    public StringFormatter() {

    }

    /**
     * Creates a verbose Member List containing the information in memberInformation
     * @param memberInformation - JsonArray, containing Members and Boats
     * @return - String, listed Members and Boats
     */
    public String getMemberListVerbose(JsonArray memberInformation) {
        stringBuilder = new StringBuilder();

        stringBuilder.append("Verbose Member List \n");
        stringBuilder.append(String.format("%-4s %-20s %-12s %-25s \n",
                "Nr", "Name", "Member ID", "Social Security Number"));

        JsonObject object;
        JsonArray array;
        for (int i = 0; i < memberInformation.size(); i++) {
            object = memberInformation.getJsonObject(i).getJsonObject("member");
            String nr = (i+1) +". ";
            String name = object.getString("firstName")+" "+object.getString("lastName");
            String memberID = object.getString("memberID");
            String SSN = object.getString("socialSecurityNumber");

            stringBuilder.append(String.format("%-4s %-20s %-12s %-25s \n",
                    nr, name, memberID, SSN ));
            array = memberInformation.getJsonObject(i).getJsonArray("boats");

            for (int j = 0; j < array.size(); j++) {
                object = array.getJsonObject(j);

                String boatNr = (j+1) + ". ";
                String boatType = object.getString("boatType");
                String size = object.getString("size");
                String boatID = object.getString("boatID");

                stringBuilder.append(String.format("%-10s %-4s %-10s %-5s %-12s \n",
                        "", boatNr, boatType, size, boatID));
            }

        }

        return stringBuilder.toString();
    }


    /**
     * Creates a compact Member List containing the information in memberInformation
     * @param memberInformation - JsonArray, containing members and boats
     * @return String, listed Members
     */
    public String getMemberListCompact(JsonArray memberInformation) {
        System.out.println("now we are getting started :P");
        stringBuilder = new StringBuilder();
        JsonObject object;

        stringBuilder.append("Compact Member List \n");
        stringBuilder.append(String.format("%-4s %-20s %-12s %-4s \n",
                "Nr", "Name", "Member ID", "Nr of Boats"));


        for (int i = 0; i < memberInformation.size(); i++) {
            object = memberInformation.getJsonObject(i).getJsonObject("member");

            String nr = (i+1)+". ";
            String name = object.getString("firstName")+" "+object.getString("lastName");
            String memberID = object.getString("memberID");
            String numberOfBoats = memberInformation.getJsonObject(i).getJsonArray("boats").size()+"";

            stringBuilder.append(String.format("%-4s %-20s %-12s %-4s \n",
                    nr, name, memberID, numberOfBoats));

        }

        System.out.println("I got here :D happy day");
        return stringBuilder.toString();
    }
}
