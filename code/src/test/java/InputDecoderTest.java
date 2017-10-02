import controller.InputDecoder;
import controller.UserCommand;
import org.junit.*;
import org.junit.rules.ExpectedException;

import javax.json.JsonArray;
import javax.json.JsonObject;

import static org.junit.Assert.*;

public class InputDecoderTest {

    private static InputDecoder inputDecoder;
    private static UserCommand userCommand;

    private static JsonArray jsonArray;
    private Object [] commandArray;
    private String [] actualCommand = {"1", "2 1", "3 1", "4 1", "5 1", "6 1 1", "7 1 1", "8", "9", "", "2", "6 1", "a b c",
            "120", "0"};
    private String [] expectedCommand = {"AddMember", "ViewMember", "EditMember", "RemoveMember",
            "AddBoat", "EditBoat", "RemoveBoat", "ChangeList", "Exit" };

    private static String expectedMemberID;
    private static String expectedBoatID;

    @Rule
    public final ExpectedException exception = ExpectedException.none();


    @BeforeClass
    public static void beforeAll() {
        inputDecoder = new InputDecoder();
        jsonArray = TestUtils.readJsonArrayFromFile("./src/test/parser_test/member_multiple.json");

        JsonObject json = jsonArray.getJsonObject(0);

        expectedMemberID = json.getJsonObject("member").getString("memberID");
        expectedBoatID = json.getJsonArray("boats").getJsonObject(0).getString("boatID");

    }

    @Test
    public void getUserCommandTest() {
        commandArray = inputDecoder.getUserCommands(actualCommand[0], jsonArray);
        assertEquals(expectedCommand[0],(commandArray[0]).toString());

        commandArray = inputDecoder.getUserCommands(actualCommand[1], jsonArray);
        assertEquals(expectedCommand[1],(commandArray[0]).toString());
        assertEquals(expectedMemberID, commandArray[1]);

        commandArray = inputDecoder.getUserCommands(actualCommand[2], jsonArray);
        assertEquals(expectedCommand[2],(commandArray[0]).toString());
        assertEquals(expectedMemberID, commandArray[1]);

        commandArray = inputDecoder.getUserCommands(actualCommand[3], jsonArray);
        assertEquals(expectedCommand[3],(commandArray[0]).toString());
        assertEquals(expectedMemberID, commandArray[1]);

        commandArray = inputDecoder.getUserCommands(actualCommand[4], jsonArray);
        assertEquals(expectedCommand[4],(commandArray[0]).toString());
        assertEquals(expectedMemberID, commandArray[1]);

        commandArray = inputDecoder.getUserCommands(actualCommand[5], jsonArray);
        assertEquals(expectedCommand[5],(commandArray[0]).toString());
        assertEquals(expectedMemberID, commandArray[1]);
        assertEquals(expectedBoatID, commandArray[2]);

        commandArray = inputDecoder.getUserCommands(actualCommand[6], jsonArray);
        assertEquals(expectedCommand[6],(commandArray[0]).toString());
        assertEquals(expectedMemberID, commandArray[1]);
        assertEquals(expectedBoatID, commandArray[2]);

        commandArray = inputDecoder.getUserCommands(actualCommand[7], jsonArray);
        assertEquals(expectedCommand[7],(commandArray[0]).toString());

        commandArray = inputDecoder.getUserCommands(actualCommand[8], jsonArray);
        assertEquals(expectedCommand[8],(commandArray[0]).toString());

        commandArray = inputDecoder.getUserCommands(actualCommand[9], jsonArray);
        assertNull((commandArray[0]));

        commandArray = inputDecoder.getUserCommands(actualCommand[10], jsonArray);
        assertNull(commandArray[1]);

        commandArray = inputDecoder.getUserCommands(actualCommand[11], jsonArray);
        assertNull(commandArray[2]);

        exception.expect(Exception.class);
        commandArray = inputDecoder.getUserCommands(actualCommand[12], jsonArray);

        commandArray = inputDecoder.getUserCommands(actualCommand[13], jsonArray);

        commandArray = inputDecoder.getUserCommands(actualCommand[14], jsonArray);

    }

}