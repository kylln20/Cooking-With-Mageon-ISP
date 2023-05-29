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

    /** Coordinates for the maze */
    private int xCoord = 7;
    private int yCoord = 0;

    /** The choice the user is picking */
    private JLabel choose;
    private int chooseY;

    private Drawing d;

    private Maze maze;

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
        } else if (sceneNum == 5) {
            System.out.println(maze.check(yCoord, xCoord, 0));
            System.out.println(maze.check(yCoord, xCoord, 1));
            System.out.println(maze.check(yCoord, xCoord, 2));
            System.out.println(maze.check(yCoord, xCoord, 3));
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
            JPanel lettuce = (new Food("Lettuce", "Pictures/lettuce.png", new int[]{0, 2}, 30, "", 1.97)).display();
            JPanel whiteFlour = (new Food("White Flour", "Pictures/flour(white).png", new int[]{1, 5}, 302, "", 2.60)).display();
            JPanel multigrainFlour = (new Food("Multigrain Flour", "Pictures/flour(multigrain).png", new int[]{6, 7}, 160, "", 2.99)).display();
            JPanel milk = (new Food("Milk", "Pictures/milk.png", new int[]{0, 3, 8}, 150, "", 1.47)).display();
            JPanel cookedRice = (new Food("Raw White Rice", "Pictures/rice(white).png", new int[]{3, 8, 9}, 242, "", 2.87)).display();
            JPanel healthyCookedRice = (new Food("Raw Wholegrain Rice", "Pictures/rice(multigrain).png", new int[]{3, 7, 8, 9}, 180, "", 3.47)).display();
            JPanel tomato = (new Food("Tomato", "Pictures/tomato.png", new int[]{2}, 22, "", 1.25)).display();
            JPanel potato = (new Food("Raw Potato", "Pictures/potato.png", new int[]{2, 10}, 87, "", 1.84)).display();
            JPanel carrot = (new Food("Carrot", "Pictures/carrot.png", new int[]{0, 2}, 22, "", 1.97)).display();
            JPanel eggs = (new Food("Raw Eggs", "Pictures/eggs.png", new int[]{11, 3, 12}, 78, "", 1.31)).display();
            JPanel beef = (new Food("Raw Beef", "Pictures/beef.png", new int[]{1, 5}, 217, "", 1.25)).display();
            JPanel fish = (new Food("Raw Fish", "Pictures/fish.png", new int[]{1, 3, 12, 13}, 190, "", 2.50)).display();
            JPanel apple = (new Food("Apple", "Pictures/apple.png", new int[]{2, 9}, 95, "", 0.79)).display();
            JPanel spinach = (new Food("Spinach", "Pictures/leafy_greens.png", new int[]{4, 0, 12, 7}, 30, "", 1.80)).display();
            JPanel butter = (new Food("Butter", "Pictures/butter.png", new int[]{10, 3, 11}, 102, "", 1.99)).display();
            JPanel corn = (new Food("Raw Corn", "Pictures/corn.png", new int[]{1, 2, 7}, 177, "", 2.04)).display();
            JPanel cheese = (new Food("Cheese", "Pictures/cheese.png", new int[]{13, 0, 10, 8}, 100, "", 1.77)).display();
            lettuce.setBounds(80 + (30 * 3), 70, 30, 30);
            panel.add(lettuce);
            panel.add(whiteFlour);
            panel.add(multigrainFlour);
            panel.add(milk);
            panel.add(cookedRice);
            panel.add(healthyCookedRice);
            panel.add(tomato);
            panel.add(potato);
            panel.add(carrot);
            panel.add(eggs);
            panel.add(beef);
            panel.add(fish);
            panel.add(apple);
            panel.add(spinach);
            panel.add(butter);
            panel.add(corn);
            panel.add(cheese);

            JLabel budget = new JLabel("Budget: $" + this.budget + " (CAD)");
            budget.setFont(new Font(Font.SERIF, Font.PLAIN,  14));
            budget.setBounds(20, 0, 300, 40);
            panel.add(budget);
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
