import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class temp{
    public static void main(String[] args) throws IOException {
        JFrame testFrame = new JFrame("ramka testowa");
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        testFrame.setTitle("JScrollablePanel Test");
        testFrame.setLayout(new BorderLayout());
        JLabel mageon = new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/MAGEON eating.png")).getScaledInstance(500, 500, Image.SCALE_SMOOTH)));
        JPanel panel = new JPanel();
        panel.add(mageon);
        panel.setLayout(null);
        mageon.setBounds(0, 0, 500, 500);
        JPanel extr = new JPanel();
        extr.add(panel);
        testFrame.add(BorderLayout.CENTER, new JScrollPane(extr));
        testFrame.setSize(375, 250);
        testFrame.setLocationRelativeTo(null);
        testFrame.setVisible(true);

    }
}