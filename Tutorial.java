/**
 * A class that creates the learning stage level
 * ICS4UO - Ms Krasteva
 * @version 1
 * @author Kayla Lin
 * Resource used: https://stackoverflow.com/questions/19125707/simplest-way-to-set-image-as-jpanel-background
 */
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;

public class Tutorial implements MouseListener, KeyListener{
    private JFrame frame = new JFrame();
    private JPanel tutorial = new JPanel();
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
    private String[] dialogueText = {"hi", "does", "this", "work"};
    
    
    public Tutorial(){
        frame.setSize(655, 440);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addMouseListener(this);
        frame.addKeyListener(this);
        frame.setLayout(null);
        drawBackground();
        
        statsbar = new StatsBar();
        dialogue = new Dialogue(dialogueText);
        addComponents();
       
        frame.setVisible(true);
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
        frame.setContentPane(background);
    }
    
    public void addComponents(){
        frame.add(statsbar.getStats());
        frame.add(dialogue.getDialogue());
    }
    
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}

    /**
     * Detects whether the mouse was clicked within the region of the fridge
     * and calls the drawBackground() method to change the background accordingly
     */
    public void mouseClicked(MouseEvent e){
        if(e.getX() > 381 && e.getX() < 640){
            if(fridgeOpen){ fridgeOpen = false; }
            else{ fridgeOpen = true; }
            frame.repaint();
            drawBackground();
            addComponents();
            frame.setVisible(true);
        }
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    
    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){}

    /**
     * Detects when the user has released a key, 
     * and calls the method in the object Dialogue to change the text message
     * @param e detects when the user has released a key
     */
    public void keyReleased(KeyEvent e){
      dialogue.keyReleased(e);
      System.out.println("key pressed");
      frame.repaint();
      drawBackground();
      addComponents();
      frame.setVisible(true);
    }

    public JPanel getPanel(){
        return tutorial;
    }

    class Drawing extends JComponent{
        public void paint(Graphics gr){
        }
    }

    public static void main(String[] args){
        new Tutorial();
    }
}