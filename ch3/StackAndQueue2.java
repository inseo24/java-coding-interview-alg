package ch3;

import java.util.LinkedList;
import java.util.Stack;

public class StackAndQueue2 {


    // 3.4 스택으로 큐: 스택 두 개로 큐 하나를 구현한 MyQueue 클래스를 구현하라.
    public class MyQueue<T> {
        Stack<T> stackNewest, stackOldest;

        public MyQueue() {
            stackNewest = new Stack<T>();
            stackOldest = new Stack<T>();
        }

        public int size() {
            return stackNewest.size() + stackOldest.size();
        }

        public void add(T value) {
            stackNewest.push(value);
        }

        private void shiftStacks() {
            if (stackOldest.isEmpty()) {
                while (!stackNewest.isEmpty()) {
                    stackOldest.push(stackNewest.pop());
                }
            }
        }

        public T peek() {
            shiftStacks();
            return stackOldest.peek();
        }

        public T remove() {
            shiftStacks();
            return stackOldest.pop();
        }
    }

    // 3.5 정렬 스택: 가장 작은 값이 위로 오도록 스택을 정렬하는 프로그램을 작성하라.
    void sort(Stack<Integer> s) {
        Stack<Integer> r = new Stack<Integer>();
        while (!s.isEmpty()) {
            int tmp = s.pop();
            while (!r.isEmpty() && r.peek() > tmp) {
                s.push(r.pop());
            }
            r.push(tmp);
        }

        while (!r.isEmpty()) {
            s.push(r.pop());
        }
    }

    // 3.6 동물 보호소: FIFO(First In First Out) 순서로 동물을 보관하는 동물 보호소가 있다.
    abstract class Animal {
        private int order;
        protected String name;
        public Animal(String n) {
            name = n;
        }
        public void setOrder(int ord) {
            order = ord;
        }
        public int getOrder() {
            return order;
        }

        public boolean isOlderThan(Animal a) {
            return this.order < a.getOrder();
        }
    }

    class AnimalQueue {
        LinkedList<Dog> dogs = new LinkedList<Dog>();
        LinkedList<Cat> cats = new LinkedList<Cat>();
        private int order = 0;

        public void enqueue(Animal a) {
            a.setOrder(order);
            order++;
            if (a instanceof Dog) dogs.addLast((Dog) a);
            else if (a instanceof Cat) cats.addLast((Cat) a);
        }

        public Animal dequeueAny() {
            if (dogs.size() == 0) {
                return dequeueCats();
            } else if (cats.size() == 0) {
                return dequeueDogs();
            }
            Dog dog = dogs.peek();
            Cat cat = cats.peek();
            if (dog.isOlderThan(cat)) {
                return dequeueDogs();
            } else {
                return dequeueCats();
            }
        }

        public Dog dequeueDogs() {
            return dogs.poll();
        }

        public Cat dequeueCats() {
            return cats.poll();
        }
    }

    class Dog extends Animal {
        public Dog(String n) {
            super(n);
        }
    }

    class Cat extends Animal {
        public Cat(String n) {
            super(n);
        }
    }
}
