
/**
 * A program to create a Rounded Border
 *
 * Course Info:
 * ICS4UO
 * Valentina Krasteva
 *
 * @author Angelina Jiang
 */

import javax.swing.border.Border;
import java.awt.*;

public class RoundedBorder implements Border {

    /** The radius of the curve-ness */
    private int radius;

    /** The colour of the button/panel/component */
    private Color fill;

    /** The colour of the border */
    private Color background;

    /** Any possible text for JButtons */
    private String text;

    /** The size of the text */
    private int size;

    /** the xCoordinate of the text */
    private int xCoord;

    /** The yCoordinate of the text */
    private int yCoord;

    /**
     * RoundedBorder constructor
     *
     * Initializes all instance variables
     *
     * @param radius The radius
     * @param fill The fill colour
     * @param background The border colour
     * @param text The text
     * @param size The size of text
     * @param xCoord The x-Coordinate of text
     * @param yCoord The y-Coordinate of text
     */
    RoundedBorder(int radius, Color fill, Color background, String text, int size, int xCoord, int yCoord) {
        this.radius = radius;
        this.fill = fill;
        this.background = background;
        this.text = text;
        this.size = size;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    /**
     * getBorderInsets method, part of the interface, Border class
     *
     * @param c The argument passed from the command line
     * @return an Insets
     */
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }

    /**
     * isBorderOpaque method
     *
     * @return whether or not the border is opaque
     */
    public boolean isBorderOpaque() {
        return true;
    }

    /**
     *  paintBorder method, part of the interface, Border class
     *
     * @param c The component
     * @param g The graphics to draw for the Border class
     * @param x
     * @param y
     * @param width The width of the component
     * @param height The height of the component
     */
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
       drawRoundRect(g, width-1, height-1, radius);
    }

    /**
     * drawRoundRect method
     *
     * A helper method designed to draw four quarter-circles and 3 rectangles to create a rounded rectangle
     *
     * @param g The graphics given
     * @param width The width of the component
     * @param height The height of the component
     * @param radius The radius of the rounded-ness
     */
    public Graphics drawRoundRect(Graphics g, int width, int height, int radius) {
        g.setColor(this.fill);
        g.fillArc(0, 0, radius * 2, radius * 2, 90, 90);
        g.fillArc(width - 2 * radius, 0, radius * 2, radius * 2, 0, 90);
        g.fillArc(width - 2 * radius, height - 2 * radius, radius * 2, radius * 2, -90, 90);
        g.fillArc(0, height - 2 * radius, radius * 2, radius * 2, 270, -90);
        g.fillRect(radius, 0, width - 2 * radius, radius);
        g.fillRect(radius, height - radius, width - 2 * radius, radius);
        g.fillRect(0, radius, width, height - 2 * radius);
        g.setColor(this.background);
        g.drawArc(0, 0, radius * 2, radius * 2, 90, 90);
        g.drawArc(width - 2 * radius, 0, radius * 2, radius * 2, 0, 90);
        g.drawArc(width - 2 * radius, height - 2 * radius, radius * 2, radius * 2, -90, 90);
        g.drawArc(0, height - 2 * radius, radius * 2, radius * 2, 270, -90);
        g.drawLine(radius, 0, width - radius, 0);
        g.drawLine(radius, height, width - radius, height);
        g.drawLine(0, radius, 0, height - radius);
        g.drawLine(width, radius, width, height - radius);
        Graphics2D newGraphics = (Graphics2D) g;
        newGraphics.setColor(Color.BLACK);
        newGraphics.setFont(new Font(Font.SERIF, Font.PLAIN,  size));
        newGraphics.drawString(text, xCoord, yCoord);
        return g;
    }
}