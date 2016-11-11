/**
 * Some sample test code.
 */

package test;

import test.util.*;
import librecipe.*;

public class SampleTest extends Test {
    public SampleTest() {
        super("sample test");
    }

    public void test(Runnable end) throws Throwable {
        Test.equal(1, 1, "1 should equal 1");
        end.run();
    }
}
