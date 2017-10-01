package view;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

/**
 * Formats text to show in console.
 * Verbose/Compact Member List, the Menu options and specific member information
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

    /**
     * Creates a String with all the Menu Options formatted nicely for output into the console.
     * @param verbose - boolean, whether the list is in compact or verbose format
     * @return String, containing the Menu options in a nice output format
     */
    public String getMenu(boolean verbose) {
        stringBuilder = new StringBuilder();

        stringBuilder.append("\n");
        stringBuilder.append("=======================================================================\n");

        stringBuilder.append(String.format("%-31s %-4s %-31s %-2s \n",
                 "||","Menu", "", "||"));
        stringBuilder.append("=======================================================================\n");
        stringBuilder.append(String.format("%-2s %-65s %-2s \n",
                "||", "", "||"));

        stringBuilder.append(String.format("%-1s %-65s %-2s \n",
               "||", "1. Add member", "||" ));
        stringBuilder.append(String.format("%-1s %-65s %-2s \n",
                "||", "2. View member <member nr>", "||" ));
        stringBuilder.append(String.format("%-1s %-65s %-2s \n",
                "||", "3. Edit member <member nr>", "||" ));
        stringBuilder.append(String.format("%-1s %-65s %-2s \n",
                "||", "4. Remove member <member nr>", "||" ));
        stringBuilder.append(String.format("%-1s %-65s %-2s \n",
                "||", "5. Add boat <member nr>", "||" ));

        if (verbose) {
            stringBuilder.append(String.format("%-1s %-65s %-2s \n",
                    "||", "6. Edit boat <member nr> <boat nr>", "||" ));
            stringBuilder.append(String.format("%-1s %-65s %-2s \n",
                    "||", "7. Remove boat <member nr> <boat nr>", "||" ));
        }
        else {
            stringBuilder.append(String.format("%-1s %-65s %-2s \n",
                    "||", "6. Edit boat <member nr> <boat nr> (NOT AVAILABLE)", "||" ));
            stringBuilder.append(String.format("%-1s %-65s %-2s \n",
                    "||", "7. Remove boat <member nr> <boat nr> (NOT AVAILABLE)", "||" ));
        }
        stringBuilder.append(String.format("%-1s %-65s %-2s \n",
                "||", "8. Change list verbosity.", "||" ));
        stringBuilder.append(String.format("%-1s %-65s %-2s \n",
                "||", "9. Exit", "||" ));
        if (!verbose) {
            stringBuilder.append(String.format("%-2s %-65s %-2s \n",
                    "||", "", "||"));
            stringBuilder.append(String.format("%-1s %-64s %-2s \n",
                    "|| ", "Actions 5, 6 only available at verbose listings", "||" ));
        }
        stringBuilder.append(String.format("%-2s %-65s %-2s \n",
                "||", "", "||"));
        stringBuilder.append("=======================================================================\n");
        stringBuilder.append("\n");
        stringBuilder.append("Enter selection nr <Menu Choice> <Member Nr> <Boat Nr> \n");
        stringBuilder.append("Ex: '5 3 1' = Edit boat 1 at member 2. Ex: '1' = Add new member\n ");
        stringBuilder.append("-----------------------------------------------------------------------\n");
        stringBuilder.append("\n");



        return stringBuilder.toString();
    }

    /**
     * Takes a Member as a JsonObject and the members boats as a JsonArray and formats it
     * to a output String
     * @param member - JsonObject, the member
     * @param boats - JsonArray, the members boats
     * @return - Formatted String
     */
    public String getMember(JsonObject member, JsonArray boats) {
        stringBuilder = new StringBuilder();
        JsonObject boat;

        stringBuilder.append("\n");
        stringBuilder.append("==========================================================================="+
                "========================================\n");
        stringBuilder.append(String.format("%-30s %-40s %-15s %-25s %-2s \n",
                "| Name", "| Address", "| Member ID", "| Social Security Number", "|"));
        stringBuilder.append("==========================================================================="+
                "========================================\n");


        String name = " "+member.getString("firstName")+" "+member.getString("lastName");
        String address = " "+ member.getString("address");
        String memberID = " "+member.getString("memberID");
        String SSN = " "+member.getString("socialSecurityNumber");

        stringBuilder.append(String.format("%-30s %-40s %-15s %-25s \n",
                 name, address, memberID, SSN ));

        for (int j = 0; j < boats.size(); j++) {
            if (j == 0) {
                stringBuilder.append(String.format("%-8s %-30s \n",
                        "", "----------------------------------------------------------------------------"+
                                "------------------------------"));
                stringBuilder.append(String.format("%-8s %-4s %-15s %-10s %-72s %-2s \n",
                        "", "| Nr", "| Boat Type", "| Size", "| Boat ID", "|"));
                stringBuilder.append(String.format("%-8s %-30s \n" ,
                        "", "----------------------------------------------------------------------------"+
                                "------------------------------"));
            }
            boat = boats.getJsonObject(j);

            String boatNr = (j+1) + ". ";
            String boatType = boat.getString("boatType");
            String size = boat.getString("size");
            String boatID = boat.getString("boatID");

            stringBuilder.append(String.format("%-9s %-4s %-15s %-10s %-15s \n",
                    "", boatNr, boatType, size, boatID));
        }


        return stringBuilder.toString();
    }

}
