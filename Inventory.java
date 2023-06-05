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
import java.io.*;
import javax.imageio.ImageIO;

public class Inventory implements MouseListener{
    private JPanel inventory = new JPanel();
    private JButton condensedInv;
    private ArrayList<Food> foods;
    private ArrayList<Food> clicked;

    public Inventory(int x1, int y1, int x2, int y2){
        inventory.setLayout(null);
        inventory.setBounds(x1, y1, x2, y2);
        inventory.setBackground(Color.PINK);

        try{
            condensedInv = new JButton(new ImageIcon(ImageIO.read(new File("/Pictures/grocery.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        }catch(IOException e){
            condensedInv = new JButton("inventory");
            condensedInv.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        }

        foods = new ArrayList<Food>();
        clicked = new ArrayList<Food>();
        condense();
    }

    public Inventory(ArrayList<Food> initial){
        inventory.setVisible(false);
        condensedInv = null;
        foods = initial;
        clicked = new ArrayList<Food>();
    }

    public void condense(){
        inventory.repaint();
        inventory.add(condensedInv);
        
        inventory.setVisible(true);
    }

    public void expand(){
        inventory.repaint();
        inventory.removeAll();
        int x = 0; 
        int y = 0;
        int w = 0;
        int l = 0;
        for(Food f: foods){
            f.display().setBounds(x, y, x+f.display().getWidth(), y+f.display().getHeight());
            inventory.add(f.display());
            x += f.display().getWidth();
            y += f.display().getHeight();
        }
        inventory.setVisible(true);
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
        test.setSize(500, 500);
        test.setLayout(null);

        Food lettuce = new Food("lettuce", "Pictures/lettuce.png", new int[]{}, 25, "im+n+q", 0.14);
        Food apple = new Food("apple", "Pictures/apple.png", new int[]{}, 25, "im+n+q", 0.14);

        Inventory i = new Inventory(0, 0, 300, 300);
        i.addFood(lettuce);
        i.addFood(apple);
        test.add(i.getPanel());
        test.setVisible(true);
    }
}