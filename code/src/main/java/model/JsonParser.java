package model;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonArray;

/**
 *
 */
public class JsonParser {

    public Member jsonToMember(JsonObject json) {
        long ssn = Long.parseLong(json.getString("socialSecurityNumber"));
        long memberID = Long.parseLong(json.getString("memberID"));
        String firstName = json.getString("firstName");
        String lastName = json.getString("lastName");
        String adress = json.getString("adress");

        Member member = new Member(memberID);
        member.setSocialSecurityNumber(ssn);
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setAdress(adress);

        return member;
    }

    public Boat jsonToBoat(JsonObject json) {
        long boatID = Long.parseLong(json.getString("boatID"));
        int size = json.getInt("size");
        BoatType type = (BoatType)json.getInt("boatType");

        Boat boat = new Boat(boatID);
        boat.setSize(size);
        boat.setBoatType(type);

        return boat;
    }

    public JsonObject memberToJson(Member member) {
        return Json.createJsonObjectBuilder()
            .add("socialSecurityNumber", member.getSocialSecurityNumber())
            .add("memberID", member.getMemberID())
            .add("firstName", member.getFirstName())
            .add("lastName", member.getLastName())
            .add("adress", member.getAdress())
            .build();
    }

    public JsonObject boatToJson(Boat boat) {
        return Json.createJsonObjectBuilder()
            .add("boatID", boat.getBoatID())
            .add("size", boat.getSize())
            .add("boatType", (int)boat.getBoatType())
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
     *  @return a JSON array containing all Members and Boats in 'map'
     */
    public JsonArray mapToJson(Map<Long, MemberNode> map) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Long key : map.keySet()) {        // Iterate over all member nodes in the map
            MemberNode mNode = map.get(key);

            JsonObject member = memberToJson(mNode.getMember());    // Create JSON member object
            JsonArrayBuilder boatsBuilder = new Json.createArrayBuilder()

            Node currentNode = mNode;

            /*
             * Collect all boats associated with the member in a JSON array
             */
            while (currentNode.getNextNode() != null) {
                BoatNode bNode = (BoatNode)currentNode;
                boatsBuilder.add(boatToJson(bNode.getBoat()));
                currentNode = currentNode.getNextNode();
            }

            // Store member and associated boats in a JSON container object
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("member", member);
            objectBuilder.add("boats", boatsbuilder.build());

            arrayBuilder.add(objectBuilder.build());  // Add container object to array
        }

        return arrayBuilder.build();
    }

    public Map<Long, MemberNode> jsonToMap(JsonArray array) {

    }
}
