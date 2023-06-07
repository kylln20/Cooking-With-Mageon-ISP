import javax.swing.border.Border;
import java.awt.*;

public class RoundedBorder implements Border {

    private int radius;
    private Color fill;
    private Color background;
    private String text;
    private int size;

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    private int xCoord;
    private int yCoord;


    RoundedBorder(int radius, Color fill, Color background, String text, int size, int xCoord, int yCoord) {
        this.radius = radius;
        this.fill = fill;
        this.background = background;
        this.text = text;
        this.size = size;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }

    public int getRadius() {
        return radius;
    }

    public Color getFill() {
        return fill;
    }

    public Color getBackground() {
        return background;
    }

    public String getText() {
        return text;
    }

    public int getSize() {
        return size;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setFill(Color fill) {
        this.fill = fill;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isBorderOpaque() {
        return true;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
       drawRoundRect(g, width-1, height-1, radius);
    }
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