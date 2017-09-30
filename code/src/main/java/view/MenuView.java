package view;

import controller.UserInteractionObserver;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.Scanner;

/**
 *
 */
public class MenuView extends View {

    private UserInteractionObserver interactionObserver;
    private Scanner scanner;
    private boolean verbose;

    public MenuView() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void show() {
    }


    @Override
    public void addObserver(UserInteractionObserver observer) {
        interactionObserver = observer;
    }

    public void toggleVerbosity() {
        verbose = !verbose;
    }

    public void showMemberListCompact(JsonArray memberInfo) {

    }

    public void showMemberListVerbose(JsonArray memberInfo) {
        StringBuilder list = new StringBuilder();

        if (!verbose) {
            list.append(String.format("%-4s %-20s %-11s %-12s\n",
                        "Nr", "Name", "Member ID", "Nr. of boats"));
        }

        for (int mIndex = 0; mIndex < memberInfo.size(); mIndex++) {
            JsonObject obj = memberInfo.getJsonObject(mIndex);

            if (verbose) {
                list.append("===================\n");
                list.append(String.format("%-4s %-11s %-20s %-11s\n",
                            "Nr", "SSN", "Name", "Member ID"));

                list.append(String.format("%-4s %-11s %-20s %-11s\n",
                            mIndex + 1,
                            obj.getString("socialSecurityNr"),
                            obj.getString("firstName") + " " + obj.getString("lastName"),
                            obj.getString("memberID")));

                if (obj.getInt("numberOfBoats") > 0 ) {
                    list.append("\nBoats\n");
                    list.append("-----\n");
                    list.append(String.format("%-8s %-10s %-4s %-10s\n",
                                "Boat Nr ", "ID ", "Size", "Type"));

                    JsonArray boats = obj.getJsonArray("boats");

                    for (int bIndex = 0; bIndex < boats.size(); bIndex++) {
                        JsonObject boat = boats.getJsonObject(bIndex);

                        list.append(String.format("%-8s %-10s %-4s %-10s\n",
                                    (mIndex + 1) + "." + (bIndex + 1),
                                    boat.getString("boatID"),
                                    boat.getInt("size"),
                                    boat.getString("type")));
                    }

                    list.append("===================\n");
                } 

                list.append("\n");
            } else {
                list.append(String.format("%-4s %-20s %-11s %-12s\n",
                            mIndex + 1,
                            obj.getString("firstName") + " " + obj.getString("lastName"),
                            obj.getString("memberID"),
                            obj.getInt("numberOfBoats")));
            }
        }

        System.out.println(list.toString());
    }

    public void showMenu() {
        System.out.println("MENU");
        System.out.println("==========");
        System.out.println("1. Add member");
        System.out.println("2. Edit member <member nr>");
        System.out.println("3. Remove member <member nr>");
        System.out.println("4. Add boat <member nr>");

        if (verbose) {
            System.out.println("5. Edit boat <boat nr>");
            System.out.println("6. Remove boat <boat nr>");
        } else {
            System.out.println("5. Edit boat <boat nr> (NOT AVAILABLE)");
            System.out.println("6. Remove boat <boat nr> (NOT AVAILABLE)");
        }

        System.out.println("7. Change list verbosity.");
        System.out.println();
        System.out.println("Enter selection nr (Ex: '5 2.1') ");
    }

    public String getUserInput() {
        System.out.print("> ");
        return scanner.nextLine();
    }

}
