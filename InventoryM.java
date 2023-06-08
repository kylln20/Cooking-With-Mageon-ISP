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

public class InventoryM implements MouseListener, KeyListener{
    private JPanel inventory = new JPanel();
    private ArrayList<Food> foods;
    private String input;
    private boolean bool;

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

    public InventoryM(ArrayList<Food> initial, String input, boolean bool){
        this.bool = bool;
        inventory.setLayout(null);
        inventory.setBackground(new Color(201, 218, 248, 0));
        foods = initial;
        this.input = input;
        expand();
    }

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

    public void addFood(Food addition){
        foods.add(addition);
        expand();
    }

    public JPanel getPanel(){return inventory;}

    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseClicked(MouseEvent e){
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}

    public static void main(String[] args){
        JFrame test = new JFrame();
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        test.setSize(610, 300);
        test.setLayout(null);
        Food[][] invR = new Food[4][3]; // player can store up to 12 different kind of foods
        invR[0][0] = (new Food("Raw Fish", "Pictures/raw_fish.png", new int[]{1, 3, 12, 13}, 190, "", 2.50));
        invR[0][1] = (new Food("Apple", "Pictures/apple.png", new int[]{2, 9}, 95, "", 0.79));
        invR[0][2] = (new Food("Spinach", "Pictures/leafy_greens.png", new int[]{4, 0, 12, 7}, 30, "", 1.80));
        invR[1][0] = (new Food("Butter", "Pictures/butter.png", new int[]{10, 3, 11}, 102, "", 1.99));
        invR[1][1] = (new Food("Uncooked Corn", "Pictures/corn.png", new int[]{1, 2, 7}, 177, "", 2.04));
        InventoryM invm = new InventoryM(invR, "s", false);
        invm.getPanel().setBounds(0, 0, 650, 440);
        test.add(invm.getPanel());
        test.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}