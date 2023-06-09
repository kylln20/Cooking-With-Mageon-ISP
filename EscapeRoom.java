/**
 * This class is temporarily used to make level 3 of Cooking With Mageon (the escape room)
 *
 * Course Info:
 * ICS4UO Ms Krasteva
 *
 * @version 1
 * @authors Kayla Lin + Angelina Jiang
 */

 import java.util.*;
 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.*;
 import java.io.*;
 import javax.imageio.ImageIO;

public class EscapeRoom implements KeyListener, MouseListener {
    JFrame frame;
    JPanel panel;
    JLabel backgroundImg;
    String imgName;
    /**
     * A StatsBar object, displays the nutrition, calories, and satisfaction level of the player
     */
    private StatsBar statsbar;
    /**
     * A Dialogue object, displays the messages the game helper MAGEON gives to the user
     */
    private Dialogue dialogue;
    private String[] dialogueText = {"Now that we're back from the grocery store, we need to store our ingredients! Your fridge can only hold up to 12 distinct foods!", "Press a food item from the left and any space in the fridge. Then, press the 'swap' button!", "Once you're finished, press the 'Done' button. Any food on the left will be discarded afterwards.",
            "Ding ding ding. Oh look, its time to start cooking! Let's make a nice healthy meal!", "Press the arrow keys or WASD to move around the kitchen"};
    Color transparent = new Color(0, 0, 0, 0);
    int x = 320;
    int y = 160;
    JLabel personImg;
    JPanel person;
    Food[][] fridge;
    boolean[][] selected = new boolean[4][3];
    JPanel problem;
    int probGuess;
    boolean correctAns = true;


    public EscapeRoom(JFrame frame, Food[][] fridge) {
        this.fridge = fridge;
        this.frame = frame;
        frame.addKeyListener(this);
        frame.addMouseListener(this);
        frame.setBackground(Color.PINK);

        frame.getContentPane().setBackground(transparent); // why

        panel = new JPanel();
        panel.setBackground(transparent);
        panel.setLayout(null);
        backgroundImg = new JLabel();
        imgName = "fridge open";

        statsbar = new StatsBar();
        dialogue = new Dialogue(dialogueText);
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
        problem.setBackground(new Color(255, 255, 255, 80));
        problem.setVisible(false);

        addComponents();
        drawPerson(x, y);
        drawBackground();

        frame.add(panel);
        frame.setVisible(true);
    }

    public void drawBackground() {
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

    public void addComponents() {
        panel.add(statsbar.getStats());
        if (!imgName.contains("cabinet")) {
            statsbar.getStats().setVisible(true);
        }
        if (!imgName.equals("stove")) {
            panel.add(dialogue.getDialogue());
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

    public void drawPerson(int x, int y) {
        if (imgName.equals("fridge closed") || imgName.equals("fridge open")) {
            personImg.setBounds(0, 0, 160, 240);
            person.setBounds(x, y, 160, 240);
            panel.add(person);
        }
    }

    public void giveProblem(String name, int calories, boolean[] nutrients) {
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
        JTextField answer = new JTextField(10);
        answer.addKeyListener(this);
        answer.setBounds(200, 50, 140, 50);
        answer.setEditable(true);
        problem.add(answer);
        answer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    probGuess = Integer.parseInt(answer.getText().trim());
                } catch (NumberFormatException ne) {
                    probGuess = 0;
                }
            }
        });
        JLabel result = new JLabel("");
        result.setBounds(50, 150, 300, 150);

        JButton submit = new JButton("Submit");
        JButton giveup = new JButton("Give Up");
        submit.setBounds(50, 200, 100, 50);
        giveup.setBounds(250, 200, 100, 50);
        giveup.setFocusable(false);
        submit.setFocusable(false);

        submit.setSize(200, 50);
        submit.setBounds(50, 200, 100, 50);
        submit.setBackground(transparent);
        submit.setOpaque(false);
        submit.setBorder(new RoundedBorder(20, new Color(201, 218, 248), new Color(201, 218, 248), "Submit", 20, 27, 29));

        problem.add(submit);
        problem.add(giveup);
        problem.add(result);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit.setVisible(false);
                giveup.setVisible(false);
                equation.setVisible(false);
                //answer.setVisible(false);

                try {
                    probGuess = Integer.parseInt(answer.getText().trim());
                } catch (NumberFormatException ne) {
                    probGuess = 0;
                }
                //result = new JLabel(); - result is the reason why everything is wonker
                if (probGuess == (a + b)) {
                    //answer.setVisible(false);
                    result.setText("<html>Congrats, you got it correct! Your food is now cooked! Press enter to continue.</html>");
                    // if correctAns was true, it is still true. If it was false, then it is still false (it means they got the recipe wrong)
                    statsbar.addCalo(calories); // updating stats ...
                    statsbar.addSatis();
                    statsbar.addNutri(nutrients);
                } else {
                    //answer.setVisible(false);
                    result.setText("<html>You got it wrong. Now, your food goes to the trash. Press enter to continue.</html>");
                    correctAns = false;
                }
                imgName = "problemB";
            }
        });

        giveup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit.setVisible(false);
                giveup.setVisible(false);
                answer.setVisible(false);
                equation.setVisible(false);
                problem.removeAll();
                problem.revalidate();
                problem.repaint();

                problem.add(result);
                result.setText("<html>You gave up. Your selected food goes to the trash</html>");
                correctAns = false;
                imgName = "problemB";
            }
        });
        problem.setVisible(true);
        panel.add(problem);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
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
            } else if (e.getX() > 275 && e.getX() < 640 && e.getY() > 10 && e.getY() < 290) {
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

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }


    public void keyPressed(KeyEvent e) {
        //System.out.println("key pressed");
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

    public void keyReleased(KeyEvent e) {
        if (imgName.equals("fridge closed") || imgName.equals("fridge open")) {
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                if (x <= 175) { // stove
                    imgName = "stove";
                    dialogue.getDialogue().setVisible(false);
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
            }
            dialogue.keyReleased(e);
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            addComponents();
            drawPerson(x, y);
            drawBackground();
        }
    }

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
                if (correctAns) {
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
                }
                addComponents();
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
                        }
                    }
                }
                Recipes recipe = new Recipes("", select);
                CookBook cb = new CookBook();
                String name = "";
                int calories = 0;
                boolean[] nutrients = new boolean[14];
                for (Recipes r : cb.getCookbook()) {
                    if (recipe.equals(r)) {
                        name = r.getName();
                        for (Food f : r.getRecipe()) {
                            calories += f.calories();
                            for (int i = 0; i < f.nutrients().length; i++) {
                                nutrients[f.nutrients()[i]] = true;
                            }
                        }
                    }
                }
                giveProblem(name, calories, nutrients);
                addComponents();
                drawBackground();
            }
        });
        finishMeal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // sceneNum = 6;
                panel.removeAll();
                panel.revalidate();
                panel.repaint();
                JLabel label = new JLabel("A meal has finished!");
                label.setBounds(0, 0, 300, 300); // we deal with later
                panel.add(label);
            }
        });
    }



    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(655, 435);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Food[][] ar = new Food[4][3];

        ar[0][0] = (new Food("Potato", "Pictures/lettuce.png", new int[]{0, 2}, 30, "", 1.97));
        ar[0][1] = (new Food("White Flour", "Pictures/flour(white).png", new int[]{1, 5}, 302, "", 2.60));
        ar[0][2] = (new Food("Multigrain Flour", "Pictures/flour(multigrain).png", new int[]{6, 7}, 160, "", 2.99));
        ar[1][0] = (new Food("Raw Eggs", "Pictures/eggs.png", new int[]{0, 3, 8}, 150, "", 1.47));
        ar[3][2] = (new Food("Uncooked White Rice", "Pictures/rice(white).png", new int[]{3, 8, 9}, 242, "", 2.87));

        EscapeRoom er = new EscapeRoom(frame, ar);
        frame.setVisible(true);
    }
}