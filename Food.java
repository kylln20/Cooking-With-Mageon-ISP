/**
 * A class representing a food item
 * This class also creates a JPanel that displays information about the food
 * ICS4UO - Ms Krasteva
 * @version 2
 * @author Kayla Lin & Angelina Jiang
 */
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Food{
    private JPanel display;
    private String name;
    private File file;

    /**
     * 0: Vitamin A
     * 1: Vitamin B12
     * 2: Vitamin C
     * 3: Vitamin D
     * 4: Vitamin K
     * 5: Protein
     * 6: Iron
     * 7: Fiber
     * 8: Calcium
     * 9: Potassium
     * 10: Sodium
     * 11: Cholesterol
     * 12: Omega 3
     * 13: Selenium
     */
    private int[] nutrients;
    private int calories;
    private int quantity;
    private double price;

    /**
     * "": nothing
     * im: just the image
     * im+n: the previous and the name of the food
     * im+n+q: the previous and the quantity of the food bought
     */
     String displayMode;

    public Food(String n, String filePath, int[] nu, int cal, String dm, double pri){
        display = new JPanel();
        name = n;
        file = new File(filePath);
        nutrients = nu;
        calories = cal;
        displayMode = dm;
        quantity = 1;
        price = pri;
        makePanel();
    }

    public String name(){ return name; }
    public int[] nutrients(){ return nutrients; }
    public int calories(){ return calories; }
    public int quantity(){ return quantity; }
    public void setQuantity(int q){ quantity = q; }
    public String displayMode(){ return displayMode; }
    public void setDisplayMode(String dm){ displayMode = dm; display.removeAll(); display.revalidate(); display.repaint(); makePanel();}
    public double price() {return price;}

    public void makePanel(){
        display.setBackground(new Color(189,235,253, 0));
        display.setLayout(null);
        if (!display.equals("")) {
            display.setBorder(new RoundedBorder(10, new Color(189, 235, 253), new Color(82, 180, 218), "", 0, 0, 0));
        }
        JLabel logo = null;
        try{
            logo = new JLabel(new ImageIcon(ImageIO.read(file).getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        }catch(IOException e){
            logo = new JLabel("image\nnot\nfound");
            logo.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
        }
        logo.setBounds(0, 0, 30, 30);

        String[] names = name.split(" ");
        JLabel nameText = new JLabel(names[0]);
        nameText.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        nameText.setBounds(45, 5, 90, 20);

        JLabel quantityText = new JLabel("Quantity: " + Integer.toString(quantity));
        quantityText.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
        quantityText.setBounds(45, 70, 90, 15);

        JLabel name2 = new JLabel();
        name2.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        name2.setBounds(45, 25, 90, 20);

        JLabel name3 = new JLabel();
        name3.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        name3.setBounds(45, 45, 90, 20);
        if(displayMode.equals("")){
            display.setSize(30, 30);
            display.add(logo);
        }else if(displayMode.equals("im")){
            display.setSize(30, 30);
            display.add(logo);
        }else if(displayMode.equals("im+n")){
            display.setSize(80, 50);
            display.add(logo);
            display.add(nameText);
            if (names.length >= 2) {
                name2.setText(names[1]);
                if (names.length == 3) {
                    name3.setText(names[2]);
                }
            }
            display.add(name2);
            display.add(name3);
        }else if(displayMode.equals("im+n+q")){
            logo.setBounds(5, 30, 30, 30);
            display.setSize(80, 50);
            display.add(logo);
            if (names.length >= 2) {
                name2.setText(names[1]);
                if (names.length == 3) {
                    name3.setText(names[2]);
                }
            }
            display.add(name2);
            display.add(name3);
            display.add(nameText);
            display.add(quantityText);
        }
        display.setVisible(true);
    }

    public JPanel display(){ return display; }

    public static void main(String[] args){
        JFrame testing = new JFrame();
        testing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testing.setSize(10, 130);
        Food lettuce = new Food("Uncooked Multigrain Rice", "Pictures/rice(multigrain).png", new int[]{}, 25, "im+n+q", 0.14);
        testing.add(lettuce.display());
        testing.setVisible(true);
    }

    @Override
    public boolean equals(Object obj) {
        Food other = (Food) obj;
        if (other.name().equals(this.name)) return true;
        return false;
    }
}