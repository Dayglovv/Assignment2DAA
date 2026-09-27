public class MinHeap {
    private int[] heap;
    private int size;
    public MinHeap() {
        heap = new int[10];
        size = 0;
    }

    public void insert(int x) {
        if (size == heap.length) {
            int[] newHeap = new int[heap.length * 2];
            for (int i = 0; i < heap.length; i++) {
                newHeap[i] = heap[i];
            }
            heap = newHeap;
        }

        heap[size] = x;
        int current = size;
        size++;
        while (current > 0) {
            int parent = (current - 1) / 2;
            if (heap[parent] <= heap[current]) {
                break;
            }
            int temp = heap[parent];
            heap[parent] = heap[current];
            heap[current] = temp;
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
            int smallest = current;
            if (left < size && heap[left] < heap[smallest]) {
                smallest = left;
            }
            if (right < size && heap[right] < heap[smallest]) {
                smallest = right;
            }
            if (smallest == current) {
                break;
            }
            int temp = heap[current];
            heap[current] = heap[smallest];
            heap[smallest] = temp;
            current = smallest;
        }
        return min;
    }

    public int size() {
        return size;
    }
}