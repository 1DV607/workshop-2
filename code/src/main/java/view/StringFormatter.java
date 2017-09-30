package view;

import javax.json.JsonArray;

/**
 * Created by Caroline Nilsson on 2017-09-30.
 */
public class StringFormatter {

    private StringBuilder stringBuilder;

    public StringFormatter() {

    }


    public String getMemberListVerbose(JsonArray memberInformation) {
       stringBuilder = new StringBuilder();

       return stringBuilder.toString();
    }

    public String getMemberListCompact(JsonArray memberInformation) {
        stringBuilder = new StringBuilder();

        return stringBuilder.toString();
    }
}
