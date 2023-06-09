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

public class StatsBar{
    public int getNumNutri() {
        int count = 0;
        for (int i = 0; i < 14; i++) {
            if (nutrients[i]) count++;
        }
        numNutri = (int) (count / 14.0 * 10);
        return numNutri;
    }

    public int getNumCal() {
        return numCal;
    }

    public int getNumSatis() {
        return numSatis;
    }

    JPanel stats = new JPanel();
    Color border = new Color(221, 50, 87);
    Color fill = new Color(238, 148, 128, 180);
    Font defaultFont = new Font(Font.SERIF, Font.PLAIN, 18);
    boolean[] nutrients = new boolean[14];

    int numNutri = 0;
    int numCal = 0;
    int numSatis = 0;

    public StatsBar(){
        stats.setLayout(null);
        stats.setBounds(10, 10, 400, 70);
        stats.setBorder(new RoundedBorder(10, fill, border, "", 0, 0, 0));
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
    public void addNutri(boolean[] x) {
        for (int i = 0; i < 14; i++) {
            if (x[i]) nutrients[i] = true;
        }
    }

    public void addCalo (int x) {
        numCal = Math.max(100, numCal + x);
    }

    public void addSatis() {
        numSatis = Math.max(100, numSatis + 80);
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
            int count = 0;
            for (int i = 0; i < 14; i++) {
                if (nutrients[i]) count++;
            }
            numNutri = (int) (count / 14.0 * 10);
            gr.fillRoundRect(1, 6, numNutri*280/100, 13, 10, 10);
            gr.fillRoundRect(1, 26, numCal*280/100, 13, 10, 10);
            gr.fillRoundRect(1, 46, numSatis*280/100, 13, 10, 10);
        }
    }
}