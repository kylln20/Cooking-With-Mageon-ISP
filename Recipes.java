/**
 * A program to play Cook With Mageon featuring using GUI's with Java Swing
 *
 * Course Info:
 * ICS4UO
 * Valentina Krasteva
 *
 * @author Angelina Jiang
 */
import java.util.ArrayList;

public class Recipes {

    /** Recipe name */
    private String name;

    /** Food needed to make the recipe */
    private ArrayList<Food> recipe;

    /**
     * Recipes constructor
     * sets the name and recipe variables
     *
     * @param name Name of the recipe
     * @param food The food needed to make the recipe
     */
    public Recipes (String name, ArrayList<Food> food) {
        this.name = name;
        recipe = food;
    }

    /**
     * equals method
     * compares two objects
     *
     * @param obj The object we are comparing this to
     *
     * @return whether the two objects are equal or not
     */
    @Override
    public boolean equals(Object obj) {
        Recipes other = (Recipes) obj;
        if (recipe.size() != other.getRecipe().size()) return false;
        for (Food f : other.getRecipe()) {
            if (!recipe.contains(f)) return false;
        }
        return true;
    }

    /**
     * getName method
     * returns the name of the recipe
     */
    public String getName() {
        return name;
    }

    /**
     * getRecipe method
     * returns the ingredients for the recipe
     */
    public ArrayList<Food> getRecipe() {
        return recipe;
    }
}
