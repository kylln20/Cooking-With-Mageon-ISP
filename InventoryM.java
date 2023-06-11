/**
 * A program to create an Inventory but modified
 *
 * Course Info:
 * ICS4UO
 * Valentina Krasteva
 *
 * @author Kayla Lin & Angelina Jiang
 */

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InventoryM implements MouseListener, KeyListener{

    /** The JPanel */
    private JPanel inventory = new JPanel();

    /** An arraylist of Food objects which will be stored */
    private ArrayList<Food> foods;

    /** How the Food objects wish to be stored */
    private String input;

    /**
     * true = organizing objects 4 by 5 (4 on the x-axis)
     * false = organizing objects 3 by 4
     */
    private boolean bool;

    /**
     * InventoryM constructor
     *
     * Sets the inventory JPanel and other instance varisbles. It also takes in the initial class and converts it to
     * an Arraylist. It also runs the expand method
     *
     * @param initial Food items in terms of an array
     * @param input The display type for the Food items
     * @param bool The display type for the InventoryM
     */
    public InventoryM(Food[][] initial, String input, boolean bool){
        inventory.setLayout(null);
        inventory.setBounds(0, 0, 650, 440);
        inventory.setBackground(new Color(201, 218, 248, 0));
        this.input = input;
        this.bool = bool;
        foods = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (initial != null && initial[i][j] != null) {
                    foods.add(initial[i][j]);
                } else {
                    foods.add(null);
                }
            }
        }
        expand();
    }

    /**
     * InventoryM constructor
     *
     * Sets the inventory JPanel and other instance variables and runs the expand method
     *
     * @param initial Food items in terms of an ArrayList
     * @param input The display type for the Food items
     * @param bool The display type for the InventoryM
     */
    public InventoryM(ArrayList<Food> initial, String input, boolean bool){
        this.bool = bool;
        inventory.setLayout(null);
        inventory.setBackground(new Color(201, 218, 248, 0));
        foods = initial;
        this.input = input;
        expand();
    }

    /**
     * expand constructor
     *
     * Removes everything from the JPanel and adds the Food in the format of what the bool variable states. The
     * food display is then added to the JPanel.
     */
    public void expand(){
        inventory.removeAll();
        inventory.revalidate();
        inventory.repaint();
        if (bool) {
            int x = 0;
            int y = 0;
            if (foods != null) {
                for (Food f : foods) {
                    if (f != null) {
                        f.setDisplayMode(input);
                        f.display().setBounds(63 * x, 70 * y, 60, 60);
                        inventory.add(f.display());
                    }
                    x += 1;
                    if (x == 4) {
                        x = 0;
                        y += 1;
                    }
                }
            }
        } else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    Food f = foods.get(j * 3 + i);
                    if (f != null) {
                        f.setDisplayMode(input);
                        f.display().setBounds(80 * i, 70 * j, 60, 60);
                        inventory.add(f.display());
                    }
                }
            }
        }
    }

    /**
     * getPanel method
     *
     * get method for JPanel, inventory
     *
     * @return inventory
     */
    public JPanel getPanel(){return inventory;}

    /**
     * mousePressed method, part of the interface, MouseListener class
     *
     * @param e The argument passed from the command line
     */
    public void mousePressed(MouseEvent e){}

    /**
     * mouseReleased method, part of the interface, MouseListener class
     *
     * @param e The argument passed from the command line
     */
    public void mouseReleased(MouseEvent e){}

    /**
     * mouseClicked method, part of the interface, MouseListener class
     *
     * @param e The argument passed from the command line
     */
    public void mouseClicked(MouseEvent e){
    }
    /**
     * mouseEntered method, part of the interface, MouseListener class
     *
     * @param e The argument passed from the command line
     */
    public void mouseEntered(MouseEvent e){}

    /**
     * mouseExited method, part of the interface, MouseListener class
     *
     * @param e The argument passed from the command line
     */
    public void mouseExited(MouseEvent e){}

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
    public void keyPressed(KeyEvent e) {}

    /**
     * keyReleased method, part of the interface, KeyListener class
     *
     * @param e The argument passed from the command line
     */
    public void keyReleased(KeyEvent e) {}
}