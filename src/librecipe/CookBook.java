package librecipe;

import librecipe.query.Query;
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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

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
     * Retrieves a saved Recipe object.
     * @param name the name of a Recipe
     * @throws Exception if JSON deserialization fails
     * @return the Recipe object being retrieved
     */
    public Recipe get(String name) throws Exception {
        return new Recipe(this.request("GET", "recipes/" + map.get(name) + ".json", null));
    }

    /**
     * Deletes a saved Recipe object.
     * @param name the name of a Recipe
     * @throws Exception if JSON deserialization fails
     * @return the Recipe object being retrieved
     */
    public Recipe rm(String name) throws Exception {
        return new Recipe(this.request("DELETE", "recipes/" + map.get(name) + ".json", null));
    }

    /**
     * Executes a search and calls ResultsHandler object on complete.
     * @param query the query as a String
     * @param eventHandler the ResultsHandler object to call
     * @return the CookBook object for chaining
     */
    public CookBook search(String query, ResultsHandler eventHandler) {
        return this.search(new Query(query), eventHandler);
    }

    /**
     * Maximum number of parallel jobs.
     */
    private final int MAX_JOBS = Runtime.getRuntime().availableProcessors() + 1;

    /**
     * Executes a search and calls ResultsHandler object on complete.
     * @param query the Query object containing all conditions
     * @param eventHandler the ResultsHandler object to call
     * @return the CookBook object for chaining
     */
    public CookBook search(final Query query, final ResultsHandler eventHandler) {
        final CookBook that = this;

        // general results list
        final ArrayList<Recipe> results = new ArrayList<Recipe>();
        
        // check when threads are done
        final Counter counter = new Counter();
        final Runnable end = new Runnable() {
            public void run() {
                counter.next();
                System.out.print(""); // small delay
                if (counter.count() == MAX_JOBS) eventHandler.onResults(results);
            }
        };

        // jobs per thread count
        final String[] recipes = new String[this.map.size()];
        for (int i = 0; i < recipes.length; i ++) recipes[i] = this.map.getKeys().get(i);
        final int RES_PER_JOB = (int)( recipes.length / MAX_JOBS );

        if (RES_PER_JOB == 0) {
            new Thread() {
                public void run() {
                    for (String name : that.map) {
                        try {
                            Recipe recipe = that.get(name);
                            if (query.matches(recipe, results)) results.add(recipe);
                        }  catch (Exception e) { /**/ }
                    }
                
                    eventHandler.onResults(results);
                }
            }.start();
        } else {
            // create the jobs
            for (int i = 0; i < MAX_JOBS; i ++) {
                int job = i + 1;
                final int a = (job - 1) * RES_PER_JOB, b = (job * RES_PER_JOB) - 1;

                new Thread() {
                    public void run() {
                        for (int j = a; j <= b; j ++) {
                            try {
                                Recipe recipe = that.get(recipes[j]);
                                if (query.matches(recipe, results)) results.add(recipe);
                            } catch (Exception ex) { /**/ }
                        }

                        end.run();
                    }
                }.start();
            }
        }

        // allow chaining
        return this;
    }

    private class Counter {
        private int i = 0;
        public void next() { this.i ++; }
        public int count() { return this.i; }
    }

    /**
     * Fetch all saved recipes.
     * @throws Exception if JSON deserialization fails
     */
    public CookBook() throws Exception {
        final CookBook that = this;
        new Thread() {
            public void run() {
                try {
                    String json = that.request("GET", "recipe-map.json", null);
                    if (json.equals("null")) that.map = new RecipeMap();
                    else that.map = new RecipeMap(json);
                    for (Runnable run : that.ready) run.run();
                } catch (Exception ex) {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    ex.printStackTrace(new PrintStream(bytes));
                    String error = new String(bytes.toByteArray(), StandardCharsets.UTF_8);

                    for (EventHandler run : that.error) run.run(error);
                }
            }
        }.start();
    }

    /**
     * Add an event listener for when CookBook errors out.
     * @param run an EventHandler object to subscribe to the error event
     * @return the CookBook object for chaining
     */
    public CookBook onError(EventHandler run) { this.error.add(run); return this; }
    private ArrayList<EventHandler> error = new ArrayList<EventHandler>();

    /**
     * Add an event listener for when CookBook is ready.
     * @param run an EventHandler object to subscribe to the ready event
     * @return the CookBook object for chaining
     */
    public CookBook onReady(Runnable run) { this.ready.add(run); return this; }
    private ArrayList<Runnable> ready = new ArrayList<Runnable>();
}