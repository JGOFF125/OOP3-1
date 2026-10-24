# My testing notes

I use these checks to make sure the sorts, output, and command-line options work
together. I keep them outside src so they are not part of the runnable application.

## How I run the checks

I open Git Bash in the project folder with JDK 21 and run:

```bash
mkdir -p build/checks
javac --release 21 -d build/checks src/products/*.java src/utilities/*.java src/appDomain/*.java tests/*.java
java -Xmx768m -cp build/checks JoaquinChecks
java -Xmx768m -cp build/checks IntegrationChecks
java -Xmx768m -cp build/checks HeapMeasurementChecks
```

I check empty arrays, one product, duplicates, repeated objects, random inputs,
and arrays that start in ascending or descending order. I also check the printed
row positions, invalid inputs, and argument order and case.

I use the actual price comparators and Product.compareTo for the calculated
difference. I use Arrays.sort only inside the checks to get the expected result.
I do not use it inside any sorting algorithm.

## What I check for descending order

I check that each product is greater than or equal to the next one according to
the selected comparison. I also check that no products are lost or added.

I use the recorded test coverage below when reviewing the submission:

| Dataset | Sorts | Comparisons | Recorded result |
| --- | --- | --- | --- |
| products1, 10 products | All six | Original, updated, difference | Passed |
| products2, 1000 products | All six | Original, updated, difference | Passed |
| products3, 1000000 products | Merge, Quick, Heap | Original, updated, difference | Passed |

I have no completed full-million test result for Bubble, Insertion, or Selection.
I still need to check the final project in a fresh Eclipse workspace and verify
the actual ZIP downloaded from D2L.

## JUnit checks

I run test/products/ProductTest.java in Eclipse by adding test as a source folder
and JUnit 5 to the build path. I keep JUnit outside the application source path
so the runnable JAR does not need it.
