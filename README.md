# libRecipe

Library for term project.

Documentation available [here](https://segbk.github.io/term-project/).

## Sample code

There is sample code available for learning about how to use the library
in [Sample.java](Sample.java). This is very arbitrary and not very complete.

To run this, simply run `make` or `make all`.

## Building the library

To compile the library, run `make jar`.
To compile the library from scratch, run `make clean && make jar`.

## Running tests

To run all unit tests, run `make test`.

## Creating new tests

To create new tests, add a new file into the [`test/`](test) directory with
a meaningful name that ends with 'Test.java'.

For example, if you were testing the Recipe class, name your test class
'RecipeTest.java'.

The base code of your test class should be:

```java
package test;

import test.util.*;

// import classes from the 'librecipe' that you want
// to test, but do not import more than you really need
//import librecipe.Recipe;

public class RecipeTest {
  public RecipeTest() {
    // change this to a meaninful name for your test
    super("the name of your test");
  }

  public void test(Runnable end) throws Throwable {
    // always call this line when you are done testing
    end.run();
  }
}
```

## Adding new tests to the runner

To add a new test to the test runner, edit the file [`test/util/Runner.java`](test/util/Runner.java)
and in the array of tests, instantiate your test class.

For example, you can add: `new RecipeTest()` for the test class above.
