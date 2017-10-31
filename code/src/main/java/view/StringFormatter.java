package view;

import model.Boat;
import model.Member;

import java.util.List;

/**
 * Formats text to show in console.
 * Verbose/Compact Member List, the Menu options and specific member information
 */
 class StringFormatter {

    private StringBuilder stringBuilder;

    StringFormatter() {

    }

    /**
     * Creates a verbose Member List String containing Member information and information about each
     * members boat(s)
     * @param members - List<Members>, containing Members
     * @return - String, listed Members and Boats
     */
    String getMemberListVerbose(List<Member> members) {
        stringBuilder = new StringBuilder();

        stringBuilder.append("\n \n");
        stringBuilder.append("Verbose Member List \n");

        Member memberObject;
        Boat boatObject;
        List<Boat> boats;

        for (int i = 0; i < members.size(); i++) {

            stringBuilder.append("\n");
            stringBuilder.append("=======================================================================\n");
            stringBuilder.append(String.format("%-12s %-30s %-25s %-2s \n",
                    "Member ID", "| Name", "| Social Security Number", "|"));
            stringBuilder.append("=======================================================================\n");

            memberObject = members.get(i);
            String memberID = " "+memberObject.getMemberID();
            String name = " "+memberObject.getFirstName()+" "+memberObject.getLastName();
            String SSN = " "+memberObject.getSocialSecurityNumber();

            stringBuilder.append(String.format("%-12s %-30s %-25s \n",
                     memberID, name, SSN ));
            boats = memberObject.getAllBoats();

            for (int j = 0; j < boats.size(); j++) {
                if (j == 0) {
                    stringBuilder.append(String.format("%-6s %-30s \n",
                            "", "-----------------------------------------"));
                    stringBuilder.append(String.format("%-6s %-12s %-15s %-10s %-2s \n",
                            "", "| Boat ID", "| Boat Type", "| Size", "|"));
                    stringBuilder.append(String.format("%-6s %-30s \n" ,
                            "", "-----------------------------------------"));
                }
                boatObject = boats.get(j);

                String boatID = boatObject.getBoatID()+"";
                String boatType = boatObject.getBoatType().getName();
                String size = boatObject.getSize()+"";

                stringBuilder.append(String.format("%-7s %-12s %-15s %-10s \n",
                        "", boatID, boatType, size));
            }
            stringBuilder.append("\n");

        }

        return stringBuilder.toString();
    }


    /**
     * Creates a compact Member List String for output
     * @param members - List<Member>, containing all members
     * @return String, listed Members
     */
    String getMemberListCompact(List<Member> members) {
        stringBuilder = new StringBuilder();
        Member memberObject;


        stringBuilder.append("\n \n");
        stringBuilder.append("Compact Member List \n");

        stringBuilder.append("\n");
        stringBuilder.append("=======================================================================\n");
        stringBuilder.append(String.format("%-12s %-30s %-17s %-2s \n",
                "Member ID", "| Name", "| Nr of boats", "|"));
        stringBuilder.append("=======================================================================\n");

        for (int i = 0; i < members.size(); i++) {

            memberObject = members.get(i);

            String memberID = " "+ memberObject.getMemberID();
            String name = " "+ memberObject.getFirstName() +" "+ memberObject.getLastName();
            String numberOfBoats = " "+ memberObject.getAllBoats().size();

            stringBuilder.append(String.format("%-12s %-30s %-17s \n",
                    memberID, name, numberOfBoats));

        }

        return stringBuilder.toString();
    }

    /**
     * Creates a String with all the Menu Options formatted nicely for output into the console.
     * @param verbose - boolean, whether the list is in compact or verbose format
     * @return String, containing the Menu options in a nice output format
     */
    String getMenu(boolean verbose) {
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
        stringBuilder.append("Enter selection nr <Menu Choice> <Member ID> <Boat ID> \n");
        stringBuilder.append("Ex: '5 3 1' = Edit boat with ID 1 at member with ID 2. Ex: '1' = Add new member\n ");
        stringBuilder.append("-----------------------------------------------------------------------\n");
        stringBuilder.append("\n");



        return stringBuilder.toString();
    }

    /**
     * Takes a Member and formats it to an output String containing Member information and
     * Boat information
     * @param member - Member Object
     * @return - Formatted String
     */
    String getMember(Member member) {
        stringBuilder = new StringBuilder();
        Boat boatObject;
        List<Boat> boats;

        stringBuilder.append("\n");
        stringBuilder.append("==========================================================================="+
                "========================================\n");
        stringBuilder.append(String.format("%-15s %-30s %-40s %-25s %-2s \n",
                "Member ID", "| Name", "| Address", "| Social Security Number", "|"));
        stringBuilder.append("==========================================================================="+
                "========================================\n");


        String name = " " + member.getFirstName() +" "+ member.getLastName();
        String address = " "+ member.getAddress();
        String memberID = " "+member.getMemberID();
        String SSN = " "+member.getSocialSecurityNumber();

        stringBuilder.append(String.format("%-15s %-30s %-40s %-25s \n",
                 memberID, name, address, SSN ));

        boats = member.getAllBoats();

        for (int j = 0; j < boats.size(); j++) {
            if (j == 0) {
                stringBuilder.append(String.format("%-8s %-30s \n",
                        "", "----------------------------------------------------------------------------"+
                                "------------------------------"));
                stringBuilder.append(String.format("%-8s %-15s %-15s %-10s %-2s \n",
                        "", "| Boat ID", "| Boat Type", "| Size", "|"));
                stringBuilder.append(String.format("%-8s %-30s \n" ,
                        "", "----------------------------------------------------------------------------"+
                                "------------------------------"));
            }
            boatObject = boats.get(j);

            String boatID = boatObject.getBoatID()+"";
            String boatType = boatObject.getBoatType().getName();
            String size = boatObject.getSize()+"";

            stringBuilder.append(String.format("%-9s %-15s %-15s %-10s \n",
                    "", boatID, boatType, size));
        }


        return stringBuilder.toString();
    }

}
