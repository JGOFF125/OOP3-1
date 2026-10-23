# Submission checklist

The source and runnable JAR can be checked before the group signs off. The group
still needs to finish the evaluation together and verify the actual uploaded ZIP.

## Prepared files

- Sort.jar with appDomain.AppDriver as the entry point
- OOP3-1-EclipseProject.zip containing the full project, Eclipse metadata, source,
  all three datasets, checks, and documentation
- HeapSortAnalysis.txt containing the custom-sort explanation, pseudocode,
  operation counts, and complexity analysis
- MarkingCriteria_Assignment1.docx supplied by the instructor, left blank for
  the group to evaluate together

## Group sign-off

1. Have every group member present and review the marking criteria row by row
2. Enter the group name and agreed marks and notes in the provided document
3. We review the report and the reproducible Product measurements together
   and confirm that the explanation is our own work
4. Make sure every member can explain their code and confirm permitted assistance
   with the instructor where needed
5. Import the nested Eclipse project ZIP into a fresh workspace with JDK 21
6. Run AppDriver with all six algorithms and all three comparisons on products1
   and products2; check Merge, Quick, and Heap on products3
7. Run the included Sort.jar from a terminal with the extracted project as the
   working directory, using -fres/products1.txt -td -sq
8. Verify argument errors and a valid input path containing spaces
9. Replace the blank marking document with the completed group evaluation
10. Rename the final outer ZIP to A1 followed by the actual group name

The full million-row input has not been completed with Bubble, Insertion, or
Selection. Their quadratic worst-case cost makes a full run expensive. Do not
claim those full runs passed. All six are checked on smaller data and edge cases.

## After uploading

Every member must download the actual last submitted ZIP from D2L, extract it,
and check the project, JAR, report, evaluation, and file contents independently.
Tell the group immediately if anything differs from the approved package. Do
not rely on being able to resubmit: the assignment says no late assignments or
resubmissions are accepted. Follow the instructor's current submission rules.

Each member must also complete the separate peer assessment as instructed.
The prepared review package does not certify group attendance, peer evaluation,
Eclipse GUI import, or D2L verification.
