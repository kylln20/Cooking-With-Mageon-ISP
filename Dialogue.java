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
    JFrame frame = new JFrame();
    JPanel test = new JPanel();
    JPanel dialogue = new JPanel();
    JLabel text;
    JLabel mageon = null;
    Color border = new Color(82,180,218);
    Color fill = new Color(189,235,253);
    String[] words = {"First", "Second", "Third", "Fourth", "Gah"};
    int wordsNum = 0;

    public Dialogue(){
      frame.setSize(655, 435);
      frame.addKeyListener(this);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(null);
      dialogueBox();
      frame.setVisible(true);
    }
      
    public void dialogueBox(){
      dialogue.setLayout(null);
      dialogue.addKeyListener(this);
      dialogue.setBounds(10, 280, 620, 110);
      dialogue.setBorder(new RoundedBorder(10, border));
      dialogue.setBackground(fill);
      
      text = new JLabel(words[wordsNum], JLabel.RIGHT);
      text.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
      text.setBounds(30, 0, 450, 110);
      text.setBackground(new Color(0, 0, 0, 0));
      dialogue.add(text);

      try{
         mageon = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/MAGEON Logo2.png")).getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
      }catch(IOException e){throw new RuntimeException(e);}
      mageon.setBounds(500, 0, 120, 120);
      dialogue.add(mageon);
      frame.add(dialogue);
    }

    
    public void keyTyped(KeyEvent e){}
    
    public void keyPressed(KeyEvent e){}
    
    public void keyReleased(KeyEvent e){
      System.out.println("hi");
      if(wordsNum < words.length-1){
         wordsNum++;
      }else{
         wordsNum = 0;
      }
      text.setText(words[wordsNum]);
    }
    
    public static void main(String[] args) {
        new Dialogue();
    }
}