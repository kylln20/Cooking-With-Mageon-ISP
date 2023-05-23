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
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CookWithMageon implements KeyListener {

    /** The main JFrame */
    private JFrame mainFrame = new JFrame("Cook with Mageon");

    /** The panel for the JFrame */
    private JPanel panel = new JPanel();

    /** Which scene or part of the game they are at */
    private int sceneNum = 0;

    /** Whether the bird, Mageon, gives instructions */
    private String dialogueBox = "";

    /** The budget or amount of money they receive when going to the grocery store */
    private double budget = 21.4; // average amount of money spent on groceries per 3 days

    /** How many nutrients, calories, and satisfaction they are able to absorb when eating the food. The lower this number is, the smaller */
    private double percentage = 0.7; // nutrients absorbed tend to be between 10% - 90%

    /**
     * CookWithMageon constructor
     * Adds the panel to the frame, allows the panel to have no layout, adds a keylistener to the frame, make the frame
     * unresizable, gives the frame a size, and allows the user to see the JFrame. It also calls update() which allows
     * the user to start the GUI
     */
    public CookWithMageon() throws IOException {
        mainFrame.addKeyListener(this);
        //mainFrame.setResizable(false); put this in final
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(640, 400);
        panel.setLayout(null);
        panel.setBackground(new Color(201, 218, 248));
        mainFrame.add(panel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        update();
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
            update();
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
     * update method
     *
     */
    public void update () {
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
            JButton instructions = new JButton("Instructions");
            instructions.setFont(new Font(Font.SERIF, Font.PLAIN,  30));
            instructions.setBounds(190, 140, 225, 40);
            //instructions.setBorderPainted(true);
            instructions.setOpaque(true);
            instructions.setBackground(new Color(207, 226, 243));
            instructions.setBorder(new RoundedBorder(10, new Color(11, 83, 148))); //10 is the radius
            //instructions.setForeground(Color.BLACK);
            panel.add(instructions);

            JFrame instructionsFrame = new JFrame("How to Play");
            instructionsFrame.setSize(640, 400);
            JPanel instructionsPanel = new JPanel();
            instructionsPanel.setLayout(null);

            JLabel instructionsLabel = new JLabel("Instructions");
            instructionsLabel.setBounds(200, 20, 300, 50);
            instructionsLabel.setFont(new Font(Font.SERIF, Font.PLAIN,  45));
            instructionsPanel.add(instructionsLabel);

            instructionsPanel.setBackground(new Color(201, 218, 248));
            instructionsFrame.add(instructionsPanel);
            instructionsFrame.setLocationRelativeTo(null);
            JTextArea instructionsText = new JTextArea("    The goal of the game is to cook meals and balance nutrition and satisfaction. \nThere will be a required amount of vitamin that must be consumed. \n" +
                    "\n" +
                    "    You must survive 3 days, cooking 3 meals a day. In the beginning, you will go \nto a grocery store before the first day, where you can buy products to use. Once \nyou return from the store, you may not go back. \n" +
                    "\n" +
                    "    If the food you make turns into a meal, you will eat it. Otherwise, it will go into \nthe trash. You will not die if you don’t anything for 3 days. \n");
            instructionsText.setBounds(35, 75,650, 600);
            instructionsText.setFont(new Font("Serif", Font.PLAIN, 17));
            instructionsPanel.add(instructionsText);
            instructionsText.setBackground(new Color(201, 218, 248));
            instructionsText.setEditable(false);
            instructions.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    instructionsFrame.setVisible(true);
                }
            });

            JLabel mainMenu = new JLabel("MAIN MENU");
            mainMenu.setFont(new Font(Font.SERIF, Font.PLAIN,  65));
            mainMenu.setBounds(130, 70, 500, 60);
            panel.add(mainMenu);

            JButton level1 = new JButton("Level 1");
            level1.setFont(new Font(Font.SERIF, Font.PLAIN,  30));
            level1.setBounds(190, 190, 225, 40);
            level1.setOpaque(true);
            level1.setBackground(new Color(207, 226, 243));
            level1.setBorder(new RoundedBorder(10, new Color(11, 83, 148))); //10 is the radius
            panel.add(level1);
            level1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panel.removeAll();
                    panel.revalidate();
                    panel.repaint();
                    mainFrame.repaint();
                    sceneNum = 2;
                    percentage = 1;
                    budget = 30;
                    update();
                }
            });

            JButton level2 = new JButton("Level 2");
            level2.setFont(new Font(Font.SERIF, Font.PLAIN,  30));
            level2.setBounds(190, 240, 225, 40);
            level2.setOpaque(true);
            level2.setBackground(new Color(207, 226, 243));
            level2.setBorder(new RoundedBorder(10, new Color(11, 83, 148))); //10 is the radius
            panel.add(level2);
            level2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panel.removeAll();
                    panel.revalidate();
                    panel.repaint();
                    mainFrame.repaint();
                    sceneNum = 2;
                    update();
                }
            });

            JButton level3 = new JButton("Level 3");
            level3.setFont(new Font(Font.SERIF, Font.PLAIN,  30));
            level3.setBounds(190, 290, 225, 40);
            level3.setOpaque(true);
            level3.setBackground(new Color(207, 226, 243));
            level3.setBorder(new RoundedBorder(10, new Color(11, 83, 148))); //10 is the radius
            level3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panel.removeAll();
                    panel.revalidate();
                    panel.repaint();
                    mainFrame.repaint();
                    sceneNum = 2;
                    percentage = 0.2;
                    budget = 18;
                    update();
                }
            });
            panel.add(level3);

            JLabel text = new JLabel("This game was made by: Kayla Lin and Angelina Jiang");
            text.setFont(new Font(Font.SERIF, Font.PLAIN,  10));
            text.setBounds(190, 330, 300, 40);
            panel.add(text);
        } else if (sceneNum == 2) {

        } else if (sceneNum == 3) {

        } else if (sceneNum == 4) {

        } else if (sceneNum == 5) { // grocery shop
            JLabel groceryStore;
            try {
                groceryStore = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/grocerystore.png")).getScaledInstance(640, 400, Image.SCALE_SMOOTH)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            groceryStore.setBounds(0, 0, 640, 400);
            panel.add(groceryStore);
            boolean[][][] maze = new boolean[10][16][4];
            maze[0][0][0] = true;
            maze[0][0][2] = true;
            maze[0][0][3] = true;
            maze[0][1][0] = true;
            maze[0][2][0] = true;
            maze[0][2][2] = true;
            maze[0][3][0] = true;
            maze[0][3][1] = true;
            maze[0][3][2] = true;
            maze[0][4][0] = true;
            maze[0][4][3] = true;
            maze[0][5][0] = true;
            maze[0][5][2] = true;
            maze[0][6][0] = true;
            maze[0][6][1] = true;
            maze[0][7][3] = true;
            maze[0][8][0] = true;
            maze[0][8][2] = true;
            maze[0][9][0] = true;
            maze[0][10][0] = true;
            maze[0][10][2] = true;
            maze[0][11][0] = true;
            maze[0][11][2] = true;
            maze[0][12][0] = true;
            maze[0][12][1] = true;
            maze[0][12][2] = true;
            maze[0][13][0] = true;
            maze[0][13][3] = true;
            maze[0][14][0] = true;
            maze[0][14][2] = true;
            maze[0][15][0] = true;
            maze[0][15][1] = true;
            maze[1][0][0] = true;
            maze[1][0][3] = true;
            maze[1][1][1] = true;
            maze[1][1][2] = true;
            maze[1][2][0] = true;
            maze[1][2][3] = true;
            maze[1][3][0] = true;
            maze[1][3][2] = true;
            maze[1][4][1] = true;
            maze[1][4][2] = true;
            maze[1][5][0] = true;

            maze[1][5][3] = true;
            maze[1][5][2] = true;
            maze[1][6][1] = true;
            maze[1][7][1] = true;
            maze[1][7][3] = true;
            maze[1][8][0] = true;
            maze[1][8][1] = true;
            maze[1][8][3] = true;
            maze[1][9][1] = true;
            maze[1][9][3] = true;
            maze[1][10][0] = true;
            maze[1][10][3] = true;
            maze[1][11][0] = true;
            maze[1][11][2] = true;
            maze[1][12][2] = true;
            maze[1][12][0] = true;
            maze[1][13][2] = true;
            maze[1][13][1] = true;
            maze[1][14][0] = true;
            maze[1][14][1] = true;
            maze[1][14][2] = true;
            maze[1][15][1] = true;
            maze[1][15][3] = true;

            maze[2][0][2] = true;
            maze[2][0][3] = true;
            maze[2][1][0] = true;
            maze[2][2][2] = true;
            maze[2][3][0] = true;
            maze[2][3][2] = true;
            maze[2][4][0] = true;
            maze[2][4][2] = true;
            maze[2][5][1] = true;
            maze[2][5][0] = true;
            maze[2][6][3] = true;
            maze[2][7][2] = true;
            maze[2][8][1] = true;
            maze[2][8][2] = true;
            maze[2][9][2] = true;
            maze[2][9][3] = true;
            maze[2][10][1] = true;
            maze[2][11][0] = true;
            maze[2][11][3] = true;
            maze[2][12][0] = true;
            maze[2][13][2] = true;
            maze[2][13][0] = true;
            maze[2][14][1] = true;
            maze[2][14][2] = true;
            maze[2][15][1] = true;
            maze[2][15][3] = true;

            maze[3][0][0] = true;
            maze[3][0][3] = true;
            maze[3][1][1] = true;
            maze[3][1][2] = true;
            maze[3][2][0] = true;
            maze[3][2][3] = true;
            maze[3][3][0] = true;
            maze[3][3][1] = true;
            maze[3][4][0] = true;
            maze[3][4][3] = true;
            maze[3][5][1] = true;
            maze[3][5][2] = true;
            maze[3][6][2] = true;
            maze[3][6][3] = true;
            maze[3][7][0] = true;
            maze[3][7][2] = true;
            maze[3][8][0] = true;
            maze[3][8][1] = true;
            maze[3][8][2] = true;
            maze[3][9][0] = true;
            maze[3][9][3] = true;
            maze[3][10][1] = true;
            maze[3][10][2] = true;
            maze[3][11][1] = true;
            maze[3][11][3] = true;
            maze[3][12][3] = true;
            maze[3][13][0] = true;
            maze[3][13][1] = true;
            maze[3][14][3] = true;
            maze[3][14][0] = true;
            maze[3][15][1] = true;


            maze[4][0][1] = true;
            maze[4][0][3] = true;
            maze[4][1][1] = true;
            maze[4][1][3] = true;
            maze[4][1][0] = true;
            maze[4][2][3] = true;
            maze[4][2][1] = true;
            maze[4][3][1] = true;
            maze[4][3][3] = true;
            maze[4][4][3] = true;
            maze[4][4][2] = true;
            maze[4][5][0] = true;
            maze[4][6][0] = true;
            maze[4][6][2] = true;
            maze[4][7][0] = true;
            maze[4][7][1] = true;
            maze[4][8][0] = true;
            maze[4][8][3] = true;
            maze[4][9][1] = true;
            maze[4][9][2] = true;
            maze[4][9][3] = true;
            maze[4][10][0] = true;
            maze[4][10][2] = true;
            maze[4][10][3] = true;
            maze[4][11][1] = true;
            maze[4][12][3] = true;
            maze[4][12][1] = true;
            maze[4][13][1] = true;
            maze[4][13][2] = true;
            maze[4][13][3] = true;
            maze[4][14][1] = true;
            maze[4][14][3] = true;
            maze[4][15][1] = true;
            maze[4][15][3] = true;

            maze[5][0][1] = true;
            maze[5][0][3] = true;
            maze[5][1][1] = true;
            maze[5][1][3] = true;
            maze[5][2][3] = true;
            maze[5][2][1] = true;
            maze[5][3][1] = true;
            maze[5][3][3] = true;
            maze[5][3][2] = true;
            maze[5][4][0] = true;
            maze[5][5][1] = true;
            maze[5][5][2] = true;
            maze[5][6][0] = true;
            maze[5][6][3] = true;
            maze[5][7][1] = true;
            maze[5][7][2] = true;
            maze[5][8][2] = true;
            maze[5][8][3] = true;
            maze[5][9][1] = true;
            maze[5][9][3] = true;
            maze[5][10][0] = true;
            maze[5][10][2] = true;
            maze[5][11][1] = true;
            maze[5][11][2] = true;
            maze[5][12][3] = true;
            maze[5][12][1] = true;
            maze[5][12][2] = true;
            maze[5][13][0] = true;
            maze[5][13][3] = true;
            maze[5][14][1] = true;
            maze[5][14][2] = true;
            maze[5][15][1] = true;
            maze[5][15][3] = true;

            maze[6][0][1] = true;
            maze[6][0][3] = true;
            maze[6][1][1] = true;
            maze[6][1][3] = true;
            maze[6][2][3] = true;
            maze[6][2][2] = true;
            maze[6][3][0] = true;
            maze[6][3][2] = true;
            maze[6][4][1] = true;
            maze[6][5][0] = true;
            maze[6][5][2] = true;
            maze[6][5][3] = true;
            maze[6][6][1] = true;
            maze[6][6][2] = true;
            maze[6][7][0] = true;
            maze[6][7][3] = true;
            maze[6][8][0] = true;
            maze[6][8][2] = true;
            maze[6][9][0] = true;
            maze[6][9][2] = true;
            maze[6][10][0] = true;
            maze[6][10][1] = true;
            maze[6][11][0] = true;
            maze[6][11][3] = true;
            maze[6][12][0] = true;
            maze[6][12][2] = true;
            maze[6][13][1] = true;
            maze[6][13][2] = true;
            maze[6][14][0] = true;
            maze[6][14][3] = true;
            maze[6][15][1] = true;
            maze[6][15][2] = true;

            maze[7][0][2] = true;
            maze[7][0][3] = true;
            maze[7][1][1] = true;
            maze[7][1][3] = true;
            maze[7][2][0] = true;
            maze[7][2][3] = true;
            maze[7][3][0] = true;
            maze[7][3][1] = true;
            maze[7][3][2] = true;
            maze[7][4][1] = true;
            maze[7][4][3] = true;
            maze[7][5][0] = true;
            maze[7][5][3] = true;
            maze[7][6][0] = true;
            maze[7][7][1] = true;
            maze[7][7][2] = true;
            maze[7][8][0] = true;
            maze[7][8][3] = true;
            maze[7][9][0] = true;
            maze[7][9][1] = true;
            maze[7][10][1] = true;
            maze[7][10][2] = true;
            maze[7][10][3] = true;
            maze[7][11][3] = true;
            maze[7][11][1] = true;
            maze[7][12][0] = true;
            maze[7][12][3] = true;
            maze[7][13][0] = true;
            maze[7][13][1] = true;
            maze[7][14][2] = true;
            maze[7][14][3] = true;
            maze[7][15][0] = true;
            maze[7][15][1] = true;

            maze[8][0][0] = true;
            maze[8][0][3] = true;
            maze[8][1][0] = true;
            maze[8][2][2] = true;
            maze[8][3][0] = true;
            maze[8][3][2] = true;
            maze[8][4][1] = true;
            maze[8][4][2] = true;
            maze[8][5][1] = true;
            maze[8][5][3] = true;
            maze[8][6][2] = true;
            maze[8][6][3] = true;
            maze[8][7][0] = true;
            maze[8][7][1] = true;
            maze[8][8][1] = true;
            maze[8][8][3] = true;
            maze[8][9][1] = true;
            maze[8][9][3] = true;
            maze[8][10][0] = true;
            maze[8][10][3] = true;
            maze[8][11][1] = true;
            maze[8][11][2] = true;
            maze[8][12][1] = true;
            maze[8][12][2] = true;
            maze[8][12][3] = true;
            maze[8][13][3] = true;
            maze[8][14][0] = true;
            maze[8][14][2] = true;
            maze[8][15][1] = true;
            maze[8][15][2] = true;

            maze[9][0][1] = true;
            maze[9][0][2] = true;
            maze[9][0][3] = true;
            maze[9][1][3] = true;
            maze[9][1][2] = true;
            maze[9][2][0] = true;
            maze[9][2][2] = true;
            maze[9][3][0] = true;
            maze[9][3][2] = true;
            maze[9][4][0] = true;
            maze[9][4][2] = true;
            maze[9][5][1] = true;
            maze[9][5][2] = true;
            maze[9][6][0] = true;
            maze[9][6][2] = true;
            maze[9][6][3] = true;
            maze[9][7][1] = true;
            maze[9][7][2] = true;
            maze[9][8][1] = true;
            maze[9][8][3] = true;
            maze[9][9][2] = true;
            maze[9][9][3] = true;
            maze[9][10][1] = true;
            maze[9][10][2] = true;
            maze[9][11][0] = true;
            maze[9][11][2] = true;
            maze[9][11][3] = true;
            maze[9][12][0] = true;
            maze[9][12][2] = true;
            maze[9][13][2] = true;
            maze[9][14][0] = true;
            maze[9][14][2] = true;
            maze[9][15][0] = true;
            maze[9][15][1] = true;
            maze[9][15][2] = true;
            Drawing d = new Drawing();
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 10; j++) {
                    for (int k = 0; k < 4; k++) {
                        if (maze[i][j][k]) {
                            if (k == 0) {
                                d.x.add(400 * j);
                                d.y.add(100 * i);
                            }

                        }
                    }
                }
            }
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
    class Drawing extends Canvas {
        private ArrayList<Integer> x;
        private ArrayList<Integer> y;
        private ArrayList<Integer> width;
        private ArrayList<Integer> height;
        public void paint (Graphics g) {
            for (int i = 0; i < x.size(); i++) {
                g.fillRect(x.get(i), y.get(i), width.get(i), height.get(i));
            }
        }
    }
}
