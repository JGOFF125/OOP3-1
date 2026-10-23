# Heap sort analysis

Owner: Sebastian Pedersen

## How it works

Heap sort stores the heap directly in the array. No extra tree is needed. For an element at index i, the children are at 2i + 1 and 2i + 2. In a min-heap, each parent is less than or equal to its children, so the smallest value is at index 0.

We want the array in descending order, so we use a min-heap:

1. Build the heap. Start at the last parent (n/2 - 1) and go back to index 0, sifting each parent down until the heap rule holds.
2. Swap the root with the last element of the heap. The smallest value is now in its final place at the back. Make the heap one smaller, then sift the new root down.
3. Repeat until one element is left. The small values end up at the back, leaving the large ones at the front.

Sorting [4, 1, 3, 2] this way gives [4, 3, 2, 1]. The comparator decides what counts as smaller, so the same code works with Product.compareTo() or either price comparator.

## Pseudocode

```
HEAPSORT(A, n)                       // A is 0-indexed; cmp is the comparator
1   for p = floor(n/2) - 1 down to 0
2       SIFTDOWN(A, p, n)
3   for last = n - 1 down to 1
4       swap A[0] and A[last]
5       SIFTDOWN(A, 0, last)

SIFTDOWN(A, root, size)
1   loop
2       left = 2*root + 1
3       if left >= size then return
4       smallest = left
5       if left + 1 < size and cmp(A[left+1], A[left]) < 0
6           smallest = left + 1
7       if cmp(A[smallest], A[root]) >= 0 then return
8       swap A[root] and A[smallest]
9       root = smallest
```

## Operation counting

I counted two things: comparisons (calls to cmp) and swaps (three assignments each). Everything else takes constant time.
Each level of SIFTDOWN does at most two comparisons and one swap, and it goes down at most floor(log2 size) levels.

**Operations by pseudocode line**

The line numbers below refer to the pseudocode above.

`HEAPSORT`:

| Line | Operation | Times it runs |
| --- | --- | --- |
| 1 | Loop over the parents | `floor(n/2)` iterations |
| 2 | Call `SIFTDOWN` | `floor(n/2)` calls |
| 3 | Loop over the last positions | `n - 1` iterations |
| 4 | Swap `A[0]` and `A[last]` | `n - 1` swaps |
| 5 | Call `SIFTDOWN` | `n - 1` calls |

`SIFTDOWN`, for one call where the value moves down `d` levels (`d` is at most
`floor(log2 size)`). Its loop runs at most `d + 1` times. The last pass returns instead of swapping.

| Line | Operation | Times it runs (per call) |
| --- | --- | --- |
| 2 | Calculate `left` | At most `d + 1` |
| 3 | Check whether `root` is a leaf | At most `d + 1` |
| 4 | Set `smallest = left` | At most `d + 1` |
| 5, 6 | Compare the children and possibly update `smallest` | At most `d + 1` comparisons |
| 7 | Compare `smallest` with `root` | At most `d + 1` comparisons |
| 8 | Swap | Exactly `d` swaps |
| 9 | Set `root = smallest` | Exactly `d` times |

On the last pass, the loop either reaches a leaf (line 3) or stops when the heap rule holds (line 7).
So a call that starts at a node of height `k` makes at most `2k` comparisons and `k` swaps.

| Step | How often | Cost | Total (worst case) |
| --- | --- | --- | --- |
| Build the heap | `floor(n/2)` sift-downs | At most `2k` comparisons and `k` swaps for a node of height `k` | See below |
| Swap during extraction | `n - 1` times | 1 swap | `n - 1` swaps |
| Sift down during extraction | `n - 1` times | At most `2 floor(log2 m)` comparisons and `floor(log2 m)` swaps, where `m` is the heap size | At most `2(n-1) floor(log2 n)` comparisons |

**Building the heap**

The number of nodes with height at least `k` is at most `floor(n / 2^k)`.
Adding these counts for `k = 1, 2, ...` gives the total possible downward levels:

```
total levels <= floor(n/2) + floor(n/4) + floor(n/8) + ... < n
comparisons  <= 2 * total levels < 2n
swaps       <= total levels < n
```

Building the heap takes **O(n)** time, even though it does about `n/2` sift-downs.

**Extracting the values**

There are `n - 1` extractions. Each uses one swap and a sift-down with at most
`2 floor(log2 n)` comparisons:

```
comparisons <= 2 (n - 1) log2 n
swaps       <= (n - 1) + (n - 1) log2 n
```

**Total in the worst case**

```
comparisons <= 2n + 2 n log2 n        swaps <= n + (n - 1)(1 + log2 n)
```

Both counts are O(n log n).

**Measured comparisons**

These counts were measured with `Product[]` containing Jeans objects and the actual
`OriginalPriceComparator`. For each size, the test starts a new `Random(304)` and uses
`nextInt(1000000)` for the original price and zero for the updated price. A test-only
wrapper counts comparator calls; it does not change the comparison result. Output
validation happens after sorting and does not add to the measured count.

Run `HeapMeasurementChecks` to reproduce the table. These are operation counts,
not elapsed sorting times; the application timer remains outside HeapSort.

| n | Measured comparisons | Bound `ceil(2n + 2n·log2(n))` | Measured / (`n·log2 n`) |
| --- | --- | --- | --- |
| 10 | 38 | 87 | 1.14 |
| 100 | 1,030 | 1,529 | 1.55 |
| 1,000 | 16,834 | 21,932 | 1.69 |
| 10,000 | 235,214 | 285,755 | 1.77 |
| 100,000 | 3,019,344 | 3,521,929 | 1.82 |
| 1,000,000 | 36,793,676 | 41,863,138 | 1.85 |

The measured counts stay under the bound and grow like n log n. These are test results, not proof.

## Time complexity

| Case | Time | Reason |
| --- | --- | --- |
| Worst | O(n log n) | There are `n - 1` extractions. Each sift-down takes at most `log2 n` levels. |
| Average | O(n log n) | Random test data used about `1.85 · n · log2 n` comparisons (see table). |
| Best | O(n) | When all values are equal, sift-down stops at its first check: about `n` comparisons to build the heap and `2n` to extract. With distinct values, the best case is still O(n log n). |

The O(n log n) upper bound holds for any input order. The exact counts can still change
when the input order changes. Quick sort, on the other hand, can take O(n²) on some inputs.

## Space complexity

Heap sort uses O(1) extra space. It rearranges the array in place with swaps and a few temporary variables. It is not stable, so equal values may not keep their original order.

## Why this algorithm is different

- **Bubble, Insertion, and Selection Sort** all have O(n²) worst-case time. Our descending Selection Sort picks the largest remaining value each time, but it scans the whole unsorted part to find it. Heap sort keeps the values in a heap instead: it gets the smallest in O(1) and fixes the heap in O(log n).
- **Merge Sort** splits the data and merges it back using a temporary array, which needs O(n) extra space. Heap sort works in place and does not use recursion.
- **Quick Sort** splits around a pivot but can take O(n²) in the worst case. Heap sort has an O(n log n) worst-case bound.
- Heap sort is also the only one here that stores a tree inside the array itself.

## Research references

- Cormen, Leiserson, Rivest, and Stein, Introduction to Algorithms, 4th ed. (MIT Press). Chapter 6.
- J. W. J. Williams, “Algorithm 232: Heapsort,” Communications of the ACM, 7(6), 1964. (ACM Digital Library)
- R. W. Floyd, “Algorithm 245: Treesort,” Communications of the ACM, 7(12), 1964. (ACM Digital Library)
