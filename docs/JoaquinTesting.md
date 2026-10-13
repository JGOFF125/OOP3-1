# Joaquin's checks

Bubble, Insertion, Selection, ResultPrinter, and AppDriver are implemented. The other members' classes are still unfinished, so the normal driver currently reports that argument parsing is not implemented.

## Run the independent checks

Use JDK 21. Open Git Bash in the cloned project folder and run:

```bash
mkdir -p build/checks
javac --release 21 -d build/checks src/products/*.java src/utilities/*.java src/appDomain/*.java tests/JoaquinChecks.java
java -cp build/checks JoaquinChecks
```

The checks cover empty and single-element arrays, duplicates, ascending and descending inputs, random values, custom comparators, product objects, and output sizes around 10 and 1,000. They also check the driver's comparison and sorting routes with a test-only Product subclass.

`Arrays.sort()` appears only in the test oracle to calculate expected results. The assignment's production sorts do not call it.

These checks reflect the current scaffold. The driver checks expect Merge, Quick, Heap, and argument parsing to be unfinished; update those checks as the team finishes those components. The test class is outside `src`, so it does not change the Eclipse source path or become part of the runnable application.

## Still needed after integration

- Run the actual application with each of the six algorithms and each comparison
- Verify the real file reader, product calculations, parser, and timer
- Check mixed-case arguments, argument order, paths with spaces, and invalid inputs
- Test the original datasets, including the million-product file once restored
- Confirm that only sorting is timed and that the JAR runs outside Eclipse

In Eclipse, AppDriver now runs the application instead of printing the old setup message. An unfinished-component message is expected until the team finishes the required dependencies.
