package model;

import javax.json.JsonObject;
import javax.json.JsonArray;
import javax.json.JsonValue;

public class JsonValidator {
    
    public boolean isValidBoat(JsonObject jsonBoat) {
        if ( ! (jsonBoat.keySet().contains("size")
               && jsonBoat.keySet().contains("boatType")) ) {
            return false;
        }

        try {
            Integer.parseInt(jsonBoat.getString("size"));
            BoatType type = BoatType.values()[Integer.parseInt(jsonBoat.getString("boatType"))];
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    public boolean isValidMember(JsonObject jsonMember) {
        if ( ! (jsonMember.keySet().contains("socialSecurityNumber")
               && jsonMember.keySet().contains("firstName")
               && jsonMember.keySet().contains("lastName")
               && jsonMember.keySet().contains("address")) ) {
            return false;
        }
        
        if (jsonMember.getString("firstName").isEmpty()
            || jsonMember.getString("lastName").isEmpty()
            || jsonMember.getString("address").isEmpty()) {
            return false;
        }

        try {
            Long.parseLong(jsonMember.getString("socialSecurityNumber"));
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    public boolean isValidMemberArray(JsonArray arr) {
        for (JsonValue val : arr) {
            JsonObject member = ((JsonObject)val).getJsonObject("member");
            JsonArray boats = ((JsonObject)val).getJsonArray("boats");

            if ( member.getValueType() != JsonValue.ValueType.OBJECT
                    || boats.getValueType() != JsonValue.ValueType.ARRAY ) {
                return false;
            }

            if ( ! (isValidMember(member) && isValidBoatArray(boats)) ) {
                return false;
            }
        }

        return true;
    }

    public boolean isValidBoatArray(JsonArray arr) {
        for (JsonValue val : arr) {
            JsonObject boat = (JsonObject)val;

            if ( ! (isValidBoat(boat)) ) {
                return false;
            }
        }

        return true;
    }
}
