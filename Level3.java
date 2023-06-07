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

public class Level3 implements MouseListener, KeyListener{
    private JPanel panel;
    private ArrayList<Food> inv; // {{0, 1, 2, 3}, {4, 5, 6, 7} ...}
    private int[] selectedLeft = new int[2]; // [4, 5] to symbolize 4th column, 5th row is selected 0-th indexed
    private int[] selectedRight = new int[2]; // [4, 5] to symbolize 4th column, 5th row is selected
    public Level3(ArrayList<Food> inv) {
        selectedLeft[0] = -1; //(null);
        selectedRight[0] = -1; //(null);
        panel = new JPanel();
        panel.setSize(650, 440);
        panel.setLayout(null);
        panel.add();
        JLabel background;
        try{
            background = (new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/kitchen1.png")))));
        }catch(IOException e){ throw new RuntimeException(e); }
        background.setBounds(0, 0, 643, 405);
        this.inv = inv;
        method();
    }
    
    public void method() {
        InventoryM invm = new InventoryM(inv);
        invm.setBounds(30, 50, 650, 440); // the one on the left (inv)
        panel.add(invm);
        
        if (selectedLeft[0] != -1) { // start the highlightt :sobs:
            JPanel highlight = new JPanel();
            highlight.setSize(30, 30);
            highlight.setBackground(255, 0, 0, 50);
            highlight.setBounds(35 + 35 * selectedLeft[0], 90 + 35 * selectedLeft[1], 30, 30); // assuming it starts at (35, 90)
            panel.add(highlight);
        }
        if (selectedRight[0] != -1) {
            JPanel highlight = new JPanel();
            highlight.setSize(30, 30);
            highlight.setBackground(255, 0, 0, 50);
            highlight.setBounds(300 + 35 * selectedRIght[0], 30 + 35 * selectedRight[1], 30, 30); // starts at 300, 30
            panel.add(highlight);
        }
        
    }
    
    public JPanel getPanel() {
        return panel;
    }
    
    public void mouseClicked(MouseEvent e) {   }

    public void mousePressed(MouseEvent e) {  }

    public void mouseReleased(MouseEvent e) {   
        // if sceneNum == 4, hardcode this later ig?
        
    }

    public void mouseEntered(MouseEvent e) {   }

    public void mouseExited(MouseEvent e) {   }
        /**
     * keyTyped method, part of the interface, KeyListener class
     *
     * @param e The argument passed from the command line
     */
    public void keyTyped(KeyEvent e) {   }

    /**
     * keyPressed method, part of the interface, KeyListener class
     * When a key is pressed, it checks the scene it's on, and allows it to be redone.
     *
     * @param e The argument passed from the command line
     */
    public void keyPressed(KeyEvent e) {   }

    /**
     * keyReleased method, part of the interface, KeyListener class
     *
     * @param e The argument passed from the command line
     */
    public void keyReleased(KeyEvent e) {    }
    
    
    public static void main (String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setSize(650, 440);
        mainFrame.addKeyListener(this);
        mainFrame.addMouseListener(this);
        //mainFrame.setResizable(false); put this in final
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(655, 440);
        mainFrame.add(panel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}