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

public class CookWithMageon implements KeyListener {

    /** The main JFrame */
    private JFrame mainFrame = new JFrame("Cook with Mageon");
    private JLayeredPane layeredPane;

    /** The panel for the JFrame */
    private JPanel panel = new JPanel();

    /** Which scene or part of the game they are at */
    private int sceneNum = 0;

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
        //System.out.println("key pressed");
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
        } else if (sceneNum == 5 && message == -1) {
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

            }
            if (xCoord == 3 && yCoord == 0) { // lettuce
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
        }
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
        if (sceneNum == 2) sceneNum = 5;
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
        } else if (sceneNum == 3) {

        } else if (sceneNum == 4) {

        } else if (sceneNum == 5) { // grocery shop
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
            Food rawFishFood = (new Food("Raw Fish", "Pictures/fish.png", new int[]{1, 3, 12, 13}, 190, "", 2.50));
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
            layeredPane.add(uncookedCorn);
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
            if (message != -1) {
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
                            message = -1;
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

            JPanel inventoryPanel = new JPanel();
            inventoryPanel.setBackground(new Color(201, 218, 248));
            inventoryPanel.setLayout(new GridLayout(4, 0));
            for (Food f : this.inventory) {
                f.setDisplayMode("im+n+q");
            }

            JFrame inventoryFrame = new JFrame();
            inventoryFrame.setResizable(false);
            inventoryFrame.setSize(655, 440);
            inventoryFrame.add(inventoryPanel);
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
            if (sceneNum == 5) { // change later
                Graphics2D g2 = (Graphics2D) g;
                for (int i = 0; i < x.size(); i++) {
                    g2.setStroke(new BasicStroke(2));
                    g2.drawLine(x.get(i), y.get(i), x2.get(i), y2.get(i));
                }
                g2.fillOval(80 + (30 * xCoord) + 10, 70 + (30 * yCoord) + 10, 10, 10);
            }
        }
    }
}
