import io.Dao;

import model.JsonParser;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.io.File;
import java.util.Scanner;

public class DaoTest {

    private static Dao testDao;
    private static Dao emptyDao;
    private static Dao oneMemberDao;
    private static Dao tenMemberDao;
    private static JsonParser jsonParser = new JsonParser();
    private static Scanner scanner;
    private static Scanner outputScanner;

    private static File outputFile;
    private static File emptyCorrectFile;
    private static File oneMemberCorrectFile;
    private static File threeMembersCorrectFile;
    private static File tenMembersCorrect;

    private static JsonArrayBuilder jsonArrayBuilder;
    private static JsonArray empty;
    private static JsonArray threeMembers;
    private static JsonArray oneMember;
    private static JsonArray tenMembers;

    private final JsonObject MEMBER1 = jsonParser.memberStringToJson("Anders", "Andersson",
            "Andersson v. 1", "85042249300001", "1111111111");
    private final JsonObject MEMBER2 = jsonParser.memberStringToJson("Lars", "Larsson",
            "Larsson v. 2", "85042249300002", "2222222222");
    private final JsonObject MEMBER3 = jsonParser.memberStringToJson("Johan", "Johansson",
            "Johansson v. 3", "85042249300003", "3333333333");
    private final JsonObject MEMBER4 = jsonParser.memberStringToJson("Lennart", "Lennartsson",
            "Lennartsson v. 4", "85042249300004", "4444444444");
    private final JsonObject MEMBER5 = jsonParser.memberStringToJson("Erik", "Eriksson",
            "Eriksson v. 5", "85042249300005", "5555555555");
    private final JsonObject MEMBER6 = jsonParser.memberStringToJson("Erling", "Erlingsson",
            "Erlings v. 6", "85042249300006", "6666666666");
    private final JsonObject MEMBER7 = jsonParser.memberStringToJson("Roger", "Rogersdotter",
            "Rogersgatan 7", "85042249300007", "7777777777");
    private final JsonObject MEMBER8 = jsonParser.memberStringToJson("Peter", "Petersson",
            "Petersson v. 8", "85042249300008", "8888888888");
    private final JsonObject MEMBER9 = jsonParser.memberStringToJson("Jöns", "Jönsson",
            "Jönsson v. 9", "85042249300009", "9999999999");
    private final JsonObject MEMBER10 = jsonParser.memberStringToJson("Daniel", "Danielsson",
            "Daniels v. 10", "85042249300010", "1010101010");


    @BeforeClass
    public static void beforeAll() {
        jsonArrayBuilder = Json.createArrayBuilder();

        try {
            outputFile = new File("./src/test/dao_test/output_test.json");
            emptyCorrectFile = new File("./src/test/dao_test/empty_correct.json");
            oneMemberCorrectFile = new File("./src/test/dao_test/one_member_correct.json");
            threeMembersCorrectFile = new File("./src/test/dao_test/three_members_correct.json");
            tenMembersCorrect = new File("./src/test/dao_test/ten_members_correct.json");
            outputScanner = new Scanner(outputFile);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

        testDao = new Dao(outputFile);
        oneMemberDao = new Dao(oneMemberCorrectFile);
        emptyDao = new Dao(emptyCorrectFile);
        tenMemberDao = new Dao(tenMembersCorrect);

    }

    @AfterClass
    public static void afterAll() {

    }

    @Before
    public void beforeEach() {

       empty = jsonArrayBuilder.build();
       oneMember = jsonArrayBuilder.add(MEMBER1).build();
       threeMembers = jsonArrayBuilder.add(MEMBER1).add(MEMBER2).add(MEMBER3).build();
       tenMembers = jsonArrayBuilder.add(MEMBER1).add(MEMBER2).add(MEMBER3).add(MEMBER4)
               .add(MEMBER5).add(MEMBER6).add(MEMBER7).add(MEMBER8).add(MEMBER9).add(MEMBER10).build();
    }

    @Test
    public void saveTest() {
        String output;
        String expected;

        testDao.save(empty);
        initializeScanner(emptyCorrectFile);

        output = readFromFile(outputScanner);
        expected = readFromFile(scanner);

        assertEquals(expected, output);

        testDao.save(oneMember);
        initializeScanner(oneMemberCorrectFile);

        output = readFromFile(outputScanner);
        expected = readFromFile(scanner);

        assertEquals(expected, output);

        testDao.save(threeMembers);
        initializeScanner(threeMembersCorrectFile);

        output = readFromFile(outputScanner);
        expected = readFromFile(scanner);

        assertEquals(expected, output);

        testDao.save(tenMembers);
        initializeScanner(tenMembersCorrect);

        output = readFromFile(outputScanner);
        expected = readFromFile(scanner);

        assertEquals(expected, output);
    }

    @Test
    public void loadTest() {
        JsonArray fromFile;
        JsonArray expected;

        fromFile = emptyDao.load();
        expected = empty;
        assertEquals(expected, fromFile);

        fromFile = oneMemberDao.load();
        expected = oneMember;
        assertEquals(expected, fromFile);

        fromFile = tenMemberDao.load();
        expected = tenMembers;
        assertEquals(expected, fromFile);
    }

    private void initializeScanner(File file) {
        try {
            scanner = new Scanner(file);
            outputScanner = new Scanner(outputFile);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String readFromFile(Scanner scanner) {
       String str = "";
        while(scanner.hasNext()) {
            str = str + scanner.nextLine();
        }

        return str;
    }

}
