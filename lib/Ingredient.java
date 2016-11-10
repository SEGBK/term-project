package lib;

/**
 * Stores details on the quantity of an ingredient.
 */
public class Ingredient implements Comparable<Ingredient> {
    private double quantity;
    private String name, units;

    /**
     * Creates a new Ingredient object from values.
     */
    public Ingredient(String name, double quantity, String units) {
        this.name = name;
        this.quantity = quantity;
        this.units = units;
    }

    /**
     * Compares the name of this Ingredient object with another.
     * @param other a different Ingredient object
     * @return -1, 0, or 1 if the name of this object is less
     *         than, equal to, or greater than the name of ther oher object
     */
    public int compareTo(Ingredient other) { return this.name.compareTo(other.getName()); }

    /**
     * Retrieves the common name for the ingredient.
     * @return the name of the ingredient as a string
     */
    public String getName() { return this.name; }

    /**
     * Retrieves the quantity of this ingredient required.
     * @return a scalar representing quantity
     */
    public double getQuantity() { return this.quantity; }

    /**
     * Retrieves the units in which quantity should be measured
     * for this ingredient.
     * @return the common name for this unit
     */
    public String getUnits() { return this.units; }
}
