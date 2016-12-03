package librecipe.query;

class QueryObject {
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