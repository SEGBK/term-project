package librecipe.query;

import librecipe.Recipe;

class QueryObject {
    private String property, query;
    private Verb verb;

    /**
     * List of verbs for querying.
     */
    private static Verb[] Verbs = {
        new Verb(new String[] {"has"}) {
            public boolean test(String propertyValue, String query) {
                return propertyValue.toLowerCase().indexOf(query.toLowerCase()) > -1;
            }
        },
        new Verb(new String[] {"is", "=", "=="}) {
            public boolean test(String propertyValue, String query) {
                return propertyValue.equalsIgnoreCase(query);
            }
        },
        new Verb(new String[] {">", "gt"}) {
            public boolean test(String propertyValue, String query) {
                return Double.parseDouble(propertyValue) > Double.parseDouble(query);
            }
        },
        new Verb(new String[] {">=", "gte"}) {
            public boolean test(String propertyValue, String query) {
                return Double.parseDouble(propertyValue) >= Double.parseDouble(query);
            }
        },
        new Verb(new String[] {"<", "lt"}) {
            public boolean test(String propertyValue, String query) {
                return Double.parseDouble(propertyValue) < Double.parseDouble(query);
            }
        },
        new Verb(new String[] {"<=", "lte"}) {
            public boolean test(String propertyValue, String query) {
                return Double.parseDouble(propertyValue) <= Double.parseDouble(query);
            }
        }
    };

    public QueryObject(String query) throws IllegalArgumentException {
        String[] q = query.split(" ");

        this.property = q[0];
        String verb = q[1];

        for (int i = 0; this.verb == null && i < QueryObject.Verbs.length; i ++) {
            if (QueryObject.Verbs[i].is(verb)) {
                this.verb = QueryObject.Verbs[i];
            }
        }

        if (this.verb == null) throw new IllegalArgumentException("the verb '" + verb + "' is not supported.");

        this.query = "";
        for (int i = 2; i < q.length; i += 1) {
            this.query += q[i] + " ";
        }

        this.query = this.query.substring(0, this.query.length() - 1);
    }

    public String getProperty() { return this.property; }
    public String getVerb() { return this.verb.toString(); }

    /**
     * Builds an expression object on top of a query.
     * @param recipe the Recipe object to borrow property values from
     * @return the Expression object that can be evaluated
     */
    public Expression getQuery(final Recipe recipe) {
        final QueryObject that = this;
        return new Expression() {
            public boolean eval() {
                return that.verb.test(recipe.getProperty(that.property), that.query);
            }
        };
    }

    public String toString() {
        return "query(" + this.property + "," + this.getVerb() + "," + this.query + ")";
    }
}