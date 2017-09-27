import io.Dao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import java.io.File;

public class DaoTest {

    private static Dao testDao;

    private static File outputFile;
    private static File emptyCorrectFile;
    private static File oneMemberCorrectFile;
    private static File threeMembersCorrectFile;

    private static JsonArrayBuilder jsonArrayBuilder;
    private static JsonArray empty;
    private static JsonArray threeMembers;
    private static JsonArray oneMember;

    private final static String MEMBER1 =
            "\"firstName\" : \"Anders\","+
            " \"lastName\" : \"Andersson\", " +
            " \"memberID\" : \"85042249300001\", " +
            " \"boats\" : 1";

    private final static String MEMBER2 =
            "\"firstName\" : \"Daniel\","+
                    " \"lastName\" : \"Grundström\", " +
                    " \"memberID\" : \"85042249300002\", " +
                    " \"boats\" : 2";

    private final static String MEMBER3 =
            "\"firstName\" : \"Jennie\","+
                    " \"lastName\" : \"Ahlgren\", " +
                    " \"memberID\" : \"85042249300003\", " +
                    " \"boats\" : 3";

    @BeforeAll
    static void beforeAll() {
        jsonArrayBuilder = Json.createArrayBuilder();

        testDao = new Dao("./src/test/dao_test/output_test.json");

        try {
            outputFile = new File("./src/test/dao_test/output_test.json");
            emptyCorrectFile = new File("./src/test/dao_test/empty_correct.json");
            oneMemberCorrectFile = new File("./src/test/dao_test/one_member_correct.json");
            threeMembersCorrectFile = new File("./src/test/dao_test/three_members_correct.json");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @AfterAll
    static void afterAll() {

    }

    @BeforeEach
    static void beforeEach() {
       empty = jsonArrayBuilder.build();
       oneMember = jsonArrayBuilder.add(MEMBER1).build();
       threeMembers = jsonArrayBuilder.add(MEMBER1).add(MEMBER2).add(MEMBER3).build();
    }

    @Test
    static void saveTest() {
        testDao.save(empty);
    }

    @Test
    static void loadTest() {
        fail("lala");
    }

}