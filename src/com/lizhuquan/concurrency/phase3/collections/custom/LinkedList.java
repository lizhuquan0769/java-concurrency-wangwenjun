package com.lizhuquan.concurrency.phase3.collections.custom;

/**
 * Created by lizhuquan on 2018/5/5.
 */
public class LinkedList<E> {

    public static void main(String[] args) {
        LinkedList<String> list = LinkedList.of("HELLO", "WORLD", "AND", "JAVA");
        list.addFirst("ZHUQUAN");
        list.addFirst("LI");
        System.out.println(list.size());
        System.out.println(list.contains("SCALA"));
        System.out.println(list.contains("JAVA"));

        String currStr = null;
        while ((currStr = list.removeFirst()) != null) {
            System.out.println(currStr);
        }
        System.out.println(list.size());
    }

    private final Node<E> NODE_NULL = (Node<E>)null;

    private final String PLAIN_NULL = "null";

    private Node<E> first;

    private int size;

    public LinkedList() {
        this.first = NODE_NULL;
    }

    public static <E> LinkedList<E> of(E...es) {
        LinkedList<E> list = new LinkedList<>();
        if (es.length != 0) {
            for (E e : es) {
                list.addFirst(e);
            }
        }
        return list;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public LinkedList<E> addFirst(E e) {
        Node newNode = new Node(e);
        newNode.next = first;
        first = newNode;
        size++;
        return this;
    }

    public boolean contains(E e) {
        Node<E> current = first;
        while (current != NODE_NULL) {
            if (current.value.equals(e)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public E removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        Node<E> node = first;
        first = first.next;
        size--;
        return node.value;
    }



    private class Node<E> {
        private E value;
        private Node<E> next;

        public Node(E value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node<?> node = (Node<?>) o;

            if (!value.equals(node.value)) return false;
            return next.equals(node.next);
        }

        @Override
        public int hashCode() {
            int result = value.hashCode();
            result = 31 * result + next.hashCode();
            return result;
        }
    }
}
