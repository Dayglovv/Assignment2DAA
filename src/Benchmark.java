import java.util.Random;
public class Benchmark {
    static final int[] SIZES = {100, 1000, 10000, 100000};
    static final int REPEATS = 5;
    static final int ACCESS_COUNT = 10000;
    static Random random = new Random(42);
    public static void main(String[] args) {
        System.out.println("Starting benchmark...");
        runRandomAccessTest();
    }

    public static int[] generateRandomData(int n) {
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt(100000);
        }
        return data;
    }

    public static int[] generateIndexes(int n) {
        int[] indexes = new int[ACCESS_COUNT];
        for (int i = 0; i < ACCESS_COUNT; i++) {
            indexes[i] = random.nextInt(n);
        }
        return indexes;
    }

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

            double arrayAverage =
                    arrayTotal / (double) REPEATS;
            double listAverage =
                    listTotal / (double) REPEATS;
            System.out.println(
                    n + ", "
                            + arrayAverage + ", "
                            + listAverage
            );
        }
    }
}