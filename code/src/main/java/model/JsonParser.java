package model;

import java.util.Map;
import java.util.TreeMap;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;

/**
 *  Parses single Member and Boat objects to/from JSON as JsonObjects. Also
 *  parses the specific Map used in the Registry to/from a JSON array as a
 *  JsonArray for use when saving and loading the Registry.
 */
public class JsonParser {

    /**
     *  Given a valid member JsonObject, parses it into a Member object
     *  
     *  @param json - JsonObject to parse to Member. Expects the JSON to have
     *                the following values: 
     *                
     *                      "socialSecurityNumber"  (String)
     *                      "memberID"              (String)
     *                      "firstName"             (String)
     *                      "lastName"              (String)
     *                      "address"               (String)
     *
     *  @return - A Member object initialized with the values in the provided
     *            JSON object.
     */
    public Member jsonToMember(JsonObject json) {
        // TODO: Validate the JSON?
        String ssn = json.getString("socialSecurityNumber");
        long memberID = Long.parseLong(json.getString("memberID"));
        String firstName = json.getString("firstName");
        String lastName = json.getString("lastName");
        String address = json.getString("address");

        Member member = new Member(memberID);
        member.setSocialSecurityNumber(ssn);
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setAddress(address);

        return member;
    }

    public JsonObject memberStringToJson(String firstName, String lastName,
            String address, String memberID, String socialSecurityNumber) {
        return Json.createObjectBuilder()
            .add("socialSecurityNumber", socialSecurityNumber)
            .add("memberID", memberID)
            .add("firstName", firstName)
            .add("lastName", lastName)
            .add("address", address)
            .build();
    }

    /**
     *  Given a valid boat JsonObject, parses it into a Boat object
     *  
     *  @param json - JsonObject to parse to Boat. Expects the JSON to have
     *                the following values: 
     *                
     *                      "boatID"            (String)
     *                      "size"              (Int)
     *                      "boatType"          (Int)
     *
     *  @return - A Boat object initialized with the values in the provided
     *            JSON object.
     */
    public Boat jsonToBoat(JsonObject json) {
        // TODO: Validate the JSON?
        long boatID = Long.parseLong(json.getString("boatID"));
        int size = Integer.parseInt(json.getString("size"));
        BoatType type = BoatType.values()[Integer.parseInt(json.getString("boatType"))];

        Boat boat = new Boat(boatID);
        boat.setSize(size);
        boat.setBoatType(type);

        return boat;
    }

    /**
     *  Parses a Member object into a JsonObject.
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
     *  @return - A boat JsonObject with the values set to the fields in the
     *            provided Boat object.
     */
    public JsonObject boatToJson(Boat boat) {
        return Json.createObjectBuilder()
            .add("boatID", Long.toString(boat.getBoatID()))
            .add("size", Integer.toString(boat.getSize()))
            .add("boatType", Integer.toString(boat.getBoatType().ordinal()))
            .build();
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
        Map<Long, MemberNode> result = new TreeMap<>();

        for (JsonValue jStruct : array) {
            JsonObject jObject = (JsonObject)jStruct;
            JsonObject jMember = jObject.getJsonObject("member");
            JsonArray jBoats = jObject.getJsonArray("boats");
            Member member = this.jsonToMember(jObject.getJsonObject("member"));
            MemberNode mNode = new MemberNode(member);

            for (JsonObject jBoat : jBoats.getValuesAs(JsonObject.class)) {
                Boat boat = this.jsonToBoat(jBoat);
                mNode.append(new BoatNode(boat));
            }

            result.put(member.getMemberID(), mNode);
        }

        return result;
    }
}
