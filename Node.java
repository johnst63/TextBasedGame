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
    public void parseFile() throws IOException {
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
                String fileNameLocal = reader.nextLine(); //the filename excluding the Node_Text_Folder extension
                fileNameLocal += ".txt";
                fileName += fileNameLocal;

                //check if fileName exists already in a node the masterlist of nodes and if so, add that node instead
                Node masterListNode = Driver.fileNameInNodeList(fileNameLocal);
                if(masterListNode == null) {
                    File textfile = new File(fileName);
                    Node newNode = new Node(textfile, Driver.node_counter);
                    children.add(newNode);
                    Driver.nodeMasterList.add(newNode);
                    Driver.node_counter++;
                } else {
                    children.add(masterListNode);
                }
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
        } else {
            printText();
            printOptions();
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
        Scanner reader = new Scanner(text);
        String str;
        boolean print = false;
        while(reader.hasNext()) {
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
    }

    public File getTextFile() {
        return text;
    }
}
