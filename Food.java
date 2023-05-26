/**
 * A class representing a food item
 * This class also creates a JPanel that displays information about the food
 * ICS4UO - Ms Krasteva
 *
 * @author Kayla Lin
 */
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Food{
    JPanel display;
    private String name;
    private File file;
    private boolean mustCook;
    private boolean cooked; // can remove this - when cooked, just change the name and everythingelse
    private String[] nutrients;
    private int calories;
    private int quantity;
    private float price;

    /**
     * "": nothing
     * im: just the image
     * im+n: the previous and the name of the food
     * im+n+q: the previous and the quantity of the food bought
     */
    private String displayMode;

    public Food(String n, String filePath, boolean mc, String[] nu, int cal, String dm){
        display = new JPanel();
        name = n;
        file = new File(filePath);
        mustCook = mc;
        cooked = false;
        nutrients = nu;
        calories = cal;
        displayMode = dm;
        makePanel();
    }

    public String name(){ return name; }
    public boolean isMustCook(){ return mustCook; }
    public boolean isCooked(){ return cooked; }
    public void setCooked(boolean c){ cooked = c; }
    public String[] nutrients(){ return nutrients; }
    public int calories(){ return calories; }
    public int quantity(){ return quantity; }
    public void setQuantity(int q){ quantity = q; }
    public String displayMode(){ return displayMode; }
    public void setDisplayMode(String dm){ displayMode = dm; }
    
    public void makePanel(){
        display.setBackground(new Color(189,235,253));
        display.setBorder(new RoundedBorder(10, new Color(82,180,218)));
        JLabel logo = null;
        try{
            logo = new JLabel(new ImageIcon(ImageIO.read(file).getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        }catch(IOException e){
            logo = new JLabel("image\nnot\nfound");
            logo.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
        }
        logo.setBounds(5, 5, 30, 30);

        JLabel nameText = new JLabel(name);
        nameText.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        nameText.setBounds(45, 5, 30, 20);

        JLabel quantityText = new JLabel("Quantity: " + Integer.toString(quantity));
        quantityText.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
        quantityText.setBounds(45, 25, 30, 10);

        if(displayMode.equals("")){
            display.setSize(0, 0);
        }else if(displayMode.equals("im")){
            display.setSize(60, 60);
            display.add(logo);
        }else if(displayMode.equals("im+n")){
            display.setSize(80, 50);
            display.add(logo);
            display.add(nameText);
        }else if(displayMode.equals("im+n+q")){
            display.setSize(80, 50);
            display.add(logo);
            display.add(nameText);
            display.add(quantityText);
        }
        display.setVisible(true);
    }

    public JPanel display(){ return display; }

    public static void main(String[] args){
        JFrame testing = new JFrame();
        testing.setSize(400, 400);
        String[] lettuceNutrients = {"Vitamin A", "Vitamin C"};
        Food lettuce = new Food("lettuce", "Pictures/food/lettuce.png", false, lettuceNutrients, 25, "im+n+q");
        testing.add(lettuce.display());
        testing.setVisible(true);
    }
}