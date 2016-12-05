package librecipe.query;

/**
 * An interface representing unevaluated boolean expressions.
 */
interface Expression {
    /**
     * Evaluates the underlying expression and returns the boolean value.
     * @return the boolean value of the expression
     */
    public boolean eval();
}