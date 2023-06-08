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

public class EscapeRoom implements KeyListener, MouseListener{
    JFrame frame;
    JLayeredPane mainPane;
    JPanel panel;
    JPanel components;
    JLabel backgroundImg;
    String imgName;
    /** A StatsBar object, displays the nutrition, calories, and satisfaction level of the player */
    private StatsBar statsbar;
    /** A Dialogue object, displays the messages the game helper MAGEON gives to the user */
    private Dialogue dialogue;
    private String[] dialogueText = new String[10];

    
    public EscapeRoom(JFrame frame){
        this.frame = frame;
        
        mainPane = frame.getLayeredPane();

        panel = new JPanel();

        frame.addKeyListener(this);
        frame.addMouseListener(this);
        
        frame.setVisible(true);
        components = new JPanel();
        statsbar = new StatsBar();
        dialogue = new Dialogue(dialogueText);
        
        panel.setLayout(null);
        backgroundImg = new JLabel();
        imgName = "fridge open";
        addComponents();
        drawBackground();
        frame.add(panel);
        frame.setVisible(true);
    }
      
    public void drawBackground(){
        try{
            if(imgName.equals("fridge closed")) {
                backgroundImg = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/kitchen1.png"))));
            }else if(imgName.equals("fridge open")){
                backgroundImg = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/kitchen2.png"))));
            }else if(imgName.equals("cabinet closed")){
                backgroundImg = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/cabinetClose.PNG"))));
            }else if(imgName.equals("cabinet open")){
                backgroundImg = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/cabinetOpen.PNG"))));
            }else if(imgName.equals("stove")){
                backgroundImg = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/stove.PNG"))));
            }

        }catch(IOException e){ throw new RuntimeException(e); }
        backgroundImg.setBounds(0, 0, 640, 400);
        panel.add(backgroundImg);
        mainPane.add(panel, 1);
    }
    
    public void addComponents(){
        components.add(statsbar.getStats());
        components.add(dialogue.getDialogue());
        components.setBackground(new Color(0, 0, 0, 0));
        mainPane.add(components, 2);
    }
    

    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseClicked(MouseEvent e){
         if(imgName.equals("fridge closed") && e.getX() > 381 && e.getX() < 640 && e.getY() > 10 && e.getY() < 290){
            imgName = "fridge open";
         }else if(imgName.equals("fridge open") && e.getX() > 275 && e.getX() < 640 && e.getY() > 10 && e.getY() < 290){
            imgName = "fridge closed";
         }
         frame.repaint();
         frame.getContentPane().removeAll();
         addComponents();

         drawBackground();
         frame.add(panel);
         frame.setVisible(true);
    } 
    
    
    
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}


    public void keyPressed(KeyEvent e){}
    public void keyReleased(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {

        } else if (e.getKeyCode() == KeyEvent.VK_UP) {

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

        } 

    }
    public void keyTyped(KeyEvent e){}


    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setSize(655, 435);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EscapeRoom er = new EscapeRoom(frame);
        frame.setVisible(true);
    }
}
