/**
 * A program to create a StatsBar for CookWithMageon using Java Swing and GUI
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

    /** The main JPanel */
    JPanel stats = new JPanel();

    /** The border colour */
    Color border = new Color(221, 50, 87);

    /** The fill colour */
    Color fill = new Color(238, 148, 128, 180);

    /** The default font */
    Font defaultFont = new Font(Font.SERIF, Font.PLAIN, 18);

    /** Whether the i-th nutrient has been obtained today */
    boolean[] nutrients = new boolean[14];

    /** percentage of nutrients obtained */
    int numNutri = 0;

    /** number of calories obtained */
    int numCal = 0;

    /** amount of satisfaction obtained*/
    int numSatis = 0;

    /**
     * StatsBar constructor
     * Sets the default background with border, as well as adding the nutrients, calories and satisfaction.
     */
    public StatsBar(){
        stats.setLayout(null);
        stats.setBounds(10, 10, 400, 70);
        stats.setBorder(new RoundedBorder(10, fill, border, "", 0, 0, 0));
        stats.setBackground(new Color(0, 0, 0, 0));

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

    /**
     * addNutri method
     *
     * goes through the nutrients absorbed and checks them off
     *
     * @param x the list of nutrients and whether or not the food eaten contains them
     */
    public void addNutri(boolean[] x) {
        for (int i = 0; i < 14; i++) {
            if (x[i]) nutrients[i] = true;
        }
    }

    /**
     * addCalo method
     *
     * add calories to the stats bar
     *
     * @param x the number of calories the user has eaten
     */
    public void addCalo (int x) {
        numCal = Math.max(100, numCal + x);
    }

    /**
     * addSatis method
     *
     * adds to the current satisfaction
     */
    public void addSatis() {
        numSatis = Math.min(100, numSatis + 80);
    }

    /**
     * getStats method
     *
     * @return stats The JPanel
     */
    public JPanel getStats(){
        return stats;
    }

    public void reset() {
        numSatis = 0;
        nutrients = new boolean[14];
        numCal = 0;
        numNutri = 0;
    }

    /**
     * Drawing class
     *
     * Creates the rounded rectangles to give a visual representation of the nutrients, satisfaction, and calories
     */
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
            numNutri = (int) (count / 14.0 * 100);
            int percentage = (int) (numCal / 1700.0 * 100);
            gr.fillRoundRect(1, 6, numNutri*280/100, 13, 10, 10);
            gr.fillRoundRect(1, 26, percentage*280/100, 13, 10, 10);
            gr.fillRoundRect(1, 46, numSatis*280/100, 13, 10, 10);
        }
    }
}