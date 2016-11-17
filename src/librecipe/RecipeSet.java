package librecipe;

import java.util.LinkedHashMap;

public class RecipeSet {
    private LinkedHashMap<String,Recipe> map;
    public RecipeSet() { this.map = new LinkedHashMap<>(); }
    public LinkedHashMap<String,Recipe> getMap() { return map; }
    public void setMap(LinkedHashMap<String,Recipe> map) {
        this.map = map;
        //System.out.print("setMap({");
        //for (String key : map.keySet()) System.out.format("%s => %s, ", key, this.map.get(key).getName());
        //System.out.println("})\n");
    }
}