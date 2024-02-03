package ch2.list.linked;

public class MyLinkedList {

    static class LinkedList {
        Node header;

        static class Node {
            int data;
            Node next = null;
        }

        LinkedList() {
            header = new Node();
        }

        void append(int d) {
            Node end = new Node();
            end.data = d;
            Node n = header;
            while (n.next != null) {
                n = n.next;
            }
            n.next = end;
        }

        void delete(int d) {
            Node n = header;
            while (n.next != null) {
                if (n.next.data == d) {
                    n.next = n.next.next;
                } else {
                    n = n.next;
                }
            }
        }

        void retrieve() {
            Node n = header.next;
            while (n.next != null) {
                System.out.print(n.data + "->");
                n = n.next;
            }
            System.out.println(n.data);
        }

        void removeDups() {
            Node n = header;
            while (n != null && n.next != null) {
                Node r = n;
                while (r.next != null) {
                    if (n.data == r.next.data) {
                        r.next = r.next.next;
                    } else {
                        r = r.next;
                    }
                }
                n = n.next;
            }
        }
    }

    public static void main(String[] args) {
        LinkedList l2 = new LinkedList();
        l2.append(1);
        l2.append(2);
        l2.append(3);
        l2.append(4);
        l2.append(5);
        l2.retrieve();
        l2.delete(3);
        l2.retrieve();
        l2.delete(1);
        l2.retrieve();

        System.out.println("========== remove duplicate ============");

        l2.append(3);
        l2.append(3);
        l2.append(2);
        l2.retrieve();
        l2.removeDups();
        l2.retrieve();
    }
}
