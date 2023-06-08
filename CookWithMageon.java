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

public class CookWithMageon implements KeyListener, MouseListener{

    /** The main JFrame */
    private JFrame mainFrame = new JFrame("Cook with Mageon");
    private JLayeredPane layeredPane;

    /** The panel for the JFrame */
    private JPanel panel = new JPanel();

    /** Which scene or part of the game they are at */
    private int sceneNum = 3;

    /** The budget or amount of money they receive when going to the grocery store */
    private double budget = 21.4; // average amount of money spent on groceries per 3 days

    private ArrayList<Food> inventory = new ArrayList<>();

    /** Satisfaction they are able to absorb when eating the food. The lower this number is, the smaller */
    private double percentage = 1.0; // 0.7, 1.0, 1.3 satisfaction absorbed

    /** Coordinates for the maze */
    private int xCoord = 7;
    private int yCoord = 0;

    /** The choice the user is picking */
    private JLabel choose;
    private int chooseY;

    private Drawing d;

    private final Maze maze;

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

    /** */
    private String[] dialogueText = {"Hello! I'm MAGEON, your personal cooking assistant. <br> Press any key to continue",
            "At the top left, there will are nutrients, calories, and <br> satisfaction bars. The goal of this game is to fill all of these bars",
            "At the right, there is a fridge. Click on the fridge to open it!",
            "In order to make healthy meals, we must fill the fridge with food. <br>To the grocery store!"};

    private JFrame inventoryFrame = new JFrame("Inventory");
    private Dialogue di = new Dialogue(new String[] {"Now that we're back from the grocery store, we need to store our ingredients! Your fridge can only hold up to 12 distinct foods!", "Press a food item from the left and any space in the fridge. Then, press the 'swap' button!", "Once you're finished, press the 'Done' button. Any food on the left will be discarded afterwards."});
    private ArrayList<Food> invL; // {{0, 1, 2, 3}, {4, 5, 6, 7} ...}
    private Food[][] invR; // {{0, 1, 2, 3}, {4, 5, 6, 7} ...}
    private int[] selectedLeft = new int[2]; // [4, 5] to symbolize 4th column, 5th row is selected 0-th indexed
    private int[] selectedRight = new int[2]; // [4, 5] to symbolize 4th column, 5th row is selected

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
        //mainFrame.setResizable(false); put this in final
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
    public void keyTyped(KeyEvent e) {

    }

    /**
     * keyPressed method, part of the interface, KeyListener class
     * When a key is pressed, it checks the scene it's on, and allows it to be redone.
     *
     * @param e The argument passed from the command line
     */
    public void keyPressed(KeyEvent e) {
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
                panel.removeAll();
                panel.revalidate();
                panel.repaint();
                if (chooseY == 290) { // hard !
                    budget = 17;
                    percentage = 0.7;
                } else if (chooseY == 240) { // med

                } else if (chooseY == 190) { // easy
                    budget = 30;
                    percentage = 1.3;
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
            dialogue.keyReleased(e); // no probem
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            addComponents();
            drawBackground();
        } else if (sceneNum == 3 && message == -1) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (!maze.check(yCoord, xCoord, 0)) {
                    yCoord--;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (!maze.check(yCoord, xCoord, 3)) {
                    xCoord--;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (!maze.check(yCoord, xCoord, 2)) {
                    yCoord++;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
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
                yes.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buying.setVisible(false);
                        sceneNum = 4;
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
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            d.repaint();
            update();
        } else if (sceneNum == 4) {
            di.keyReleased(e);
            panel.removeAll();
            panel.revalidate();
            update();
            panel.repaint();
        }
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
        } else if (sceneNum == 1) {
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
        } else if (sceneNum == 2) {
            panel.setBackground(new Color(0, 0, 0, 0));
            statsbar = new StatsBar();
            dialogue = new Dialogue(dialogueText);
            addComponents();
            drawBackground();
        } else if (sceneNum == 3) {
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
                Food lettuceFood = (new Food("Lettuce", "Pictures/lettuce.png", new int[]{0, 2}, 30, "", 1.97));
                Food whiteFlourFood = (new Food("White Flour", "Pictures/flour(white).png", new int[]{1, 5}, 302, "", 2.60));
                Food multigrainFlourFood = (new Food("Multigrain Flour", "Pictures/flour(multigrain).png", new int[]{6, 7}, 160, "", 2.99));
                Food milkFood = (new Food("Milk", "Pictures/milk.png", new int[]{0, 3, 8}, 150, "", 1.47));
                Food whiteRiceFood = (new Food("Uncooked White Rice", "Pictures/rice(white).png", new int[]{3, 8, 9}, 242, "", 2.87));
                Food multigrainRiceFood = (new Food("Uncooked Wholegrain Rice", "Pictures/rice(multigrain).png", new int[]{3, 7, 8, 9}, 180, "", 3.47));
                Food tomatoFood = (new Food("Tomato", "Pictures/tomato.png", new int[]{2}, 22, "", 1.25));
                Food potatoFood = (new Food("Raw Potato", "Pictures/potato.png", new int[]{2, 10}, 87, "", 1.84));
                Food carrotFood = (new Food("Carrot", "Pictures/carrot.png", new int[]{0, 2}, 22, "", 1.97));
                Food rawEggsFood = (new Food("Raw Eggs", "Pictures/eggs.png", new int[]{11, 3, 12}, 78, "", 1.31));
                Food rawBeefFood = (new Food("Raw Beef", "Pictures/beef.png", new int[]{1, 5}, 217, "", 1.25));
                Food rawFishFood = (new Food("Raw Fish", "Pictures/raw_fish.png", new int[]{1, 3, 12, 13}, 190, "", 2.50));
                Food appleFood = (new Food("Apple", "Pictures/apple.png", new int[]{2, 9}, 95, "", 0.79));
                Food spinachFood = (new Food("Spinach", "Pictures/leafy_greens.png", new int[]{4, 0, 12, 7}, 30, "", 1.80));
                Food butterFood = (new Food("Butter", "Pictures/butter.png", new int[]{10, 3, 11}, 102, "", 1.99));
                Food uncookedCornFood = (new Food("Uncooked Corn", "Pictures/corn.png", new int[]{1, 2, 7}, 177, "", 2.04));
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
                inventoryFrame.add(i.getPanel());
                inventoryFrame.setLocationRelativeTo(null);
                inventoryFrame.setVisible(false);


                inventory.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        inventoryFrame.setVisible(true);
                    }
                });

            /*JLabel groceryStore;
            try {
                groceryStore = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/grocerystore.png")).getScaledInstance(640, 400, Image.SCALE_SMOOTH)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            groceryStore.setBounds(0, 0, 640, 400);
            panel.add(groceryStore);*/
            }
        } else if (sceneNum == 4) {
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
                highlight.setBounds(417 + 80 * selectedRight[0], 35 + 70 * selectedRight[1], 60, 60); // assuming it starts at (35, 90)
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
                public void actionPerformed(ActionEvent e) { // next, can add confirmation later
                    sceneNum = 5;
                    panel.removeAll();
                    panel.revalidate();
                    panel.repaint();

                    update();
                }
            });

            JLabel background;
            try{
                background = (new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/kitchen2.png")))));
            }catch(IOException e){ throw new RuntimeException(e); }
            background.setBounds(0, 0, 643, 405);
            panel.add(background);
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
        }
    }

    public void mousePressed(MouseEvent e) {  }

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

    public void mouseEntered(MouseEvent e) {   }

    public void mouseExited(MouseEvent e) {   }

    class Drawing extends JComponent {
        private ArrayList<Integer> x;
        private ArrayList<Integer> y;
        private ArrayList<Integer> x2;
        private ArrayList<Integer> y2;
        public Drawing() {
            x = new ArrayList<>();
            y = new ArrayList<>();
            x2 = new ArrayList<>();
            y2 = new ArrayList<>();
        }
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

    /** A helper method that draws the background of the JPanel*/
    public void drawBackground(){
        try{
            if(fridgeOpen) {
                background = new JLabel(new ImageIcon(ImageIO.read(kitchenOpen)));
            }else{
                background = new JLabel(new ImageIcon(ImageIO.read(kitchenClose)));
            }
        }catch(IOException e){ throw new RuntimeException(e); }
        background.setBounds(0, 0, 643, 405);
        panel.add(background);
    }
    public void addComponents(){
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
    }
}
