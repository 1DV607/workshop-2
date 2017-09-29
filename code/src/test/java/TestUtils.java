import javax.json.Json;
import javax.json.JsonValue;
import javax.json.JsonString;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class TestUtils {
    public static JsonArray createJsonMemberArray(String[][] members,
                                                   String[][] boats) {
        JsonArrayBuilder expectedJson = Json.createArrayBuilder();
        int nrOfMembers = members.length > 0 
            ? members[0].length
            : 0;
        int nrOfBoats = boats.length > 0 
            ? boats[0].length 
            : 0;

        for (int mI = 0; mI < nrOfMembers; mI++) {
            JsonObjectBuilder container = Json.createObjectBuilder();

            container.add("member", Json.createObjectBuilder()
                    .add("socialSecurityNumber", members[0][mI])
                    .add("memberID", members[1][mI])
                    .add("firstName", members[2][mI])
                    .add("lastName", members[3][mI])
                    .add("address", members[4][mI]));

            JsonArrayBuilder jBoats = Json.createArrayBuilder();

            for (String mBoatID : members[5][mI].split(" ")) {
                for (int bI = 0; bI < nrOfBoats; bI++) {
                    if (boats[0][bI].equals(mBoatID)) {
                        jBoats.add(Json.createObjectBuilder()
                                .add("boatID", boats[0][bI])
                                .add("size", boats[1][bI])
                                .add("boatType", boats[2][bI]));
                    }
                }
            }

            container.add("boats", jBoats);
            expectedJson.add(container);
        }

        return expectedJson.build();
    }

    public static boolean jsonObjectsEqual(JsonObject obj1, JsonObject obj2) {
        if (obj1.keySet().size() != obj2.keySet().size()) {
            return false; 
        }

        for (String key : obj1.keySet()) {
            if ( !(obj2.keySet().contains(key)) ) {
                return false;
            }

            return jsonValuesEqual(obj1.get(key), obj2.get(key));
        }

        return true;
    }

    public static boolean jsonArraysEqual(JsonArray arr1, JsonArray arr2) {
        if (arr1.size() != arr2.size()) {
            return false; 
        }

        for (JsonValue val1 : arr1) {
            if ( !(arr2.contains(val1))) {
                return false;
            }

            return jsonValuesEqual(val1, arr2.get(arr2.indexOf(val1)));
        }

        return true;
    }

    private static boolean jsonValuesEqual(JsonValue val1, JsonValue val2) {
        if (val1.getValueType() != val2.getValueType()) {
            return false;
        }

        switch (val1.getValueType()) {
            case OBJECT: {
                return jsonObjectsEqual((JsonObject)val1, (JsonObject)val2);
            }

            case ARRAY: {
                return jsonArraysEqual((JsonArray)val1, (JsonArray)val2);
            }

            case STRING: {
                return ((JsonString)val1).equals((JsonString)val2);
            }

            case NUMBER: {
                return (JsonNumber)val1 == (JsonNumber)val2;
            }
                
            default: {
                return true;
            }
        }
    }
}
