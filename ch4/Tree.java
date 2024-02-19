import java.util.ArrayList;
import java.util.LinkedList;

public class Tree {

    static class Graph {
        public Node[] nodes;

        public Node[] getNodes() {
            return nodes;
        }
    }

    static class Node {
        public State state;
        public Node[] children;

        public Node() {
            children = new Node[] {};
        }

        public Node[] getAdjacent() {
            return children;
        }
    }

    static class TreeNode {
        public int data;
        public TreeNode left;
        public TreeNode right;
        public TreeNode parent;

        public TreeNode(int data) {
            this.data = data;
        }

        // 트리 노드 출력
        public void printTreeNodes() {
            printTreeNodes("", true);
        }

        private void printTreeNodes(String prefix, boolean isTail) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + data);
            // 자식 노드들이 있을 경우
            if (left != null || right != null) {
                if (left != null) {
                    left.printTreeNodes(prefix + (isTail ? "    " : "│   "), right == null);
                }
                if (right != null) {
                    right.printTreeNodes(prefix + (isTail ? "    " : "│   "), true);
                }
            }
        }
    }

    enum State {
        Unvisited, Visited, Visiting;
    }

    // 4.1 노드 사이의 경로: 방향 그래프가 주어졌을 때 특정한 두 노드 간에 경로가 존재하는지 확인
    static boolean search(Graph g, Node start, Node end) {
        if (start == end) return true;

        // 큐를 초기화한다.
        LinkedList<Node> q = new LinkedList<Node>();

        for (Node node : g.getNodes()) {
            node.state = State.Unvisited;
        }
        // 시작 노드는 방문 중 상태로 표시하고 큐에 추가
        start.state = State.Visiting;
        q.add(start);

        Node u;
        while (!q.isEmpty()) {
            u = q.removeFirst(); // 큐에서 첫 번째 노드를 꺼낸다.
            if (u != null) {
                for (Node v : u.getAdjacent()) {
                    if (v.state == State.Unvisited) { // 인접한 노드가 미방문 상태라면(중복 방문 방지)
                        if (v == end) {
                            return true;
                        } else {
                            v.state = State.Visiting;
                            q.add(v);
                        }
                    }
                }
                u.state = State.Visited;
            }
        }
        return false;
    }

    // 4.2 최소 트리: 오름차순으로 정렬된 배열 안의 원소는 정수고 중복된 값이 없을 때, 높이가 최소가 되는 이진 탐색 트리를 만들기
    // 높이가 최소가 되려면 왼쪽 트리 노드 개수와 오른쪽 트리 노드 개수가 가능한다면 같아야 한다. 즉, 최대한 양쪽이 균형적인 트리를 만들어야 높이가 최소가 된다.
    TreeNode createMinimalBST(int arr[]) {
        return createMinimalBST(arr, 0, arr.length - 1, null);
    }

    TreeNode createMinimalBST(int arr[], int start, int end, TreeNode parent) {
        if (end < start) {
            return null;
        }
        int mid = (start + end) / 2;
        TreeNode n = new TreeNode(arr[mid]); // 중간값을 루트 노드로 설정
        n.parent = parent;
        n.left = createMinimalBST(arr, start, mid - 1, n); // 루트 기준, 왼쪽 배열 원소들을 왼쪽 서브트리로 설정
        n.right = createMinimalBST(arr, mid + 1, end, n); // 루트 기준, 오른쪽 배열 원소들을 오른쪽 서브트리로 설정
        return n;
    }

    // 4.3 깊이의 리스트: 이진 트리가 주어졌을 때 같은 레벨에 있는 노드를 연결리스트로 연결해주는 알고리즘
    void createLevelLinkedList(TreeNode root, ArrayList<LinkedList<TreeNode>> lists, int level) {
        if (root == null) return;

        LinkedList<TreeNode> list = null;
        if (lists.size() == level) { // 해당 레벨의 연결리스트가 없으면, 새로 생성
            list = new LinkedList<TreeNode>();
            lists.add(list);
        } else {
            // 해당 레벨의 연결리스트가 있으면, 기존 연결리스트를 가져옴
            list = lists.get(level);
        }
        list.add(root);
        createLevelLinkedList(root.left, lists, level + 1);
        createLevelLinkedList(root.right, lists, level + 1);
    }

    // 4.4 균형 확인: 이진 트리가 균형인지 확인하는 함수
    // 균형 트리 : 모든 노드에 대해 왼쪽과 오른쪽 서브트리의 높이 차이가 최대 1이하인 트리
    int checkHeight(TreeNode root) {
        if (root == null) {
            return -1;
        }

        int leftHeight = checkHeight(root.left);
        if (leftHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE; // 에러 전파

        int rightHeight = checkHeight(root.right);
        if (rightHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE; // 에러 전파

        // 기준이 되는 노드에서 왼쪽 노드의 높이와 오른쪽 노드의 높이가 1 이상 차이가 나면 균형이 깨진 것
        int heightDiff = leftHeight - rightHeight;
        if (Math.abs(heightDiff) > 1) {
            return Integer.MIN_VALUE; // 에러 전파
        } else {
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    boolean isBalanced(TreeNode root) {
        return checkHeight(root) != Integer.MIN_VALUE;
    }

    // 4.5 BST 검증: 주어진 이진 트리가 이진 탐색 트리인지 확인하는 함수
    // 이진 탐색 트리: 모든 왼쪽 자식 노드 <= n < 모든 오른쪽 자식 노드
    boolean checkBST(TreeNode n) {
        return checkBST(n, null, null);
    }

    boolean checkBST(TreeNode n, Integer min, Integer max) {
        if (n == null) {
            return true;
        }

        // 왼쪽 자식 노드는 부모 노드보다 작아야 하고, 오른쪽 자식 노드는 부모 노드보다 커야 한다.
        if ((min != null && n.data <= min) || (max != null && n.data > max)) {
            return false;
        }

        if (!checkBST(n.left, min, n.data) || !checkBST(n.right, n.data, max)) {
            return false;
        }

        return true;
    }

    // 4.6 후속자: 이진 탐색 트리에서 주어진 노드의 후속자(중위 후속자)를 찾는 알고리즘
    // 중위 후속자 : 현재 노드의 값보다 큰 값들 중에서 가장 작은 값을 가진 노드
    TreeNode inorderSuccessor(TreeNode n) {
        if (n == null) return null;

        // 오른쪽 자식이 있으면 오른쪽 서브트리에서 가장 왼쪽 노드가 중위 후속자
        if (n.right != null) {
            return leftMostChild(n.right);
        } else {
            TreeNode q = n;
            TreeNode x = q.parent;
            // 오른쪽 자식이 없고, 부모 노드가 있으면
            while (x != null && x.left != q) {
                q = x;
                x = x.parent;
            }
            return x;
        }
    }

    TreeNode leftMostChild(TreeNode n) {
        if (n == null) {
            return null;
        }
        while (n.left != null) {
            n = n.left;
        }
        return n;
    }

    public static void main(String[] args) {
        // 4.1 search 함수 테스트
        Graph g = new Graph();
        Node[] nodes = new Node[6];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node();
        }

        nodes[0].children = new Node[] { nodes[1], nodes[4], nodes[5] };
        nodes[1].children = new Node[] { nodes[3], nodes[4] };
        nodes[2].children = new Node[] { nodes[1] };
        nodes[3].children = new Node[] { nodes[2], nodes[4] };

        g.nodes = nodes;

        Node start = nodes[0];
        Node end = nodes[5];

        System.out.println(search(g, start, end)); // true

        Node start2 = nodes[3];
        Node end2 = nodes[1];

        System.out.println(search(g, start2, end2)); // true
        System.out.println("====================");

        // 4.2 createMinimalBST 함수 테스트
        //         5
        //       /   \
        //      2     7
        //     / \   / \
        //    1   3  6  8
        //         \     \
        //          4     9
        int[] arr = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        TreeNode root = new Tree().createMinimalBST(arr);
        root.printTreeNodes();

        System.out.println("====================");

        // 4.3 createLevelLinkedList 함수 테스트
        // 0 : 5
        // 1 : 2 7
        // 2 : 1 3 6 8
        // 3 : 4 9
        ArrayList<LinkedList<TreeNode>> lists = new ArrayList<LinkedList<TreeNode>>();
        new Tree().createLevelLinkedList(root, lists, 0);
        for (int i = 0; i < lists.size(); i++) {
            System.out.print(i + " : ");
            for (TreeNode n : lists.get(i)) {
                System.out.print(n.data + " ");
            }
            System.out.println();
        }

        System.out.println("====================");

        // 4.4 isBalanced 함수 테스트
        // root 노드 기준 왼쪽 서브트리 높이: 3, 오른쪽 서브트리 높이: 3
        System.out.println(new Tree().isBalanced(root)); // true

        System.out.println("====================");

        // 4.5 checkBST 함수 테스트
        // 왼쪽 자식 노드는 부모 노드보다 작고, 오른쪽 자식 노드는 부모 노드보다 크면 이진 탐색 트리(BST)
        System.out.println(new Tree().checkBST(root)); // true

        System.out.println("====================");

        // 4.6 inorderSuccessor 함수 테스트
        TreeNode n = root.left.left;
        System.out.println(new Tree().inorderSuccessor(n).data);
    }
}
