package io;



import model.Boat;
import model.BoatType;
import model.Member;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;

/**
 *  Parses single as well as collections of Member and Boat objects to/from 
 *  JSON as JsonObject/JsonArray. The JSON is validated using the helper class
 *  JsonValidator before being parsed.
 */
class JsonParser {

    private JsonValidator validator;

    JsonParser() {
        validator = new JsonValidator();
    }

    /**
     *  Given a valid member JsonObject, parses it into a Member object.
     *
     *  If the JsonObject does not contain the field "memberID", a new Member
     *  is created and a memberID is generated for it.
     *
     *  @param json - JsonObject to parse to Member. Expects the JSON to have
     *                the following values: 
     *                
     *                      "socialSecurityNumber"  (String)
     *                      "memberID"              (String) [Optional]
     *                      "firstName"             (String)
     *                      "lastName"              (String)
     *                      "address"               (String)
     *
     *  @return - A Member object initialized with the values in the provided JSON object.
     *
     *  @throws IllegalArgumentException if the json is not a valid Member Json Object.
     */
    private Member jsonToMember(JsonObject json) {
        if ( ! validator.isValidMember(json)) {
            throw new IllegalArgumentException();
        }

        Member member;
        String ssn = json.getString("socialSecurityNumber");
        String firstName = json.getString("firstName");
        String lastName = json.getString("lastName");
        String address = json.getString("address");
        int memberID;

        memberID = Integer.parseInt(json.getString("memberID"));
        member = new Member(memberID, ssn, firstName, lastName, address);
        member.setSocialSecurityNumber(ssn);
        return member;


    }


    /**
     *  Parses a Member object into a JsonObject.
     *
     *  @param member - Member to parse into a JsonObject
     *
     *  @return - A member JsonObject with the values set to the fields in the
     *            provided Member object.
     */
    private JsonObject memberToJson(Member member) {
        return Json.createObjectBuilder()
            .add("socialSecurityNumber", member.getSocialSecurityNumber())
            .add("memberID", Long.toString(member.getMemberID()))
            .add("firstName", member.getFirstName())
            .add("lastName", member.getLastName())
            .add("address", member.getAddress())
            .build();
    }

    /**
     *  Parses a Boat object into a JsonObject.
     *
     *  @param boat - Boat to parse into a JsonObject
     *
     *  @return - A boat JsonObject with the values set to the fields in the
     *            provided Boat object.
     */
    private JsonObject boatToJson(Boat boat) {
        return Json.createObjectBuilder()
            .add("boatID", Long.toString(boat.getBoatID()))
            .add("size", Integer.toString(boat.getSize()))
            .add("boatType", boat.getBoatType().getName())
            .build();
    }

    /**
     *  Takes a List of Members and creates a JSON array in the format:
     *
     *  [
     *      {
     *          "member": { <member object> },
     *          "boats": [ { <boat object 1> }, { <boat object 2> } ... ]
     *      },
     *      {
     *          "member": { <member object 2 },
     *          "boats": [ { <boat object 1> }, { <boat object 2> } ... ]
     *      },
     *      ...
     *  ]
     *  
     *  @param  members - A List of Members  
     *  
     *  @return - a JSON array containing all Members and Boats in 'members'
     */
    JsonArray membersToJson(List<Member> members) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Member member : members) {        // Iterate over all members
            JsonObject jMember = this.memberToJson(member);    // Create JSON member object
            JsonArrayBuilder jBoats = Json.createArrayBuilder();

            ArrayList<Boat> boats = member.getAllBoats();

            /*
             * Collect all boats associated with the member in a JSON array
             */
            for (Boat boat : boats) {
                jBoats.add(this.boatToJson(boat));
            }

            // Store member and associated boats in a JSON container object
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("member", jMember);
            objectBuilder.add("boats", jBoats.build());

            arrayBuilder.add(objectBuilder.build());  // Add container object to array
        }

        return arrayBuilder.build();
    }

    /**
     *  Parses a JsonArray into a List of Members. The boats associated
     *  with a member are added to the respective Member object.
     *
     *  @param array - JsonArray to parse to List. Expects the following format:
     *
     *  [
     *      {
     *          "member": { <member object> },
     *          "boats": [ { <boat object 1> }, { <boat object 2> } ... ]
     *      },
     *      {
     *          "member": { <member object 2 },
     *          "boats": [ { <boat object 1> }, { <boat object 2> } ... ]
     *      },
     *      ...
     *  ]
     *
     *  @return - A List, in the form of an ArrayList, of Members which
     *            each contain respective associated Boats.
     */
    ArrayList<Member> jsonToMembers(JsonArray array) {

        if ( ! validator.isValidMemberArray(array)) {
            throw new IllegalArgumentException();
        }

        ArrayList<Member> result = new ArrayList<>();

        for (JsonValue jStructure : array) {
            JsonObject jObject = (JsonObject)jStructure;
            JsonObject jMember = jObject.getJsonObject("member");
            JsonArray jBoats = jObject.getJsonArray("boats");
            try {
                Member member = this.jsonToMember(jMember);

                for (JsonObject jBoat : jBoats.getValuesAs(JsonObject.class)) {
                    if (!validator.isValidBoat(jBoat)) {
                        throw new IllegalArgumentException();
                    }

                    int size = Integer.parseInt(jBoat.getString("size"));
                    BoatType type = BoatType.fromString(jBoat.getString("boatType"));
                    int boatID;

                    boatID = Integer.parseInt(jBoat.getString("boatID"));
                    member.addBoat(boatID, type, size);
                    result.add(member);
                }
            }
            catch(Exception exception){
                System.out.println("Not able to load members");
            }

        }


        return result;
    }
}
