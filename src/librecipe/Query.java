package librecipe;

import java.util.ArrayList;

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

        // clean the query
        query = query.replaceAll("\\s+", " ") // we want to treat all whitespaces as a single space
                     .replaceAll("\\s*\\(\\s*", "(").replaceAll("\\s*\\)\\s*", ")") // remove whitespaces beside brackets
                     .trim(); // remove whitespaces from start and end

        // parse the query
        this.query = this.parse(query);
    }

    /**
     * Parse a given expression into a list of conditions
     * and joiners.
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

    private static String[] joiners = {
        "AND", "OR"
    };

    /**
     * Get joiner by name.
     * @param name the string name of the joiner
     * @return joiner id
     */
    public int toJoiner(String name) {
        for (int i = 0; i < this.joiners.length; i ++) {
            if (this.joiners[i].equalsIgnoreCase(name)) return i;
        }

        return -1;
    }

    private class Entry {
        private int joiner;
        private ArrayList<Entry> condition;
        private QueryObject query;

        public Entry(int joiner) { this.joiner = joiner; }
        public Entry(ArrayList<Entry> condition) { this.condition = condition; }
        public Entry(QueryObject query) { this.query = query; }

        public int getJoiner() { return this.joiner; }
        public ArrayList<Entry> getCondition() { return this.condition; }
        public QueryObject getQuery() { return this.query; }

        public char type() {
            return this.condition != null ? 'c' : (this.query != null ? 'q' : 'j');
        }
    }

    private class QueryObject {
        private String property, verb, query;

        public QueryObject(String query) {
            String[] q = query.split(" ");

            this.property = q[0];
            this.verb = q[1];

            this.query = "";
            for (int i = 2; i < q.length; i += 1) {
                this.query += q[i] + " ";
            }

            this.query = this.query.substring(0, this.query.length() - 1);
        }

        public String getProperty() { return this.property; }
        public String getVerb() { return this.verb; }
        public String getQuery() { return this.query; }

        public String toString() {
            return "query(" + this.property + "," + this.verb + "," + this.query + ")";
        }
    }
}