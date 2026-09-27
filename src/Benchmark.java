import java.util.Random;
public class Benchmark {
    static final int[] SIZES = {100, 1000, 10000, 100000};
    static final int REPEATS = 5;
    static final int ACCESS_COUNT = 10000;
    static final int OPERATION_COUNT = 1000;
    static Random random = new Random(42);
    public static void main(String[] args) {
        System.out.println("Starting benchmark...");
        runRandomAccessTest();
        runSearchTest();
        runInsertionRemovalTest();
        runHeapTest();
    }
    public static int[] generateRandomData(int n) {
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt(100000);
        }
        return data;
    }
    public static int[]generateIndexes(int n) {
        int[] indexes = new int[ACCESS_COUNT];

        for (int i = 0; i < ACCESS_COUNT; i++) {
            indexes[i] = random.nextInt(n);
        }
        return indexes;
    }

    public static int[] generateSearchValues() {
        int[] values = new int[ACCESS_COUNT];
        for (int i = 0; i < ACCESS_COUNT; i++) {
            values[i] = random.nextInt(100000);
        }
        return values;
    }


    // ==========================================
    // WORKLOAD 1
    // Random Access
    // ==========================================

    public static void runRandomAccessTest() {
        System.out.println();
        System.out.println("=== Random Access Test ===");
        System.out.println("n, DynamicArray(ns), LinkedList(ns)");
        for (int n : SIZES) {
            int[] data = generateRandomData(n);
            int[] indexes = generateIndexes(n);
            DynamicArray array = new DynamicArray();
            for (int x : data) {
                array.add(x);
            }
            LinkedList list = new LinkedList();
            for (int x : data) {
                list.add(x);
            }
            long arrayTotal = 0;
            long listTotal = 0;
            for (int repeat = 0; repeat < REPEATS; repeat++) {
                long start = System.nanoTime();
                int arrayResult = 0;
                for (int index : indexes) {
                    arrayResult += array.get(index);
                }
                long end = System.nanoTime();
                arrayTotal += end - start;
                start = System.nanoTime();
                int listResult = 0;
                for (int index : indexes) {
                    listResult += list.get(index);
                }
                end = System.nanoTime();
                listTotal += end - start;
            }
            double arrayAverage = arrayTotal / (double) REPEATS;
            double listAverage = listTotal / (double) REPEATS;
            System.out.println(
                    n + ", "
                            + arrayAverage + ", "
                            + listAverage
            );
        }
    }


    // ==========================================
    // WORKLOAD 2
    // Search
    // ==========================================

    public static void runSearchTest() {
        System.out.println();
        System.out.println("=== Search Test ===");
        System.out.println("n, DynamicArray(ns), LinkedList(ns)");
        for (int n : SIZES) {
            int[] data = generateRandomData(n);
            int[] searchValues = generateSearchValues();
            DynamicArray array = new DynamicArray();
            for (int x : data) {
                array.add(x);
            }
            LinkedList list = new LinkedList();
            for (int x : data) {
                list.add(x);
            }
            long arrayTotal = 0;
            long listTotal = 0;
            for (int repeat = 0; repeat < REPEATS; repeat++) {
                long start = System.nanoTime();
                int arrayFound = 0;
                for (int value : searchValues) {
                    if (array.contains(value)) {
                        arrayFound++;
                    }
                }
                long end = System.nanoTime();
                arrayTotal += end - start;
                start = System.nanoTime();
                int listFound = 0;
                for (int value : searchValues) {
                    if (list.contains(value)) {
                        listFound++;
                    }
                }
                end = System.nanoTime();
                listTotal += end - start;
            }
            double arrayAverage = arrayTotal / (double) REPEATS;
            double listAverage = listTotal / (double) REPEATS;
            System.out.println(
                    n + ", "
                            + arrayAverage + ", "
                            + listAverage
            );
        }
    }


    // ==========================================
    // WORKLOAD 3
    // Insertion and Removal
    // ==========================================

    public static void runInsertionRemovalTest() {
        System.out.println();
        System.out.println("=== Insertion and Removal Test ===");
        System.out.println(
                "n, " +
                        "ArrayInsertBeginning(ns), " +
                        "ArrayRemoveBeginning(ns), " +
                        "ArrayInsertMiddle(ns), " +
                        "ArrayRemoveMiddle(ns), " +
                        "ListInsertBeginning(ns), " +
                        "ListRemoveBeginning(ns), " +
                        "ListInsertMiddle(ns), " +
                        "ListRemoveMiddle(ns)"
        );
        for (int n : SIZES) {
            long arrayInsertBeginningTotal = 0;
            long arrayRemoveBeginningTotal = 0;
            long arrayInsertMiddleTotal = 0;
            long arrayRemoveMiddleTotal = 0;
            long listInsertBeginningTotal = 0;
            long listRemoveBeginningTotal = 0;
            long listInsertMiddleTotal = 0;
            long listRemoveMiddleTotal = 0;
            for (int repeat = 0; repeat < REPEATS; repeat++) {
                // ==================================
                // Dynamic Array
                // ==================================

                DynamicArray array = new DynamicArray();
                for (int i = 0; i < n; i++) {
                    array.add(i);
                }
                long start = System.nanoTime();
                for (int i = 0; i < OPERATION_COUNT; i++) {
                    array.add(0, i);
                }
                long end = System.nanoTime();
                arrayInsertBeginningTotal += end - start;
                start = System.nanoTime();
                for (int i = 0; i < OPERATION_COUNT; i++) {
                    array.remove(0);
                }
                end = System.nanoTime();
                arrayRemoveBeginningTotal += end - start;

                array = new DynamicArray();
                for (int i = 0; i < n; i++) {
                    array.add(i);
                }
                int middle = n / 2;
                start = System.nanoTime();
                for (int i = 0; i < OPERATION_COUNT; i++) {
                    array.add(middle, i);
                }
                end = System.nanoTime();
                arrayInsertMiddleTotal += end - start;
                start = System.nanoTime();
                for (int i = 0; i < OPERATION_COUNT; i++) {
                    array.remove(middle);
                }
                end = System.nanoTime();
                arrayRemoveMiddleTotal += end - start;
                LinkedList list = new LinkedList();
                for (int i = 0; i < n; i++) {
                    list.add(i);
                }
                start = System.nanoTime();
                for (int i = 0; i < OPERATION_COUNT; i++) {
                    list.add(0, i);
                }
                end = System.nanoTime();
                listInsertBeginningTotal += end - start;
                start = System.nanoTime();
                for (int i = 0; i < OPERATION_COUNT; i++) {
                    list.remove(0);
                }
                end = System.nanoTime();
                listRemoveBeginningTotal += end - start;
                list = new LinkedList();
                for (int i = 0; i < n; i++) {
                    list.add(i);
                }
                start = System.nanoTime();
                for (int i = 0; i < OPERATION_COUNT; i++) {
                    list.add(middle, i);
                }
                end = System.nanoTime();
                listInsertMiddleTotal += end - start;

                start = System.nanoTime();
                for (int i = 0; i < OPERATION_COUNT; i++) {
                    list.remove(middle);
                }
                end = System.nanoTime();
                listRemoveMiddleTotal += end - start;
            }

            double arrayInsertBeginningAverage =
                    arrayInsertBeginningTotal / (double) REPEATS;
            double arrayRemoveBeginningAverage =
                    arrayRemoveBeginningTotal / (double) REPEATS;
            double arrayInsertMiddleAverage =
                    arrayInsertMiddleTotal / (double) REPEATS;
            double arrayRemoveMiddleAverage =
                    arrayRemoveMiddleTotal / (double) REPEATS;
            double listInsertBeginningAverage =
                    listInsertBeginningTotal / (double) REPEATS;
            double listRemoveBeginningAverage =
                    listRemoveBeginningTotal / (double) REPEATS;
            double listInsertMiddleAverage =
                    listInsertMiddleTotal / (double) REPEATS;
            double listRemoveMiddleAverage =
                    listRemoveMiddleTotal / (double) REPEATS;
            System.out.println(
                    n + ", "
                            + arrayInsertBeginningAverage + ", "
                            + arrayRemoveBeginningAverage + ", "
                            + arrayInsertMiddleAverage + ", "
                            + arrayRemoveMiddleAverage + ", "
                            + listInsertBeginningAverage + ", "
                            + listRemoveBeginningAverage + ", "
                            + listInsertMiddleAverage + ", "
                            + listRemoveMiddleAverage
            );
        }
    }
        // ==========================================
        // WORKLOAD 4
        // Min-Heap Priority Processing
        // ==========================================

    public static void runHeapTest() {
        System.out.println();
        System.out.println("=== Min-Heap Test ===");
        System.out.println(
                "n, Insert(ns), ExtractMin(ns), " +
                        "InsertComparisons, ExtractComparisons, Sorted"
        );
        for (int n : SIZES) {
            long insertTotal = 0;
            long extractTotal = 0;
            long insertComparisonsTotal = 0;
            long extractComparisonsTotal = 0;
            boolean allSorted = true;
            for (int repeat = 0; repeat < REPEATS; repeat++) {
                // Generate data before timing
                int[] data = generateRandomData(n);
                MinHeap heap = new MinHeap();
                // -----------------------------
                // Insert
                // -----------------------------
                long start = System.nanoTime();
                for (int value : data) {
                    heap.insert(value);
                }
                long end = System.nanoTime();
                insertTotal += end - start;
                insertComparisonsTotal += heap.getComparisons();
                // -----------------------------
                // Extract Min
                // -----------------------------
                heap.resetComparisons();
                start = System.nanoTime();
                int previous = Integer.MIN_VALUE;
                for (int i = 0; i < n; i++) {
                    int current = heap.extractMin();
                    if (current < previous) {
                        allSorted = false;
                    }
                    previous = current;
                }
                end = System.nanoTime();
                extractTotal += end - start;
                extractComparisonsTotal += heap.getComparisons();
            }
            double insertAverage =
                    insertTotal / (double) REPEATS;
            double extractAverage =
                    extractTotal / (double) REPEATS;
            double insertComparisonsAverage =
                    insertComparisonsTotal / (double) REPEATS;
            double extractComparisonsAverage =
                    extractComparisonsTotal / (double) REPEATS;
            System.out.println(
                    n + ", "
                            + insertAverage + ", "
                            + extractAverage + ", "
                            + insertComparisonsAverage + ", "
                            + extractComparisonsAverage + ", "
                            + allSorted
            );
        }
    }
}