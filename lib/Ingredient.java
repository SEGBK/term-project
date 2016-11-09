/**
 * lib/Ingredient.java - term-project
 * Stores details on the quantity of an ingredient.
 */

package lib;

public class Ingredient implements Comparable<Ingredient> {
    private double quantity;
    private String name, units;

    public Ingredient(String name, double quantity, String units) {
        this.name = name;
        this.quantity = quantity;
        this.units = units;
    }

    public int compareTo(Ingredient o) { return this.name.compareTo(o.getName()); }
    public String getName() { return this.name; }
    public double getQuantity() { return this.quantity; }
    public String getUnits() { return this.units; }
}