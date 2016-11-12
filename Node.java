import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * Created by Sheolfire on 11/11/2016.
 */
public class Node {
    private File text; //a file containing the text describing a situation in the game
    private ArrayList<Node> children; //the situations/nodes that can be reached from the node's given choices
    private ArrayList<String> choices;
    private int id;
    private boolean readFrom = false; //whether or not the file has already been parsed

    public Node(File text, int id) {
        this.text = text;
        this.id = id;
        choices = new ArrayList<String>();
        children = new ArrayList<Node>();
    }

    /**
     * This method is responsible for parsing the text file of the node, obtaining the user's choices, the nodes the choices will branch to, and the description of the room
     * @throws FileNotFoundException
     */
    public void parseFile() throws FileNotFoundException {
        if(!readFrom) {
            Scanner reader = new Scanner(text);
            int numChoices = reader.nextInt();
            reader.nextLine();
            String str;

            for (int i = 0; i < numChoices; i++) {
                str = reader.nextLine();
                choices.add(str);
            }

            while (reader.hasNext() && (str = reader.nextLine()).contains("@nodes@") != true) {

            }

            String fileName;
            for (int i = 0; i < numChoices; i++) {
                fileName = Driver.NODE_TEXT_FOLDER;
                fileName += reader.nextLine();
                fileName += ".txt";
                File textfile = new File(fileName);
                children.add(new Node(textfile, Driver.node_counter));
                Driver.node_counter++;
            }

            boolean print = false;
            while (reader.hasNext()) {
                str = reader.nextLine();
                if(print && str.contains("@endd@")) {
                    print = false;
                }
                if (print) {
                    System.out.println(str);

                }
                if (str.contains("@desc@")) {
                    print = true;
                }
            }
            reader.close();

            printOptions();
            readFrom = true;
        }
    }
    /**
     * print out the possible options for the player
     * @throws FileNotFoundException
     */
    public void printOptions() throws FileNotFoundException {
        int i = 1;
        for(String choice: choices) {
            System.out.println(i++ + ": " + choice);
        }
    }

    public Node getChild(int index) {
        return children.get(index);
    }


    public void printText() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(text));
        String str;
        boolean print = false;
        while((str = reader.readLine()) != null) {
            if(print) {
                System.out.println(str);
                if(str.contains("@")) {
                    print = false;
                }
            }
            if(str.contains("@desc@")) {
                print = true;
            }
        }
        reader.close();
    }

}
