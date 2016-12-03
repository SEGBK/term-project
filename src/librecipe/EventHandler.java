package librecipe;

/**
 * Similar to Runnable but can receive data.
 */
public interface EventHandler {
    /**
     * Callback to execute on event launch.
     * @param data the data pertaining to the event
     */
    abstract void run(String data);
}