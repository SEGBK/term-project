package librecipe.query;

import java.util.ArrayList;

class Entry {
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