import model.Member;
import model.JsonParser;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class MemberTest {

    private static final String JSON_FILE_DIR = "src/test/member_test_files/";

    private static String socialSecurityNumber;

    private static ArrayList<String> ssnList;
    private static ArrayList<Member> members;

    /**
     * creates 1 million unique social security numbers
     */
    @BeforeClass
    public static void beforeAll() {

        ssnList = new ArrayList<>();

        long temp = 1111111111;
        for (int i = 0; i < 1000000; i++) {
            temp = temp+i;
            socialSecurityNumber = Long.toString(temp);
            ssnList.add(socialSecurityNumber);
        }
    }

    @Before
    public void beforeEach() {
        members = new ArrayList<>();
    }

    /**
     * creates 1 million members with different social security numbers and then
     * check if the membersIDs are different for all members
     */
    @Test
    public void idGenerationTest() {

        for (String nr : ssnList) {
            members.add(new Member(nr));
        }
        Member memberCompare;
        Member memberCompareWith;

        for (int i = 0; i < 1000000 -1; i++) {
            memberCompare = members.get(i);
            memberCompareWith = members.get(i+1);

            assertNotEquals(memberCompare.getMemberID(), memberCompareWith.getMemberID());
        }
    }

    @Test
    public void equalsEqualTest() {
        JsonParser parser = new JsonParser();
        JsonArray arr = TestUtils.readJsonArrayFromFile(JSON_FILE_DIR + "few_equal.json");

        for (JsonValue json : arr) {
            members.add(parser.jsonToMember((JsonObject)json));
        }

        for (int i = 0; i < members.size() - 1; i++) {
            Member m1 = members.get(i);
            Member m2 = members.get(i + 1);
            assertEquals(m1, m2);
        }
    }

    @Test
    public void equalsNotEqualTest() {
        JsonParser parser = new JsonParser();
        JsonArray arr = TestUtils.readJsonArrayFromFile(JSON_FILE_DIR + "few_not_equal.json");

        for (JsonValue json : arr) {
            members.add(parser.jsonToMember((JsonObject)json));
        }

        for (int i = 0; i < members.size() - 1; i++) {
            Member m1 = members.get(i);
            Member m2 = members.get(i + 1);
            assertNotEquals(m1, m2);
        }
    }
}
