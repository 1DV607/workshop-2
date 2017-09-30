package model;


import io.Dao;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.Map;

/**
 *
 */
public class Registry {

    private Map<Long,MemberNode> members;
    private Dao dao;
    private JsonParser jsonParser;

    public Registry(Dao dao, JsonParser jsonParser) {
        this.dao = dao;
        this.jsonParser = jsonParser;
        members = jsonParser.jsonToMap(dao.load());
    }

    /**
     * Adds a new Member to the linked list with the member id as key
     * @param json - Member information as a JsonObject
     * @return true if successfully added otherwise false
     */
    public boolean addMember(JsonObject json) {
        try {
            Member member = jsonParser.jsonToNewMember(json);
            long memberID = member.getMemberID();
            MemberNode memberNode = new MemberNode(member);
            members.put(memberID, memberNode);
            saveChanges();
            return true;
        }
        catch (Exception e) {
            return false;
        }

    }

    /**
     * Removes a Member and all Boats connected to this member
     * @param memberID - id of the Member to remove
     * @return true if the Member was successfully removed, otherwise false
     */
    public boolean removeMember(long memberID) {
        MemberNode memberToRemove;
        try {
            memberToRemove = findMember(memberID);
            memberToRemove.remove();
            members.remove(memberID);
            saveChanges();
            return true;
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Takes a member id and change the Members information to the information
     * contained within the JsonObject
     * @param memberID - long, the id of the Member to change information
     * @param json - JsonObject, the new information
     * @return true if edit was successful otherwise false
     */
    public boolean editMember(long memberID, JsonObject json) {
        try {
            MemberNode memberNode = findMember(memberID);
            Member member = memberNode.getMember();
            setMemberInfo(member, json);
            saveChanges();
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    /**
     * Takes a member id and a JsonObject and adds a Boat to the member with
     * the information contained within the JsonObject
     * @param memberID - long, the Members id
     * @param json - JsonObject, contains information about the boat
     * @return true if successful otherwise false
     */
    public boolean addBoat(long memberID, JsonObject json) {
        System.out.println("in add boat");
        try {
            MemberNode memberNode = findMember(memberID);

            int nrOfBoats = 0;
            Node currentNode = memberNode;
            while ( (currentNode = currentNode.getNextNode()) != null ) {
                nrOfBoats++;
            }

            Boat boat = jsonParser.jsonToNewBoat(json, memberID, nrOfBoats + 1);
            BoatNode boatNode = new BoatNode(boat);
            memberNode.append(boatNode);
            saveChanges();
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    /**
     * Removes a BoatNode from the linked list
     * @param memberID - id of the Member who owns the Boat
     * @param boatID - id of the boat to remove
     * @return true if the boat was successfully removed, otherwise false
     */
    public boolean removeBoat(long memberID, long boatID)  {
        try {
            BoatNode boatNode = findBoat(memberID, boatID);
            boatNode.remove();
            saveChanges();
            return true;
        }
        catch (NullPointerException exception) {
            return false;
        }
    }

    /**
     * takes a member id, a boat id and a JsonObject and change the information of the Boat
     * to the information contained within the JsonObject
     * @param memberID - long, the id of the Member who owns the Boat
     * @param boatID - long, id of the Boat to change
     * @param json - JsonObject, contains the new information
     * @return true if successful otherwise false
     */
    public boolean editBoat(long memberID, long boatID, JsonObject json) {
        try {
            BoatNode boatNode = findBoat(memberID, boatID);
            Boat boat = boatNode.getBoat();
            setBoatInfo(boat, json);
            saveChanges();
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    /**
     * Returns an JsonArray containing all Members and Boats stored in the HashMap
     * @return - JsonArray, containing all Members information
     */
    public JsonArray getAllMembersInfo() {
        JsonArray array;
        try {
            array = jsonParser.mapToJson(members);
            return array;
        }
        catch (Exception exception) {
            array = Json.createArrayBuilder().build();
            return array;
        }
    }

    /**
     * Returns the Member object as a JsonObject
     * @param memberID - ID of the Member
     * @return JsonObject, null if the Member wasn't found
     */
    public JsonObject getMember(long memberID) {
        JsonObject json;
        try {
            MemberNode memberNode = findMember(memberID);
            json = jsonParser.memberToJson(memberNode.getMember());
            return json;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns all Boat objects associated with the Member with the specified
     * memberID.
     * @param memberID - long, ID of the Member who owns the Boats
     * @return JsonArray of member's boats
     */
    public JsonArray getMemberBoats(long memberID) {
        JsonArrayBuilder json = Json.createArrayBuilder();

        try {
            MemberNode mNode = findMember(memberID);

            Node current = mNode;
            while ( (current = current.getNextNode()) != null) {
                Boat boat = ((BoatNode)current).getBoat();
                json.add(jsonParser.boatToJson(boat));
            }

            return json.build();
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns the Boat object as an JsonObject
     * @param memberID - long, ID of the Member who owns the Boat
     * @param boatID - long, ID of the Boat
     * @return JsonObject, null if the boat wasn't found
     */
    public JsonObject getBoat(long memberID, long boatID) {
        JsonObject json;
        try {
            BoatNode boatNode = findBoat(memberID, boatID);
            json = jsonParser.boatToJson(boatNode.getBoat());
            return json;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Takes a member id and uses the id as key to finding the MemberNode in the HashMap
     * @param memberID - id of the Member to find
     * @return MemberNode, belonging to the member id
     * @throws NullPointerException
     */
    private MemberNode findMember(long memberID) throws NullPointerException {
        MemberNode memberNode = members.get(memberID);
        if (memberNode == null) {
            throw new NullPointerException();
        }
        else {
            return memberNode;
        }
    }

    /**
     * Takes a Member and a JsonObject and change the information stored in Member
     * to the information contained in JsonObject unless the Strings are empty
     * @param member - Member, the member to change
     * @param json - JsonObject, containing the new information
     */
    private void setMemberInfo(Member member, JsonObject json) {
        String firstName = json.getString("firstName");
        String lastName = json.getString("lastName");
        String address = json.getString("address");

        if (firstName.length() != 0) {
            member.setFirstName(firstName);
        }
        if (lastName.length() != 0) {
            member.setLastName(lastName);
        }
        if (address.length() != 0) {
            member.setAddress(address);
        }
    }

    /**
     * Takes a memberID and a boatID and returns the specific boat by going
     * through the Nodes in the Hash Map and comparing the boat ID's
     * @param memberID - id of the member who owns the boat
     * @param boatID - id of the boat to find
     * @return Boat or null if the Boat wasn't found
     * @throws NullPointerException if the Member does not exist or if the Member has no Boats
     */
    private BoatNode findBoat(long memberID, long boatID) throws NullPointerException {

        Node memberNode = members.get(memberID);
        BoatNode boatNode = (BoatNode) memberNode.getNextNode();

        long id =  boatNode.getBoat().getBoatID();
        if (Long.compare(id, boatID) == 0) {
            return boatNode;
        }

        while (boatNode.getNextNode() != null) {
            id = boatNode.getBoat().getBoatID();
            if (Long.compare(id, boatID) == 0) {
                return boatNode;
            }
            boatNode = (BoatNode) boatNode.getNextNode();

        }
        return null;
    }

    /**
     * Takes a Boat and a JsonObject and change the information in the Boat object
     * to the information in the JsonObject unless the Strings are empty
     * @param boat - Boat, to change information in
     * @param json - JsonObject, containing the new information
     */
    private void setBoatInfo(Boat boat, JsonObject json) throws NumberFormatException {
        String boatType = json.getString("boatType");
        String size = json.getString("size");

        if (boatType.length() != 0) {
            boat.setBoatType(BoatType.fromString(boatType));
        }
        if (size.length() != 0)  {
            boat.setSize(Integer.parseInt(size));
        }
    }

    /**
     * Calls the method save() in dao to save new information.
     */
    private void saveChanges() {
        dao.save(jsonParser.mapToJson(members));
    }
}
