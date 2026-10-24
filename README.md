# OOP III Assignment 1

We use Eclipse and Java 21. We sort Product arrays directly from largest to
smallest using Bubble, Insertion, Selection, Merge, Median-of-3 Quick, and Heap.

## Run in Eclipse

1. I set the project JRE to JavaSE-21
2. I import the complete project through File > Import > Existing Projects into Workspace
3. I run src/appDomain/AppDriver.java as a Java Application
4. I open Run Configurations > Arguments and enter:

```text
-fres/products1.txt -td -sq
```

I keep the working directory set to the project folder. I can also use
OOP3-1.launch for the sample run.

## Command-line options

| Option | Values |
| --- | --- |
| -f | Input file path |
| -t | o: original price, u: updated price, d: calculated difference |
| -s | b: Bubble, i: Insertion, s: Selection, m: Merge, q: Quick, z: Heap |

I attach each value directly to its option. I can change the option order and
use uppercase or lowercase letters. I keep the file path in its original case.

I use Product[] and Comparator<Product> in each sort. I use Product.compareTo
for calculated difference and the two separate comparators for prices.
I keep their normal comparison signs and put larger products first inside the
sorts. I do not sort ascending and reverse afterward.

I time only the sort and print the elapsed time in milliseconds. I print all
products up to 10, then the first, last, and every hundredth row up to 1000.
Above 1000, I use every thousandth row instead.

## Export and run the JAR

I use File > Export > Java > Runnable JAR file, select the AppDriver launch
configuration, and export Sort.jar.

From the project folder, I run:

```text
java -jar Sort.jar -fres/products1.txt -td -sq
java -jar Sort.jar -sZ -To -fres/products2.txt
```

## Checks

I can run the integration and Heap measurement checks from Git Bash with JDK 21:

```bash
mkdir -p build/checks
javac --release 21 -d build/checks src/products/*.java src/utilities/*.java src/appDomain/*.java tests/*.java
java -Xmx768m -cp build/checks IntegrationChecks
java -Xmx768m -cp build/checks HeapMeasurementChecks
```

I can run test/products/ProductTest.java in Eclipse after adding test as a
source folder and JUnit 5 to the build path.

We have passing recorded results for all six sorts and all three comparisons
on products1 and products2. Merge, Quick, and Heap also passed on products3.
We have no completed full-million run for Bubble, Insertion, or Selection.

## Submission

We include these items in A1 followed by our actual group name, ending in .zip:

- Sort.jar
- A separate ZIP of the complete Eclipse project, including .project,
  .classpath, .settings, src, and res
- Our [Heap report](docs/CustomSortAnalysis.md)
- Our completed marking criteria document

We complete the marking criteria together with every group member present.
We check the final project in a fresh Eclipse workspace and run the packaged JAR.
We each complete the peer assessment and download and check the actual D2L ZIP.

We keep the required Eclipse project metadata. We leave out the workspace
.metadata folder and .git folder. A GitHub source ZIP does not include the
runnable JAR or our completed marking form.

## Contributions

| Member | Main responsibilities |
| --- | --- |
| Christian | Product hierarchy, comparators, file reader |
| Joaquin | Bubble, Insertion, Selection, output, AppDriver |
| Adeeb | Merge, Median-of-3 Quick, timing, packaging |
| Sebastian | Heap, argument parsing, custom-sort report |
