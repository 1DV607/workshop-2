package view;

import model.Boat;
import model.Member;

import java.util.List;

/**
 * Formats text to show in console.
 * Verbose/Compact Member List, the Menu options and specific member information
 */
public class StringFormatter {

    private StringBuilder stringBuilder;

    public StringFormatter() {

    }

    /**
     * Creates a verbose Member List String containing Member information and information about each
     * members boat(s)
     * @param members - List<Members>, containing Members
     * @return - String, listed Members and Boats
     */
    public String getMemberListVerbose(List<Member> members) {
        stringBuilder = new StringBuilder();

        stringBuilder.append("\n \n");
        stringBuilder.append("Verbose Member List \n");

        Member memberObject;
        Boat boatObject;
        List<Boat> boats;

        for (int i = 0; i < members.size(); i++) {

            stringBuilder.append("\n");
            stringBuilder.append("============================================================================\n");
            stringBuilder.append(String.format("%-4s %-30s %-12s %-25s %-2s \n",
                    "Nr", "| Name", "| Member ID", "| Social Security Number", "|"));
            stringBuilder.append("============================================================================\n");

            memberObject = members.get(i);
            String nr = (i+1) +". ";
            String name = " "+memberObject.getFirstName()+" "+memberObject.getLastName();
            String memberID = " "+memberObject.getMemberID();
            String SSN = " "+memberObject.getSocialSecurityNumber();

            stringBuilder.append(String.format("%-4s %-30s %-12s %-25s \n",
                     nr, name, memberID, SSN ));
            boats = memberObject.getAllBoats();

            for (int j = 0; j < boats.size(); j++) {
                if (j == 0) {
                    stringBuilder.append(String.format("%-4s %-30s \n",
                            "", "-----------------------------------------------------------------------"));
                    stringBuilder.append(String.format("%-4s %-4s %-15s %-10s %-37s %-2s \n",
                            "", "| Nr", "| Boat Type", "| Size", "| Boat ID", "|"));
                    stringBuilder.append(String.format("%-4s %-30s \n" ,
                            "", "-----------------------------------------------------------------------"));
                }
                boatObject = boats.get(j);

                String boatNr = (j+1) + ". ";
                String boatType = boatObject.getBoatType().getName();
                String size = boatObject.getSize()+"";
                String boatID = boatObject.getBoatID()+"";

                stringBuilder.append(String.format("%-5s %-4s %-15s %-10s %-15s \n",
                        "", boatNr, boatType, size, boatID));
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
    public String getMemberListCompact(List<Member> members) {
        stringBuilder = new StringBuilder();
        Member memberObject;


        stringBuilder.append("\n \n");
        stringBuilder.append("Compact Member List \n");

        stringBuilder.append("\n");
        stringBuilder.append("=======================================================================\n");
        stringBuilder.append(String.format("%-4s %-30s %-15s %-17s %-2s \n",
                "Nr", "| Name", "| Member ID", "| Nr of boats", "|"));
        stringBuilder.append("=======================================================================\n");

        for (int i = 0; i < members.size(); i++) {

            memberObject = members.get(i);

            String nr = (i+1)+". ";
            String name = " "+ memberObject.getFirstName() +" "+ memberObject.getLastName();
            String memberID = " "+ memberObject.getMemberID();
            String numberOfBoats = " "+ memberObject.getAllBoats().size();

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
     * Takes a Member and formats it to an output String containing Member information and
     * Boat information
     * @param member - Member Object
     * @return - Formatted String
     */
    public String getMember(Member member) {
        stringBuilder = new StringBuilder();
        Boat boatObject;
        List<Boat> boats;

        stringBuilder.append("\n");
        stringBuilder.append("==========================================================================="+
                "========================================\n");
        stringBuilder.append(String.format("%-30s %-40s %-15s %-25s %-2s \n",
                "| Name", "| Address", "| Member ID", "| Social Security Number", "|"));
        stringBuilder.append("==========================================================================="+
                "========================================\n");


        String name = " " + member.getFirstName() +" "+ member.getLastName();
        String address = " "+ member.getAddress();
        String memberID = " "+member.getMemberID();
        String SSN = " "+member.getSocialSecurityNumber();

        stringBuilder.append(String.format("%-30s %-40s %-15s %-25s \n",
                 name, address, memberID, SSN ));

        boats = member.getAllBoats();

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
            boatObject = boats.get(j);

            String boatNr = (j+1) + ". ";
            String boatType = boatObject.getBoatType().getName();
            String size = boatObject.getSize()+"";
            String boatID = boatObject.getBoatID()+"";

            stringBuilder.append(String.format("%-9s %-4s %-15s %-10s %-15s \n",
                    "", boatNr, boatType, size, boatID));
        }


        return stringBuilder.toString();
    }

}
