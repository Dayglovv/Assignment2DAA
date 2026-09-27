public class LinkedList {
    private Node head;
    private int size;
    private long accesses;
    private class Node {
        int data;
        Node next;
        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    public void add(int x) {
        Node newNode = new Node(x);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
                accesses++;
            }
            current.next = newNode;
        }
        size++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Node newNode = new Node(x);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
                accesses++;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
    }

    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            int removed = head.data;
            head = head.next;
            size--;
            return removed;
        }
        Node current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
            accesses++;
        }
        int removed = current.next.data;
        current.next = current.next.next;
        size--;
        return removed;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public boolean contains(int x) {
        Node current = head;
        while (current != null) {
            if (current.data == x) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    public int size() {
        return size;
    }

    public long getAccesses() {
        return accesses;
    }
    public void resetAccesses() {
        accesses = 0;
    }
}