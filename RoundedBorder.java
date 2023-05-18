import javax.swing.border.Border;
import java.awt.*;

public class RoundedBorder implements Border {

    private int radius;
    private Color c;


    RoundedBorder(int radius, Color c) {
        this.radius = radius;
        this.c = c;
    }


    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }


    public boolean isBorderOpaque() {
        return true;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(this.c);
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}