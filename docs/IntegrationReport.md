# Integration status

All six algorithms are integrated using Product[] and Comparator<Product>. We use the abstract Product class directly in every sorting method and helper. Comparable<Product> and both normal price comparators are retained.

## Sources and changes

- Source of truth: main commit 2fbdad8cda0f91000402e3975ccbeb6471d4a9c1
- Joaquin's Bubble, Insertion, and Selection: signatures and temporary variables changed to Product only; sorting logic retained
- Sebastian's Heap: signature, siftDown, swap, and temporary variable changed to Product only; min-heap logic retained
- Adeeb's Merge and Quick: logic copied from OOP3-1-Adeeb.zip; Javadoc completed during submission review
- Merge combines sections iteratively and selects the larger product first
- Quick selects the median of the first, middle, and last products, partitions directly descending, and uses an explicit bounds stack
- Product calculations, Comparable, price comparators, reader behavior, AppDriver behavior, parser, timing, output, datasets, and Eclipse metadata are unchanged
- Class, method, constructor, and field Javadoc corrected and completed
- Heap report updated with Product-based measurements reproducible through tests/HeapMeasurementChecks.java and corrected Selection Sort description
- Integer-based sorting tests and the generic integration dispatcher are replaced with Product-based checks
- The test-only Product subclass no longer overrides compareTo; driver checks exercise the real base-class implementation

No production algorithm uses built-in sorting, reverses an ascending result, prints output, or measures time. AppDriver reads input and selects the comparison/task before BenchmarkRunner starts, and prints results after it stops. Allocations inside the sorting algorithms are part of sorting time.

## Verification — October 23, 2026

- OpenJDK 21 compilation with --release 21 -Xlint:all: passed without warnings
- Production Javadoc for declared members with -private -Xdoclint:all,-missing -Werror: passed without errors or warnings
- HeapMeasurementChecks: passed sizes 10 through 1,000,000 and reproduced the report table
- JoaquinChecks: passed Product sorting, 13 output boundaries, and all six driver routes
- IntegrationChecks: passed 18,447,516 assertions
- Christian's preserved JUnit suite: all 17 tests passed
- Runnable JAR: passed 45 algorithm/comparison/dataset combinations, an external working directory, a file path containing spaces, and invalid/missing input checks
- All six sorts passed original price, updated price, and calculated difference on products1 (10) and products2 (1,000)
- Merge, Quick, and Heap passed all three comparisons on products3 (1,000,000)
- Small cases cover empty, singleton, two-element, duplicate values, repeated references, ascending, descending, and random mixed-subclass arrays
- Tests compare expected values and preserve every product occurrence; unstable sorts are allowed to reorder equal values
- Normal negative, zero, and positive comparator results and Product.compareTo are checked independently
- Parser tests cover all argument permutations and mixed-case options for every algorithm/comparison
- No custom type parameter, T array, or Comparator<? super T> remains in production sorting code

The three quadratic sorts were not run on all one million products. Their smaller-file and edge checks passed. The million-product source data is unchanged.

## Remaining submission work

We review the final package together before submission. The Java 21 Eclipse metadata is retained. Actual GUI import into a fresh Eclipse workspace must still be checked; this environment verifies Java compilation and terminal execution only. A review package is prepared with the runnable JAR, nested complete-project ZIP, report, and blank instructor marking form. Complete the group marking criteria together, review the Heap analysis in the author's own words, rename the outer ZIP with the actual group name, and arrange one group submission. See SubmissionChecklist.md. Each member submits their individual peer evaluation.

## Run the checks

From the project root with JDK 21:

```bash
mkdir -p build/checks
javac --release 21 -Xlint:all -d build/checks src/products/*.java src/utilities/*.java src/appDomain/*.java tests/*.java
java -Xmx768m -cp build/checks JoaquinChecks
java -Xmx768m -cp build/checks IntegrationChecks
java -Xmx768m -cp build/checks HeapMeasurementChecks
```

Christian's original tests remain in test/products/ProductTest.java. Add JUnit 5 and that test source folder in Eclipse to run them. Neither standalone checks nor JUnit tests are in the default production source path.

## Build a runnable JAR

```bash
mkdir -p build/app dist
javac --release 21 -d build/app src/products/*.java src/utilities/*.java src/appDomain/*.java
jar --create --file dist/Sort.jar --main-class appDomain.AppDriver -C build/app .
java -jar dist/Sort.jar -fres/products1.txt -td -sq
```

Keep generated JARs and build directories out of source commits. To export from Eclipse, follow the README's Runnable JAR instructions.
