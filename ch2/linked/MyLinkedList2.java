package ch2.linked;

// Concurrent Linked List
public class MyLinkedList2<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList2() {
        this.head = new Node<T>(null);
        this.tail = new Node<T>(null);
        this.head.next = this.tail;
        this.tail.prev = this.head;
        this.size = 0;
    }

    public void add(T element) {
        Node<T> node = new Node<T>(element);
        Node<T> prev = this.tail.prev;
        prev.next = node;
        node.prev = prev;
        node.next = this.tail;
        this.tail.prev = node;
        this.size++;
    }

    public void add(int index, T element) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> node = new Node<T>(element);
        Node<T> prev = this.head;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        Node<T> next = prev.next;
        prev.next = node;
        node.prev = prev;
        node.next = next;
        next.prev = node;
        this.size++;
    }

    public T get(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> node = this.head.next;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.element;
    }

    public T remove(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> prev = this.head;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        Node<T> node = prev.next;
        Node<T> next = node.next;
        prev.next = next;
        next.prev = prev;
        this.size--;
        return node.element;
    }

    public int size() {
        return this.size;
    }

    private static class Node<T> {
        T element;
        Node<T> prev;
        Node<T> next;

        Node(T element) {
            this.element = element;
        }
    }
}
