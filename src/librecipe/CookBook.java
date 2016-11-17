package librecipe;

import java.net.URL;
import java.lang.Thread;
import java.util.HashMap;
import java.lang.Runnable;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.BufferedReader;
import java.util.LinkedHashMap;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import javax.net.ssl.HttpsURLConnection;

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
    private RecipeMap map;

    /**
     * Creates a new Recipe to the list.
     * @param recipe the Recipe object to send up
     * @throws Exception if JSON serialization fails
     */
    public void add(Recipe recipe) throws Exception {
        String name = this.request("POST", "recipes.json", recipe.serialize()).split(":")[1].substring(1);
        name = name.substring(0, name.length() - 2);

        map.put(recipe.getName(), name);
        this.request("PUT", "recipe-map.json", map.serialize());
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
        final CookBook that = this;
        this.ready = new ArrayList<>();

        new Thread() {
            public void run() {
                try {
                    String json = that.request("GET", "recipe-map.json", null);
                    if (json.equals("null")) that.map = new RecipeMap();
                    else that.map = new RecipeMap(json);
                } catch (Exception ex) {
                    for (EventHandler run : that.error) run.run(ex.getMessage());
                }

                for (Runnable run : that.ready) run.run();
            }
        }.start();
    }

    /**
     * Add an event listener for when CookBook errors out.
     */
    private ArrayList<EventHandler> error;
    public void onError(EventHandler run) { this.error.add(run); }

    /**
     * Add an event listener for when CookBook is ready.
     */
    private ArrayList<Runnable> ready;
    public void onReady(Runnable run) { this.ready.add(run); }
}