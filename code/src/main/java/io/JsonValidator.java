package io;

import model.data.BoatType;

import javax.json.JsonObject;
import javax.json.JsonArray;
import javax.json.JsonValue;

/**
 *  Helper class that validates that JsonObjects/JsonArrays representing Boats and Members has 
 *  the correct structure and the correct fields.
 */
class JsonValidator {
    
    /**
     *  Checks if the specified JsonObject contains the neccessary fields in 
     *  order to be parsed as a Boat. 
     *
     *  @param jsonBoat - Boat JsonObject to validate
     *
     *  @return true if JSON is valid, false if it is not.
     */
    boolean isValidBoat(JsonObject jsonBoat) {
        if ( ! (jsonBoat.keySet().contains("size")
               && jsonBoat.keySet().contains("boatType")) ) {
            return false;
        }

        try {
            Integer.parseInt(jsonBoat.getString("size"));
            BoatType.fromString(jsonBoat.getString("boatType"));
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    /**
     *  Checks if the specified JsonObject contains the neccessary fields in 
     *  order to be parsed as a Member. Also ensures that values are not empty
     *  and that the social security number is a number.
     *
     *  Does not check for field memberID as it is optional when parsing a Member.
     *
     *  @param jsonMember - Member JsonObject to validate
     *
     *  @return true if JSON is valid, false if it is not.
     */
    boolean isValidMember(JsonObject jsonMember) {
        if ( ! (jsonMember.keySet().contains("socialSecurityNumber")
               && jsonMember.keySet().contains("firstName")
               && jsonMember.keySet().contains("lastName")
               && jsonMember.keySet().contains("address")) ) {
            return false;
        }
        
        if (jsonMember.getString("firstName").isEmpty()
            || jsonMember.getString("lastName").isEmpty()
            || jsonMember.getString("address").isEmpty()
            || jsonMember.getString("socialSecurityNumber").isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     *  Checks that the provided JsonArray has the correct structure in order
     *  to be parsed as a List by JsonParser. Also checks each member and each boat
     *  for validity.
     *
     *  @param arr - JsonArray to validate.
     *
     *  @return true if array structure is valid AND all json members are valid 
     *          AND all json boats are valid, false otherwise.
     */
    boolean isValidMemberArray(JsonArray arr) {

       try {
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
       }
       catch (Exception e) {
          return false;
       }

        return true;
    }

    /**
     *  Checks that the provided JsonArray has the correct structure in order
     *  to be parsed as a List by JsonParser. Also validates all Boats in the 
     *  array individually.
     *
     *  @param arr - JsonArray to validate.
     *
     *  @return true if array structure is valid AND each json boat is valid, false otherwise.
     */
    private boolean isValidBoatArray(JsonArray arr) {
        for (JsonValue val : arr) {
            JsonObject boat = (JsonObject)val;

            if ( ! (isValidBoat(boat)) ) {
                return false;
            }
        }

        return true;
    }
}
