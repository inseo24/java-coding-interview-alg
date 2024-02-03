package ch3.stack;

import java.util.Arrays;

public class MyStack<E> {
    private Object[] data;
    private int size;


    public MyStack() {
        this.size = 0;
        this.data = new Object[10];
    }

    public void push(E element) {
        if (data.length - 1 == size) {
            grow();
        }
        this.data[size] = element;
        this.size++;
    }

    public Object[] grow() {
        int oldCapacity = data.length;
        int newCapacity = oldCapacity * 2;
        return data = Arrays.copyOf(data, newCapacity);
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public E pop() {
        if (isEmpty()) {
            return null;
        }
        this.size--;
        E deleted = (E) this.data[size];
        this.data[size] = null;
        return deleted;
    }

    public E peek() {
        if (this.data != null && this.size != 0) {
            return (E) this.data[this.size - 1];
        }

        return null;
    }
}
