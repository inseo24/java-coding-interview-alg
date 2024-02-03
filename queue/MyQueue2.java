package queue;


public class MyQueue2<T> {

        private class QueueNode {
            private T data;
            private QueueNode next;
            public QueueNode(T data) {
                this.data = data;
            }
        }

        private QueueNode first;
        private QueueNode last;

        public MyQueue2() {
            this.first = null;
            this.last = null;
        }

        public void add(T data) {
            QueueNode t = new QueueNode(data);
            if (last != null) {
                last.next = t;
            }
            last = t;
            if (first == null) {
                first = last;
            }
        }

        public T remove() {
            if (first == null) {
                return null;
            }
            T data = first.data;
            first = first.next;
            if (first == null) {
                last = null;
            }
            return data;
        }

        public T peek() {
            if (first == null) {
                return null;
            }
            return first.data;
        }

        public boolean isEmpty() {
            return first == null;
        }

}
