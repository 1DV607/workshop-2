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

        stringBuilder.append("\n \n");
        stringBuilder.append("Verbose Member List \n");

        JsonObject object;
        JsonArray array;
        for (int i = 0; i < memberInformation.size(); i++) {

            stringBuilder.append("\n");
            stringBuilder.append("============================================================================\n");
            stringBuilder.append(String.format("%-4s %-30s %-12s %-25s %-2s \n",
                    "Nr", "| Name", "| Member ID", "| Social Security Number", "|"));
            stringBuilder.append("============================================================================\n");

            object = memberInformation.getJsonObject(i).getJsonObject("member");
            String nr = (i+1) +". ";
            String name = " "+object.getString("firstName")+" "+object.getString("lastName");
            String memberID = " "+object.getString("memberID");
            String SSN = " "+object.getString("socialSecurityNumber");

            stringBuilder.append(String.format("%-4s %-30s %-12s %-25s \n",
                     nr, name, memberID, SSN ));
            array = memberInformation.getJsonObject(i).getJsonArray("boats");

            for (int j = 0; j < array.size(); j++) {
                if (j == 0) {
                    stringBuilder.append(String.format("%-4s %-30s \n",
                            "", "-----------------------------------------------------------------------"));
                    stringBuilder.append(String.format("%-4s %-4s %-15s %-10s %-37s %-2s \n",
                            "", "| Nr", "| Boat Type", "| Size", "| Boat ID", "|"));
                    stringBuilder.append(String.format("%-4s %-30s \n" ,
                            "", "-----------------------------------------------------------------------"));
                }
                object = array.getJsonObject(j);

                String boatNr = (j+1) + ". ";
                String boatType = object.getString("boatType");
                String size = object.getString("size");
                String boatID = object.getString("boatID");

                stringBuilder.append(String.format("%-5s %-4s %-15s %-10s %-15s \n",
                        "", boatNr, boatType, size, boatID));
            }
            stringBuilder.append("\n");

        }

        return stringBuilder.toString();
    }


    /**
     * Creates a compact Member List containing the information in memberInformation
     * @param memberInformation - JsonArray, containing members and boats
     * @return String, listed Members
     */
    public String getMemberListCompact(JsonArray memberInformation) {
        stringBuilder = new StringBuilder();
        JsonObject object;

        stringBuilder.append("\n \n");
        stringBuilder.append("Compact Member List \n");

        stringBuilder.append("\n");
        stringBuilder.append("=======================================================================\n");
        stringBuilder.append(String.format("%-4s %-30s %-15s %-17s %-2s \n",
                "Nr", "| Name", "| Member ID", "| Nr of boats", "|"));
        stringBuilder.append("=======================================================================\n");

        for (int i = 0; i < memberInformation.size(); i++) {

            object = memberInformation.getJsonObject(i).getJsonObject("member");

            String nr = (i+1)+". ";
            String name = " "+ object.getString("firstName")+" "+object.getString("lastName");
            String memberID = " " +object.getString("memberID");
            String numberOfBoats = " "+ memberInformation.getJsonObject(i).getJsonArray("boats").size()+"";

            stringBuilder.append(String.format("%-4s %-30s %-15s %-17s \n",
                    nr, name, memberID, numberOfBoats));

        }

        return stringBuilder.toString();
    }

}
