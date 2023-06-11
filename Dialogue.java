/**
 * A program to test the dialogue box JPanel
 *
 * Course Info:
 * ICS4UO
 * Valentina Krasteva
 *
 * Version 2
 * @author Angelina Jiang and Kayla Lin 
 * Source used: https://stackoverflow.com/questions/26420428/how-to-word-wrap-text-in-jlabel
 * ^ how to wrap text in JLabel
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Dialogue implements KeyListener{
    /** The JPanel containing the MAGEON and the text they're saying say */
    JPanel dialogue = new JPanel();
    /** The JLabel containing MAGEON's message*/
    JLabel text;
    /** The JLabel containing a picture*/
    JLabel im = null;
    /** The colour of the border of the dialogue box */
    Color border = new Color(82,180,218);
    /** The colour of the dialogue box */
    Color fill = new Color(189,235,253, 230);
    /** The messages MAGEON will say */
    String[] words;

    /** The message that MAGEON is on */
    int wordsNum = 0;

    /**
     * The Dialogue constructor
     * Size, location, and colour is set for the dialogue JPanel
     * Font is set for the text JLabel
     * The MAGEON image is added to the icon JLabel
     * The dialogue JPanel is added to the JFrame
     * @param words the scene that the dialogue box is being added to // fix
     */
    public Dialogue(String[] words){
        dialogue.setLayout(null);
        dialogue.addKeyListener(this);
        dialogue.setBounds(5, 300, 630, 90);
        dialogue.setBorder(new RoundedBorder(10, fill, border, "", 0, 0, 0));
        dialogue.setBackground(new Color(0, 0, 0, 0));

        this.words = words;
        text = new JLabel("<html>" + words[wordsNum] + "</html>");
        text.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        text.setBounds(30, 0, 450, 90);
        text.setBackground(new Color(0, 0, 0, 0));
        dialogue.add(text);

        try{
            im = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/MAGEON Logo2.png")).getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
        }catch(IOException e){throw new RuntimeException(e);}
        im.setBounds(500, 10, 70, 70);
        dialogue.add(im);
    }

    /**
     * lastWord method
     *
     * @return whether or not this is the last dialogue sentence Mageon says
     */
    public boolean lastWord(){
        return (wordsNum == words.length-1);
    }

    /**
     * getDialogue method
     *
     * @return dialogue The JPanel
     */
    public JPanel getDialogue(){
        return dialogue;
    }

    /**
     * keyTyped method, part of the interface, KeyListener class
     *
     * @param e The argument passed from the command line
     */
    public void keyTyped(KeyEvent e) {}

    /**
     * keyPressed method, part of the interface, KeyListener class
     * When a key is pressed, it checks the scene it's on, and allows it to be redone.
     *
     * @param e The argument passed from the command line
     */
    public void keyPressed(KeyEvent e) {}

    /**
     * Detects when the user has released a key, and increases the variable wordsNum to change the message
     * @param e detects when the user has released a key
     */
    public void keyReleased(KeyEvent e){
        if(wordsNum == words.length-1) {
            dialogue.setVisible(false);
            dialogue.repaint();
        }
        if(!lastWord()){ wordsNum++;}
        text.setText("<html>" + words[wordsNum] + "</html>");
    }
}