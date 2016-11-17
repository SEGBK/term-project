package librecipe;

import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.DataOutputStream;
import java.io.StringWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

/**
 * Storage handler.
 */
public final class CookBook {
    private final String FIREBASE_URL = "https://cookhelper-3ee04.firebaseio.com/";
    private final String FIREBASE_AUTH = "?auth=B42gABi9RrVXJrcwmvojuXJtxxCR0aLwsJHAdble";

    private String request(String method, String path, String body) throws Exception {
        // prepare the request
        URL url = new URL(FIREBASE_URL + path + FIREBASE_AUTH);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type",  "application/json");  
        connection.setUseCaches(false);

        // write down put/post data
        if (body != null) {
            connection.setDoOutput(true);
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            dos.writeBytes(body);
            dos.close();
        }

        // read in response  
        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringWriter response = new StringWriter();
        String line;
        while ((line = reader.readLine()) != null) response.append(line);
        reader.close();

        // return the created response
        return response.toString();
    }

    private HashMap<String,String> map = new HashMap<>();
    private LinkedHashMap<String,Recipe> mapRecipes = new LinkedHashMap<>();

    /**
     * Adds a new Recipe to the list.
     */
    public void add(Recipe recipe) throws Exception {
        String name = this.request("POST", "recipes.json", recipe.serialize()).split(":")[1].substring(1);
        name = name.substring(0, name.length() - 2);

        map.put(recipe.getName(), name);
        mapRecipes.put(name, recipe);
    }

    /**
     * Updates an existing Recipe.
     */
    public void save(Recipe recipe) throws Exception {
        this.request("PUT", "recipes/" + map.get(recipe.getName()) + ".json", recipe.serialize());
    }

    /**
     * Retrieves a saved Saveable object.
     */
    public Recipe get(String name) throws Exception {
        return new Recipe(this.request("GET", "recipes/" + map.get(name) + ".json", null));
    }

    /**
     * Fetch all saved recipes.
     */
    public CookBook() throws Exception {
        String mapStr = this.request("GET", "recipes.json", null);
        this.mapRecipes = (new ObjectMapper().readValue("{\"map\":" + mapStr + "}", RecipeSet.class)).getMap();
        for (String key : this.mapRecipes.keySet()) this.map.put(this.mapRecipes.get(key).getName(), key);
    }
}