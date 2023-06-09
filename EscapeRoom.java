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
    JLabel backgroundImg;
    String imgName;
    /** A StatsBar object, displays the nutrition, calories, and satisfaction level of the player */
    private StatsBar statsbar;
    /** A Dialogue object, displays the messages the game helper MAGEON gives to the user */
    private Dialogue dialogue;
    private String[] dialogueText = {"one", "two", "three", "four", "five", "six", "seven"};
    Color transparent = new Color(0, 0, 0, 0);
    int x = 320;
    int y = 160;
    JLabel personImg;
    JPanel person;
    
    public EscapeRoom(JFrame frame){
        this.frame = frame;
        frame.addKeyListener(this);
        frame.addMouseListener(this);
        frame.setBackground(Color.PINK);
        
        frame.getContentPane().setBackground(transparent);
        mainPane = frame.getLayeredPane();
        
        panel = new JPanel();
        panel.setBackground(transparent);
        panel.setLayout(null);
        backgroundImg = new JLabel();
        imgName = "fridge open";

        statsbar = new StatsBar();
        dialogue = new Dialogue(dialogueText);
        try{
            personImg = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/person.png")).getScaledInstance(160, 240, Image.SCALE_SMOOTH)));
        }catch(IOException e){throw new RuntimeException(e);}
        personImg.setBounds(0, 0, 160, 240);
        person = new JPanel();
        person.setBackground(transparent);
        person.setLayout(null);
        person.add(personImg);

        addComponents();
        drawPerson(x, y);
        drawBackground();

        mainPane.add(panel, JLayeredPane.DEFAULT_LAYER);
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
    }
    
    public void addComponents(){
        panel.add(statsbar.getStats());
        panel.add(dialogue.getDialogue());
    }
    
    public void drawPerson(int x, int y){
        personImg.setBounds(0, 0, 160, 240);
        person.setBounds(x, y, 160, 240);
        panel.add(person);
    } 

    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseClicked(MouseEvent e){
        if(imgName.equals("fridge closed") && e.getX() > 381 && e.getX() < 640 && e.getY() > 10 && e.getY() < 290){
        imgName = "fridge open";
        }else if(imgName.equals("fridge open") && e.getX() > 275 && e.getX() < 640 && e.getY() > 10 && e.getY() < 290){
        imgName = "fridge closed";
        }
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
        addComponents();
        drawPerson(x, y);
        drawBackground();
    } 
    
    
    
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}


    public void keyPressed(KeyEvent e){}
    public void keyReleased(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(x < 460){
                x+=5;
            }
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            addComponents();
            drawPerson(x, y);
            drawBackground();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(x > 160){
                x-=5;
            }
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            addComponents();
            drawPerson(x, y);
            drawBackground();

        } else if (e.getKeyCode() == KeyEvent.VK_UP) {

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

        } else if(e.getKeyCode() == KeyEvent.VK_ENTER){
            dialogue.keyReleased(e);
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            addComponents();
            drawBackground();
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