public class Tests {
    public static void main(String[] args) {
        System.out.println("Testing Dynamic Array");
        DynamicArray array = new DynamicArray();
        array.add(10);
        array.add(20);
        array.add(30);
        System.out.println(array.get(0));
        System.out.println(array.get(1));
        System.out.println(array.get(2));

        array.add(1, 15);
        System.out.println("After insert:");
        System.out.println(array.get(0));
        System.out.println(array.get(1));
        System.out.println(array.get(2));
        System.out.println(array.get(3));

        array.remove(2);
        System.out.println("After remove:");
        System.out.println(array.get(0));
        System.out.println(array.get(1));
        System.out.println(array.get(2));
        System.out.println("Contains 20: " + array.contains(20));
        System.out.println("Size: " + array.size());


        System.out.println();
        System.out.println("Testing Linked List");
        LinkedList list = new LinkedList();
        list.add(10);
        list.add(20);
        list.add(30);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        list.add(1, 15);
        System.out.println("After insert:");
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));

        list.remove(2);
        System.out.println("After remove:");
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));

        System.out.println("Contains 20: " + list.contains(20));
        System.out.println("Size: " + list.size());

        System.out.println();
        System.out.println("Testing Min Heap");
        MinHeap heap = new MinHeap();
        heap.insert(10);
        heap.insert(5);
        heap.insert(20);
        heap.insert(2);
        heap.insert(8);
        System.out.println("Minimum: " + heap.peekMin());
        System.out.println("Extracting:");
        while (heap.size() > 0) {
            System.out.println(heap.extractMin());
        }
    }
}