package librecipe;

import java.net.URL;
import java.util.HashMap;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.BufferedReader;
import java.util.LinkedHashMap;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import javax.net.ssl.HttpsURLConnection;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.JsonMappingException;

/**
 * Handles the storage and retreival of all recipes.
 */
public class CookBook {
    /**
     * Firebase endpoint details. Useful for storage since it allows
     * the library to be developed independent of the Android SDK.
     */
    private final String FIREBASE_URL = "https://cookhelper-3ee04.firebaseio.com/";
    private final String FIREBASE_AUTH = "?auth=B42gABi9RrVXJrcwmvojuXJtxxCR0aLwsJHAdble";

    /**
     * Executes a request against Firebase.
     * @param method the HTTP method to use (GET,PUT,POST)
     * @param path the path of the endpoint, without the initial slash
     * @param body the post data in JSON (null or querystring for GET)
     * @return the body of the response
     */
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

    /**
     * Map between recipe names and Firebase IDs.
     */
    private HashMap<String,String> map = new HashMap<>();

    /**
     * Map between Firebase IDs and Recipe objects.
     */
    private LinkedHashMap<String,Recipe> mapRecipes = new LinkedHashMap<>();

    /**
     * Creates a new Recipe to the list.
     * @param recipe the Recipe object to send up
     * @throws Exception if JSON serialization fails
     */
    public void add(Recipe recipe) throws Exception {
        String name = this.request("POST", "recipes.json", recipe.serialize()).split(":")[1].substring(1);
        name = name.substring(0, name.length() - 2);

        map.put(recipe.getName(), name);
        mapRecipes.put(name, recipe);
    }

    /**
     * Updates an existing Recipe.
     * @param recipe the Recipe object to Updates
     * @throws Exception if JSON serialization fails
     */
    public void save(Recipe recipe) throws Exception {
        this.request("PUT", "recipes/" + map.get(recipe.getName()) + ".json", recipe.serialize());
    }

    /**
     * Retrieves a saved Saveable object.
     * @param name the name of a Recipe
     * @throws Exception if JSON deserialization fails
     */
    public Recipe get(String name) throws Exception {
        return new Recipe(this.request("GET", "recipes/" + map.get(name) + ".json", null));
    }

    /**
     * Fetch all saved recipes.
     * @throws Exception if JSON deserialization fails
     */
    public CookBook() throws Exception {
        String mapStr = this.request("GET", "recipes.json", null);
        this.mapRecipes = (new ObjectMapper().readValue("{\"map\":" + mapStr + "}", RecipeSet.class)).getMap();
        for (String key : this.mapRecipes.keySet()) this.map.put(this.mapRecipes.get(key).getName(), key);
    }
}