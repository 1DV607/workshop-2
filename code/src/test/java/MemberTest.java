import model.Member;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MemberTest {

    private static String socialSecurityNumber;

    private static ArrayList<String> ssnList;
    private static ArrayList<Member> members;

    /**
     * creates 1 million unique social security numbers
     */
    @BeforeClass
    public static void beforeAll() {

        ssnList = new ArrayList<>();
        members = new ArrayList<>();

        long temp = 1111111111;
        for (int i = 0; i < 1000000; i++) {
            temp = temp+i;
            socialSecurityNumber = Long.toString(temp);
            ssnList.add(socialSecurityNumber);
        }
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
}