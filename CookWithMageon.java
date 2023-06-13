/**
 * A program to play Cook With Mageon featuring using GUI's with Java Swing
 *
 * Course Info:
 * ICS4UO
 * Valentina Krasteva
 *
 * @author Angelina Jiang and Kayla Lin
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File; 
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class CookWithMageon implements KeyListener, MouseListener{

    /** The main JFrame */
    private JFrame mainFrame = new JFrame("Cook with Mageon");

    /** The layered pane for the JFrame */
    private JLayeredPane layeredPane;

    /** The panel for the JFrame */
    private JPanel panel = new JPanel();

    /** Which scene or part of the game they are at */
    private int sceneNum = 0;

    /** The budget or amount of money they receive when going to the grocery store */
    private double budget = 11.2; // average amount of money spent on groceries per 1 day

    /** The food the user currently owns */
    private ArrayList<Food> inventory = new ArrayList<>();

    /** Coordinates for the maze */
    private int xCoord = 7;
    private int yCoord = 0;

    /** The choice the user is picking for the difficulty level */
    private JLabel choose;

    /** The choice the user is picking for the difficulty level on the JPanel */
    private int chooseY;

    /** The maze's drawing */
    private Drawing d;

    /** Storing information about the maze */
    private final Maze maze;

    /** The information on the pop-up for the maze */
    private int message = -1;

    /** A JLabel containing the image used as the background of the JPanel */
    private JLabel background;

    /** A picture of the kitchen with the fridge door open */
    private File kitchenOpen = new File("Pictures/kitchen2.png");

    /** A picture of the kitchen with the fridge door closed */
    private File kitchenClose = new File("Pictures/kitchen1.png");

    /** Whether or not the user has opened the fridge door*/
    private boolean fridgeOpen = false;

    /** A StatsBar object, displays the nutrition, calories, and satisfaction level of the player */
    private StatsBar statsbar;

    /** A Dialogue object, displays the messages the game helper MAGEON gives to the user */
    private Dialogue dialogue;

    /** The dialogue text for Level 1*/
    private String[] dialogueText1 = {"Hello! I'm MAGEON, your personal cooking assistant. <br> Press the ENTER key to continue",
            "At the top left, there will are nutrients, calories, and <br> satisfaction bars. The goal of this game is to fill all of these bars",
            "At the right, there is a fridge. Click on the fridge to open it!",
            "In order to make healthy meals, we must fill the fridge with food. <br>To the grocery store! Press the shopping cart to continue."};

    /** The inventory frame for the maze */
    private JFrame inventoryFrame = new JFrame("Inventory");

    /** The dialogue for Level 2 and 3 */
    private Dialogue di = new Dialogue(new String[]{"Welcome to the grocery store!<br>Here, you'll be buying all the food you need for today.", "Use the WASD keys or the arrow keys to navigate the grocery store.<br>", "To see what you've purchased, click on the Inventory button on the <br>top right.", "Make sure you don't exceed your budget, shown at the top left.<br>", "I'll see you again back home! Have fun!<br>"});

    /** The left inventory for organizing the fridge */
    private ArrayList<Food> invL; // {{0, 1, 2, 3}, {4, 5, 6, 7} ...}

    /** The right inventory for the fridge */
    private Food[][] invR; // {{0, 1, 2, 3}, {4, 5, 6, 7} ...}

    /** The singular item selected on the left side for organizing the fridge */
    private int[] selectedLeft = new int[2]; // [4, 5] to symbolize 4th column, 5th row is selected 0-th indexed

    /** The singular item selected on the right side for organizing the fridge*/
    private int[] selectedRight = new int[2]; // [4, 5] to symbolize 4th column, 5th row is selected

    /** The background image */
    private JLabel backgroundImg;

    /** A variable storing what the background will look like */
    private String imgName;

    /** A placeholder variable to hold the colour transparent */
    private Color transparent = new Color(0, 0, 0, 0);

    /** x and y coordinates for the person in Level 3 */
    private int x = 320;
    private int y = 160;

    /** How the person looks like in Level 3 */
    private JLabel personImg;

    /** A JPanel to hold the JLabel personImg */
    private JPanel person;

    /** The food and the placement of the fridge for Level 3 */
    private Food[][] fridge;

    /**  The selected objects in the fridge for Level 3 */
    private boolean[][] selected = new boolean[4][3];

    /** JPanel for the problem the user must solve in Level 3 */
    private JPanel problem;

    /** The variables used when making the recipe in Level 3 */
    private ArrayList<String> name;
    private int calories;
    private boolean[] nutrients;

    private int numMeals;

    /**
     * CookWithMageon constructor
     * Adds the panel to the frame, allows the panel to have no layout, adds a keylistener to the frame, make the frame
     * unresizable, gives the frame a size, and allows the user to see the JFrame. It also calls update() which allows
     * the user to start the GUI
     */
    public CookWithMageon() throws IOException {
        layeredPane = mainFrame.getLayeredPane();
        layeredPane.add(panel, 1);
        mainFrame.addKeyListener(this);
        mainFrame.addMouseListener(this);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(655, 440);
        panel.setLayout(null);
        panel.setBackground(new Color(201, 218, 248));
        mainFrame.add(panel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        chooseY = 190;
        maze = new Maze();
        update();
        selectedLeft[0] = -1;
        selectedRight[0] = -1;
        invR = new Food[4][3]; // player can store up to 12 different kind of foods
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
    public void keyTyped(KeyEvent e) {}

    /**
     * keyPressed method, part of the interface, KeyListener class
     * When a key is pressed, it checks the scene it's on, and allows it to be redone.
     *
     * @param e The argument passed from the command line
     */
    public void keyPressed(KeyEvent e) {
        if (sceneNum == 5) {
            if ( di.lastWord()) {
                if (imgName.equals("fridge open") || imgName.equals("fridge closed")) {
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                        if (x < 460) {
                            x += 5;
                        }
                        panel.removeAll();
                        panel.revalidate();
                        panel.repaint();
                        addComponents();
                        drawPerson(x, y);
                        drawBackground();
                    } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                        if (x > 0) {
                            x -= 5;
                        }
                        panel.removeAll();
                        panel.revalidate();
                        panel.repaint();
                        addComponents();
                        drawPerson(x, y);
                        drawBackground();
                    }
                }
            }
        }
    }

    /**
     * keyReleased method, part of the interface, KeyListener class
     *
     * @param e The argument passed from the command line
     */
    public void keyReleased(KeyEvent e) {
        if (sceneNum == 0) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                panel.removeAll();
                panel.revalidate();
                panel.repaint();
                sceneNum = 1;
                update();
            }
        } else if (sceneNum == 1) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                sceneNum = 2;
                if (chooseY == 290) { // hard !
                    budget = 8.2;
                } else if (chooseY == 240) { // med

                } else if (chooseY == 190) { // easy
                    budget = 17.4;
                }
                update();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                chooseY = Math.min(290, chooseY + 50);
                panel.removeAll();
                panel.revalidate();
                panel.repaint();
                update();
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                chooseY = Math.max(190, chooseY - 50);
                panel.removeAll();
                panel.revalidate();
                panel.repaint();
                update();
            }
        } else if (sceneNum == 2) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                dialogue.keyReleased(e);
                panel.removeAll();
                panel.revalidate();
                panel.repaint();
                addComponents();
                drawBackground();
            }
        } else if (sceneNum == 3 && message == -1) {
            di.keyReleased(e);
            if (di.lastWord()) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                    if (!maze.check(yCoord, xCoord, 0)) {
                        yCoord--;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                    if (!maze.check(yCoord, xCoord, 3)) {
                        xCoord--;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                    if (!maze.check(yCoord, xCoord, 2)) {
                        yCoord++;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                    if (!maze.check(yCoord, xCoord, 1)) {
                        xCoord++;
                    }
                }
                if (xCoord < 0) xCoord = 0;
                if (xCoord > 15) xCoord = 15;
                if (yCoord < 0) yCoord = 0;
                if (yCoord > 9) yCoord = 9;
                if (yCoord == 9 && xCoord == 8 && e.getKeyCode() == KeyEvent.VK_DOWN) { // got thrugh exit
                    message = 17;
                    JPanel buying = new JPanel();
                    buying.setLayout(null);
                    buying.setBackground(new Color(72, 176, 216, 0));
                    buying.setSize(400, 220);
                    buying.setBounds(127, 100, 400, 220);
                    buying.setBorder(new RoundedBorder(40, new Color(72, 176, 216), new Color(16, 85, 112), "", 0, 0, 0));
                    JLabel question = new JLabel("Would you like to leave");
                    question.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
                    question.setBounds(80, 0, 350, 60);
                    buying.add(question);
                    JLabel itemName = new JLabel("the grocery store?");
                    itemName.setFont(new Font(Font.SERIF, Font.BOLD, 25));
                    itemName.setBounds(100, 40, 400, 40);
                    buying.add(itemName);
                    JLabel price = new JLabel("Note: This action is irreversible");
                    price.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
                    price.setBounds(150, 70, 400, 40);
                    buying.add(price);

                    JButton no = new JButton("NO");
                    JButton yes = new JButton("YES");
                    yes.setFocusable(false); // must be used so that the keyboard doesn't stop working :sobs:
                    yes.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
                    yes.setBounds(50, 140, 120, 60);
                    //instructions.setBorderPainted(true);
                    yes.setOpaque(true);
                    yes.setBackground(new Color(72, 176, 216));
                    yes.setBorder(new RoundedBorder(15, new Color(217, 234, 211), new Color(217, 234, 211), "YES", 20, 40, 35));
                    //instructions.setForeground(Color.BLACK);
                    buying.add(yes);
                    no.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
                    no.setBorder(new RoundedBorder(15, new Color(244, 204, 204), new Color(244, 204, 204), "NO", 20, 45, 35));
                    no.setBounds(235, 140, 120, 60);
                    //no.setOpaque(true);
                    no.setBackground(new Color(72, 176, 216));
                    no.setFocusable(false);
                    buying.add(no);
                    layeredPane.add(buying, 2);
                    yes.addActionListener(new ActionListener() { // if 'yes' is clicked, the scene moves to sceneNum 4
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            buying.setVisible(false);
                            sceneNum = 4;
                            di = new Dialogue(new String[] {"Now that we're back from the grocery store, <br>we need to store our ingredients!<br> Your fridge can only hold up to 12 distinct foods!", "Press a food item from the left and any space <br>in the fridge.<br> Then, press the 'swap' button!", "Once you're finished, press the 'Done' button.<br>Any food on the left will be discarded afterwards."});
                            panel.removeAll();
                            panel.revalidate();
                            panel.repaint();
                            update();
                        }
                    });
                    no.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            buying.setVisible(false);
                            message = -1;
                        }
                    });
                } else if (xCoord == 3 && yCoord == 0) { // lettuce
                    message = 0;
                } else if (xCoord == 5 && yCoord == 1) { // white flour
                    message = 1;
                } else if (xCoord == 8 && yCoord == 1) { // multigrain flour
                    message = 2;
                } else if (xCoord == 14 && yCoord == 1) { // milk
                    message = 3;
                } else if (xCoord == 1 && yCoord == 4) { // raw white rice
                    message = 4;
                } else if (xCoord == 3 && yCoord == 5) { // multigrain rice
                    message = 5;
                } else if (xCoord == 8 && yCoord == 4) { // tomato
                    message = 6;
                } else if (xCoord == 13 && yCoord == 4) { // potato
                    message = 7;
                } else if (xCoord == 12 && yCoord == 5) { // carrot
                    message = 8;
                } else if (xCoord == 5 && yCoord == 6) { // eggs
                    message = 9;
                } else if (xCoord == 10 && yCoord == 7) { //beef
                    message = 10;
                } else if (xCoord == 12 && yCoord == 8) { // fish
                    message = 11;
                } else if (xCoord == 0 && yCoord == 9) { //apple
                    message = 12;
                } else if (xCoord == 6 && yCoord == 9) { // spinach
                    message = 13;
                } else if (xCoord == 10 && yCoord == 4) { //butter
                    message = 14;
                } else if (xCoord == 11 && yCoord == 9) { //corn
                    message = 15;
                } else if (xCoord == 15 && yCoord == 9) { //cheese
                    message = 16;
                } else {
                    message = -1;
                }
                d.repaint();
            }
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            update();
        } else if (sceneNum == 4) {
            di.keyReleased(e);
            panel.removeAll();
            panel.revalidate();
            update();
            panel.repaint();
        } else if (sceneNum == 5) {
            di.keyReleased(e);
            if (di.lastWord()) {
                if (imgName.equals("fridge closed") || imgName.equals("fridge open")) {
                    if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                        if (x <= 175) { // stove
                            imgName = "stove";
                            di.getDialogue().setVisible(false);
                            statsbar.getStats().setVisible(false);
                            panel.removeAll();
                            panel.revalidate();
                            panel.repaint();
                            addComponents();
                            drawPerson(x, y);
                            cook();
                            drawBackground();
                        } else if (x <= 417) { // cabinet
                            imgName = "cabinet closed";
                            statsbar.getStats().setVisible(false);
                            panel.removeAll();
                            panel.revalidate();
                            panel.repaint();
                            drawBackground();
                        }
                    }
                }
                if (!imgName.contains("problem")) {
                    if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                        imgName = "fridge closed";
                        panel.removeAll();
                        panel.revalidate();
                        panel.repaint();
                        addComponents();
                        drawPerson(x, y);
                        drawBackground();
                        statsbar.getStats().setVisible(true); // get back stats back
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (imgName.equals("problemB")) {
                        imgName = "fridge closed";
                        panel.removeAll();
                        panel.revalidate();
                        panel.repaint();
                        addComponents();
                        drawPerson(x, y);
                        drawBackground();
                    }
                }
            }
        } else if (sceneNum == 6) {
            update();
        } else if (sceneNum == 7) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                sceneNum = 1;
                message = -1;
                xCoord = 7; yCoord = 0;
                statsbar.reset();
                invL.clear();
                invR = new Food[4][3];
                selectedLeft = new int[2];
                selectedRight = new int[2];
                selectedLeft[0] = -1;
                selectedRight[0] = -1;
                selected = new boolean[4][3];
                name.clear();
                numMeals = 0;
                panel.removeAll();
                panel.revalidate();
                panel.repaint();
                update();
            } else if (e.getKeyCode() == KeyEvent.VK_Q) {
                mainFrame.setVisible(false);
                mainFrame.dispose();
            }
        }
    }

    /**
     * update method
     *
     * updates the current program
     */
    public void update () {
        if (sceneNum == 0) { // splash screen
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
        } else if (sceneNum == 1) { // main menu screen
            name = new ArrayList<>();
            JButton instructions = new JButton("Instructions");
            instructions.setFocusable(false); // must be used so that the keyboard doesn't stop working :sobs:
            instructions.setFont(new Font(Font.SERIF, Font.PLAIN,  30));
            instructions.setBounds(190, 140, 225, 40);
            //instructions.setBorderPainted(true);
            instructions.setOpaque(true);
            instructions.setBackground(new Color(207, 226, 243, 0));
            instructions.setBorder(new RoundedBorder(20, new Color(207, 226, 243), new Color(11, 83, 148), "Instructions", 30, 40, 30)); //10 is the radius
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
            JTextArea instructionsText = new JTextArea("    YOU must try to cook meals for yourself for a whole day. And this \nisn't just ordering takeout. The goal of the game is to cook 3 nutritious, \ncaloric meals that will satisfy you, the player. \n" +
                    "    You must survive 1 day, cooking 3 meals. Before you cook, \nyou'll go to the grocery store. There, you'll have to navigate the maze of a store,\nbuying healthy products with the budget you have, shown at the \ntop right of the screen.\n"+
                    "    Once you return home, you'll first put all your groceries in the fridge. Then, \nit's time to cook! If the food you combine counts as a meal, you will eat it and gain \nthose nutrients and calories. Otherwise, it will go into the trash.\n"+
                    "    You will not die if you don’t eat anything all day. \n");
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

            choose = new JLabel("*");
            choose.setBounds(230, chooseY, 300, 50);
            choose.setFont(new Font(Font.SERIF, Font.PLAIN,  45));
            panel.add(choose);

            JLabel level1 = new JLabel("Level 1");
            level1.setFont(new Font(Font.SERIF, Font.PLAIN,  30));
            level1.setBounds(270, 190, 225, 40);
            panel.add(level1);

            JLabel level2 = new JLabel("Level 2");
            level2.setFont(new Font(Font.SERIF, Font.PLAIN,  30));
            level2.setBounds(270, 240, 225, 40);
            panel.add(level2);

            JLabel level3 = new JLabel("Level 3");
            level3.setFont(new Font(Font.SERIF, Font.PLAIN,  30));
            level3.setBounds(270, 290, 225, 40);
            panel.add(level3);

            JLabel text = new JLabel("This game was made by: Kayla Lin and Angelina Jiang");
            text.setFont(new Font(Font.SERIF, Font.PLAIN,  10));
            text.setBounds(190, 330, 300, 40);
            panel.add(text);
        } else if (sceneNum == 2) { // Level 1
            
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            panel.setBackground(new Color(0, 0, 0, 0));
            statsbar = new StatsBar();
            dialogue = new Dialogue(dialogueText1);
            addComponents();
            drawBackground();

        } else if (sceneNum == 3) { // Level 2
            panel.add(di.getDialogue());
            panel.setBackground(new Color(201, 218, 248));
            { // grocery shop
                d = new Drawing();
                d.setBounds(0, 0, 655, 440);
                panel.add(d);
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 16; j++) {
                        for (int k = 0; k < 4; k++) {
                            if (maze.check(i, j, k)) {
                                if (k == 0) {
                                    d.x.add(80 + (j * 30));
                                    d.y.add(70 + (i * 30));
                                    d.x2.add(80 + (j * 30) + 30);
                                    d.y2.add(70 + (i * 30));
                                } else if (k == 1) {
                                    d.x.add(80 + (j * 30) + 30);
                                    d.y.add(70 + (i * 30));
                                    d.x2.add(80 + (j * 30) + 30);
                                    d.y2.add(70 + (i * 30) + 30);
                                } else if (k == 2) {
                                    d.x.add(80 + (j * 30));
                                    d.y.add(70 + (i * 30) + 30);
                                    d.x2.add(80 + (j * 30) + 30);
                                    d.y2.add(70 + (i * 30) + 30);
                                } else {
                                    d.x.add(80 + (j * 30));
                                    d.y.add(70 + (i * 30));
                                    d.x2.add(80 + (j * 30));
                                    d.y2.add(70 + (i * 30) + 30);
                                }
                            }
                        }
                    }
                }
                Food lettuceFood = (new Food("Lettuce", "Pictures/lettuce.png", new int[]{0, 2, 4}, 30, "", 0.59));
                Food whiteFlourFood = (new Food("White Flour", "Pictures/flour.png", new int[]{1, 5}, 302, "", 1.62));
                Food multigrainFlourFood = (new Food("Multigrain Flour", "Pictures/multigrain_flour.png", new int[]{5, 6, 7}, 160, "", 2.22));
                Food milkFood = (new Food("Milk", "Pictures/milk.png", new int[]{0, 3, 8}, 150, "", 1.47));
                Food whiteRiceFood = (new Food("Cooked Rice", "Pictures/rice(white).png", new int[]{3, 8}, 242, "", 1.09));
                Food multigrainRiceFood = (new Food("Healthy Cooked Rice", "Pictures/rice(multigrain).png", new int[]{3, 7, 8, 9}, 180, "", 1.35));
                Food tomatoFood = (new Food("Tomato", "Pictures/tomato.png", new int[]{2}, 22, "", 0.67));
                Food potatoFood = (new Food("Potato", "Pictures/potato.png", new int[]{2, 10}, 87, "", 0.65));
                Food carrotFood = (new Food("Carrot", "Pictures/carrot.png", new int[]{0, 2, 4}, 22, "", 0.76));
                Food rawEggsFood = (new Food("Raw Eggs", "Pictures/eggs.png", new int[]{3, 11, 12}, 78, "", 1.10));
                Food rawBeefFood = (new Food("Raw Beef", "Pictures/raw_beef.png", new int[]{1, 5}, 217, "", 2.25));
                Food rawFishFood = (new Food("Raw Fish", "Pictures/raw_fish.png", new int[]{1, 3, 12, 13}, 190, "", 2.50));
                Food appleFood = (new Food("Apple", "Pictures/apple.png", new int[]{2, 9}, 95, "", 0.79));
                Food spinachFood = (new Food("Spinach", "Pictures/leafy_greens.png", new int[]{0, 4, 7, 12}, 30, "", 1.2));
                Food butterFood = (new Food("Butter", "Pictures/butter.png", new int[]{10, 3, 11}, 102, "", 0.66));
                Food uncookedCornFood = (new Food("Cooked Corn", "Pictures/corn.png", new int[]{1, 2, 7}, 177, "", 1.46));
                Food cheeseFood = (new Food("Cheese", "Pictures/cheese.png", new int[]{13, 0, 10, 8}, 100, "", 1.77));

                JPanel lettuce = lettuceFood.display();
                JPanel whiteFlour = whiteFlourFood.display();
                JPanel multigrainFlour = multigrainFlourFood.display();
                JPanel milk = milkFood.display();
                JPanel multigrainRice = multigrainRiceFood.display();
                JPanel whiteRice = whiteRiceFood.display();
                JPanel tomato = tomatoFood.display();
                JPanel potato = potatoFood.display();
                JPanel carrot = carrotFood.display();
                JPanel rawEggs = rawEggsFood.display();
                JPanel rawBeef = rawBeefFood.display();
                JPanel rawFish = rawFishFood.display();
                JPanel apple = appleFood.display();
                JPanel spinach = spinachFood.display();
                JPanel butter = butterFood.display();
                JPanel uncookedCorn = uncookedCornFood.display();
                JPanel cheese = cheeseFood.display();
                Food[] buyable = new Food[17];
                lettuce.setBounds(80 + (30 * 3), 70, 30, 30);
                whiteFlour.setBounds(80 + (30 * 5), 70 + 30, 30, 30);
                multigrainFlour.setBounds(80 + (30 * 8), 70 + 30, 30, 30);
                milk.setBounds(80 + (30 * 14), 70 + (30), 30, 30);
                whiteRice.setBounds(80 + (30), 70 + (30 * 4), 30, 30);
                multigrainRice.setBounds(80 + (30 * 3), 70 + (30 * 5), 30, 30);
                tomato.setBounds(80 + (30 * 8), 70 + (30 * 4), 30, 30);
                potato.setBounds(80 + (30 * 13), 70 + (30 * 4), 30, 30);
                carrot.setBounds(80 + (30 * 12), 70 + (30 * 5), 30, 30);
                rawEggs.setBounds(80 + (30 * 5), 70 + (30 * 6), 30, 30);
                rawBeef.setBounds(80 + (30 * 10), 70 + (30 * 7), 30, 30);
                rawFish.setBounds(80 + (30 * 12), 70 + (30 * 8), 30, 30);
                apple.setBounds(80 + (30 * 0), 70 + (30 * 9), 30, 30);
                spinach.setBounds(80 + (30 * 6), 70 + (30 * 9), 30, 30);
                butter.setBounds(80 + (30 * 10), 70 + (30 * 4), 30, 30);
                uncookedCorn.setBounds(80 + (30 * 11), 70 + (30 * 9), 30, 30);
                cheese.setBounds(80 + (30 * 15), 70 + (30 * 9), 30, 30);
                panel.add(lettuce);
                panel.add(whiteFlour);
                panel.add(multigrainFlour);
                panel.add(milk);
                panel.add(whiteRice);
                panel.add(multigrainRice);
                panel.add(tomato);
                panel.add(potato);
                panel.add(carrot);
                panel.add(rawEggs);
                panel.add(rawBeef);
                panel.add(rawFish);
                panel.add(apple);
                panel.add(spinach);
                panel.add(butter);
                panel.add(uncookedCorn);
                panel.add(cheese);

                buyable[0] = lettuceFood; buyable[1] = whiteFlourFood; buyable[2] = multigrainFlourFood; buyable[3] = milkFood; buyable[4] = whiteRiceFood;
                buyable[5] = multigrainRiceFood; buyable[6] = tomatoFood; buyable[7] = potatoFood; buyable[8] = carrotFood; buyable[9] = rawEggsFood;
                buyable[10] = rawBeefFood; buyable[11] = rawFishFood; buyable[12] = appleFood; buyable[13] = spinachFood; buyable[14] = butterFood;
                buyable[15] = uncookedCornFood; buyable[16] = cheeseFood;

                DecimalFormat df = new DecimalFormat("0.00");
                // DecimalFormat, default is RoundingMode.HALF_EVEN
                JLabel budgetLabel = new JLabel("Budget: $" + df.format(this.budget) + " (CAD)");
                budgetLabel.setFont(new Font(Font.SERIF, Font.PLAIN,  18));
                budgetLabel.setBounds(20, 10, 300, 40);
                panel.add(budgetLabel);
                if (message != -1 && message != 17) {
                    JPanel buying = new JPanel();
                    buying.setLayout(null);
                    buying.setBackground(new Color(72, 176, 216, 0));
                    buying.setSize(400, 220);
                    buying.setBounds(127, 100, 400, 220);
                    buying.setBorder(new RoundedBorder(40, new Color(72, 176, 216), new Color(16, 85, 112),"",0, 0, 0));
                    JLabel question = new JLabel("Would you like to buy 1 ");
                    question.setFont(new Font(Font.SERIF, Font.PLAIN,  25));
                    question.setBounds(80, 0, 350, 60);
                    buying.add(question);
                    JLabel itemName = new JLabel(buyable[message].name());
                    itemName.setFont(new Font(Font.SERIF, Font.BOLD, 25));
                    itemName.setBounds(100, 40, 400, 40);
                    buying.add(itemName);
                    JLabel price = new JLabel("for $" + (df.format(buyable[message].price())) + " CAD?");
                    price.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
                    price.setBounds(115, 70, 400, 40);
                    buying.add(price);

                    JButton no = new JButton("NO");
                    JButton yes = new JButton("YES");
                    yes.setFocusable(false); // must be used so that the keyboard doesn't stop working :sobs:
                    yes.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
                    yes.setBounds(50, 140, 120, 60);
                    //instructions.setBorderPainted(true);
                    yes.setOpaque(true);
                    yes.setBackground(new Color(72, 176, 216));
                    yes.setBorder(new RoundedBorder(15, new Color(217, 234, 211), new Color(217, 234, 211), "YES", 20, 40, 35));
                    //instructions.setForeground(Color.BLACK);
                    buying.add(yes);
                    JLabel enoughMoney = new JLabel("");
                    enoughMoney.setBounds(50, 100, 130, 60);
                    buying.add(enoughMoney);
                    if (budget < buyable[message].price()) {
                        enoughMoney.setText("Not Enough Money :(");
                    }
                    no.setFont(new Font(Font.SERIF, Font.PLAIN,  25));
                    no.setBorder(new RoundedBorder(15, new Color(244, 204, 204), new Color(244, 204, 204), "NO", 20, 45, 35));
                    no.setBounds(235, 140, 120, 60);
                    //no.setOpaque(true);
                    no.setBackground(new Color(72, 176, 216));
                    no.setFocusable(false);
                    buying.add(no);
                    layeredPane.add(buying, 2);
                    yes.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (enoughMoney.getText().equals("Not Enough Money :(")) {
                                // nothing happens
                            } else {
                                budget -= buyable[message].price();
                                buying.setVisible(false);
                                if (inventory.contains(buyable[message])) {
                                    for (int i = 0; i < inventory.size(); i++) {
                                        if (inventory.get(i).name().equals(buyable[message].name())) {
                                            inventory.get(i).setQuantity(inventory.get(i).quantity() + 1);
                                        }
                                    }
                                } else {
                                    inventory.add(buyable[message]);
                                }
                                message = -1;
                                inventoryFrame.getContentPane().removeAll();
                                inventoryFrame.revalidate();
                                inventoryFrame.repaint();

                                panel.removeAll();
                                panel.revalidate();
                                panel.repaint();
                                update();
                            }
                        }
                    });
                    no.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            buying.setVisible(false);
                            message = -1;
                        }
                    });
                }
                JButton inventory = new JButton("Inventory");
                inventory.setBackground(new Color(201, 218, 248));
                inventory.setOpaque(true);
                inventory.setFocusable(false);
                inventory.setBounds(520, 20, 100, 30);
                inventory.setBorder(new RoundedBorder(10, new Color(207, 226, 243), new Color(130, 155, 204), "Inventory", 20, 15, 20));
                panel.add(inventory);

                inventoryFrame.setResizable(false);
                inventoryFrame.setSize(610, 300);
                Inventory i = new Inventory(this.inventory);
                inventoryFrame.add(BorderLayout.CENTER, new JScrollPane(i.getPanel()));
                inventoryFrame.setLocationRelativeTo(null);
                inventoryFrame.setVisible(false);


                inventory.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        inventoryFrame.setVisible(true);
                    }
                });
            }
        } else if (sceneNum == 4) { // organizing the fridge
            invL = inventory;
            panel.add(di.getDialogue());

            InventoryM invmL = new InventoryM(invL, "im+s", true);
            invmL.getPanel().setBounds(30, 30, 650, 440); // the one on the left (inv)
            panel.add(invmL.getPanel());

            InventoryM invmR = new InventoryM(invR, "im+s", false);
            invmR.getPanel().setBounds(410, 30, 650, 440); // the one on the left (inv)
            panel.add(invmR.getPanel());

            if (selectedLeft[0] != -1) {
                JPanel highlight = new JPanel();
                highlight.setSize(60, 60);
                highlight.setBackground(new Color(255, 0, 0, 0));
                highlight.setBorder(new RoundedBorder(10, new Color(255, 255, 126), new Color(255,255,126), "", 0, 0, 0));
                highlight.setBounds(35 + 63 * selectedLeft[0], 35 + 70 * selectedLeft[1], 60, 60); // assuming it starts at (35, 90)
                panel.add(highlight);
            }
            if (selectedRight[0] != -1) {
                JPanel highlight = new JPanel();
                highlight.setSize(60, 60);
                highlight.setBackground(new Color(255, 0, 0, 0));
                highlight.setBorder(new RoundedBorder(10, new Color(255, 0, 0, 90), new Color(255,0,0), "", 0, 0, 0));
                highlight.setBounds(417 + 80 * selectedRight[0], 35 + 70 * selectedRight[1], 60, 60); // assuming i
                panel.add(highlight);
            } 
            JButton swap = new JButton("swap");
            swap.setSize(150, 50);
            swap.setBorder(new RoundedBorder(20, new Color(201, 218, 248), new Color(145, 165, 199), "Swap", 20, 50, 30));
            swap.setBounds(450, 330, 150, 50);
            swap.setFocusable(false);
            swap.setBackground(new Color(0, 0, 0, 0));
            swap.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!di.lastWord()) return;
                    Food temp = invR[selectedRight[1]][selectedRight[0]];
                    if (temp == null) {
                        invR[selectedRight[1]][selectedRight[0]] = invL.get(selectedLeft[1] * 4 + selectedLeft[0]);
                        invL.remove(selectedLeft[1] * 4 + selectedLeft[0]);
                    } else {
                        invR[selectedRight[1]][selectedRight[0]] = invL.get(selectedLeft[1] * 4 + selectedLeft[0]);
                        invL.set(selectedLeft[1] * 4 + selectedLeft[0], temp);
                    }
                    selectedLeft[0] = -1; selectedRight[0] = -1;
                    panel.removeAll();
                    panel.revalidate();
                    panel.repaint();
                    update();
                }
            });
            if (selectedLeft[0] != -1 && selectedRight[0] != -1) {
                panel.add(swap);
            }
            JButton done = new JButton("Done");
            done.setSize(150, 50);
            done.setBorder(new RoundedBorder(20, new Color(201, 218, 248), new Color(145, 165, 199), "Done", 20, 50, 30));
            done.setBounds(300, 330, 150, 50);
            done.setFocusable(false);
            done.setBackground(new Color(0, 0, 0, 0));
            panel.add(done);
            done.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!di.lastWord()) return;
                    sceneNum = 5;
                    panel.removeAll();
                    panel.revalidate();
                    panel.repaint();
                    di = new Dialogue(new String[]{"Ding ding ding. Oh look, its time to start cooking!<br> Let's make a nice healthy meal!", "Press the arrow keys or WASD to move around the kitchen.<br>", "When you're underneath the stove, you can press UP <br> or W to create a food item.", "Press COOK or COMBINE to make a meal with your foods.<br>You can select items in your fridge by clicking on them. These will<br>be highlighted in red.", "Press DOWN or S to then go back to the whole kitchen.<br>", "At the end of a meal, press FINISH MEAL to well, finish cooking<br>that meal! If the food you made counts as a meal,<br>it will display such on the results screen for that day.<br>", "Remember your stats bars at the top left. Try to fill them!<br>Good luck and have fun!<br>"});
                    update();
                }
            });

            JLabel background;
            try{
                background = (new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/kitchen2.png")))));
            }catch(IOException e){ throw new RuntimeException(e); }
            background.setBounds(0, 0, 643, 405);
            panel.add(background);
        } else if (sceneNum == 5) { // Level 3
            panel.removeAll();
            panel.setBackground(new Color(201, 218, 248));
            fridge = invR;
            mainFrame.getContentPane().setBackground(transparent); // why

            backgroundImg = new JLabel();
            imgName = "fridge open";

            statsbar = new StatsBar();
            try {
                personImg = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/person.png")).getScaledInstance(160, 240, Image.SCALE_SMOOTH)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            personImg.setBounds(0, 0, 160, 240);
            person = new JPanel();
            person.setBackground(transparent);
            person.setLayout(null);
            person.add(personImg);

            problem = new JPanel();
            problem.setBackground(new Color(255, 255, 255, 140));
            problem.setVisible(false);

            addComponents();
            drawPerson(x, y);
            drawBackground();
        } else if (sceneNum == 6) { // end of meal
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            panel.setBackground(new Color(201, 218, 248));
            JLabel mageon;
            try{
                mageon = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/MAGEON eating.png")).getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
            }catch(IOException e){
                mageon = new JLabel("Mageon eating");
            }
            mageon.setBounds(400, 50,150, 150);
            panel.add(mageon);
            JLabel congrats = new JLabel("You made:");
            panel.add(congrats);
            int x = 50;
            for (String name : name) {
                JLabel label = new JLabel(name);
                label.setBounds(20, x, 200, 20);
                x += 20;
                label.setFont(new Font(Font.SERIF, Font.PLAIN, 13));
                panel.add(label);
            }
            congrats.setFont(new Font(Font.SERIF, Font.PLAIN, 13));
            congrats.setBounds(20, 20, 200, 40);
            
            numMeals++;
            imgName = "fridge open";
            if (numMeals == 3) {
                JLabel instruct = new JLabel("<html>Aaand you're done! Congrats! Press ENTER to go back to the main menu or press Q to exit<html>");
                panel.add(instruct);
                sceneNum = 7;

                instruct.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
                instruct.setBounds(20, 200, 600, 200);
            } else {
                JLabel instruct = new JLabel("Press ENTER to continue onto the next meal");
                panel.add(instruct);
                sceneNum = 5;
                imgName = "problemB";
                instruct.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
                instruct.setBounds(20, 250, 500, 200);
            }
        }
        mainFrame.repaint();
    }

    public void giveProblem(String name, int calories, boolean[] nutrients) {
        problem.removeAll();
        problem.revalidate();
        problem.repaint();
        imgName = "problemA";
        panel.setBackground(new Color(201, 218, 248));
        problem.setBounds(120, 50, 400, 300);
        //problem.setBounds(0, 0, 650, 440);
        problem.setLayout(null);
        int a = (int) (Math.random() * 1001);
        int b = (int) (Math.random() * 1001);
        JLabel equation = new JLabel(a + " + " + b + " = ");
        equation.setBounds(50, 50, 200, 50);
        equation.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        problem.add(equation);

        ArrayList<Integer> coordinates = new ArrayList<>();
        coordinates.add(70);
        coordinates.add(170);
        coordinates.add(270);
        Collections.shuffle(coordinates);
        int x = (int) (Math.random() * 2001);
        if (x == a + b) x++;
        int y = (int) (Math.random() * 2001);
        if (y == a + b) y--;
        JButton wrong1 = new JButton(String.valueOf(x));
        JButton wrong2 = new JButton(String.valueOf(y));
        wrong1.setSize(70, 50);
        wrong2.setSize(70, 50);
        wrong1.setBounds(coordinates.remove(0), 100, 70, 50);
        wrong1.setBorder(new RoundedBorder(10, new Color(201, 218, 248), new Color(201, 218, 248), String.valueOf(x), 20, 10, 30));
        wrong2.setBounds(coordinates.remove(0), 100, 70, 50);
        wrong2.setBorder(new RoundedBorder(10, new Color(201, 218, 248), new Color(201, 218, 248), String.valueOf(y), 20, 10, 30));
        problem.add(wrong1);
        problem.add(wrong2);
        wrong1.setBackground(transparent);
        wrong2.setBackground(transparent);

        wrong1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                panel.revalidate();
                panel.repaint();

                JLabel label = new JLabel("<html>:( You lost some ingredients .. Press ENTER to continue <html>");
                label.setBounds(50, 0, panel.getWidth(), panel.getHeight());

                panel.add(label);
                imgName = "problemB";
            }
        });

        wrong2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                panel.revalidate();
                panel.repaint();

                JLabel label = new JLabel("<html>:( You lost some ingredients .. Press ENTER to continue <html>");
                label.setBounds(50, 0, panel.getWidth(), panel.getHeight());

                panel.add(label);
                imgName = "problemB";
            }
        });

        JButton right = new JButton(String.valueOf(a + b));
        right.setSize(50, 50);
        right.setBounds(coordinates.remove(0), 100, 70,50);
        right.setBorder(new RoundedBorder(10, new Color(201, 218, 248), new Color(201, 218, 248), String.valueOf(a + b), 20, 10, 30));
        problem.add(right);
        right.setBackground(transparent);

        right.setFocusable(false);
        wrong1.setFocusable(false);
        wrong2.setFocusable(false);
        right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                panel.revalidate();
                panel.repaint();

                JLabel label = new JLabel("<html>Congrats! You have picked the right answer! You have made: " + name + "<html>. Press ENTER to continue <html>");
                label.setBounds(50, 0, panel.getWidth(), panel.getHeight());
                statsbar.addNutri(nutrients);
                statsbar.addSatis();
                statsbar.addCalo(calories * 10);
                panel.add(label);
                imgName = "problemB";
            }
        });

        problem.setVisible(true);
        panel.add(problem);
    }

    /**
     * mouseClicked method, part of the interface, MouseListener class
     *
     * @param e The argument passed from the command line
     */
    public void mouseClicked(MouseEvent e) {
        if (sceneNum == 2) {
            if (dialogue.lastWord() && e.getX() > 450 && e.getX() < 610 && e.getY() > 270 && e.getY() < 400) {
                sceneNum = 3;
                panel.removeAll();
                panel.revalidate();
                panel.repaint();
                update();
                return;
            }
            if(!fridgeOpen && e.getX() > 381 && e.getX() < 640 && e.getY() > 10 && e.getY() < 290){
                fridgeOpen = true;
            }else if(fridgeOpen && e.getX() > 275 && e.getX() < 640 && e.getY() > 10 && e.getY() < 290){
                fridgeOpen = false;
            }
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            addComponents();
            drawBackground();
        } else if (sceneNum == 5) {
            if (imgName.equals("problemA") || imgName.equals("problemB") || imgName.equals("stove")) return;
            if (imgName.equals("fridge open")) {
                if (e.getX() > 417 && e.getX() < 477 && e.getY() > 60 && e.getY() < 120 && fridge != null && fridge[0][0] != null) {
                    selected[0][0] = !selected[0][0];
                } else if (e.getX() > 497 && e.getX() < 497 + 60 && e.getY() > 60 && e.getY() < 120 && fridge != null && fridge[0][1] != null) {
                    selected[0][1] = !selected[0][1];
                } else if (e.getX() > 577 && e.getX() < 577 + 60 && e.getY() > 60 && e.getY() < 120 && fridge != null && fridge[0][2] != null) {
                    selected[0][2] = !selected[0][2];
                } else if (e.getX() > 417 && e.getX() < 477 && e.getY() > 130 && e.getY() < 190 && fridge != null && fridge[1][0] != null) {
                    selected[1][0] = !selected[1][0];
                } else if (e.getX() > 497 && e.getX() < 497 + 60 && e.getY() > 130 && e.getY() < 190 && fridge != null && fridge[1][1] != null) {
                    selected[1][1] = !selected[1][1];
                } else if (e.getX() > 577 && e.getX() < 577 + 60 && e.getY() > 130 && e.getY() < 190 && fridge != null && fridge[1][2] != null) {
                    selected[1][2] = !selected[1][2];
                } else if (e.getX() > 417 && e.getX() < 477 && e.getY() > 200 && e.getY() < 260 && fridge != null && fridge[2][0] != null) {
                    selected[2][0] = !selected[2][0];
                } else if (e.getX() > 497 && e.getX() < 497 + 60 && e.getY() > 200 && e.getY() < 260 && fridge != null && fridge[2][1] != null) {
                    selected[2][1] = !selected[2][1];
                } else if (e.getX() > 577 && e.getX() < 577 + 60 && e.getY() > 200 && e.getY() < 260 && fridge != null && fridge[2][2] != null) {
                    selected[2][2] = !selected[2][2];
                } else if (e.getX() > 417 && e.getX() < 477 && e.getY() > 270 && e.getY() < 330 && fridge != null && fridge[3][0] != null) {
                    selected[3][0] = !selected[3][0];
                } else if (e.getX() > 497 && e.getX() < 497 + 60 && e.getY() > 270 && e.getY() < 330 && fridge != null && fridge[3][1] != null) {
                    selected[3][1] = !selected[3][1];
                } else if (e.getX() > 577 && e.getX() < 577 + 60 && e.getY() > 270 && e.getY() < 330 && fridge != null && fridge[3][2] != null) {
                    selected[3][2] = !selected[3][2];
                } else if (e.getX() > 275 && e.getX() < 381 && e.getY() > 10 && e.getY() < 290) {
                    imgName = "fridge closed";
                }
            } else if (imgName.equals("fridge closed") && e.getX() > 381 && e.getX() < 640 && e.getY() > 10 && e.getY() < 290) {
                imgName = "fridge open";
            } else if (imgName.equals("cabinet closed") && e.getX() > 135 && e.getX() < 540 && e.getY() < 250) {
                imgName = "cabinet open";
            } else if (imgName.equals("cabinet open") && ((e.getX() > 120 && e.getX() < 540 && e.getY() < 250) || (e.getX() > 540 && e.getX() < 610 && e.getY() < 330) || (e.getX() > 40 && e.getX() < 120 && e.getY() < 330))) {
                imgName = "cabinet closed";
            }
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            addComponents();
            drawPerson(x, y);
            drawBackground();
        }
    }

    /**
     * mousePressed method, part of the interface, MouseListener class
     *
     * @param e The argument passed from the command line
     */
    public void mousePressed(MouseEvent e) {  }

    /**
     * mouseReleased method, part of the interface, MouseListener class
     *
     * @param e The argument passed from the command line
     */
    public void mouseReleased(MouseEvent e) {
        if (sceneNum == 4) {
            int x = -1, y = -1;
            if (selectedLeft[0] != -1) {
                x = selectedLeft[0];
                y = selectedLeft[1];
            }
            if (e.getX() > 36 && e.getX() < 96 && e.getY() > 60 && e.getY() < 120 && invL.size() >= 1) {
                x = 0; y = 0;
            } else if (e.getX() > 99 && e.getX() < 99 + 60 && e.getY() > 60 && e.getY() < 120 && invL.size() >= 2) {
                x = 1; y = 0;
            } else if (e.getX() > 162 && e.getX() < 162 + 60 && e.getY() > 60 && e.getY() < 120 && invL.size() >= 3) {
                x = 2; y = 0;
            } else if (e.getX() > 225 && e.getX() < 285 && e.getY() > 60 && e.getY() < 120 && invL.size() >= 4) {
                x = 3; y = 0;
            } else if (e.getX() > 36 && e.getX() < 96 && e.getY() > 130 && e.getY() < 190 && invL.size() >= 5) {
                x = 0; y = 1;
            } else if (e.getX() > 99 && e.getX() < 99 + 60 && e.getY() > 130 && e.getY() < 190 && invL.size() >= 6) {
                x = 1; y = 1;
            } else if (e.getX() > 162 && e.getX() < 162 + 60 && e.getY() > 130 && e.getY() < 190 && invL.size() >= 7) {
                x = 2; y = 1;
            } else if (e.getX() > 225 && e.getX() < 285 && e.getY() > 130 && e.getY() < 190 && invL.size() >= 8) {
                x = 3; y = 1;
            } else if (e.getX() > 36 && e.getX() < 96 && e.getY() > 200 && e.getY() < 260 && invL.size() >= 9) {
                x = 0; y = 2;
            } else if (e.getX() > 99 && e.getX() < 99 + 60 && e.getY() > 200 && e.getY() < 260 && invL.size() >= 10) {
                x = 1; y = 2;
            } else if (e.getX() > 162 && e.getX() < 162 + 60 && e.getY() > 200 && e.getY() < 260 && invL.size() >= 11) {
                x = 2; y = 2;
            } else if (e.getX() > 225 && e.getX() < 285 && e.getY() > 200 && e.getY() < 260 && invL.size() >= 12) {
                x = 3; y = 2;
            } else if (e.getX() > 36 && e.getX() < 96 && e.getY() > 270 && e.getY() < 330 && invL.size() >= 13) {
                x = 0; y = 3;
            } else if (e.getX() > 99 && e.getX() < 99 + 60 && e.getY() > 270 && e.getY() < 330 && invL.size() >= 14) {
                x = 1; y = 3;
            } else if (e.getX() > 162 && e.getX() < 162 + 60 && e.getY() > 270 && e.getY() < 330 && invL.size() >= 15) {
                x = 2; y = 3;
            } else if (e.getX() > 225 && e.getX() < 285 && e.getY() > 270 && e.getY() < 330 && invL.size() >= 16) {
                x = 3; y = 3;
            } else if (e.getX() > 36 && e.getX() < 96 && e.getY() > 340 && e.getY() < 400  && invL.size() >= 17) {
                x = 0; y = 4;
            }

            int xR = -1, yR = -1;
            if (selectedRight[0] != -1) {
                xR = selectedRight[0];
                yR = selectedRight[1];
            }
            if (e.getX() > 417 && e.getX() < 477 && e.getY() > 60 && e.getY() < 120) {
                xR = 0; yR = 0;
            } else if (e.getX() > 497 && e.getX() < 497 + 60 && e.getY() > 60 && e.getY() < 120) {
                xR = 1; yR = 0;
            } else if (e.getX() > 577 && e.getX() < 577 + 60 && e.getY() > 60 && e.getY() < 120) {
                xR = 2; yR = 0;
            } else if (e.getX() > 417 && e.getX() < 477 && e.getY() > 130 && e.getY() < 190) {
                xR = 0; yR = 1;
            } else if (e.getX() > 497 && e.getX() < 497 + 60 && e.getY() > 130 && e.getY() < 190) {
                xR = 1; yR = 1;
            } else if (e.getX() > 577 && e.getX() < 577 + 60 && e.getY() > 130 && e.getY() < 190) {
                xR = 2; yR = 1;
            } else if (e.getX() > 417 && e.getX() < 477 && e.getY() > 200 && e.getY() < 260) {
                xR = 0; yR = 2;
            } else if (e.getX() > 497 && e.getX() < 497 + 60 && e.getY() > 200 && e.getY() < 260) {
                xR = 1; yR = 2;
            } else if (e.getX() > 577 && e.getX() < 577 + 60 && e.getY() > 200 && e.getY() < 260) {
                xR = 2; yR = 2; //
            } else if (e.getX() > 417 && e.getX() < 477 && e.getY() > 270 && e.getY() < 330) {
                xR = 0; yR = 3;
            } else if (e.getX() > 497 && e.getX() < 497 + 60 && e.getY() > 270 && e.getY() < 330) {
                xR = 1; yR = 3;
            } else if (e.getX() > 577 && e.getX() < 577 + 60 && e.getY() > 270 && e.getY() < 330) {
                xR = 2; yR = 3;
            }
            selectedLeft[0] = x; selectedLeft[1] = y;
            selectedRight[0] = xR; selectedRight[1] = yR;
            panel.removeAll();
            panel.revalidate();
            update();
            panel.repaint();
        }
    }

    /**
     * mouseEntered method, part of the interface, MouseListener class
     *
     * @param e The argument passed from the command line
     */
    public void mouseEntered(MouseEvent e) {   }

    /**
     * mouseExited method, part of the interface, MouseListener class
     *
     * @param e The argument passed from the command line
     */
    public void mouseExited(MouseEvent e) {   }

    /**
     * Drawing class
     *
     * Draws the maze using Graphics
     */
    class Drawing extends JComponent {

        /** an ArrayList of x-coordinates */
        private ArrayList<Integer> x;

        /** an ArrayList of y-coordinates corresponding with the x-coordinates */
        private ArrayList<Integer> y;

        /** an ArrayList of the second x-coordinate */
        private ArrayList<Integer> x2;

        /** an ArrayList of the second y-coordinates corresponding with the second x-coordinates */
        private ArrayList<Integer> y2;
        // forms (x, y) and (x2, y2)

        /**
         * Drawing constructor
         *
         * initializes the instance variables
         */
        public Drawing() {
            x = new ArrayList<>();
            y = new ArrayList<>();
            x2 = new ArrayList<>();
            y2 = new ArrayList<>();
        }

        /**
         * paint method, part of the interface, JComponent class
         *
         * @param g The argument passed from the command line
         */
        public void paint (Graphics g) {
            if (sceneNum == 3) { // change later
                Graphics2D g2 = (Graphics2D) g;
                for (int i = 0; i < x.size(); i++) {
                    g2.setStroke(new BasicStroke(2));
                    g2.drawLine(x.get(i), y.get(i), x2.get(i), y2.get(i));
                }
                g2.fillOval(80 + (30 * xCoord) + 10, 70 + (30 * yCoord) + 10, 10, 10);
            }
        }
    }

    /**
     * drawBackground method
     *
     * A helper method that draws the background of the JPanel
     */
    public void drawBackground(){
        if (sceneNum == 4 || sceneNum == 2) {
            try {
                if (fridgeOpen) {
                    background = new JLabel(new ImageIcon(ImageIO.read(kitchenOpen)));
                } else {
                    background = new JLabel(new ImageIcon(ImageIO.read(kitchenClose)));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            background.setBounds(0, 0, 643, 405);
            panel.add(background);
        } else if (sceneNum == 5) {
            try {
                if (imgName.equals("fridge closed")) {
                    backgroundImg = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/kitchen1.png"))));
                } else if (imgName.equals("fridge open")) {
                    backgroundImg = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/kitchen2.png"))));
                } else if (imgName.equals("cabinet closed")) {
                    backgroundImg = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/cabinet_closed.png"))));
                } else if (imgName.equals("cabinet open")) {
                    backgroundImg = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/cabinet_opened.png"))));
                } else if (imgName.equals("stove")) {
                    backgroundImg = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/stove.png"))));
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            backgroundImg.setBounds(0, 0, 640, 400);
            panel.add(backgroundImg);
        }
    }

    /**
     * addComponents method
     *
     * a helper method to add the JComponents
     */
    public void addComponents(){
        if (sceneNum == 2) {
            JLabel im = null;
            try {
                im = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/grocery.png")).getScaledInstance(160, 130, Image.SCALE_SMOOTH)));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            im.setBounds(450, 270, 160, 130);
            if (!dialogue.lastWord()) {
                im.setVisible(false);
            }
            panel.add(im);
            panel.add(statsbar.getStats());
            panel.add(dialogue.getDialogue());
        } else if (sceneNum == 5) {
            panel.add(statsbar.getStats());
            if (!imgName.contains("cabinet")) {
                statsbar.getStats().setVisible(true);
            }
            if (!imgName.equals("stove")) {
                panel.add(di.getDialogue());
            }
            if (imgName.equals("fridge open")) { // iteming
                InventoryM inv = new InventoryM(fridge, "im+s", false);
                inv.getPanel().setBounds(410, 30, 650, 440);
                panel.add(inv.getPanel());
            }
            // highlights
            if (fridge != null && imgName.equals("fridge open")) {
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (selected[i][j]) {
                            JPanel highlight = new JPanel();
                            highlight.setSize(60, 60);
                            highlight.setBackground(new Color(255, 0, 0, 0));
                            highlight.setBorder(new RoundedBorder(10, new Color(255, 0, 0, 90), new Color(255, 0, 0), "", 0, 0, 0));
                            highlight.setBounds(417 + 80 * j, 35 + 70 * i, 60, 60); // assuming i
                            panel.add(highlight);
                        }
                    }
                }
            }
        }
    }

    /**
     * cook method
     *
     * a helper method to help the player cook
     */
    public void cook() {
        JButton cook = new JButton("Cook");
        JButton combine = new JButton("Combine");
        JButton finishMeal = new JButton("Finish Meal");
        finishMeal.setSize(200, 50);
        finishMeal.setBounds(240, 350, 200, 50);
        finishMeal.setBackground(transparent);
        finishMeal.setFocusable(false);
        finishMeal.setOpaque(false);
        finishMeal.setBorder(new RoundedBorder(20, new Color(201, 218, 248), new Color(201, 218, 248), "Finish Meal", 20, 27, 29));
        cook.setSize(100, 100);
        cook.setBounds(225, 300, 100, 50);
        cook.setBackground(transparent);
        cook.setOpaque(false);
        cook.setFocusable(false);
        cook.setBorder(new RoundedBorder(20, new Color(201, 218, 248), new Color(201, 218, 248), "Cook", 20, 27, 29));
        combine.setSize(100, 100);
        combine.setBounds(350, 300, 100, 50);
        combine.setBackground(transparent);
        combine.setOpaque(false);
        combine.setFocusable(false);
        combine.setBorder(new RoundedBorder(20, new Color(201, 218, 248), new Color(201, 218, 248), "Combine", 20, 15, 29));
        panel.add(cook);
        panel.add(combine);
        panel.add(finishMeal);
        cook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                panel.revalidate();
                panel.repaint();
                //giveProblem();
                imgName = "fridge open";
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (selected[i][j] && fridge != null && fridge[i][j] != null) {
                            if (fridge[i][j].name().equals("Raw Beef")) {
                                fridge[i][j].setName("Cooked Beef");
                                fridge[i][j].setPath("Pictures/cooked_beef.png");
                            } else if (fridge[i][j].name().equals("White Flour")) {
                                fridge[i][j].setName("Bread");
                                fridge[i][j].setPath("Pictures/white_bread.png");
                            } else if (fridge[i][j].name().equals("Multigrain Flour")) {
                                fridge[i][j].setName("Healthy Bread");
                                fridge[i][j].setPath("Pictures/multigrain_bread.png");
                            } else if (fridge[i][j].name().equals("Raw Fish")) {
                                fridge[i][j].setName("Cooked Fish");
                                fridge[i][j].setPath("Pictures/cooked_fish.png");
                            } else if (fridge[i][j].name().equals("Raw Eggs")) {
                                fridge[i][j].setName("Cooked Eggs");
                                fridge[i][j].setPath("Pictures/cooked_egg.png");
                            }
                        }
                    }
                }
                addComponents();
                drawPerson(x, y);
                drawBackground();
            }
        });
        combine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                panel.revalidate();
                panel.repaint();
                ArrayList<Food> select = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (selected[i][j] && fridge != null && fridge[i][j] != null) {
                            select.add(fridge[i][j]);
                            if (fridge[i][j].quantity() == 1) {
                                fridge[i][j] = null;
                            }  else {
                                fridge[i][j].setQuantity(fridge[i][j].quantity() - 1);
                            }
                            selected[i][j] = false;
                        } 
                    }
                }
                Recipes recipe = new Recipes("", select);
                CookBook cb = new CookBook();
                String names = "";
                calories = 0;
                nutrients = new boolean[14];
                for (Recipes r : cb.getCookbook()) {
                    if (recipe.equals(r)) {
                        names = r.getName();
                        for (Food f : r.getRecipe()) {
                            calories += f.calories();
                            for (int i = 0; i < f.nutrients().length; i++) {
                                nutrients[f.nutrients()[i]] = true;
                            }
                        }
                        break;
                    }
                }
                if (!names.equals("")) name.add(names);
                panel.removeAll();
                panel.revalidate();
                panel.repaint();
                if (calories != 0) {
                    giveProblem(name.get(name.size()-1), calories, nutrients);
                    addComponents();
                    drawPerson(x, y);
                    drawBackground();
                } else {
                    panel.removeAll();
                    panel.revalidate();
                    panel.repaint();

                    JLabel label = new JLabel("<html>:( None of your ingredients formed a recipe .. Press ENTER to continue <html>");
                    label.setBounds(50, 0, panel.getWidth(), panel.getHeight());

                    panel.add(label);
                    imgName = "problemB";
                }
            }
        });
        finishMeal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                sceneNum = 6;
                panel.removeAll();
                panel.revalidate();
                panel.repaint();
                JLabel label = new JLabel("A meal has finished!");
                label.setBounds(0, 0, 300, 300); // we deal with later
                panel.add(label);
            }
        });
    }
    /**
     * drawPerson method
     *
     * a helper class that draws a person
     */
    public void drawPerson(int x, int y) {
        if (imgName.equals("fridge closed") || imgName.equals("fridge open")) {
            personImg.setBounds(0, 0, 160, 240);
            person.setBounds(x, y, 160, 240);
            panel.add(person);
        }
    }

}