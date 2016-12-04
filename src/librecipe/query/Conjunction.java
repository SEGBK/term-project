package librecipe.query;

abstract class Conjunction {
    private String name;

    /**
     * Creates a new Conjunction object with the given name.
     * @param name the String name of the conjunction
     */
    public Conjunction(String name) {
        this.name = name;
    }

    /**
     * @return the string name of this conjunction
     */
    public String getName() { return name; }

    /**
     * Tests conjunction against left and right booleans.
     * @param left the Expression object on the left-side of the boolean operator
     * @param right the Expression object on the right-side of the boolean operator
     */
    public abstract boolean test(Expression left, Expression right);
}