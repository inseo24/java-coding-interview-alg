package list.array;

// ArrayList 구현
public class MyArrayList2<T> {
    private T[] array;
    private int size;
    private int index;

    public MyArrayList2(int capacity) {
        this.size = 1;
        this.array = (T[]) new Object[this.size];
        this.index = 0;
    }

    public void add(T element) {
        if (this.index == this.size - 1) {
            grow();
        }
        array[this.index] = element;
        this.index++;
    }

    private void grow() {
        this.size = this.size / 2;
        T[] bigger = (T[]) new Object[this.size];
        System.arraycopy(this.array, 0, bigger, 0, this.array.length);
        this.array = bigger;
    }

    public T get(int index) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        return this.array[index];
    }

    public void remove(int index) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = index; i < this.size - 1; i++) {
            this.array[i] = this.array[i + 1];
        }
        this.index--;
    }
}
