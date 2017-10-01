package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

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
public class JsonParser {

    private JsonValidator validator;

    public JsonParser() {
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
    public Member jsonToMember(JsonObject json) {
        if ( ! validator.isValidMember(json)) {
            throw new IllegalArgumentException();
        }

        Member member;
        String ssn = json.getString("socialSecurityNumber");
        String firstName = json.getString("firstName");
        String lastName = json.getString("lastName");
        String address = json.getString("address");
        Long memberID;

        /*
         *  Try to read and set member's memberID. If the memberID field is 
         *  not found in the JSON, a member with a generated memberID is
         *  created instead.
         */
        try {
            memberID = Long.parseLong(json.getString("memberID"));
            member = new Member(memberID);
            member.setSocialSecurityNumber(ssn);
        } catch (Exception ex) {
            member = new Member(ssn);
        }

        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setAddress(address);

        return member;
    }

    /**
     *  Given a valid boat JsonObject, parses it into a Boat object.
     *
     *  If the JsonObject does not contain the field "boatID", a new Boat
     *  is created and a boatID is generated for it.
     *
     *  @param json - JsonObject to parse to Boat. Expects the JSON to have
     *                the following values: 
     *                
     *                      "boatID"            (String) [Optional]
     *                      "size"              (Int)
     *                      "boatType"          (Int)
     *
     *  @param [memberID] - Optional. Provided if the caller wants to generate boatID
     *                      instead of setting it from JSON.
     *
     *  @param [nrOfBoats] - Optional. Provided if the caller wants to generate boatID
     *                       instead of setting it from JSON.
     *
     *  @return - A Boat object initialized with the values in the provided
     *            JSON object.
     *
     *  @throws IllegalArgumentException if Json is not a valid Boat object 
     *          OR if boatID field is not in json and the optional parameters
     *          memberID and nrOfBoats are not provided.
     */
    public Boat jsonToBoat(JsonObject json, Optional<Long> memberID,
            Optional<Integer> nrOfBoats) {
        if ( ! validator.isValidBoat(json)) {
            throw new IllegalArgumentException();
        }

        Boat boat;
        int size = Integer.parseInt(json.getString("size"));
        BoatType type = BoatType.fromString(json.getString("boatType"));
        Long boatID;

        /*
         *  Try to read and set boat's boatID. If the boatID field is 
         *  not found in the JSON, a boat with a generated boatID is
         *  created instead.
         */
        try {
            boatID = Long.parseLong(json.getString("boatID"));
            boat = new Boat(boatID);
        } catch (Exception ex) {
            if ( ! (memberID.isPresent() && nrOfBoats.isPresent()) ) {
                throw new IllegalArgumentException("memberID and nrOfBoats of " 
                    + "the new boat's owner must be specified if boatID is "
                    + "missing from JSON.");
            }

            boat = new Boat(memberID.get(), nrOfBoats.get());
        }

        boat.setSize(size);
        boat.setBoatType(type);

        return boat;
    }

    /**
     *  Parses a Member object into a JsonObject.
     *
     *  @param member - Member to parse into a JsonObject
     *
     *  @return - A member JsonObject with the values set to the fields in the
     *            provided Member object.
     */
    public JsonObject memberToJson(Member member) {
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
    public JsonObject boatToJson(Boat boat) {
        return Json.createObjectBuilder()
            .add("boatID", Long.toString(boat.getBoatID()))
            .add("size", Integer.toString(boat.getSize()))
            .add("boatType", boat.getBoatType().getName())
            .build();
    }

    /**
     *  Parses a JsonArray into a list of Boats.
     *
     *  @param json - JsonArray containing the boat's information. Expects
     *                the following format:
     *
     *                [
     *                    { <boat object 1> },
     *                    { <boat object 2> },
     *                    ...
     *                ]
     *
     *  @return - An ArrayList with the parsed Boats.
     */
    public List<Boat> jsonToBoatList(JsonArray json) throws IllegalArgumentException {
        if ( ! validator.isValidBoatArray(json) ) {
            throw new IllegalArgumentException();
        }

        List<Boat> boats = new ArrayList<>();

        for (JsonValue val : json) {
            boats.add(jsonToBoat((JsonObject)val, Optional.empty(), Optional.empty()));
        }

        return boats;
    }

    /**
     *  Takes a Map with member ID and a linked list containing Member and
     *  associated boats and creates a JSON array in the format:
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
     *  @param  map - A Map with memberID as key and a linked list containing 
     *                Member and associated boats as value  
     *  
     *  @return - a JSON array containing all Members and Boats in 'map'
     */
    public JsonArray mapToJson(Map<Long, MemberNode> map) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Long key : map.keySet()) {        // Iterate over all member nodes in the map
            MemberNode mNode = map.get(key);

            JsonObject member = memberToJson(mNode.getMember());    // Create JSON member object
            JsonArrayBuilder boatsBuilder = Json.createArrayBuilder();

            Node currentNode = mNode;

            /*
             * Collect all boats associated with the member in a JSON array
             */
            while (currentNode.getNextNode() != null) {
                currentNode = currentNode.getNextNode();
                BoatNode bNode = (BoatNode)currentNode;
                boatsBuilder.add(boatToJson(bNode.getBoat()));
            }

            // Store member and associated boats in a JSON container object
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("member", member);
            objectBuilder.add("boats", boatsBuilder.build());

            arrayBuilder.add(objectBuilder.build());  // Add container object to array
        }

        return arrayBuilder.build();
    }

    /**
     *  Parses a JsonArray into a Map with memberID as key and a linked list 
     *  a Member and it's associated Boats.
     *
     *  @param array - JsonArray to parse to Map. Expects the following format:
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
     *  @return - A Map with the Members and associated Boats that are 
     *            contained in the JsonArray.
     */
    public Map<Long, MemberNode> jsonToMap(JsonArray array) {

        if ( ! validator.isValidMemberArray(array)) {
            throw new IllegalArgumentException();
        }

        Map<Long, MemberNode> result = new HashMap<>();

        for (JsonValue jStruct : array) {
            JsonObject jObject = (JsonObject)jStruct;
            JsonObject jMember = jObject.getJsonObject("member");
            JsonArray jBoats = jObject.getJsonArray("boats");
            Member member = this.jsonToMember(jObject.getJsonObject("member"));
            MemberNode mNode = new MemberNode(member);

            for (JsonObject jBoat : jBoats.getValuesAs(JsonObject.class)) {
                Boat boat = this.jsonToBoat(jBoat, Optional.empty(), Optional.empty());
                mNode.append(new BoatNode(boat));
            }

            result.put(member.getMemberID(), mNode);
        }

        return result;
    }
}
