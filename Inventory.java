/**
 * A program to test the Inventory JPanel
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

public class Inventory implements MouseListener{

    /** The JPanel */
    private JPanel inventory = new JPanel();

    /** An arraylist of Food objects which will be stored */
    private ArrayList<Food> foods;

    /**
     * Inventory constructor
     *
     * Sets the inventory JPanel and other instance variables. It also calls the expand class
     *
     * @param initial Food items in terms of an array
     */
    public Inventory(ArrayList<Food> initial){
        inventory.setLayout(null);
        inventory.setBackground(new Color(201, 218, 248));
        foods = initial;
        expand();
    }

    /**
     * expand method
     *
     * redoes inventory and goes through the current inventory to add the Food
     */
    public void expand(){
        inventory.removeAll();
        inventory.revalidate();
        inventory.repaint();
        JLabel inv = new JLabel("Inventory");
        inv.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        inv.setBounds(250, 0, 610, 50);
        inventory.add(inv);
        int x = 0;
        int y = 0;
        for(Food f: foods){
            f.setDisplayMode("im+n+q");
            f.display().setBounds(30 + 135 * x, 50 + 95 * y, 130, 90);
            inventory.add(f.display());
            x += 1;
            if (x == 4) {
                x = 0;
                y += 1;
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
}