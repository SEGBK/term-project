package librecipe.query;

import java.util.ArrayList;

import librecipe.Step;
import librecipe.Recipe;
import librecipe.Ingredient;

/**
 * An object to manage a given query and handle
 * matching recipes to it.
 */
public class Query {
    private ArrayList<Entry> query;

    /**
     * Parses a query string into a series of requirements.
     * @param query the string query as given by the user
     */
    public Query(String query) {
        this.query = new ArrayList<Entry>();

        // default query
        if (query.indexOf("(") == -1) query = "(name has " + query + ")";

        // clean the query
        query = query.replaceAll("\\s+", " ") // we want to treat all whitespaces as a single space
                     .replaceAll("\\s*\\(\\s*", "(").replaceAll("\\s*\\)\\s*", ")") // remove whitespaces beside brackets
                     .trim(); // remove whitespaces from start and end

        // parse the query
        this.query = this.parse(query);
    }

    /**
     * Parse a given expression into a list of conditions
     * and conjunctions.
     */
    private ArrayList<Entry> parse(String string) {
        ArrayList<Entry> query = new ArrayList<Entry>();

        while (!string.isEmpty()) {
            // grab location of next expression start
            int nextStart = string.indexOf("(");
            nextStart = nextStart == -1 ? 0 : nextStart;

            // read in potential joiner
            String join = string.substring(0, nextStart);

            // if joiner exists, parse it
            if (!join.isEmpty()) {
                query.add(new Entry(this.toJoiner(join)));
            }

            // skip the joiner and expression start
            string = string.substring(nextStart + 1);

            // find end of expression
            int end = this.next(string);

            // grab actual expression
            String exp = string.substring(0, end);

            // parse it if it isn't a regular query
            if (exp.charAt(0) == '(') query.add(new Entry(this.parse(exp)));
            else query.add(new Entry(new QueryObject(exp)));

            // trim
            string = string.substring(end + 1);
        }

        return query;
    }

    /**
     * Find corresponding closing bracket.
     * @return index of the respective closing bracket or -1 if not found
     */
    private int next(String string) {
        // keep track of open brackets
        int open = 0;

        // find next closing
        for (int i = 0; i < string.length(); i ++) {
            // on closing, decrease number of open
            // expressions and only return if there
            // are no open expressions
            if (string.charAt(i) == ')') {
                if (open == 0) return i;
                else open --;
            }

            // record open bracket
            else if (string.charAt(i) == '(') open ++;
        }

        // if not found, we return -1
        return -1;
    }

    private final Conjunction[] conjunctions = {
        new Conjunction("AND") {
            public boolean test(Expression left, Expression right) {
                return left.eval() && right.eval();
            }
        },
        new Conjunction("OR") {
            public boolean test(Expression left, Expression right) {
                return left.eval() || right.eval();
            }
        }
    };

    /**
     * Get joiner by name.
     * @param name the string name of the joiner
     * @return joiner id
     */
    public int toJoiner(String name) {
        for (int i = 0; i < this.conjunctions.length; i ++) {
            if (this.conjunctions[i].getName().equalsIgnoreCase(name)) return i;
        }

        return -1;
    }

    /**
     * Execute query over given recipe object.
     * @param recipe the Recipe object to test against
     * @return true if query matches object
     */
    public boolean matches(Recipe recipe, ArrayList<Recipe> results) {
        return this.matches(this.query, recipe, results);
    }

    /**
     * Converts a boolean value into an Expression object.
     * @return an Expression created from a final boolean
     */
    private Expression exp(final boolean state) {
        return new Expression() {
            public boolean eval() { return state; }
        };
    }

    /**
     * Execute list of query entries over given recipe object.
     * @param entries the list of query entries
     * @param recipe the Recipe object to test against
     * @return true if query matches object
     */
    public boolean matches(ArrayList<Entry> entries, Recipe recipe, ArrayList<Recipe> results) {
        boolean state = false;
        Conjunction conj = this.conjunctions[1];

        for (int i = 0; i < entries.size(); i ++) {
            final Entry e = entries.get(i);

            switch (e.type()) {
                case 'c':
                state = conj.test(this.exp(state), this.exp(this.matches(e.getCondition(), recipe, results)));
                break;

                case 'q':
                state = conj.test(this.exp(state), e.getQuery().getQuery(recipe, results));
                break;

                default:
                conj = this.conjunctions[e.getJoiner()];
                break;
            }
        }

        return state;
    }
}