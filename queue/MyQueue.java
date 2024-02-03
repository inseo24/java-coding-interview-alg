package queue;

public class MyQueue<T> {
    private Object[] data;
    private int head;
    private int tail;
    private int size;

    public MyQueue() {
        this.data = new Object[10];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    public void add(T element) {
        if (size == data.length) {
            grow();
        }
        data[tail] = element;
        tail = (tail + 1) % data.length; // tail 위치를 다시 0으로 돌려줌(wrap-around)
        size++;
    }

    private void grow() {
        int oldCapacity = data.length;
        int newCapacity = oldCapacity * 2;
        Object[] newData = new Object[newCapacity];

        // wrap-around 처리
        for (int i = 0; i < size; i++) {
            newData[i] = data[(head + i) % oldCapacity];
        }

        data = newData;
        head = 0;
        tail = size;
    }

    public T remove() {
        if (isEmpty()) {
            return null;
        }
        T element = (T) data[head];
        data[head] = null;
        head = (head + 1) % data.length; // head 위치를 다시 0으로 돌려줌(wrap-around)
        size--;
        return element;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return (T) data[head];
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
