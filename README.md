# OOP3-1

This is our setup for Assignment 1: 

This integration build combines Joaquin, Christian, and Sebastian's ZIPs. Bubble, Insertion, Selection, and Heap work with the real reader, parser, comparisons, timer, and output. Merge and Quick are still unfinished, so this is not ready for final submission.

Read [docs/IntegrationReport.md](docs/IntegrationReport.md) for the source of each part, fixes, verified checks, and remaining work.

Member 3 is currently unassigned; Merge and Quick remain unfinished. The original million-product dataset is included unchanged from Christian's ZIP. A development `Sort.jar` is included for testing the four completed sorts.

## Open it in Eclipse

1. Install a JDK 21, then add it under **Window > Preferences > Java > Installed JREs**. Under **Execution Environments**, select that JDK for **JavaSE-21**.
2. Clone this repo using GitHub Desktop, or download the ZIP and extract it.
3. In Eclipse, use **File > Import > General > Existing Projects into Workspace**.
4. Select the cloned or extracted folder containing `.project`. Import `OOP3-1`.
5. Run `src/appDomain/AppDriver.java` as a **Java Application**. Use `-fres/products1.txt -td -sz` in the Arguments tab to run Heap Sort.

There is also an `OOP3-1.launch` configuration with the smallest dataset and sample arguments. The working directory should be the project folder. Do not import just the `src` folder.

## Who does what

| Member | Work | Files |
| --- | --- | --- |
| 1 | Product model, seven product types, comparisons, and file reading | Everything in `src/products/`, plus `ProductFileReader.java` |
| 2 - Joaquin | Bubble, Insertion, Selection, output, and connecting the application | `BubbleSort.java`, `InsertionSort.java`, `SelectionSort.java`, `ResultPrinter.java`, `AppDriver.java` |
| 3 | Merge, Quick with Median-of-3, timer, JAR export, and final ZIPs | `MergeSort.java`, `QuickSort.java`, `BenchmarkRunner.java`; final packaging |
| 4 | Heap Sort, command-line parsing, and custom algorithm report | `HeapSort.java`, `CommandLineOptions.java`, `docs/CustomSortAnalysis.md` |

Other Java files listed above are in `src/utilities/ Members 1, 3, and 4 

This balances smaller classes, simpler sorts, harder sorts, writing, and integration. If someone gets stuck, ask in the group chat early so we can adjust the work.

## Rules for working separately

- Create your own branch before editing. Use `member-1`, `joaquin`, `member-3`, or `member-4`, or agree on names in chat.
- Only change your assigned files. Ask in chat before changing someone else's files or a shared method signature.
- Keep the public signatures already in the setup. Add private helper methods as needed.
- Keep `.project`, `.classpath`, and `.settings` in Git. These are needed to import the Eclipse project.
- Do not push generated `bin` files, your Eclipse workspace `.metadata`, or a finished JAR into the source repository.
- Commit small working changes. Push your branch and open a pull request into `main`.
- I will check the pull requests before merging. Pull the latest `main` into your branch regularly.
- Do not delete someone else's files or test data.
- Write useful Javadoc for every class and method. Update the starter descriptions to explain your actual implementation.

## Shared interfaces

Every sort uses this signature:

```java
public static <T> void sort(T[] items, Comparator<? super T> comparison)
```

It rearranges the same array in descending order. The comparator itself must follow its normal contract. No printing, timing, `Arrays.sort()`, or sorting ascending and then reversing.

Other shared methods:

```java
ProductFileReader.read(String path)                 // Product[], throws IOException
CommandLineOptions.parse(String[] args)              // CommandLineOptions
ResultPrinter.print(Product[] items)                 // void
BenchmarkRunner.measureNanos(Runnable sortingTask)    // long
```

The product constructor takes original price first, updated price second. Its getters return `double`. `calcRealDiff()` gives the adjusted absolute difference. `compareTo()` compares those differences using the normal Comparable contract.

The options object exposes `getFilePath()`, `getCompareType()`, and `getSortType()`. The parser must return lowercase type characters while keeping the file path unchanged.

## Testing without waiting for another member

Members 2, 3, and 4 can test their sorts using `Integer[]` with `Comparator.naturalOrder()`. The expected order is largest to smallest. This does not depend on the product classes being finished.

Member 1 can create products and test file reading directly. Member 4 can test argument parsing separately. Member 2 can test output with a local test subclass of Product that implements the difference calculation. Member 3 can test timing with a standalone Runnable. Keep temporary substitutes out of the production files.

Test empty arrays, one item, duplicates, already sorted arrays, and mixed values. After merging, also test actual products using all three comparisons. Compiling alone does not mean the assignment is complete.

## Assignment requirements

- Use a `Product[]` array, not an ArrayList. Read the first line to get its size
- Product adjustments: Jeans 0.5, TShirts 0.2, Skirts 0.1, Dresses 0.6, Shorts 0.3, Jackets 0.7, Shoes 0.4
- File options: `-f` followed directly by the file path
- Comparison options: `-to` original price, `-tu` updated price, `-td` adjusted difference
- Sort options: `-sb` Bubble, `-si` Insertion, `-ss` Selection, `-sm` Merge, `-sq` Quick, `-sz` Heap
- Options and type letters are case-insensitive and can appear in any order. Explain missing or invalid arguments clearly
- Member 1 reports file/data errors, Member 4 reports argument errors, and Member 2 handles expected errors in the driver with useful messages
- Read data and select the comparison before starting the timer. Print results after stopping it. Include a unit, such as milliseconds
- Each algorithm must support benchmarking through its selected `-s` option If we compare several runs, start each with the same unsorted input, and copy arrays outside the timed section
- For up to 10 products, print all. For 11 to 1,000, print first, last, and every hundredth in between. Above 1,000, use every thousandth instead. Use one-based row positions and avoid printing the last row twice
- Show product type, original price, updated price, and calculated differenc
- Heap Sort must be significantly different from the other five. Ask the instructor if there is any uncertainty about approval
- The custom sort report needs original explanation, pseudocode, operation counting for the steps, and complexity analysis

## Run the finished application

In Eclipse, open **Run > Run Configurations > Arguments** and use:

```text
-fres/products1.txt -td -sb
```

After exporting the finished runnable JAR:

```text
java -jar Sort.jar -fres/products1.txt -td -sb
java -jar Sort.jar -sQ -To -fres/products2.txt
```

The b, i, s, and z options work in this build. The m and q options report that their algorithms are unfinished

## Final submission

Member 3 handles packaging once everyone's code is merged and checked:

1. Run the application from Eclipse using Java 21. Check all six algorithms and all three comparisons
2. Use **File > Export > Java > Runnable JAR file**, select the AppDriver launch configuration, and name it `Sort.jar`
3. Run the JAR from a terminal and verify its output and argument errors
4. Zip the complete Eclipse project folder using File Explorer. Include `.project`, `.classpath`, `.settings`, `src`, and `res`. Do not zip only the source folder
5. Test importing that project ZIP into a fresh Eclipse workspace and run it.
6. Include `Sort.jar`, the complete project ZIP, the finished custom sort report, and the completed marking criteria in the final `A1<GroupName>.zip`
7. Only one person submits the group package, check Brightspace for the deadline


