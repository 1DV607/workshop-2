package view;

import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 * Created by Caroline Nilsson on 2017-09-30.
 */
public class StringFormatter {

    private StringBuilder stringBuilder;

    public StringFormatter() {

    }

    public String getMemberListVerbose(JsonArray memberInformation) {
        stringBuilder = new StringBuilder();

        //if (!verbose) {
        //    stringBuilder.append(String.format("%-4s %-20s %-11s %-12s\n",
        //                "Nr", "Name", "Member ID", "Nr. of boats"));
        //}

        //for (int mIndex = 0; mIndex < memberInfo.size(); mIndex++) {
        //    JsonObject obj = memberInfo.getJsonObject(mIndex);

        //    if (verbose) {
        //        stringBuilder.append("===================\n");
        //        stringBuilder.append(String.format("%-4s %-11s %-20s %-11s\n",
        //                    "Nr", "SSN", "Name", "Member ID"));

        //        stringBuilder.append(String.format("%-4s %-11s %-20s %-11s\n",
        //                    mIndex + 1,
        //                    obj.getString("socialSecurityNr"),
        //                    obj.getString("firstName") + " " + obj.getString("lastName"),
        //                    obj.getString("memberID")));

        //        if (obj.getInt("numberOfBoats") > 0 ) {
        //            stringBuilder.append("\nBoats\n");
        //            stringBuilder.append("-----\n");
        //            stringBuilder.append(String.format("%-8s %-10s %-4s %-10s\n",
        //                        "Boat Nr ", "ID ", "Size", "Type"));

        //            JsonArray boats = obj.getJsonArray("boats");

        //            for (int bIndex = 0; bIndex < boats.size(); bIndex++) {
        //                JsonObject boat = boats.getJsonObject(bIndex);

        //                stringBuilder.append(String.format("%-8s %-10s %-4s %-10s\n",
        //                            (mIndex + 1) + "." + (bIndex + 1),
        //                            boat.getString("boatID"),
        //                            boat.getInt("size"),
        //                            boat.getString("type")));
        //            }

        //            stringBuilder.append("===================\n");
        //        } 

        //        stringBuilder.append("\n");
        //    } else {
        //        stringBuilder.append(String.format("%-4s %-20s %-11s %-12s\n",
        //                    mIndex + 1,
        //                    obj.getString("firstName") + " " + obj.getString("lastName"),
        //                    obj.getString("memberID"),
        //                    obj.getInt("numberOfBoats")));
        //    }
        //}

       return stringBuilder.toString();
    }

    public String getMemberListCompact(JsonArray memberInformation) {
        stringBuilder = new StringBuilder();

        return stringBuilder.toString();
    }
}
