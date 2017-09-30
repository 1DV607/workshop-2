package view;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

/**
 * 
 */
public class StringFormatter {

    private StringBuilder stringBuilder;

    public StringFormatter() {

    }

    public String getMemberListVerbose(JsonArray memberInformation) {
        stringBuilder = new StringBuilder();

        stringBuilder.append("Verbose Member List \n");
        stringBuilder.append(String.format("%-4s %-20s %-12s %-25s \n",
                "Nr", "Name", "Member ID", "Social Security Number"));

        JsonObject object;
        JsonArray array;
        for (int i = 0; i < memberInformation.size(); i++) {
            object = memberInformation.getJsonObject(i).getJsonObject("member");
            String nr = i+". ";
            String name = object.getString("firstName")+" "+object.getString("lastName");
            String memberID = object.getString("memberID");
            String SSN = object.getString("socialSecurityNumber");

            stringBuilder.append(String.format("%-4s %-20s %-12s %-25s \n",
                    nr, name, memberID, SSN ));
            array = memberInformation.getJsonObject(i).getJsonArray("boats");

            for (int j = 0; j < array.size(); j++) {
                object = array.getJsonObject(j).getJsonObject("boat");

                String boatNr = j + ". ";
                String boatType = object.getString("boatType");
                String size = object.getString("size");
                String boatID = object.getString("boatID");

                stringBuilder.append(String.format("%-10s %-4s %-10s %-5s %-12s \n",
                        "", boatNr, boatType, size, boatID));
            }

        }

        return stringBuilder.toString();
    }




    public String getMemberListCompact(JsonArray memberInformation) {
        
		stringBuilder = new StringBuilder();
        JsonObject object;

        stringBuilder.append("Compact Member List \n");
        stringBuilder.append(String.format("%-4s %-20s %-12s %-4s \n",
                "Nr", "Name", "Member ID", "Nr of Boats"));


        for (int i = 0; i < memberInformation.size(); i++) {
            object = memberInformation.getJsonObject(i).getJsonObject("member");

            String nr = i+". ";
            String name = object.getString("firstName")+" "+object.getString("lastName");
            String memberID = object.getString("memberID");
            String numberOfBoats = memberInformation.getJsonObject(i).getJsonArray("boats").size()+"";

            stringBuilder.append(String.format("%-4s %-20s %-12s %-4s \n",
                    nr, name, memberID, numberOfBoats));

        }


        return stringBuilder.toString();
    }
}
