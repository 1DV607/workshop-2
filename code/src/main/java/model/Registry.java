package model;


import io.Dao;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.HashMap;
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
        members = new HashMap<Long, MemberNode>();
    }

    public boolean addMember(JsonObject json) {
        throw new NotImplementedException();
    }

    /**
     * Removes a Member and all Boats connected to this member
     * @param memberID - id of the Member to remove
     * @return true if the Member was successfully removed, otherwise false
     */
    public boolean removeMember(long memberID) {
        MemberNode memberNode = members.get(memberID);
        if (memberNode == null) {
            return false;
        }
        else {
            memberNode.remove();
            members.remove(memberID);
            return true;
        }
    }

    public boolean editMember(long memberID, JsonObject json) {
        throw new NotImplementedException();
    }

    public boolean addBoat(long memberID, JsonObject json) {
        throw new NotImplementedException();
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
            return true;
        }
        catch (NullPointerException exception) {
            return false;
        }
    }

    public boolean editBoat(long memberID, long boatID, JsonObject json) {
        throw new NotImplementedException();
    }

    public JsonArray getAllMembersInfo() {
        throw new NotImplementedException();
    }

    public JsonArray getAllMembersInfoTest() {
        return Json.createArrayBuilder()
            .add(Json.createObjectBuilder()
                    .add("firstName", "Bengt")
                    .add("lastName", "Bengtsson")
                    .add("memberID", "1234")
                    .add("socialSecurityNr", "9007250575")
                    .add("numberOfBoats", 1)
                    .add("boats", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                            .add("boatID", "12341")
                            .add("size", 10)
                            .add("type", "SailBoat"))))
            .add(Json.createObjectBuilder()
                    .add("firstName", "Lars")
                    .add("lastName", "Larsson")
                    .add("memberID", "1235")
                    .add("socialSecurityNr", "9007250575")
                    .add("numberOfBoats", 3)
                    .add("boats", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                            .add("boatID", "12351")
                            .add("size", 5)
                            .add("type", "Canoe"))
                        .add(Json.createObjectBuilder()
                            .add("boatID", "12352")
                            .add("size", 7)
                            .add("type", "Motorsailer"))
                        .add(Json.createObjectBuilder()
                            .add("boatID", "12353")
                            .add("size", 3)
                            .add("type", "Other"))))
            .build();
    }

    private Member findMember(long memberID) {
        throw new NotImplementedException();
    }

    private void setMemberInfo(Member member, JsonObject json) {

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

        while (boatNode.getNextNode() != null) {
            long id = boatNode.getBoat().getBoatID();
            if (Long.compare(id, boatID) == 0) {
                return boatNode;
            }
            boatNode = (BoatNode) boatNode.getNextNode();

        }
        return null;
    }

    private void setBoatInfo(Boat boat, JsonObject json) {

    }
}
