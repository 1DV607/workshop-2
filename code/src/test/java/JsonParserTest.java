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

    @AfterClass
    public static void tearDownAll() {

    }

    @Before
    public void init() {
    }

    @After
    public void tearDown() {
    }

    @Test 
    public void boatToJsonTest() {
        JsonObject expected = Json.createObjectBuilder()
            .add("boatID", "123")
            .add("size", "0")
            .add("boatType", "1")
            .build();

        Boat boat = new Boat(123, 0, BoatType.Motorsailer);
        JsonObject actual = parser.boatToJson(boat);

        assertEquals(expected, actual);
    }

    @Test
    public void jsonToBoatTest() {
        Boat expected = new Boat(666, 10, BoatType.Kayak);

        JsonObject json = Json.createObjectBuilder()
            .add("boatID", "666")
            .add("size", "10")
            .add("boatType", "3")
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

        assertEquals(expected, actual);
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
        Boat boat2 = new Boat(222, 10, BoatType.SailBoat);
        MemberNode mNode = new MemberNode(member);
        mNode.append(new BoatNode(boat1));
        mNode.append(new BoatNode(boat2));
        expected.put(member.getMemberID(), mNode);

        JsonArray jsonArr = JsonParserTest.createJsonMemberArray(
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
                new String[] { "2", "1" }
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
        mNode.append(new BoatNode(new Boat(222, 10, BoatType.SailBoat)));
        expected.put(1111L, mNode);
        mNode = new MemberNode(new Member(2222, "2222222222", "Bertil", "Bertilsson", "Bertilgatan 1"));
        mNode.append(new BoatNode(new Boat(333, 9, BoatType.Motorsailer)));
        mNode.append(new BoatNode(new Boat(444, 15, BoatType.SailBoat)));
        expected.put(2222L, mNode);
        mNode = new MemberNode(new Member(3333, "3333333333", "Cici", "Cicisson", "Cicigatan 1"));
        expected.put(3333L, mNode);

        JsonArray jsonArr = JsonParserTest.createJsonMemberArray(
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
                new String[] { "2", "1", "1", "0" }
            }
        );

        Map<Long, MemberNode> actual = parser.jsonToMap(jsonArr);
        assertEquals(expected, actual);
    }

    @Test
    public void mapToJsonTestEmpty() {
        JsonArray jArr = parser.mapToJson(new HashMap<Long, MemberNode>());

        assertEquals("[]", jArr.toString());
    }

    @Test
    public void mapToJsonSingleMemberTest() {
        JsonArray expected = JsonParserTest.createJsonMemberArray(
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
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void mapToJsonMultipleMembersTest() {
        final JsonArray expected = JsonParserTest.createJsonMemberArray(
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
                new String[] { "0", "0", "1", "2", "2", "2" }
            });


        Map<Long, MemberNode> map = parser.jsonToMap(expected);
        JsonArray actual = parser.mapToJson(map);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void createJsonMemberArrayTest() {
        JsonArray expected = Json.createArrayBuilder()
            .add(Json.createObjectBuilder()
                    .add("member", Json.createObjectBuilder()
                        .add("socialSecurityNumber", "1111111111")
                        .add("memberID", "1111")
                        .add("firstName", "Lars")
                        .add("lastName", "Larsson")
                        .add("address", "Larsgatan 1"))
                    .add("boats", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                            .add("boatID", "111")
                            .add("size", "10")
                            .add("boatType", "0"))
                        .add(Json.createObjectBuilder()
                            .add("boatID", "222")
                            .add("size", "11")
                            .add("boatType", "0"))))
            .add(Json.createObjectBuilder()
                    .add("member", Json.createObjectBuilder()
                        .add("socialSecurityNumber", "2222222222")
                        .add("memberID", "2222")
                        .add("firstName", "Kalle")
                        .add("lastName", "Kallesson")
                        .add("address", "Kallegatan 2"))
                    .add("boats", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                            .add("boatID", "333")
                            .add("size", "12")
                            .add("boatType", "1"))))
            .add(Json.createObjectBuilder()
                    .add("member", Json.createObjectBuilder()
                        .add("socialSecurityNumber", "3333333333")
                        .add("memberID", "3333")
                        .add("firstName", "Per")
                        .add("lastName", "Persson")
                        .add("address", "Persgatan 3"))
                    .add("boats", Json.createArrayBuilder()))
            .build();

        JsonArray actual = JsonParserTest.createJsonMemberArray(
            new String[][] {
                new String[] { "1111111111", "2222222222", "3333333333" },
                new String[] { "1111", "2222", "3333" },
                new String[] { "Lars", "Kalle", "Per" },
                new String[] { "Larsson", "Kallesson", "Persson" },
                new String[] { "Larsgatan 1", "Kallegatan 2", "Persgatan 3" },
                new String[] { "111 222", "333", ""}
            },
            new String[][] {
                new String[] { "111", "222", "333" },
                new String[] { "10", "11", "12" },
                new String[] { "0", "0", "1" }
            });

        assertEquals(expected, actual);
    }

    private static JsonArray createJsonMemberArray(String[][] members,
                                                   String[][] boats) {
        JsonArrayBuilder expectedJson = Json.createArrayBuilder();
        int nrOfMembers = members[0].length;
        int nrOfBoats = boats.length > 0 
            ? boats[0].length 
            : 0;

        for (int mI = 0; mI < nrOfMembers; mI++) {
            JsonObjectBuilder container = Json.createObjectBuilder();

            container.add("member", Json.createObjectBuilder()
                    .add("socialSecurityNumber", members[0][mI])
                    .add("memberID", members[1][mI])
                    .add("firstName", members[2][mI])
                    .add("lastName", members[3][mI])
                    .add("address", members[4][mI]));

            JsonArrayBuilder jBoats = Json.createArrayBuilder();

            for (String mBoatID : members[5][mI].split(" ")) {
                for (int bI = 0; bI < nrOfBoats; bI++) {
                    if (boats[0][bI].equals(mBoatID)) {
                        jBoats.add(Json.createObjectBuilder()
                                .add("boatID", boats[0][bI])
                                .add("size", boats[1][bI])
                                .add("boatType", boats[2][bI]));
                    }
                }
            }

            container.add("boats", jBoats);
            expectedJson.add(container);
        }

        return expectedJson.build();
    }
}

