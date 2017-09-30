import java.util.Map;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Arrays;

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
    private static JsonParser parser;
    
    @BeforeClass
    public static void initAll() {
        parser = new JsonParser();
    }

    @Test 
    public void boatToJsonTest() {
        JsonObject expected = Json.createObjectBuilder()
            .add("boatID", "123")
            .add("size", "0")
            .add("boatType", "Motorsailer")
            .build();

        Boat boat = new Boat(123, 0, BoatType.Motorsailer);
        JsonObject actual = parser.boatToJson(boat);

        assertTrue(TestUtils.jsonObjectsEqual(expected, actual));
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

        JsonArray jsonArr = TestUtils.createJsonMemberArray(
            new String[][] {
                new String[] { "1111111111" },
                new String[] { "1111" },
                new String[] { "Anna" },
                new String[] { "Annasson" },
                new String[] { "Annagatan 1" },
                new String[] { "111 222" },
            },
            new String[][] {
                new String[] { "111", "222" },
                new String[] { "5", "10" },
                new String[] { "Canoe", "Sailboat" }
            }
        );

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

        JsonArray jsonArr = TestUtils.createJsonMemberArray(
            new String[][] {
                new String[] { "1111111111", "2222222222", "3333333333" },
                new String[] { "1111", "2222", "3333" },
                new String[] { "Anna", "Bertil", "Cici" },
                new String[] { "Annasson", "Bertilsson", "Cicisson" },
                new String[] { "Annagatan 1", "Bertilgatan 1", "Cicigatan 1" },
                new String[] { "111 222", "333 444", "" },
            },
            new String[][] {
                new String[] { "111", "222", "333", "444" },
                new String[] { "5", "10", "9", "15" },
                new String[] { "Canoe", "Motorsailer", "Motorsailer", "Sailboat" }
            }
        );

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
        JsonArray expected = TestUtils.createJsonMemberArray(
            new String[][] {
                new String[] { "1111111111" },
                new String[] { "1111" },
                new String[] { "Lars" },
                new String[] { "Larsson" },
                new String[] { "Larsgatan 1" },
                new String[] { "" },
            },
            new String[][] {}
        );

        Map<Long, MemberNode> map = parser.jsonToMap(expected);
        JsonArray actual = parser.mapToJson(map);
        assertTrue(TestUtils.jsonArraysEqual(expected, actual));
    }

    @Test
    public void mapToJsonMultipleMembersTest() {
        final JsonArray expected = TestUtils.createJsonMemberArray(
            new String[][] {
                new String[] { "1111111111", "2222222222", "3333333333" },
                new String[] { "1111", "2222", "3333" },
                new String[] { "Lars", "Kalle", "Per" },
                new String[] { "Larsson", "Kallesson", "Persson" },
                new String[] { "Larsgatan 1", "Kallegatan 2", "Persgatan 3" },
                new String[] { "111 222", "333", "444 555 666" },
            },
            new String[][] {
                new String[] { "111", "222", "333", "444", "555", "666" },
                new String[] { "10", "11", "12", "13", "14", "15" },
                new String[] { "Sailboat", "Sailboat", "Motorsailer", "Canoe", "Canoe", "Canoe" }
            });


        Map<Long, MemberNode> map = parser.jsonToMap(expected);
        JsonArray actual = parser.mapToJson(map);
        assertTrue(TestUtils.jsonArraysEqual(expected, actual));
    }
}

