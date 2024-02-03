package ch1.hashtable;

import java.util.LinkedList;

public class HashTable {
    private class Node {
        String key;
        String value;

        public Node(String key, String value) {
            this.key = key;
            this.value = value;
        }

        String value() {
            return value;
        }

        void value(String value) {
            this.value = value;
        }
    }

    LinkedList<Node>[] data;

    public HashTable(int size) {
        this.data = new LinkedList[size];
    }

    Node searchKey(LinkedList<Node> list, String key) {
        if (list == null) return null;
        for (Node node : list) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }

    public void put(String key, String value) {
        int hashValue = hashFunction(key);
        int index = convertToIndex(hashValue);

        LinkedList<Node> list = data[index];
        if (list == null) {
            list = new LinkedList<Node>();
            data[index] = list;
        }

        Node node = searchKey(list, key);
        if (node == null) {
            list.addLast(new Node(key, value));
        } else {
            node.value(value);
        }
    }

    public String get(String key) {
        int hashValue = hashFunction(key);
        int index = convertToIndex(hashValue);
        LinkedList<Node> nodes = data[index];
        Node node = searchKey(nodes, key);
        return node == null ? "Not found" : node.value();
    }

    private int hashFunction(String key) {
        return key.chars().sum();
    }

    private int convertToIndex(int hashValue) {
        return hashValue % data.length;
    }
}
