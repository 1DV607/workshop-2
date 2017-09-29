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

    private final static String EMPTY_JSON_ARRAY = "[]";

    private static Map<Long, MemberNode> map;
    private static JsonParser parser;
    
    @BeforeClass
    public static void initAll() {
        map = new HashMap<Long, MemberNode>();
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
        map.clear();
    }

    @Test
    public void mapToJsonTestEmpty() {
        JsonArray jArr = parser.mapToJson(map);

        assertEquals(EMPTY_JSON_ARRAY, jArr.toString());
    }

    @Test
    public void mapToJsonSingleNoBoatsTest() {
        //final String EXPECTED = 
        //    "[" + 
        //      "{" +
        //        "\"member\":" +
        //          "{" +
        //            "\"socialSecurityNumber\":" +   "\"5010010202\"," +
        //            "\"memberID\":"             +   "\"1111\"," +
        //            "\"firstName\":"            +   "\"Lars\"," +
        //            "\"lastName\":"             +   "\"Larsson\"," +
        //            "\"address\":"              +   "\"Larsgatan 10\"" +
        //          "}," + 
        //        "\"boats\":" + 
        //          "[" +
        //          "]" +
        //      "}" +
        //    "]";

        //Member member = new Member(1111);
        //member.setFirstName("Lars");
        //member.setLastName("Larsson");
        //member.setSocialSecurityNumber(5010010202L);
        //member.setAddress("Larsgatan 10");

        //map.put(member.getMemberID(), new MemberNode(member));
        //jsonarray jarr = parser.maptojson(map);

        //assertequals(expected, jarr.tostring());
    }

    @Test
    public void maptojsonmultiplememberstest() {
        //final JsonArray expected = JsonParserTest.createJsonArray(
        //    new String[][] {
        //        new String[] { "Lars", "Kalle", "Per" },
        //        new String[] { "Larsson", "Kallesson", "Persson" },
        //        new String[] { "Larsgatan 1", "Kallegatan 2", "Persgatan 3" },
        //        new String[] { "1111111111", "2222222222", "3333333333" },
        //        new String[] { "1111", "2222", "3333" },
        //        new String[] { "111 222", "333", "444 555 666" },
        //    },
        //    new String[][] {
        //        new String[] { "111", "222", "333", "444", "555", "666" },
        //        new String[] { 10, 11, 12, 13, 14, 15 },
        //        new String[] { 0, 0, 1, 2, 2, 2 }
        //    });


        //Member member = new Member(1111);
        //member.setFirstName("Lars");
        //member.setLastName("Larsson");
        //member.setSocialSecurityNumber(1111111111L);
        //member.setAddress("Larsgatan 1");

        //Boat boat1 = new Boat(111);
        //boat1.setSize(10);
        //boat1.setBoatType(BoatType.SailBoat);

        //Boat boat2 = new Boat(222);
        //boat2.setSize(11);
        //boat2.setBoatType(BoatType.SailBoat);

        //MemberNode mNode = new MemberNode(member);
        //mNode.append(new BoatNode(boat1));
        //mNode.append(new BoatNode(boat2));

        //map.put(member.getMemberID(), new MemberNode(member));

        //member = new Member(2222);
        //member.setFirstName("Kalle");
        //member.setLastName("Kallesson");
        //member.setSocialSecurityNumber(2222222222L);
        //member.setAddress("Kallegatan 2");

        //boat1 = new Boat(333);
        //boat1.setSize(12);
        //boat1.setBoatType(BoatType.Motorsailer);

        //mNode = new MemberNode(member);
        //mNode.append(new BoatNode(boat1));

        //map.put(member.getMemberID(), new MemberNode(member));

        //member = new Member(3333);
        //member.setFirstName("Per");
        //member.setLastName("Persson");
        //member.setSocialSecurityNumber(3333333333L);
        //member.setAddress("Persgatan 3");

        //boat1 = new Boat(444);
        //boat1.setSize(13);
        //boat1.setBoatType(BoatType.Canoe);

        //boat2 = new Boat(555);
        //boat2.setSize(14);
        //boat2.setBoatType(BoatType.Canoe); 
        //Boat boat3 = new Boat(666);
        //boat3.setSize(15);
        //boat3.setBoatType(BoatType.Canoe);

        //mNode = new MemberNode(member);
        //mNode.append(new BoatNode(boat1));
        //mNode.append(new BoatNode(boat2));
        //mNode.append(new BoatNode(boat3));

        //map.put(member.getMemberID(), new MemberNode(member));

        //JsonArray jArr = parser.mapToJson(map);
    }

    @Test
    public void createJsonMemberArrayTest() {
        JsonArray expected = Json.createArrayBuilder()
            .add(Json.createObjectBuilder()
                    .add("member", Json.createObjectBuilder()
                        .add("firstName", "Lars")
                        .add("lastName", "Larsson")
                        .add("address", "Larsgatan 1")
                        .add("socialSecurityNumber", "1111111111")
                        .add("memberID", "1111"))
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
                        .add("firstName", "Kalle")
                        .add("lastName", "Kallesson")
                        .add("address", "Kallegatan 2")
                        .add("socialSecurityNumber", "2222222222")
                        .add("memberID", "2222"))
                    .add("boats", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                            .add("boatID", "333")
                            .add("size", "12")
                            .add("boatType", "1"))))
            .build();

        JsonArray actual = JsonParserTest.createJsonMemberArray(
            new String[][] {
                new String[] { "Lars", "Kalle" },
                new String[] { "Larsson", "Kallesson" },
                new String[] { "Larsgatan 1", "Kallegatan 2" },
                new String[] { "1111111111", "2222222222" },
                new String[] { "1111", "2222" },
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
        int nrOfBoats = boats[0].length;

        System.out.println("Members: " + nrOfMembers + " Boats: " + nrOfBoats);

        for (int mI = 0; mI < nrOfMembers; mI++) {
            System.out.println(mI);
            JsonObjectBuilder container = Json.createObjectBuilder();

            container.add("member", Json.createObjectBuilder()
                    .add("firstName", members[0][mI])
                    .add("lastName", members[1][mI])
                    .add("address", members[2][mI])
                    .add("socialSecurityNumber", members[3][mI])
                    .add("memberID", members[4][mI]));

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

