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

    public boolean removeMember(long memberID) {
        throw new NotImplementedException();
    }

    public boolean editMember(long memberID, JsonObject json) {
        throw new NotImplementedException();
    }

    public boolean addBoat(long memberID, JsonObject json) {
        throw new NotImplementedException();
    }

    public boolean removeBoat(long memberID, long boatID) {
        throw new NotImplementedException();
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

    private Boat findBoat(long memberID, long boatID) {
        throw new NotImplementedException();
    }

    private void setBoatInfo(Boat boat, JsonObject json) {

    }
}
