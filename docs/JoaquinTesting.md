# Application checks

Bubble, Insertion, Selection, ResultPrinter, and AppDriver are implemented. The integrated parser, products, reader, Heap Sort, and timer now work. Adeeb's Merge and Quick are now integrated using Product arrays.

## Run the independent checks

Use JDK 21. Open Git Bash in the cloned project folder and run:

```bash
mkdir -p build/checks
javac --release 21 -d build/checks src/products/*.java src/utilities/*.java src/appDomain/*.java tests/*.java
java -cp build/checks JoaquinChecks
```

The checks cover empty and single-element arrays, duplicates, ascending and descending inputs, random values, the actual price comparators, natural Product ordering, product objects, and output sizes around 10 and 1,000. They also check the driver's comparison and sorting routes with a test-only Product subclass.

`Arrays.sort()` appears only in the test oracle to calculate expected results. The assignment's production sorts do not call it.

These checks now reflect the integrated build. The driver checks execute all six algorithms and use Product.compareTo for calculated difference. The test class is outside `src`, so it does not change the Eclipse source path or become part of the runnable application.

## Verified integration

All six algorithms pass all three comparisons on products1 and products2. Merge, Quick, and Heap also pass on the million-product products3 file. IntegrationChecks covers small edge cases, normal comparison signs, object preservation, parser case/order, reader errors, and timer execution. See IntegrationReport.md for results and remaining submission checks. Actual Eclipse GUI import still needs a manual check.

## Combined checks

After compiling the production files and both standalone test files as above, run:

```bash
java -Xmx768m -cp build/checks IntegrationChecks
```

Christian's original JUnit 5 tests are preserved in `test/products/ProductTest.java`. To run them in Eclipse, add the `test` folder as a source folder and add the JUnit 5 library to the build path. They are kept outside the default production source path so the application has no JUnit runtime dependency.
