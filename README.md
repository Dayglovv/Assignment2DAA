# Assignment 2

## 3. Correctness

### Proof 1: DynamicArray.add(index, x)

**Loop invariant:** Before every iteration of the loop, all elements after the current index have been moved one position to the right, and the elements before the current index have not been changed.

**Initialization:** At the beginning of the loop, `i` is equal to `size`. No elements have been moved yet, so the invariant is true.

**Maintenance:** During every iteration, `data[i - 1]` is copied to `data[i]`. This moves one element one position to the right. Then `i` decreases by one, so the invariant remains true.

**Termination:** The loop stops when `i` becomes equal to `index`. All elements from `index` to the end have been moved one position to the right.

After the loop, `data[index] = x` places the new element in the correct position. Therefore, `add(index, x)` correctly inserts the element without losing existing elements.

---

### Proof 2: MinHeap.insert(x)

**Loop invariant:** Before every iteration, the heap is a valid Min-Heap except that the newly inserted element may be smaller than its parent.

**Initialization:** The new element is added at the end of the heap. The existing elements already satisfy the Min-Heap property, so the only possible violation is between the new element and its parent.

**Maintenance:** If the parent is smaller than or equal to the current element, the Min-Heap property is already satisfied. Otherwise, the current element and its parent are swapped. The smaller element moves upward, and the algorithm continues checking the new parent.

**Termination:** The loop stops when the current element reaches the root or its parent is smaller than or equal to it.

Therefore, after `insert(x)`, the Min-Heap property is restored and the heap remains correct.

---

## 4. Complexity Analysis

### Dynamic Array

| Operation       | Best Case |   Average Case | Worst Case |
| --------------- | --------: | -------------: | ---------: |
| `add(x)`        |      Ω(1) | Θ(1) amortized |       O(n) |
| `add(index, x)` |      Ω(1) |           Θ(n) |       O(n) |
| `remove(index)` |      Ω(1) |           Θ(n) |       O(n) |
| `get(index)`    |      Ω(1) |           Θ(1) |       O(1) |
| `contains(x)`   |      Ω(1) |           Θ(n) |       O(n) |

### Linked List

| Operation       | Best Case | Average Case | Worst Case |
| --------------- | --------: | -----------: | ---------: |
| `add(x)`        |      Ω(1) |         Θ(n) |       O(n) |
| `add(index, x)` |      Ω(1) |         Θ(n) |       O(n) |
| `remove(index)` |      Ω(1) |         Θ(n) |       O(n) |
| `get(index)`    |      Ω(1) |         Θ(n) |       O(n) |
| `contains(x)`   |      Ω(1) |         Θ(n) |       O(n) |

### Min-Heap

| Operation      | Best Case | Average Case | Worst Case |
| -------------- | --------: | -----------: | ---------: |
| `insert(x)`    |      Ω(1) |     Θ(log n) |   O(log n) |
| `peekMin()`    |      Ω(1) |         Θ(1) |       O(1) |
| `extractMin()` |      Ω(1) |     Θ(log n) |   O(log n) |
