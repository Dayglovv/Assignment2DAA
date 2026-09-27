public class MinHeap {
    private int[] heap;
    private int size;
    private long comparisons;
    public MinHeap() {
        heap = new int[10];
        size = 0;
        comparisons = 0;
    }

    public void insert(int value) {
        if (size == heap.length) {
            int[] newHeap = new int[heap.length * 2];
            for (int i = 0; i < heap.length; i++) {
                newHeap[i] = heap[i];
            }
            heap = newHeap;
        }
        heap[size] = value;
        int current = size;
        size++;
        while (current > 0) {
            int parent = (current - 1) / 2;
            comparisons++;
            if (heap[current] >= heap[parent]) {
                break;
            }
            int temp = heap[current];
            heap[current] = heap[parent];
            heap[parent] = temp;
            current = parent;
        }
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        return heap[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        int min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        int current = 0;
        while (true) {
            int left = current * 2 + 1;
            int right = current * 2 + 2;
            if (left >= size) {
                break;
            }
            int smallerChild = left;
            if (right < size) {
                comparisons++;
                if (heap[right] < heap[left]) {
                    smallerChild = right;
                }
            }
            comparisons++;
            if (heap[current] <= heap[smallerChild]) {
                break;
            }
            int temp = heap[current];
            heap[current] = heap[smallerChild];
            heap[smallerChild] = temp;
            current = smallerChild;
        }
        return min;
    }
    public int size() {
        return size;
    }
    public long getComparisons() {
        return comparisons;
    }
    public void resetComparisons() {
        comparisons = 0;
    }
}