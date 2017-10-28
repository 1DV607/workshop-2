import model.Member;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MemberNodeTest {

    //used to create MemberNodes
    private final static Member MEMBER = new Member(45);

    //used to store MemberNodes
    private static ArrayList<MemberNode> nodeArray10;
    private static ArrayList<MemberNode> nodeArray1000;

    @BeforeClass
    //initialize ArrayList's
    public static void beforeAll() {
        nodeArray10 = new ArrayList<>();
        nodeArray1000 = new ArrayList<>();

    }

    /**
     * creates and adds 10 MemberNodes to an ArrayList and 10000 MemberNodes to another ArrayList
     */
    @Before
    public void before() {
        for (int i = 0; i < 10; i++) {
            nodeArray10.add(new MemberNode(MEMBER));
        }
        for (int i = 0; i < 1000; i++) {
            nodeArray1000.add(new MemberNode(MEMBER));
        }
    }

    /**
     * clears ArrayLists
     */
    @After
    public void after() {
        nodeArray10.clear();
        nodeArray1000.clear();
    }

    /**
     * Append all nodes in ArrayList to the startingNode (Node at possition 0 in ArrayList)
     * Remove the startingNode and check if all Nodes in the ArrayList has nextNode -> null and
     * previousNode -> null
     */
    @Test
    public void removeTest() {
        Node startingNode = nodeArray10.get(0);

        for (int i = 1; i < 10; i++) {
            startingNode.append(nodeArray10.get(i));

        }

        startingNode.remove();

        for (Node node : nodeArray10) {
            assertNull(node.getNextNode());
            assertNull(node.getPreviousNode());
        }

        startingNode = nodeArray1000.get(0);

        for (int i = 1; i < 1000; i++) {
            startingNode.append(nodeArray1000.get(i));
        }

        startingNode.remove();

        for (Node node : nodeArray1000) {
            assertNull(node.getNextNode());
            assertNull(node.getPreviousNode());
        }
    }
}