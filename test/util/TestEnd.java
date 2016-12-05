/**
 * test/TestEnd.java - lab3
 * A runnable to signal the end of a test.
 */

package test.util;

public abstract class TestEnd {
    /**
     * Method to call when the test has ended.
     * @param passed a boolean signifying if the test passed.
     */
    public abstract void run(Boolean passed);
}
