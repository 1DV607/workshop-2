package model;


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.Map;

/**
 *
 */
public class Registry {

    private Map<Long,MemberNode> members;

    public Registry() {

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
