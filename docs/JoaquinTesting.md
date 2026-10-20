# Joaquin's checks

Bubble, Insertion, Selection, ResultPrinter, and AppDriver are implemented. The integrated parser, products, reader, Heap Sort, and timer now work. Merge and Quick remain unfinished.

## Run the independent checks

Use JDK 21. Open Git Bash in the cloned project folder and run:

```bash
mkdir -p build/checks
javac --release 21 -d build/checks src/products/*.java src/utilities/*.java src/appDomain/*.java tests/JoaquinChecks.java
java -cp build/checks JoaquinChecks
```

The checks cover empty and single-element arrays, duplicates, ascending and descending inputs, random values, custom comparators, product objects, and output sizes around 10 and 1,000. They also check the driver's comparison and sorting routes with a test-only Product subclass.

`Arrays.sort()` appears only in the test oracle to calculate expected results. The assignment's production sorts do not call it.

These checks now reflect the integrated build. The driver checks still expect Merge and Quick to be unfinished; update those checks when those algorithms are supplied. The test class is outside `src`, so it does not change the Eclipse source path or become part of the runnable application.

## Still needed after integration

- Run the actual application with each of the six algorithms and each comparison
- Verify the real file reader, product calculations, parser, and timer
- Check mixed-case arguments, argument order, paths with spaces, and invalid inputs
- Repeat all tests when Merge and Quick are integrated; the million-product file is now included
- Confirm that only sorting is timed and that the JAR runs outside Eclipse

In Eclipse, AppDriver now runs the application instead of printing the old setup message. An unfinished-component message is expected for Merge and Quick only.

## Combined checks

After compiling the production files and both standalone test files as above, run:

```bash
java -Xmx768m -cp build/checks IntegrationChecks
```

Christian's original JUnit 5 tests are preserved in `test/products/ProductTest.java`. To run them in Eclipse, add the `test` folder as a source folder and add the JUnit 5 library to the build path. They are kept outside the default production source path so the application has no JUnit runtime dependency.
