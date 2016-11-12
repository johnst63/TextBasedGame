import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Sheolfire on 11/11/2016.
 */
public class Node {
    private File text; //a file containing the text describing a situation in the game
    private ArrayList<Node> children; //the situations/nodes that can be reached from the node's given choices
    private ArrayList<String> choices;
    private int id;

    public Node(File text, int id) {
        this.text = text;
        this.id = id;
        choices = new ArrayList<String>();
        children = new ArrayList<Node>();
    }

    public void parseFile() throws FileNotFoundException {
        Scanner reader = new Scanner(text);
        int numChoices = reader.nextInt();
        reader.nextLine();
        String str;

        for(int i = 0; i < numChoices; i++) {
            str = reader.nextLine();
            choices.add(str);
        }

        while(reader.hasNext() && (str = reader.nextLine()).contains("@nodes@") != true) {

        }
        str = reader.nextLine();
        String fileName;
        for(int i = 0; i < numChoices; i++) {
            fileName = Driver.NODE_TEXT_FOLDER;
            fileName += reader.nextLine();
            fileName += ".txt";
            File textfile = new File(fileName);
            children.add(new Node(textfile, Driver.node_counter));
            Driver.node_counter++;
        }

        boolean print = false;
        while(reader.hasNext()) {
            str = reader.nextLine();
            if(print)
                System.out.println(str);
            if(str.contains("@desc@")) {
                print = true;
            }
        }
        reader.close();

    }
    /**
     * print out the possible options for the player
     * @throws FileNotFoundException
     */
    public void printOptions() throws FileNotFoundException {
        //BufferedReader reader = new BufferedReader(new FileReader(text));
        Scanner reader = new Scanner(text);
        String str;
        int numChoices = reader.nextInt();
        reader.nextLine();

        for(int i = 0; i < numChoices; i++) {
            str = reader.nextLine();
            System.out.println((i + 1) + ": " + str);
        }
        reader.close();
    }



    public void printText() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(text));
        String str;
        boolean print = false;
        while((str = reader.readLine()) != null) {
            if(print)
                System.out.println(str);
            if(str.contains("@desc@")) {
                print = true;
            }
        }
        reader.close();
    }
}
