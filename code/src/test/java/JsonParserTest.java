import java.util.Map;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonValue;
import javax.json.JsonObject;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import model.MemberNode;
import model.BoatNode;
import model.JsonParser;
import model.Member;
import model.Boat;
import model.BoatType;

public class JsonParserTest {
    private static final String JSON_FILE_DIR = "./src/test/parser_test/";
    private static JsonParser parser;
    
    @BeforeClass
    public static void initAll() {
        parser = new JsonParser();
    }

    @Test 
    public void boatToJsonTest() {
        JsonArray expectedArr = TestUtils.readJsonArrayFromFile(JSON_FILE_DIR + "boat_multiple.json");
        List<Boat> boats = new ArrayList<>(); 
        boats.add(new Boat(123, 4, BoatType.Motorsailer));
        boats.add(new Boat(0, 10, BoatType.Sailboat));
        boats.add(new Boat(1_000_000_000_000L, 23, BoatType.Canoe));
        boats.add(new Boat(-1_000_000_000_000L, 23, BoatType.Other));
        boats.add(new Boat(999, -100, BoatType.Other));
        boats.add(new Boat(888, 0, BoatType.Other));
        boats.add(new Boat(777, 2_000_000_000, BoatType.Other));

        for (int i = 0; i < boats.size(); i++) {
            JsonObject expected = expectedArr.getJsonObject(i);
            JsonObject actual = parser.boatToJson(boats.get(i));
            assertTrue(TestUtils.jsonObjectsEqual(expected, actual));
        }
    }

    @Test
    public void jsonToBoatTest() {
        Boat expected = new Boat(666, 10, BoatType.Kayak);

        JsonObject json = Json.createObjectBuilder()
            .add("boatID", "666")
            .add("size", "10")
            .add("boatType", "Kayak")
            .build();

        Boat actual = parser.jsonToBoat(json);

        assertEquals(expected, actual);
    }

    @Test
    public void jsonToBoatListTest() {
        Boat expected = new Boat(666, 10, BoatType.Kayak);

        JsonObject json = Json.createObjectBuilder()
            .add("boatID", "666")
            .add("size", "10")
            .add("boatType", "Kayak")
            .build();

        Boat actual = parser.jsonToBoat(json);

        assertEquals(expected, actual);
    }

    @Test
    public void memberToJsonTest() {
        JsonObject expected = Json.createObjectBuilder()
            .add("socialSecurityNumber", "1234512345")
            .add("memberID", "1234")
            .add("firstName", "Daniel")
            .add("lastName", "Danielsson")
            .add("address", "Danielgatan 1")
            .build();

        Member member = new Member(1234, "1234512345", "Daniel", "Danielsson", "Danielgatan 1");
        JsonObject actual = parser.memberToJson(member);

        assertTrue(TestUtils.jsonObjectsEqual(expected, actual));
    }

    @Test
    public void jsonToMemberTest() {
        Member expected = new Member(2345, "9876598765", "Ernst", "Ernstsson", "Ernstgatan 1");

        JsonObject json = Json.createObjectBuilder()
            .add("socialSecurityNumber", "9876598765")
            .add("memberID", "2345")
            .add("firstName", "Ernst")
            .add("lastName", "Ernstsson")
            .add("address", "Ernstgatan 1")
            .build();

        Member actual = parser.jsonToMember(json);

        assertEquals(expected, actual);
    }

    @Test
    public void jsonToMapTestEmpty() {
        JsonArray jsonArr = Json.createArrayBuilder().build();

        Map<Long, MemberNode> map = parser.jsonToMap(jsonArr);

        assertNotNull(map);
        assertTrue(map.isEmpty());
    }

    @Test
    public void jsonToMapSingleMemberTest() {
        Map<Long, MemberNode> expected = new HashMap<>();
        Member member = new Member(1111, "1111111111", "Anna", "Annasson", "Annagatan 1"); 
        Boat boat1 = new Boat(111, 5, BoatType.Canoe);
        Boat boat2 = new Boat(222, 10, BoatType.Sailboat);
        MemberNode mNode = new MemberNode(member);
        mNode.append(new BoatNode(boat1));
        mNode.append(new BoatNode(boat2));
        expected.put(member.getMemberID(), mNode);

        JsonArray jsonArr = TestUtils.readJsonArrayFromFile(JSON_FILE_DIR + "member_single.json");
        Map<Long, MemberNode> actual = parser.jsonToMap(jsonArr);
        assertEquals(expected, actual);
    }

    @Test
    public void jsonToMapMultipleMemberTest() {
        Map<Long, MemberNode> expected = new HashMap<>();
        MemberNode mNode = new MemberNode(new Member(1111, "1111111111", "Anna", "Annasson", "Annagatan 1"));
        mNode.append(new BoatNode(new Boat(111, 5, BoatType.Canoe)));
        mNode.append(new BoatNode(new Boat(222, 10, BoatType.Sailboat)));
        expected.put(1111L, mNode);
        mNode = new MemberNode(new Member(2222, "2222222222", "Bertil", "Bertilsson", "Bertilgatan 1"));
        mNode.append(new BoatNode(new Boat(333, 9, BoatType.Motorsailer)));
        mNode.append(new BoatNode(new Boat(444, 15, BoatType.Sailboat)));
        expected.put(2222L, mNode);
        mNode = new MemberNode(new Member(3333, "3333333333", "Cici", "Cicisson", "Cicigatan 1"));
        expected.put(3333L, mNode);

        JsonArray jsonArr = TestUtils.readJsonArrayFromFile(JSON_FILE_DIR + "member_multiple.json");
        Map<Long, MemberNode> actual = parser.jsonToMap(jsonArr);
        assertEquals(expected, actual);
    }

    @Test
    public void mapToJsonTestEmpty() {
        JsonArray expected = Json.createArrayBuilder().build();
        JsonArray actual = parser.mapToJson(new HashMap<Long, MemberNode>());

        assertTrue(TestUtils.jsonArraysEqual(expected, actual));
    }

    @Test
    public void mapToJsonSingleMemberTest() {
        JsonArray expected = TestUtils.readJsonArrayFromFile(JSON_FILE_DIR + "member_single.json");
        Map<Long, MemberNode> map = parser.jsonToMap(expected);
        JsonArray actual = parser.mapToJson(map);
        assertTrue(TestUtils.jsonArraysEqual(expected, actual));
    }

    @Test
    public void mapToJsonMultipleMembersTest() {
        final JsonArray expected = TestUtils.readJsonArrayFromFile(JSON_FILE_DIR + "member_multiple.json");
        Map<Long, MemberNode> map = parser.jsonToMap(expected);
        JsonArray actual = parser.mapToJson(map);
        assertTrue(TestUtils.jsonArraysEqual(expected, actual));
    }
}

