import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Sheolfire on 11/11/2016.
 */
public class Driver {

    public static final String NODE_TEXT_FOLDER = "node_textfiles/";
    public static int node_counter = 0;
    public static ArrayList<Node> allNodes = new ArrayList<Node>();

    public static void main(String[] args) {
        boolean running = true;
        Node start = new Node(new File("node_textfiles/node0.txt"), node_counter++);
        Node curNode = start;

        Scanner userInput = new Scanner(System.in);
        int input = 0;

        while(running) {
            try {
                curNode.parseFile();
            } catch (IOException e) {

            }
            input = userInput.nextInt();
            curNode = curNode.getChild(input-1); //-1 to account for conversion from user numbers to array position

        }
    }

}
