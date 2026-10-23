# Integration status — no Member 3 yet

This project combines the uploaded Joaquin, Christian, and Sebastian ZIPs. No one is currently assigned to Member 3. It is not a finished assignment submission.

## Joaquin — completed

Bubble Sort, Insertion Sort, Selection Sort, ResultPrinter, and AppDriver integration are implemented. The completed sorts run directly in descending order through the shared comparator signature. Printing uses the required sample intervals. The driver connects the parser, reader, comparison, selected sort, timer, and results, and handles expected errors.

No further algorithm implementation is assigned to Joaquin. Joaquin still coordinates the final integration once Member 3's files arrive, participates in the group self-evaluation, and submits an individual peer evaluation.

## Member 3 — still unassigned

1. Implement MergeSort.sort() with the existing generic signature, directly in descending order. No built-in sorting and no ascending-sort-then-reverse shortcut.
2. Implement QuickSort.sort() using Median-of-3 pivot selection and the existing generic signature. Handle duplicates, empty/single-item arrays, and already sorted inputs.
3. Review and test BenchmarkRunner. A small timer implementation was added solely to make the four completed sorts runnable now. It executes the sorting task once between System.nanoTime() readings; input and output are outside timing. Member 3 does not need to rewrite it if it meets the requirements.
4. Test Merge and Quick independently, then integrate them with the real products, all three comparison types, and supplied datasets. Update tests that currently expect those two algorithms to be unfinished.
5. Coordinate final checks across all six algorithms. Test argument errors, output boundaries, sorting order, product preservation, and sorting-only timing. The three quadratic sorts have not been run on the million-product dataset.
6. Export the final runnable Sort.jar only after all six algorithms work, and verify it from a terminal.
7. Zip the complete Eclipse project including .project, .classpath, .settings, src, and res. Import it into a fresh Eclipse workspace with JDK 21 and run it.
8. Prepare the outer A1<GroupName>.zip containing the final Sort.jar, complete project ZIP, Heap analysis, and completed group marking criteria. Check the deadline and arrange one group submission. Each member handles their own peer assessment.

## Sources and minimal changes

- Joaquin: the base Eclipse project, three basic sorts, result printer, driver, and independent tests.
- Christian: products, comparators, reader, original JUnit tests, and the million-product dataset.
- Sebastian: Heap Sort, parser, and Heap analysis, copied byte for byte from his ZIP.
- Integration addition: the small BenchmarkRunner implementation so the completed sorts can run together.
- Necessary validation fix: Product rejects negative and non-finite prices. Christian's original test suite expects these errors, but the uploaded constructor did not implement them. No comments were added or changed in his source files.
- Christian's remaining product files and reader are restored byte for byte from his ZIP. Previous comment/Javadoc, typo, import, and reader cleanups were removed.
- JoaquinChecks was updated because Heap and argument parsing are now finished. IntegrationChecks exercises the real combined components.
- Eclipse metadata is retained from Joaquin's project. Christian's JUnit test is preserved outside the production source path; add JUnit 5 and the test source folder in Eclipse to run it.

## Verification

Java 21 compilation passes. JoaquinChecks passes 630 integer sorting cases, generic products, 13 output-size boundaries, and driver routing. All 17 of Christian's JUnit tests pass. IntegrationChecks passes 6,024,441 assertions covering parser case/order, reader errors, invalid prices, timer execution, order, and preservation of product objects.

Bubble, Insertion, Selection, and Heap pass on the 10- and 1,000-product files across all three comparisons. Heap passes on the million-product file across all three comparisons. The development JAR passes all 24 combinations for the two smaller files, plus error messages and paths with spaces outside the project directory.

The million-product file is unchanged from Christian's ZIP. No sorting data was fabricated. Actual Eclipse GUI import remains to be checked. Documentation and the Heap analysis need their owners' final review; group self-evaluation and individual peer assessments remain outstanding.

## Run the current build

Extract into a new folder. In Eclipse import the enclosed OOP3-1 project, configure JavaSE-21, and run AppDriver with:

-fres/products1.txt -td -sz

Use b, i, s, or z for completed algorithms. The m and q options report that their implementations are unfinished. Sort.jar is a development build, not the final submission JAR. GitHub was not modified.
