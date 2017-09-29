import model.Member;
import model.MemberNode;
import model.Node;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class NodeTest {

    //used to create MemberNodes
    private final static Member MEMBER = new Member(45);

    //used to store Nodes
    private static ArrayList<Node> nodeArray10;
    private static ArrayList<Node> nodeArray1000;

    /**
     * Initializes the ArrayLists
     * Adds MemberNodes to the ArrayLists
     */
    @BeforeClass
    public static void beforeAll() {
        nodeArray10 = new ArrayList<Node>();
        nodeArray1000 = new ArrayList<Node>();

        for (int i = 0; i < 10; i++) {
            nodeArray10.add(new MemberNode(MEMBER));
        }
        for (int i = 0; i < 1000; i++) {
            nodeArray1000.add(new MemberNode(MEMBER));
        }

    }

    /**
     * Removes the Next/Previous pointers for all
     * Nodes in the ArrayLists
     */
    @After
    public void after() {
        for (Node n : nodeArray10) {
            n.setNextNode(null);
            n.setPreviousNode(null);
        }

        for (Node n : nodeArray1000) {
            n.setNextNode(null);
            n.setPreviousNode(null);
        }
    }

    /**
     * Append the MemberNodes to startingNode (Node at possition 0 in ArrayList)
     * Check that all nodes are present and added in an correct order
     */
    @Test
    public void appendTest() {
        Node startingNode = nodeArray10.get(0);
        Node expected;
        Node actual;

        for (int i = 1; i < 10; i++) {
            startingNode.append(nodeArray10.get(i));
        }

        actual = startingNode;
        for (int i = 1; i < 10 ; i++) {
            actual = actual.getNextNode();
            expected = nodeArray10.get(i);
            assertEquals(expected, actual);
        }

        startingNode = nodeArray1000.get(0);

        for (int i = 1; i < 1000; i++) {
            startingNode.append(nodeArray1000.get(i));
        }

        actual = startingNode;
        for (int i = 1; i < 1000 ; i++) {
            actual = actual.getNextNode();
            expected = nodeArray1000.get(i);
            assertEquals(expected, actual);
        }
    }

}