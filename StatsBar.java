/**
 * A program to test the stats bar JPanel
 *
 * Course Info:
 * ICS4UO
 * Valentina Krasteva
 *
 * Version 1
 * @author Angelina Jiang and Kayla Lin
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;

public class StatsBar{
    JPanel stats = new JPanel();
    Color border = new Color(249,203,156);
    Color fill = new Color(249,203,156, 230);
    Font defaultFont = new Font(Font.SERIF, Font.PLAIN, 18);

    int numNutri = 0;
    int numCal = 0;
    int numSatis = 0;

    public StatsBar(){
        stats.setLayout(null);
        stats.setBounds(10, 10, 400, 70);
        stats.setBorder(new RoundedBorder(10, new Color(249,203,156)));
        stats.setBackground(fill);

        JLabel nutrients = new JLabel("Nutrients");
        nutrients.setFont(defaultFont);
        nutrients.setBounds(5, 5, 100, 20);
        
        JLabel calories = new JLabel("Calories");
        calories.setFont(defaultFont);
        calories.setBounds(5, 25, 100, 20);
        
        JLabel satisfaction = new JLabel("Satisfaction");
        satisfaction.setFont(defaultFont);
        satisfaction.setBounds(5, 45, 100, 20);
        
        stats.add(nutrients);
        stats.add(calories);
        stats.add(satisfaction);

        Drawing bars = new Drawing();
        bars.setBounds(110, 5, 280, 60);
        stats.add(bars);
    }

    public JPanel getStats(){
        return stats;
    }

    class Drawing extends JComponent{
        public void paint(Graphics gr){
            gr.setColor(Color.LIGHT_GRAY);
            gr.fillRoundRect(0, 5, 280, 15, 10, 10);
            gr.fillRoundRect(0, 25, 280, 15, 10, 10);
            gr.fillRoundRect(0, 45, 280, 15, 10, 10);
            gr.setColor(Color.DARK_GRAY);
            gr.drawRoundRect(0, 5, 280, 15, 10, 10);
            gr.drawRoundRect(0, 25, 280, 15, 10, 10);
            gr.drawRoundRect(0, 45, 280, 15, 10, 10);

            gr.setColor(Color.RED);
            gr.fillRoundRect(1, 6, numNutri*280/100, 13, 10, 10);
            gr.fillRoundRect(1, 26, numCal*280/100, 13, 10, 10);
            gr.fillRoundRect(1, 46, numSatis*280/100, 13, 10, 10);
        }
    }
}