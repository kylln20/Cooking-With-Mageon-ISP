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
    private JPanel inventory = new JPanel();
    private ArrayList<Food> foods;

    public Inventory(int x1, int y1, int x2, int y2){
        inventory.setLayout(null);
        inventory.setBounds(x1, y1, x2, y2);
        inventory.setBackground(new Color(201, 218, 248));
        foods = new ArrayList<Food>();
        expand();
    }

    public Inventory(ArrayList<Food> initial){
        inventory.setLayout(null);
        inventory.setBackground(new Color(201, 218, 248));
        foods = initial;
        expand();
    }

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
        Food lettuce = (new Food("Lettuce", "Pictures/lettuce.png", new int[]{0, 2}, 30, "", 1.97));
        Food apple = new Food("Multigrain Flour", "Pictures/flour(multigrain).png", new int[]{}, 25, "im+n+q", 0.14);
        Food pr = new Food("Multigrain Flour", "Pictures/flour(multigrain).png", new int[]{}, 25, "im+n+q", 0.14);
        Food po = new Food("Multigrain Flour", "Pictures/flour(multigrain).png", new int[]{}, 25, "im+n+q", 0.14);

        Inventory i = new Inventory(0, 0, 640, 300);
        i.addFood(lettuce);
        i.addFood(apple);
        i.addFood(pr);
        i.addFood(po);
        test.add(i.getPanel());
        test.setVisible(true);
    }
}