import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

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
        private int size = 0;

        public TreeNode(int data) {
            this.data = data;
            size = 1;
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

        // 4.11
        public TreeNode getRandomNode() {
            int leftSize = left == null ? 0 : left.size();
            Random random = new Random();
            int index = random.nextInt(size);
            if (index < leftSize) {
                return left.getRandomNode();
            } else if (index == leftSize) {
                return this;
            } else {
                return right.getRandomNode();
            }
        }

        public int size() {
            return size;
        }

        public void insertInOrder(int d) {
            if (d <= data) {
                if (left == null) {
                    left = new TreeNode(d);
                } else {
                    left.insertInOrder(d);
                }
            } else {
                if (right == null) {
                    right = new TreeNode(d);
                } else {
                    right.insertInOrder(d);
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

    // 4.7 순서 정하기: 프로젝트의 의존 관계가 주어졌을 때, 프로젝트를 수행하는 순서를 찾는 알고리즘
    // 모든 노드에 대해 진입 차수(들어오는 에지 수)를 계산
    // 진입 차수가 0인 노드를 큐에 넣음
    // 큐가 빌 때까지 다음 과정을 반복
    // - 큐에서 노드를 꺼내고, 이 노드는 수행 순서 리스트에 추가
    // - 이 노드에서 나가는 모든 에지를 제거하고, 연결된 노드이 진입 차수를 감소시킴
    // - 진입 차수가 0이 된 노드를 큐에 넣음
    // 모든 노드를 방문했는데도 큐가 비지 않으면, 루프가 존재한다는 뜻이므로 순서를 찾을 수 없음
    public class Project {
        private ArrayList<Project> children = new ArrayList<Project>();
        private HashMap map = new HashMap();
        private String name;
        private int dependencies = 0;

        public Project(String n) {
            name = n;
        }

        public void addNeighbor(Project node) {
            if (!map.containsKey(node.getName())) {
                children.add(node);
                map.put(node.getName(), node);
                node.incrementDependencies();
            }
        }

        public void incrementDependencies() {
            dependencies++;
        }

        public void decrementDependencies() {
            dependencies--;
        }

        public String getName() {
            return name;
        }

        public ArrayList<Project> getChildren() {
            return children;
        }

        public int getNumberDependencies() {
            return dependencies;
        }
    }

    public class Graph_v2 {
        private ArrayList<Project> nodes = new ArrayList<Project>();
        private HashMap<String, Project> map = new HashMap<String, Project>();

        public Project getOrCreateNode(String name) {
            if (!map.containsKey(name)) {
                Project node = new Project(name);
                nodes.add(node);
                map.put(name, node);
            }

            return map.get(name);
        }

        public void addEdge(String startName, String endName) {
            Project start = getOrCreateNode(startName);
            Project end = getOrCreateNode(endName);
            start.addNeighbor(end);
        }

        public ArrayList<Project> getNodes() {
            return nodes;
        }
    }

    public class Solution {
        public Project[] findBuildOrder(String[] projects, String[][] dependencies) {
            Graph_v2 graph = buildGraph(projects, dependencies);
            return orderProjects(graph.getNodes());
        }

        public Graph_v2 buildGraph(String[] projects, String[][] dependencies) {
            Graph_v2 graph = new Graph_v2();
            for (String project : projects) {
                graph.getOrCreateNode(project);
            }

            for (String[] dependency : dependencies) {
                String first = dependency[0];
                String second = dependency[1];
                graph.addEdge(first, second);
            }

            return graph;
        }

        public Project[] orderProjects(ArrayList<Project> projects) {
            Project[] order = new Project[projects.size()];

            int endOfList = addNonDependent(order, projects, 0);

            int toBeProcessed = 0;
            while (toBeProcessed < order.length) {
                Project current = order[toBeProcessed];

                if (current == null) {
                    return null;
                }

                ArrayList<Project> children = current.getChildren();
                for (Project child : children) {
                    child.decrementDependencies();
                }

                endOfList = addNonDependent(order, children, endOfList);
                toBeProcessed++;
            }

            return order;
        }

        public int addNonDependent(Project[] order, ArrayList<Project> projects, int offset) {
            for (Project project : projects) {
                if (project.getNumberDependencies() == 0) {
                    order[offset] = project;
                    offset++;
                }
            }

            return offset;
        }
    }



    // 4.8 첫 번째 공통 조상: 이진 트리에서 두 노드의 첫 번째 공통 조상을 찾는 알고리즘
    TreeNode commonAncestor(TreeNode p, TreeNode q) {
        int delta = depth(p) - depth(q); // p와 q의 깊이 차이를 계산
        TreeNode first = delta > 0 ? q : p; // 더 깊은 노드를 찾음
        TreeNode second = delta > 0 ? p : q; // 더 얕은 노드를 찾음
        second = goUpBy(second, Math.abs(delta)); // 더 깊은 노드를 더 얕은 노드의 높이만큼 올림

        // 두 노드가 같아질 때까지 위로 올림
        while (first != second && first != null && second != null) {
            first = first.parent;
            second = second.parent;
        }
        return first == null || second == null ? null : first;
    }

    TreeNode goUpBy(TreeNode node, int delta) {
        while (delta > 0 && node != null) {
            node = node.parent;
            delta--;
        }
        return node;
    }

    int depth(TreeNode n) {
        int depth = 0;
        while (n != null) {
            n = n.parent;
            depth++;
        }
        return depth;
    }


    // 4.9 BST 수열: 이진 탐색 트리 안에서 원소가 중복되지 않는다고 할 때, 주어진 이진 탐색 트리를 만들 수 있는 모든 배열을 출력하는 알고리즘
    ArrayList<LinkedList<Integer>> allSequences(TreeNode node) {
        ArrayList<LinkedList<Integer>> result = new ArrayList<LinkedList<Integer>>();

        if (node == null) {
            result.add(new LinkedList<Integer>());
            return result;
        }

        LinkedList<Integer> prefix = new LinkedList<Integer>();
        prefix.add(node.data);

        // 왼쪽 서브트리와 오른쪽 서브트리의 모든 배열을 계산
        ArrayList<LinkedList<Integer>> leftSeq = allSequences(node.left);
        ArrayList<LinkedList<Integer>> rightSeq = allSequences(node.right);

        // 왼쪽 서브트리와 오른쪽 서브트리의 모든 배열을 조합
        for (LinkedList<Integer> left : leftSeq) {
            for (LinkedList<Integer> right : rightSeq) {
                ArrayList<LinkedList<Integer>> weaved = new ArrayList<LinkedList<Integer>>();
                weaveLists(left, right, weaved, prefix);
                result.addAll(weaved);
            }
        }

        return result;
    }

    void weaveLists(LinkedList<Integer> first, LinkedList<Integer> second, ArrayList<LinkedList<Integer>> results, LinkedList<Integer> prefix) {
        // 한쪽 리스트가 비어있으면 나머지 리스트를 결과에 추가
        if (first.size() == 0 || second.size() == 0) {
            LinkedList<Integer> result = (LinkedList<Integer>) prefix.clone();
            result.addAll(first);
            result.addAll(second);
            results.add(result);
            return;
        }

        // 첫 번째 원소를 리스트에서 제거
        int headFirst = first.removeFirst();
        prefix.addLast(headFirst);
        weaveLists(first, second, results, prefix);
        prefix.removeLast();
        first.addFirst(headFirst);

        // 두 번째 원소를 리스트에서 제거
        int headSecond = second.removeFirst();
        prefix.addLast(headSecond);
        weaveLists(first, second, results, prefix);
        prefix.removeLast();
        second.addFirst(headSecond);
    }


    // 4.10 하위 트리 확인: 주어진 두 이진 트리 t1과 t2가 t2가 t1의 하위 트리인지 확인하는 알고리즘
    boolean containsTree(TreeNode t1, TreeNode t2) {
        if (t2 == null) return true; // 빈 트리는 어떤 트리의 하위 트리이다.
        return subTree(t1, t2);
    }

    boolean subTree(TreeNode r1, TreeNode r2) {
        if (r1 == null) {
            return false; // 큰 트리가 빈 트리에 포함되지 않으면
        } else if (r1.data == r2.data && matchTree(r1, r2)) {
            return true;
        }
        return subTree(r1.left, r2) || subTree(r1.right, r2);
    }

    boolean matchTree(TreeNode r1, TreeNode r2) {
        if (r1 == null && r2 == null) {
            return true; // 두 트리가 모두 빈 트리이면
        } else if (r1 == null || r2 == null) {
            return false; // 한 트리가 빈 트리이면
        } else if (r1.data != r2.data) {
            return false; // 데이터가 다르면
        } else {
            return matchTree(r1.left, r2.left) && matchTree(r1.right, r2.right);
        }
    }


    // 4.11 임의의 노드: 이진 트리 클래스를 확장하여 임의의 노드에 빠르게 접근할 수 있는 getRandNode() 함수를 구현

    // 4.12 합의 경로: 각 노드의 값이 정수인 이진 트리가 주어졌을 때, 특정한 합을 가지는 경로의 개수를 찾는 알고리즘
    int countPathsWithSum(TreeNode root, int targetSum) {
        if (root == null) return 0;
        int pathsFromRoot = countPathsWithSumFromNode(root, targetSum, 0);

        int pathsOnLeft = countPathsWithSum(root.left, targetSum);
        int pathsOnRight = countPathsWithSum(root.right, targetSum);

        return pathsFromRoot + pathsOnLeft + pathsOnRight;
    }

    int countPathsWithSumFromNode(TreeNode node, int targetSum, int currentSum) {
        if (node == null) return 0;

        currentSum += node.data;

        int totalPaths = 0;
        if (currentSum == targetSum) {
            totalPaths++;
        }

        totalPaths += countPathsWithSumFromNode(node.left, targetSum, currentSum);
        totalPaths += countPathsWithSumFromNode(node.right, targetSum, currentSum);

        return totalPaths;
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
