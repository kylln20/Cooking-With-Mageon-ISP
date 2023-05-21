/**
 * A program to test the dialogue box JPanel
 *
 * Course Info:
 * ICS4UO
 * Valentina Krasteva
 *
 * Version 1
 * @author Angelina Jiang and Kayla Lin (2.5hrs)
 */

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Dialogue implements KeyListener{
    /** */
    JFrame frame = new JFrame("test");
    JPanel dialogue = new JPanel();
    Color border = new Color(82,180,218);
    Color fill = new Color(191,237,254, 90);
    String[] words = {"First", "Second", "Third", "Fourth", "Gah"};
    int wordsNum = 0;

    public Dialogue(){
      frame.setSize(660, 420);
      frame.addKeyListener(this);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(null);
      dialogueBox();
      frame.setVisible(true);
    }
      
    public void dialogueBox(){
      dialogue.setLayout(null);
      dialogue.addKeyListener(this);
      dialogue.setBounds(10, 250, 620, 140);
      dialogue.setBorder(new RoundedBorder(10, border));
      dialogue.setBackground(fill);

      JLabel text = new JLabel(words[wordsNum], JLabel.RIGHT);
      text.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
      text.setBounds(30, 10, 450, 120);
      dialogue.add(text);

      JLabel mageon = null;
      try{
         mageon = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/MAGEON Logo2.png")).getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
      }catch(IOException e){throw new RuntimeException(e);}
      mageon.setBounds(500, 10, 120, 120);
      dialogue.add(mageon);
      frame.add(dialogue);
    }

    
    public void keyTyped(KeyEvent e){
      if(e.getKeyCode() == KeyEvent.VK_SPACE){
         System.out.println("hi");
         if(wordsNum < words.length-1){
            wordsNum++;
         }else{
            wordsNum = 0;
         }
         dialogue.removeAll();
         dialogue.revalidate();
         dialogue.repaint();
         dialogueBox();
      }
      frame.repaint();
    }
    
    public void keyPressed(KeyEvent e){}
    
    public void keyReleased(KeyEvent e){}
    
    public static void main(String[] args) {
        new Dialogue();
    }
}