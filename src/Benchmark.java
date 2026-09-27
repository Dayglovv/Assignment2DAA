import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
public class Benchmark {
    static final int[] SIZES = {100, 1000, 10000, 100000};
    static final int REPEATS = 5;
    static final int ACCESS_COUNT = 10000;
    static final int OPERATION_COUNT = 1000;
    static final String RESULTS_DIR = "results/tables";
    static Random random = new Random(42);
    public static void main(String[] args) {
        System.out.println("Starting benchmark...");
        createResultsDirectory();
        runRandomAccessTest();
        runSearchTest();
        runInsertionRemovalTest();
        runHeapTest();
        System.out.println();
        System.out.println("Benchmark finished.");
        System.out.println("CSV files saved to: " + RESULTS_DIR);
    }
    // =========================================================
    // Create results/tables directory
    // =========================================================
    public static void createResultsDirectory() {
        File directory = new File(RESULTS_DIR);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Created directory: " + RESULTS_DIR);
            } else {
                System.out.println("Could not create directory: " + RESULTS_DIR);
            }
        }
    }
    // =========================================================
    // Generate random data
    // =========================================================

    public static int[] generateRandomData(int n) {
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt(100000);
        }
        return data;
    }

    // =========================================================
    // Generate random indexes
    // =========================================================
    public static int[] generateIndexes(int n) {
        int[] indexes = new int[ACCESS_COUNT];
        for (int i = 0; i < ACCESS_COUNT; i++) {
            indexes[i] = random.nextInt(n);
        }
        return indexes;
    }
    // =========================================================
    // Generate search values
    // =========================================================
    public static int[] generateSearchValues() {
        int[] values = new int[ACCESS_COUNT];
        for (int i = 0; i < ACCESS_COUNT; i++) {
            values[i] = random.nextInt(100000);
        }
        return values;
    }
    // =========================================================
    // WORKLOAD 1
    // Random Access
    // =========================================================
    public static void runRandomAccessTest() {
        System.out.println();
        System.out.println("=== Workload 1: Random Access ===");
        String fileName = RESULTS_DIR + "/workload1_random_access.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("n,dynamic_array_ns,linked_list_ns");
            for (int n : SIZES) {
                int[] data = generateRandomData(n);
                int[] indexes = generateIndexes(n);
                DynamicArray array = new DynamicArray();
                for (int x : data) {array.add(x);}
                LinkedList list = new LinkedList();
                for (int x : data) {list.add(x);}
                long arrayTotal = 0;
                long listTotal = 0;
                for (int repeat = 0; repeat < REPEATS; repeat++) {
                    long start = System.nanoTime();
                    int arrayResult = 0;
                    for (int index : indexes) {arrayResult += array.get(index);}
                    long end = System.nanoTime();
                    arrayTotal += end - start;
                    start = System.nanoTime();
                    int listResult = 0;
                    for (int index : indexes) {
                        listResult += list.get(index);
                    }
                    end = System.nanoTime();
                    listTotal += end - start;
                    // Prevent the compiler from considering
                    // the calculations completely unused.
                    if (arrayResult == Integer.MIN_VALUE ||
                            listResult == Integer.MIN_VALUE) {
                        System.out.print("");
                    }
                }
                double arrayAverage = arrayTotal / (double) REPEATS;
                double listAverage = listTotal / (double) REPEATS;
                writer.println(n + "," + arrayAverage + "," + listAverage);
                System.out.println(n + " -> DynamicArray: " + arrayAverage + " ns, LinkedList: " + listAverage + " ns");
            }
        } catch (IOException e) {
            System.out.println("Error writing Workload 1 CSV: " + e.getMessage());
        }
        System.out.println("Saved: " + fileName);
    }


    // =========================================================
    // WORKLOAD 2
    // Search
    // =========================================================
    public static void runSearchTest() {
        System.out.println();
        System.out.println("=== Workload 2: Search ===");
        String fileName = RESULTS_DIR + "/workload2_search.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("n,dynamic_array_ns,linked_list_ns");
            for (int n : SIZES) {int[] data = generateRandomData(n);
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
                    if (arrayFound == Integer.MIN_VALUE ||
                            listFound == Integer.MIN_VALUE) {
                        System.out.print("");
                    }
                }
                double arrayAverage = arrayTotal / (double) REPEATS;
                double listAverage = listTotal / (double) REPEATS;
                writer.println(n + "," + arrayAverage + "," + listAverage);
                System.out.println(n + " -> DynamicArray: " + arrayAverage + " ns, LinkedList: " +                         listAverage + " ns");
            }

        } catch (IOException e) {
            System.out.println("Error writing Workload 2 CSV: " + e.getMessage());
        }
        System.out.println("Saved: " + fileName);
    }
    // =========================================================
    // WORKLOAD 3
    // Insertion and Removal
    // =========================================================
    public static void runInsertionRemovalTest() {
        System.out.println();
        System.out.println("=== Workload 3: Insertion and Removal ===");
        String fileName = RESULTS_DIR + "/workload3_insertion_removal.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("n," + "array_insert_beginning_ns," + "array_remove_beginning_ns," + "array_insert_middle_ns," + "array_remove_middle_ns," + "list_insert_beginning_ns," + "list_remove_beginning_ns," + "list_insert_middle_ns," + "list_remove_middle_ns," + "array_insert_beginning_movements," + "array_remove_beginning_movements," + "array_insert_middle_movements," + "array_remove_middle_movements," + "list_insert_beginning_accesses," + "list_remove_beginning_accesses," +"list_insert_middle_accesses," +"list_remove_middle_accesses");
            for (int n : SIZES) {
                long arrayInsertBeginningTotal = 0;
                long arrayRemoveBeginningTotal = 0;
                long arrayInsertMiddleTotal = 0;
                long arrayRemoveMiddleTotal = 0;
                long listInsertBeginningTotal = 0;
                long listRemoveBeginningTotal = 0;
                long listInsertMiddleTotal = 0;
                long listRemoveMiddleTotal = 0;
                long arrayInsertBeginningMovementsTotal = 0;
                long arrayRemoveBeginningMovementsTotal = 0;
                long arrayInsertMiddleMovementsTotal = 0;
                long arrayRemoveMiddleMovementsTotal = 0;
                long listInsertBeginningAccessesTotal = 0;
                long listRemoveBeginningAccessesTotal = 0;
                long listInsertMiddleAccessesTotal = 0;
                long listRemoveMiddleAccessesTotal = 0;
                for (int repeat = 0; repeat < REPEATS; repeat++) {
                    // =========================================
                    // Dynamic Array
                    // =========================================
                    DynamicArray array = new DynamicArray();
                    for (int i = 0; i < n; i++) {
                        array.add(i);
                    }
                    // Insert at beginning
                    array.resetMovements();
                    long start = System.nanoTime();
                    for (int i = 0; i < OPERATION_COUNT; i++) {
                        array.add(0, i);
                    }
                    long end = System.nanoTime();
                    arrayInsertBeginningTotal += end - start;
                    arrayInsertBeginningMovementsTotal +=
                            array.getMovements();
                    // Remove at beginning
                    array.resetMovements();
                    start = System.nanoTime();
                    for (int i = 0; i < OPERATION_COUNT; i++) {
                        array.remove(0);
                    }
                    end = System.nanoTime();
                    arrayRemoveBeginningTotal += end - start;
                    arrayRemoveBeginningMovementsTotal +=
                            array.getMovements();
                    // Restore array
                    array = new DynamicArray();
                    for (int i = 0; i < n; i++) {
                        array.add(i);
                    }
                    int middle = n / 2;
                    // Insert in middle
                    array.resetMovements();
                    start = System.nanoTime();
                    for (int i = 0; i < OPERATION_COUNT; i++) {
                        array.add(middle, i);
                    }
                    end = System.nanoTime();
                    arrayInsertMiddleTotal += end - start;
                    arrayInsertMiddleMovementsTotal +=
                            array.getMovements();
                    // Remove in middle
                    array.resetMovements();
                    start = System.nanoTime();
                    for (int i = 0; i < OPERATION_COUNT; i++) {
                        array.remove(middle);
                    }
                    end = System.nanoTime();
                    arrayRemoveMiddleTotal += end - start;
                    arrayRemoveMiddleMovementsTotal +=
                            array.getMovements();
                    // =========================================
                    // Linked List
                    // =========================================
                    LinkedList list = new LinkedList();
                    for (int i = 0; i < n; i++) {
                        list.add(i);
                    }
                    // Insert at beginning
                    list.resetAccesses();
                    start = System.nanoTime();
                    for (int i = 0; i < OPERATION_COUNT; i++) {
                        list.add(0, i);
                    }
                    end = System.nanoTime();
                    listInsertBeginningTotal += end - start;
                    listInsertBeginningAccessesTotal +=
                            list.getAccesses();
                    // Remove at beginning
                    list.resetAccesses();
                    start = System.nanoTime();
                    for (int i = 0; i < OPERATION_COUNT; i++) {
                        list.remove(0);
                    }
                    end = System.nanoTime();
                    listRemoveBeginningTotal += end - start;
                    listRemoveBeginningAccessesTotal +=
                            list.getAccesses();
                    // Restore list
                    list = new LinkedList();
                    for (int i = 0; i < n; i++) {
                        list.add(i);
                    }
                    // Insert in middle
                    list.resetAccesses();
                    start = System.nanoTime();
                    for (int i = 0; i < OPERATION_COUNT; i++) {
                        list.add(middle, i);
                    }
                    end = System.nanoTime();
                    listInsertMiddleTotal += end - start;
                    listInsertMiddleAccessesTotal +=
                            list.getAccesses();
                    // Remove in middle
                    list.resetAccesses();
                    start = System.nanoTime();
                    for (int i = 0; i < OPERATION_COUNT; i++) {
                        list.remove(middle);
                    }
                    end = System.nanoTime();
                    listRemoveMiddleTotal += end - start;
                    listRemoveMiddleAccessesTotal +=
                            list.getAccesses();
                }
                // =========================================
                // Calculate averages
                // =========================================
                double arrayInsertBeginningAverage =
                        arrayInsertBeginningTotal /
                                (double) REPEATS;
                double arrayRemoveBeginningAverage =
                        arrayRemoveBeginningTotal /
                                (double) REPEATS;
                double arrayInsertMiddleAverage =
                        arrayInsertMiddleTotal /
                                (double) REPEATS;
                double arrayRemoveMiddleAverage =
                        arrayRemoveMiddleTotal /
                                (double) REPEATS;
                double listInsertBeginningAverage =
                        listInsertBeginningTotal /
                                (double) REPEATS;
                double listRemoveBeginningAverage =
                        listRemoveBeginningTotal /
                                (double) REPEATS;
                double listInsertMiddleAverage =
                        listInsertMiddleTotal /
                                (double) REPEATS;
                double listRemoveMiddleAverage =
                        listRemoveMiddleTotal /
                                (double) REPEATS;
                double arrayInsertBeginningMovementsAverage =
                        arrayInsertBeginningMovementsTotal /
                                (double) REPEATS;

                double arrayRemoveBeginningMovementsAverage =
                        arrayRemoveBeginningMovementsTotal /
                                (double) REPEATS;

                double arrayInsertMiddleMovementsAverage =
                        arrayInsertMiddleMovementsTotal /
                                (double) REPEATS;

                double arrayRemoveMiddleMovementsAverage =
                        arrayRemoveMiddleMovementsTotal /
                                (double) REPEATS;


                double listInsertBeginningAccessesAverage =
                        listInsertBeginningAccessesTotal /
                                (double) REPEATS;

                double listRemoveBeginningAccessesAverage =
                        listRemoveBeginningAccessesTotal /
                                (double) REPEATS;

                double listInsertMiddleAccessesAverage =
                        listInsertMiddleAccessesTotal /
                                (double) REPEATS;

                double listRemoveMiddleAccessesAverage =
                        listRemoveMiddleAccessesTotal /
                                (double) REPEATS;


                // =========================================
                // Write CSV
                // =========================================

                writer.println(
                        n + "," +

                                arrayInsertBeginningAverage + "," +
                                arrayRemoveBeginningAverage + "," +
                                arrayInsertMiddleAverage + "," +
                                arrayRemoveMiddleAverage + "," +

                                listInsertBeginningAverage + "," +
                                listRemoveBeginningAverage + "," +
                                listInsertMiddleAverage + "," +
                                listRemoveMiddleAverage + "," +

                                arrayInsertBeginningMovementsAverage + "," +
                                arrayRemoveBeginningMovementsAverage + "," +
                                arrayInsertMiddleMovementsAverage + "," +
                                arrayRemoveMiddleMovementsAverage + "," +

                                listInsertBeginningAccessesAverage + "," +
                                listRemoveBeginningAccessesAverage + "," +
                                listInsertMiddleAccessesAverage + "," +
                                listRemoveMiddleAccessesAverage
                );


                System.out.println(
                        "n = " + n + " completed."
                );
            }

        } catch (IOException e) {

            System.out.println(
                    "Error writing Workload 3 CSV: " +
                            e.getMessage()
            );
        }

        System.out.println(
                "Saved: " + fileName
        );
    }


    // =========================================================
    // WORKLOAD 4
    // Min-Heap
    // =========================================================

    public static void runHeapTest() {

        System.out.println();
        System.out.println(
                "=== Workload 4: Min-Heap ==="
        );

        String fileName =
                RESULTS_DIR + "/workload4_min_heap.csv";


        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {

            writer.println(
                    "n," +
                            "insert_ns," +
                            "extract_min_ns," +
                            "insert_comparisons," +
                            "extract_min_comparisons," +
                            "sorted"
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


                    // =========================================
                    // Insert
                    // =========================================

                    long start = System.nanoTime();

                    for (int value : data) {
                        heap.insert(value);
                    }

                    long end = System.nanoTime();

                    insertTotal += end - start;

                    insertComparisonsTotal +=
                            heap.getComparisons();


                    // =========================================
                    // Extract Min
                    // =========================================

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

                    extractComparisonsTotal +=
                            heap.getComparisons();
                }


                // =========================================
                // Calculate averages
                // =========================================

                double insertAverage =
                        insertTotal / (double) REPEATS;

                double extractAverage =
                        extractTotal / (double) REPEATS;

                double insertComparisonsAverage =
                        insertComparisonsTotal /
                                (double) REPEATS;

                double extractComparisonsAverage =
                        extractComparisonsTotal /
                                (double) REPEATS;


                // =========================================
                // Write CSV
                // =========================================

                writer.println(
                        n + "," +
                                insertAverage + "," +
                                extractAverage + "," +
                                insertComparisonsAverage + "," +
                                extractComparisonsAverage + "," +
                                allSorted
                );


                System.out.println(
                        "n = " + n +
                                " -> Insert: " +
                                insertAverage +
                                " ns, ExtractMin: " +
                                extractAverage +
                                " ns, Sorted: " +
                                allSorted
                );
            }

        } catch (IOException e) {

            System.out.println(
                    "Error writing Workload 4 CSV: " +
                            e.getMessage()
            );
        }

        System.out.println(
                "Saved: " + fileName
        );
    }
}