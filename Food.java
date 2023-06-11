/**
 * A class representing a food item
 * This class also creates a JPanel that displays information about the food
 * ICS4UO - Ms Krasteva
 * @version 2
 * @author Kayla Lin & Angelina Jiang
 */
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Food{

    /** The JPanel containing the food */
    private JPanel display;

    /** The name of the Food */
    private String name;

    /** File link to the Food img */
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

    /** The list of nutrients that the Food contains */
    private int[] nutrients;

    /** The number of calories that the Food contains*/
    private int calories;

    /** How many items of this specific food they have */
    private int quantity;

    /** How much a piece of this Food costs */
    private double price;

    /**
     * "": image that's 30 x 30
     * im: the previous with border
     * im+n: the previous and the name of the food
     * im+n+q: the previous and the quantity of the food bought
     * im+s: the image with border by 60 x 60
     * s: the image without border by 60 x 60
     */
    /** The way the JPanel is displayed */
     String displayMode;

     /**
      * Food constructor
      *
      * Sets all instance variables and calls makePanel method
      *
      * @param n Name of the food
      * @param filePath The path for the file
      * @param nu An array of the nutrients
      * @param dm The display mode for the food
      * @param pri The price of the food
      */
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

    /**
     * name method
     *
     * @return name
     */
    public String name(){ return name; }

    /**
     * setName method
     *
     * @param n The new name
     */
    public void setName(String n){ name = n;}

    /**
     * nutrients method
     *
     * @return nutrients
     */
    public int[] nutrients(){ return nutrients; }

    /**
     * calories method
     *
     * @return calories
     */
    public int calories(){ return calories; }

    /**
     * quantity method
     *
     * @return quantity
     */
    public int quantity(){ return quantity; }

    /**
     * setQuantity method
     *
     * @param q The new quantity
     */
    public void setQuantity(int q){ quantity = q; }

    /**
     * setDisplayMode method
     *
     * Sets a new display mode, removes everything from the JPanel and remakes the panel
     *
     * @param dm The new display mode
     */
    public void setDisplayMode(String dm){ displayMode = dm; display.removeAll(); display.revalidate(); display.repaint(); makePanel();}

    /**
     * price method
     *
     * @return price
     */
    public double price() {return price;}

    /**
     * setPath method
     *
     * @param p The new file path
     */
    public void setPath(String p){file = new File(p);}

    /**
     * makePanel method
     *
     * Sets the background, and based on the display mode, adds the image, name, quantity, border
     */
    public void makePanel(){
        display.setBackground(new Color(189,235,253, 0)); // Sets the background
        display.setLayout(null);
        JLabel logo = null;
        try{ // Sets the Food image
            logo = new JLabel(new ImageIcon(ImageIO.read(file).getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        }catch(IOException e){
            logo = new JLabel("image\nnot\nfound");
            logo.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
        }
        logo.setBounds(0, 0, 30, 30);

        String[] names = name.split(" "); // The names
        JLabel nameText = new JLabel(names[0]); // First word in the name
        nameText.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        nameText.setBounds(45, 5, 90, 20);

        JLabel quantityText = new JLabel("Quantity: " + Integer.toString(quantity)); // Quantity
        quantityText.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
        quantityText.setBounds(45, 70, 90, 15);

        JLabel name2 = new JLabel(); // Second word in the name
        name2.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        name2.setBounds(45, 25, 90, 20);

        JLabel name3 = new JLabel(); // Third word in the name
        name3.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        name3.setBounds(45, 45, 90, 20);
        if(displayMode.equals("")){
            display.setSize(30, 30);
            display.add(logo);
        }else if(displayMode.equals("im")){
            display.setBorder(new RoundedBorder(10, new Color(189, 235, 253), new Color(82, 180, 218), "", 0, 0, 0));
            display.setSize(30, 30);
            display.add(logo);
        }else if(displayMode.equals("im+n")){
            display.setBorder(new RoundedBorder(10, new Color(189, 235, 253), new Color(82, 180, 218), "", 0, 0, 0));
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
            display.setBorder(new RoundedBorder(10, new Color(189, 235, 253), new Color(82, 180, 218), "", 0, 0, 0));
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
        } else if (displayMode.equals("im+s") || displayMode.equals("s")) {
            if (displayMode.equals("im+s")) {
                display.setBorder(new RoundedBorder(10, new Color(189, 235, 253), new Color(82, 180, 218), "", 0, 0, 0));
            }
            try{
                logo = new JLabel(new ImageIcon(ImageIO.read(file).getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
            }catch(IOException e){
                logo = new JLabel(name);
                logo.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
            }
            logo.setBounds(0, 0, 60, 60);
            display.setSize(60, 60);
            display.add(logo);
        }
        display.setVisible(true);
    }

    /**
     * display method
     *
     * @return display The JPanel
     */
    public JPanel display(){ return display; }

    /**
     * equals method
     *
     * Compares this object and obj Object. It checks the name to see if they are the same
     *
     * @param obj The object being compared to
     * @return whether or not the two Food objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        Food other = (Food) obj;
        if (other.name().equals(this.name)) return true;
        return false;
    }
}