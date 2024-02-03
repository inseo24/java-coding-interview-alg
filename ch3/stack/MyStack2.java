package ch3.stack;


// Stack이 유용한 경우 : 재귀 알고리즘 사용할 때(backtracking 등)
public class MyStack2<T> {

    private class StackNode {
        private T data;
        private StackNode next;
        public StackNode(T data) {
            this.data = data;
        }
    }

    private StackNode top;

    public MyStack2() {
        this.top = null;
    }

    public MyStack2(T data) {
        this.top = new StackNode(data);
    }

    public T pop() {
        if (top == null) {
            return null;
        }
        T item = top.data;
        top = top.next;
        return item;
    }

    public void push(T item) {
        StackNode t = new StackNode(item);
        t.next = top;
        top = t;
    }

    public T peek() {
        if (top == null) {
            return null;
        }
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }
}
