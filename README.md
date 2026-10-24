# OOP III Assignment 1

We use Eclipse and Java 21 to sort Product arrays directly from largest to smallest.
All six algorithms are integrated on main. The code checks passed; our group
evaluation, fresh Eclipse import, and final submission checks still need sign-off.

## Run in Eclipse

1. Configure a JDK 21 under Window > Preferences > Java > Installed JREs and select
   it for the JavaSE-21 execution environment
2. Clone the repository or extract the complete project ZIP
3. Use File > Import > General > Existing Projects into Workspace
4. Select the folder containing .project and import OOP3-1
5. Run src/appDomain/AppDriver.java as a Java Application
6. In Run Configurations > Arguments, use:

```text
-fres/products1.txt -td -sq
```

We keep the working directory set to the project folder so relative paths resolve.
OOP3-1.launch provides a sample configuration. Import the complete project, not
just src. The Eclipse project metadata is included in Git.

## Options and sorting

We attach each value directly to its option. Option letters and values are
case-insensitive and can appear in any order; the file path keeps its case.

| Option | Values |
| --- | --- |
| -f | Absolute or relative input file path |
| -t | o: original price, u: updated price, d: calculated difference |
| -s | b: Bubble, i: Insertion, s: Selection, m: Merge, q: Quick, z: Heap |

Every sort accepts:

```java
public static void sort(Product[] items, Comparator<Product> comparison)
```

We use Product arrays throughout the sorting layer, without custom type parameters.
Product implements Comparable<Product> for the calculated difference. Separate
Comparator<Product> classes compare original and updated prices. Both interfaces
keep their normal negative, zero, and positive return values.

We place larger values first inside each algorithm. We do not sort ascending and
reverse afterward. Quick uses a Median-of-3 pivot. Heap uses a min-heap to move
smaller products to the end. Timing stays outside the algorithms and includes
sorting only, with results printed afterward in milliseconds.

We print all products up to 10. Up to 1,000, we print the first, last, and every
hundredth row in between. Above 1,000, we use every thousandth row instead.

## Input data

The first line gives the product count and determines the Product array size.
Each remaining row contains a product type and two prices:

```text
2
Jeans 10.00 20.00
Shoes 25.00 15.00
```

| Product | Difference adjustment |
| --- | --- |
| Jeans | 0.5 |
| TShirts | 0.2 |
| Skirts | 0.1 |
| Dresses | 0.6 |
| Shorts | 0.3 |
| Jackets | 0.7 |
| Shoes | 0.4 |

Each subclass returns adjustment * abs(original price - updated price).
The supplied files in res contain 10, 1,000, and 1,000,000 products.
We also accept other files using the same format.

## Checks completed

The tested code snapshot is cd37f2a. The README update does not change code.

- Java 21 compilation with --release 21 -Xlint:all passed
- All 17 Product JUnit tests passed
- IntegrationChecks passed 18,447,516 assertions
- All six algorithms passed all three comparisons on products1 and products2
- Merge, Quick, and Heap passed all three comparisons on products3
- The packaged runnable JAR passed 45 dataset/algorithm/comparison combinations,
  including paths with spaces, an external working directory, and input errors
- We checked empty arrays, single products, duplicates, repeated objects, ascending
  input, descending input, random inputs, and preservation of every product
- HeapMeasurementChecks reproduces the Product-based counts in the Heap report

We have not completed full-million runs with Bubble, Insertion, or Selection.
Their smaller-file and edge checks passed. Fresh Eclipse GUI import and checking
the actual D2L upload remain manual steps.

For the standalone checks, use JDK 21 in Git Bash or another shell that expands
the source file patterns:

```bash
mkdir -p build/checks
javac --release 21 -Xlint:all -d build/checks src/products/*.java src/utilities/*.java src/appDomain/*.java tests/*.java
java -Xmx768m -cp build/checks JoaquinChecks
java -Xmx768m -cp build/checks IntegrationChecks
java -Xmx768m -cp build/checks HeapMeasurementChecks
```

To run test/products/ProductTest.java in Eclipse, add test as a source folder
and add the JUnit 5 library. The production application has no JUnit dependency.

## Runnable JAR

In Eclipse, use File > Export > Java > Runnable JAR file, choose the AppDriver
launch configuration, and export Sort.jar.

From the project folder, run:

```text
java -jar Sort.jar -fres/products1.txt -td -sq
java -jar Sort.jar -sZ -To -fres/products2.txt
```

We can also build it with JDK 21 from Git Bash:

```bash
mkdir -p build/app dist
javac --release 21 -d build/app src/products/*.java src/utilities/*.java src/appDomain/*.java
jar --create --file dist/Sort.jar --main-class appDomain.AppDriver -C build/app .
java -jar dist/Sort.jar -fres/products1.txt -td -sq
```

Generated classes, build folders, and JARs are excluded from Git. A GitHub source
ZIP therefore does not include the runnable JAR or completed evaluation.

## Final submission

Before uploading, we need to:

1. Review the custom-sort explanation and operation counts together
2. Complete the marking criteria with all group members present and save it
   under our actual group name
3. Import the complete project ZIP into a fresh Eclipse workspace with Java 21
   and verify the project and final JAR
4. Put these files into A1 followed by our actual group name, ending in .zip:
   - Sort.jar
   - A separate ZIP of the complete Eclipse project, including .project,
     .classpath, .settings, src, and all datasets in res
   - The custom-sort report
   - The completed marking criteria document
5. Submit one group package before the deadline shown on D2L
6. Each complete the peer assessment as instructed
7. Each download and check the actual last-submitted D2L ZIP independently

The prepared review package contains a blank marking form. We must replace it
with our completed group evaluation before submitting. We cannot count the
review package as group sign-off or proof that the uploaded copy works.

See [SubmissionChecklist](docs/SubmissionChecklist.md) for the final checks and
[IntegrationReport](docs/IntegrationReport.md) for the implementation and test details.

## Contributions

| Member | Main responsibilities |
| --- | --- |
| Christian | Product hierarchy, comparators, file reader |
| Joaquin | Bubble, Insertion, Selection, output, AppDriver integration |
| Adeeb | Merge, Median-of-3 Quick, timing, final packaging |
| Sebastian | Heap, argument parsing, custom-sort report |

We review shared changes together, keep meaningful Javadoc, and preserve each
other's work when integrating updates.
