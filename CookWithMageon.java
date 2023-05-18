/**
 * A program to play Cook With Mageon featuring using GUI's with Java Swing
 *
 * Course Info:
 * ICS4UO
 * Valentina Krasteva
 *
 * Version 1
 * @author Angelina Jiang and Kayla Lin
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class CookWithMageon extends JFrame implements KeyListener{

    /** The main JFrame */
    private JFrame mainFrame = new JFrame("Cook with Mageon");

    /** The panel for the JFrame */
    private JPanel panel = new JPanel();

    /** Which scene or part of the game they are at */
    private int sceneNum = 0;

    /** Whether the bird, Mageon, gives instructions */
    private String dialogueBox = "";

    /**
     * CookWithMageon constructor
     * Adds the panel to the frame, allows the panel to have no layout, adds a keylistener to the frame, make the frame
     * unresizable, gives the frame a size, and allows the user to see the JFrame. It also calls method() which allows
     * the user to start the GUI
     */
    public CookWithMageon() throws IOException {
        panel.setLayout(null);
        mainFrame.add(panel);
        mainFrame.addKeyListener(this);
        //mainFrame.setLocationRelativeTo(null);
        //mainFrame.setResizable(false); put this in final
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(640, 400);
        mainFrame.setVisible(true);
        method();
    }

    /**
     * The entry point to the program
     *
     * Runs the program
     *
     * @param args The arguments passed from the command line
     */
    public static void main (String[] args) throws IOException {
        CookWithMageon cwm = new CookWithMageon();
    }

    /**
     * keyTyped method, part of the interface, KeyListener class
     *
     * @param e The argument passed from the command line
     */
    public void keyTyped(KeyEvent e) {

    }

    /**
     * keyPressed method, part of the interface, KeyListener class
     * When a key is pressed, it checks the scene it's on, and allows it to be redone.
     *
     * @param e The argument passed from the command line
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            method();
        }
        mainFrame.repaint();
    }

    /**
     * keyReleased method, part of the interface, KeyListener class
     *
     * @param e The argument passed from the command line
     */
    public void keyReleased(KeyEvent e) {

    }

    /**
     * method method, part of the interface, KeyListener class
     *
     */
    public void method () {
        if (sceneNum == 0) {
            panel.setBackground(new Color(201, 218, 248));
            JLabel logo = null;
            try {
                logo = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/MAGEON Logo.png")).getScaledInstance(55, 55, Image.SCALE_SMOOTH)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            logo.setBounds(165, 75, 55, 55);
            panel.add(logo);

            JLabel label1 = new JLabel("MAGEON Corp©  Presents");
            label1.setFont(new Font(Font.SERIF, Font.PLAIN,  20));
            label1.setBounds(230, 85, 300, 30);
            panel.add(label1);

            JLabel title = new JLabel("Cooking with");
            title.setFont(new Font(Font.SERIF, Font.PLAIN,  50));
            title.setBounds(175, 135, 300, 60);
            panel.add(title);

            JLabel title2 = new JLabel("MAGEON");
            title2.setFont(new Font(Font.SERIF, Font.BOLD,  50));
            title2.setBounds(200, 200, 300, 60);
            panel.add(title2);

            JLabel description = new JLabel("Press enter to continue");
            description.setFont(new Font(Font.SERIF, Font.PLAIN,  15));
            description.setBounds(250, 325, 300, 30);
            panel.add(description);
            sceneNum = 1; // automatically go to scene 2, since no further action can be taken
        } else if (sceneNum == 1) {
            panel.setBackground(new Color(201, 218, 248));

            JButton instructions = new JButton("Instructions");
            instructions.setFont(new Font(Font.SERIF, Font.PLAIN,  20));
            instructions.setBounds(200, 140, 200, 30);
            //instructions.setBorderPainted(true);
            instructions.setOpaque(true);
            instructions.setBackground(new Color(207, 226, 243));
            instructions.setBorder(new RoundedBorder(10, new Color(11, 83, 148))); //10 is the radius
            //instructions.setForeground(Color.BLACK);

            panel.add(instructions);

            JLabel mainMenu = new JLabel("MAIN MENU");
            mainMenu.setFont(new Font(Font.SERIF, Font.BOLD,  50));
            mainMenu.setBounds(150, 70, 500, 60);
            panel.add(mainMenu);

            sceneNum = 2;
        } else if (sceneNum == 2) {
            panel.setBackground(new Color(248, 201, 201));
        } else if (sceneNum == 3) {

        } else if (sceneNum == 4) {

        } else if (sceneNum == 5) {

        } else if (sceneNum == 6) {

        } else if (sceneNum == 7) {

        } else if (sceneNum == 8) {

        } else if (sceneNum == 9) {

        } else if (sceneNum == 10) {

        } else if (sceneNum == 11) {

        } else if (sceneNum == 12) {

        } else if (sceneNum == 13) {

        }
        mainFrame.repaint();
    }
}
