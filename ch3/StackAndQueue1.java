package ch3;

import ch3.stack.MyStack2;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class StackAndQueue1 {

    // 3.1 한 개로 세 개: 배열 한 개로 스택 세 개를 어떻게 구현할지 설명하라.
    // 고정 크기로 배열을 할당하는 경우

    // 배열 크기가 n이라고 가정
    // 스택 1: 0 ~ n/3 - 1
    // 스택 2: n/3 ~ 2n/3 - 1
    // 스택 3: 2n/3 ~ n - 1

    class FixedMultiStack {
        private int numberOfStacks = 3;
        private int stackCapacity;
        private int[] values;
        private int[] sizes;

        public FixedMultiStack(int stackSize) {
            stackCapacity = stackSize;
            values = new int[stackSize * numberOfStacks]; // 3 * 3 = 9
            sizes = new int[numberOfStacks];
        }

        // stackNum : 스택 번호 (0, 1, 2)
        public void push(int stackNum, int value) throws StackOverflowError {
            if (isFull(stackNum)) {
                throw new StackOverflowError(); // FullStackException
            }

            sizes[stackNum]++;
            values[indexOfTop(stackNum)] = value;
        }

        public int pop(int stackNum) {
            if (isEmpty(stackNum)) {
                throw new EmptyStackException();
            }

            int topIndex = indexOfTop(stackNum);
            int value = values[topIndex];
            values[topIndex] = 0;
            sizes[stackNum]--;
            return value;
        }

        public int peek(int stackNum) {
            if (isEmpty(stackNum)) {
                throw new EmptyStackException();
            }

            return values[indexOfTop(stackNum)];
        }

        public boolean isEmpty(int stackNum) {
            return sizes[stackNum] == 0;
        }

        public boolean isFull(int stackNum) {
            return sizes[stackNum] == stackCapacity;
        }

        // 한 배열 안에서 각 스택이 시작하는 위치가 다르기 때문에, 각 스택의 top 위치를 계산할 때 stackNum을 고려
        private int indexOfTop(int stackNum) {
            int offset = stackNum * stackCapacity;
            int size = sizes[stackNum];
            return offset + size - 1;
        }
    }

    // 가변 크기로 배열을 할당하는 경우
    class MultiStack {
        private class StackInfo {
            public int start, size, capacity;
            public StackInfo(int start, int capacity) {
                this.start = start;
                this.capacity = capacity;
            }

            public boolean isWithinStackCapacity(int index) {
                if (index < 0 || index >= values.length) {
                    return false;
                }

                int contiguousIndex = index < start ? index + values.length : index;
                int end = start + capacity;
                return start <= contiguousIndex && contiguousIndex < end;
            }

            public int lastCapacityIndex() {
                return adjustIndex(start + capacity - 1);
            }

            public int lastElementIndex() {
                return adjustIndex(start + size - 1);
            }

            public boolean isFull() {
                return size == capacity;
            }

            public boolean isEmpty() {
                return size == 0;
            }
        }

        private StackInfo[] info;
        private int[] values;

        public MultiStack(int numberOfStacks, int defaultSize) {
            info = new StackInfo[numberOfStacks];
            for (int i = 0; i < numberOfStacks; i++) {
                info[i] = new StackInfo(defaultSize * i, defaultSize);
            }
            values = new int[numberOfStacks * defaultSize];
        }

        public void push(int stackNum, int value) throws StackOverflowError {
            if (allStacksAreFull()) {
                throw new StackOverflowError();
            }

            StackInfo stack = info[stackNum];
            if (stack.isFull()) {
                expand(stackNum);
            }

            stack.size++;
            values[stack.lastElementIndex()] = value;
        }

        public int pop(int stackNum) {
            StackInfo stack = info[stackNum];
            if (stack.isEmpty()) {
                throw new EmptyStackException();
            }

            int value = values[stack.lastElementIndex()];
            values[stack.lastElementIndex()] = 0;
            stack.size--;
            return value;
        }

        public int peek(int stackNum) {
            StackInfo stack = info[stackNum];
            return values[stack.lastElementIndex()];
        }

        public void shift(int stackNum) {
            StackInfo stack = info[stackNum];

            if (stack.size >= stack.capacity) {
                int nextStack = (stackNum + 1) % info.length;
                shift(nextStack);
                stack.capacity++;
            }

            int index = stack.lastCapacityIndex();
            while (stack.isWithinStackCapacity(index)) {
                values[index] = values[previousIndex(index)];
                index = previousIndex(index);
            }

            values[stack.start] = 0;
            stack.start = nextIndex(stack.start);
            stack.capacity--;
        }

        private void expand(int stackNum) {
            shift((stackNum + 1) % info.length);
            info[stackNum].capacity++;
        }

        public int numberOfElements() {
            int size = 0;
            for (StackInfo sd : info) {
                size += sd.size;
            }
            return size;
        }

        public boolean allStacksAreFull() {
            return numberOfElements() == values.length;
        }


        // 배열의 크기를 넘어가는 인덱스 값이 들어오는 경우, 배열의 크기로 나눈 나머지 값을 반환
        private int adjustIndex(int index) {
            int max = values.length;
            return ((index % max) + max) % max;
        }

        private int nextIndex(int index) {
            return adjustIndex(index + 1);
        }

        private int previousIndex(int index) {
            return adjustIndex(index - 1);
        }
    }

    // 3.2 스택 Min: 기본적인 push와 pop 기능이 구현된 스택에서 최솟값을 반환하는 min 함수를 추가하려고 한다.
    // 각 노드 별로 자기 자신보다 작은 값을 기록하는 방식으로 구현할 수 있으나 이 경우 메모리 사용량이 많아진다.
    // 다른 방법으로 스택을 하나 추가해 각 상태에서의 최솟값을 기록하고, push와 pop 연산을 할 때마다 최솟값을 갱신하는 방식으로 구현할 수 있다.
    public class StackWithMin extends MyStack2<Integer> {
        private MyStack2<Integer> minStack;

        public StackWithMin() {
            minStack = new MyStack2<Integer>();
        }

        public void push(int value) {
            if (value <= min()) {
                minStack.push(value);
            }
            super.push(value);
        }

        public Integer pop() {
            int value = super.pop();
            if (value == min()) {
                minStack.pop();
            }
            return value;
        }

        public int min() {
            if (minStack.isEmpty()) {
                return Integer.MAX_VALUE;
            } else {
                return minStack.peek();
            }
        }
    }


    // 3.3 스택 of Plates: 스택이 하나 넘어가면 새로운 스택을 시작하는 SetOfStacks 클래스를 구현하라.
    class StackOfStacks {
        ArrayList stacks = new ArrayList();
        public void push(int value) {
            Stack last = getLastStack();
            if (last != null && !last.isFull()) {
                last.push(value);
            } else {
                MyStack2 stack = new MyStack2();
                stack.push(value);
                stacks.add(stack);
            }
        }

        int pop() {
            Stack last = getLastStack();
            if (last == null) throw new EmptyStackException();
            int value = last.pop();
            if (last.size == 0) stacks.remove(stacks.size() - 1);
            return value;
        }

        Stack getLastStack() {
            if (stacks.size() == 0) return null;
            return (Stack) stacks.get(stacks.size() - 1);
        }
    }

    public class Stack {
        private int capacity;
        public Node top, bottom;
        public int size = 0;

        public Stack(int capacity) { this.capacity = capacity; }
        public boolean isFull() { return capacity == size; }

        public void join(Node above, Node below) {
            if (below != null) below.above = above;
            if (above != null) above.below = below;
        }

        public boolean push(int v) {
            if (size >= capacity) return false;
            size++;
            Node n = new Node(v);
            if (size == 1) bottom = n;
            join(n, top);
            top = n;
            return true;
        }

        public int pop() {
            Node t = top;
            top = top.below;
            size--;
            return t.value;
        }

        public boolean isEmpty() { return size == 0; }

        public int removeBottom() {
            Node b = bottom;
            bottom = bottom.above;
            if (bottom != null) bottom.below = null;
            size--;
            return b.value;
        }

        private class Node {
            public int value;
            public Node above;
            public Node below;
            public Node(int value) {
                this.value = value;
            }
        }
    }

}
