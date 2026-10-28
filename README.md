# Resource Management System (Java)

A coursework-based Java program that simulates distributing a limited budget among university departments. It uses a priority queue to select departments, reads requested purchases from text files, and reports funded and unfunded items.

> **Portfolio status:** Private coursework archive. Check your course's academic-integrity and starter-code sharing rules before publishing. This repository includes assignment-provided driver/test data and may contain instructor-provided code.

## Concepts demonstrated
- Java classes and object-oriented programming
- `PriorityQueue` and `LinkedList`
- Reading structured text input using `Scanner`
- Budget allocation, conditional logic, and formatted reports

## Run locally
From the repository's root folder, with a Java JDK installed:

```bash
javac Driver.java ResourceManagement.java
java Driver
```

The driver runs three scenarios using the input files in `TestCase1/` and `TestCase2/`. Run from the root directory so the relative file paths resolve.

## Input format
Each department file starts with the department name, followed by one requested item per line:

```text
ExampleDepartment
100.00 , ExampleItem
250.00 , AnotherItem
```

## How the algorithm works
1. Read each department's requested items.
2. Choose a department from a priority queue ordered by its allocated spending.
3. Skip requested items that exceed the remaining budget.
4. Purchase an affordable item, or allocate a scholarship when no requested items remain.
5. Update the department's spending and repeat until the budget is exhausted.
6. Print a report of purchased and unpurchased items.

## Notes
This project was developed for coursework. The driver and data are provided as test fixtures. The allocation strategy is a simulation and not a production financial system. Floating-point `double` is used for currency in this version; a production application would use decimal-safe money handling.
