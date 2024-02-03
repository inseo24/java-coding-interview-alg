package ch2.list.linked;

public class LinkedListTest {
    // LinkedListNode
    public static class LinkedListNode {
        int data;
        LinkedListNode next;
        int size = 0;

        public LinkedListNode(int data) {
            this.data = data;
            size++;
        }

        public void append(int data) {
            LinkedListNode end = new LinkedListNode(data);
            LinkedListNode n = this;
            while (n.next != null) {
                n = n.next;
            }
            n.next = end;
            size++;
        }

        public void delete(int data) {
            LinkedListNode n = this;
            while (n.next != null) {
                if (n.next.data == data) {
                    n.next = n.next.next;
                    break;
                }
                n = n.next;
            }
            size--;
        }

        public void retrieve(LinkedListNode n) {
            while (n.next != null) {
                System.out.print(n.data + "->");
                n = n.next;
            }
            System.out.println(n.data + ", size : " + this.size);
        }

        public int getSize() {
            return this.size;
        }

        // 2.1 중복 없애기
        public void removeDuplicate(LinkedListNode n) {
            LinkedListNode current = n;
            while (current != null) {
                LinkedListNode runner = current;
                while (runner.next != null) {
                    if (runner.next.data == current.data) {
                        runner.next = runner.next.next;
                        size--;
                    } else {
                        runner = runner.next;
                    }
                }
                current = current.next;
            }
        }

        // 2.2 뒤에서 k번째 원소 구하기
        // 길이를 아는 경우
        public int getK(LinkedListNode n, int k) {
            int size = this.size;
            if (size < k) {
                return -1;
            }
            int target = size - k;

            LinkedListNode current = n;
            for (int i = 0; i < target; i++) {
                current = current.next;
            }
            System.out.println("getK element : " + current.data);
            return current.data;
        }

        // 2.2 뒤에서 k번째 원소 구하기
        // 길이를 모르는 경우
        public void getKNoSize(LinkedListNode n, int k) {
            LinkedListNode p1 = n;
            LinkedListNode p2 = n;

            for (int i = 0; i < k; i++) {
                if (p1 == null) break;
                p1 = p1.next;
            }
            System.out.println("p1 : " + p1.data + ", p2 : " + p2.data);

            while (p1 != null) {
                p1 = p1.next;
                p2 = p2.next;
            }

            System.out.println("getKNoSize element : " + p2.data);
        }

        // 2.3 중간 노드 삭제
        public void deleteMiddleNode(LinkedListNode n) {
            LinkedListNode p1 = n;
            LinkedListNode p2 = n;
            LinkedListNode prev = null;

            while (p1 != null && p1.next != null) {
                prev = p2;
                p1 = p1.next.next;
                p2 = p2.next;
            }
            prev.next = p2.next;
        }

        // 2.3 중간 노드 삭제(삭제할 노드만 주어짐)
        public void deleteMiddleNode2(LinkedListNode n) {
            if (n == null || n.next == null) {
                return;
            }
            LinkedListNode next = n.next;
            n.data = next.data;
            n.next = next.next;
        }

        // 2.4 분할
        // 3 -> 5 -> 8 -> 5 -> 10 -> 2 -> 1
        // 3 -> 1 -> 2 -> 10 -> 5 -> 5 -> 8
        // x = 5
        // 실제 : 3 -> 2 -> 1 -> 5 -> 8 -> 5 -> 10
        public LinkedListNode partition(LinkedListNode node, int x) {
            LinkedListNode beforeStart = null;
            LinkedListNode beforeEnd = null;
            // 3 -> 2 -> 1
            // beforeStart = 3
            // beforeEnd = 1
            LinkedListNode afterStart = null;
            LinkedListNode afterEnd = null;
            // 5 -> 8 -> 5 -> 10
            // afterStart = 5
            // afterEnd = 10

            while (node != null) { // 3
                LinkedListNode next = node.next;
                node.next = null; // 3 -> null

                if (node.data < x) {
                    if (beforeStart == null) {
                        beforeStart = node; // 3
                        beforeEnd = beforeStart; // 3
                    } else {
                        beforeEnd.next = node;
                        beforeEnd = node;
                    }
                } else {
                    if (afterStart == null) { // 5
                        afterStart = node; // 5 -> 8
                        afterEnd = afterStart; // 5
                    } else {
                        afterEnd.next = node; // 5 -> 8
                        afterEnd = node; // 8
                    }
                }

                node = next;
            }

            if (beforeStart == null) {
                return afterStart;
            }

            // 3 -> 1 -> 2 -> 5 -> 8 -> 5 -> 10
            beforeEnd.next = afterStart;
            return beforeStart;
        }


        // 2.5 리스트의 합
        // 7 -> 1 -> 6 + 5 -> 9 -> 2 = 617 + 295 = 912
        // 2 -> 1 -> 9
        public LinkedListNode sumLists(LinkedListNode l1, LinkedListNode l2, int carry) {
            if (l1 == null && l2 == null && carry == 0) {
                return null;
            }

            LinkedListNode result = new LinkedListNode(0);
            int value = carry;
            if (l1 != null) {
                value += l1.data;
            }
            if (l2 != null) {
                value += l2.data;
            }

            result.data = value % 10; // 2 1 9

            if (l1 != null || l2 != null) {
                LinkedListNode more = sumLists(l1 == null ? null : l1.next,
                        l2 == null ? null : l2.next,
                        value >= 10 ? 1 : 0);
                result.next = more;
            }
            return result;
        }


        // 0 -> 1 -> 1 -> 0
        //          slow
        //                      fast
        //          right
        //     left
        // 2.6 회문
        public boolean isPalindrome(LinkedListNode head) {
            LinkedListNode reversed = reverseAndClone(head);
            return isEqual(head, reversed);
        }

        public LinkedListNode reverseAndClone(LinkedListNode node) {
            LinkedListNode head = null;
            while (node != null) {
                LinkedListNode n = new LinkedListNode(node.data);
                n.next = head;
                head = n;
                node = node.next;
            }
            return head;
        }

        public boolean isEqual(LinkedListNode one, LinkedListNode two) {
            while (one != null && two != null) {
                if (one.data != two.data) {
                    return false;
                }
                one = one.next;
                two = two.next;
            }
            return one == null && two == null;
        }

        // 2.7 교집합
        // getTailAndSize
        public class Result {
            public LinkedListNode tail;
            public int size;

            public Result(LinkedListNode tail, int size) {
                this.tail = tail;
                this.size = size;
            }
        }

        public Result getTailAndSize(LinkedListNode list) {
            if (list == null) return null;

            int size = 1;
            LinkedListNode current = list;
            while (current.next != null) {
                size++;
                current = current.next;
            }
            return new Result(current, size);
        }

        public LinkedListNode getKthNode(LinkedListNode head, int k) {
            LinkedListNode current = head;
            while (k > 0 && current != null) {
                current = current.next;
                k--;
            }
            return current;
        }

        public LinkedListNode findIntersection(LinkedListNode list1, LinkedListNode list2) {
            if (list1 == null || list2 == null) return null;

            Result result1 = getTailAndSize(list1);
            Result result2 = getTailAndSize(list2);

            if (result1.tail != result2.tail) {
                return null;
            }

            LinkedListNode shorter = result1.size < result2.size ? list1 : list2;
            LinkedListNode longer = result1.size < result2.size ? list2 : list1;

            longer = getKthNode(longer, Math.abs(result1.size - result2.size));

            while (shorter != longer) {
                shorter = shorter.next;
                longer = longer.next;
            }

            return longer;
        }

        // 2.8 루프 발견
        //      A -> B -> C -> D -> E -> C
        // (Non-Cycle)     (Cycle) <- Cycle Length
        //               (Entry)  -> a(fast)
        //                slow    fast + n
        //                      Cycle Length - n

        // 1. fast(2n), slow(n) runner
        // 2. fast == slow
        public LinkedListNode findBeginning(LinkedListNode head) {
            LinkedListNode slow = head;
            LinkedListNode fast = head;

            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
                if (slow == fast) {
                    break;
                }
            }

            if (fast == null || fast.next == null) {
                return null;
            }

            slow = head;
            while (slow != fast) {
                slow = slow.next;
                fast = fast.next;
            }

            return fast;
        }
    }

    public static void main(String[] args) {
        LinkedListNode n = new LinkedListNode(1);
        n.append(2);
        n.append(3);
        n.append(4);
        n.append(5);
        n.append(3);
        n.append(2);
        n.append(1);
        n.retrieve(n);
        n.removeDuplicate(n);
        n.retrieve(n);
        n.getK(n, 2);
        ;

        System.out.println("===============================");

        LinkedListNode n2 = new LinkedListNode(1);
        n2.append(2);
        n2.append(3);
        n2.append(4);
        n2.append(5);
        n2.append(3);
        n2.append(2);
        n2.append(1);
        n2.retrieve(n2);
        n2.getKNoSize(n2, 3);
        n2.retrieve(n2);
        n2.deleteMiddleNode(n2);
        n2.retrieve(n2);

        System.out.println("===============================");

        // 분할
        System.out.println("분할");
        LinkedListNode n3 = new LinkedListNode(3);
        n3.append(5);
        n3.append(8);
        n3.append(5);
        n3.append(10);
        n3.append(2);
        n3.append(1);
        n3.retrieve(n3);
        LinkedListNode n4 = n3.partition(n3, 5);
        n4.retrieve(n4);


        // 리스트의 합
        System.out.println("리스트의 합");
        System.out.println("===============================");
        LinkedListNode l1 = new LinkedListNode(7);
        l1.append(1);
        l1.append(6);
        LinkedListNode l2 = new LinkedListNode(5);
        l2.append(9);
        l2.append(2);
        LinkedListNode l3 = l1.sumLists(l1, l2, 0);
        l3.retrieve(l3);

        System.out.println("===============================");
        System.out.println("교집합");
        // 교집합
        // 3 -> 1 -> 5 -> 9 -> 7 -> 2 -> 1
        // 4 -> 6 -> 7 -> 2 -> 1
        // 실제 : 7 -> 2 -> 1
        LinkedListNode l4 = new LinkedListNode(3);
        l4.append(1);
        l4.append(5);
        l4.append(9);
        l4.append(7);
        l4.append(2);
        l4.append(1);
        LinkedListNode l5 = new LinkedListNode(4);
        l5.append(6);
        l5.append(7);
        l5.append(2);
        l5.append(1);
        l4.findIntersection(l4, l5);


        System.out.println("===============================");
        System.out.println("루프 발견");
        // 루프 발견
        // 3 -> 1 -> 5 -> 9 -> 7 -> 2 -> 1
        // 4 -> 6 -> 7 -> 2 -> 1
        // 실제 : 7 -> 2 -> 1
        LinkedListNode l7 = new LinkedListNode(3);
        l7.append(1);
        l7.append(5);
        l7.append(9);
        l7.append(7);
        l7.append(2);
        l7.append(1);
        LinkedListNode l8 = new LinkedListNode(4);
        l8.append(6);
        l8.append(7);
        l8.append(2);
        l8.append(1);
        l7.findBeginning(l7);
    }
}
