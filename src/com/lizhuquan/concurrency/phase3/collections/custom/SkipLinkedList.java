package com.lizhuquan.concurrency.phase3.collections.custom;

import java.util.Random;

/**
 * un done
 * Created by lizhuquan on 2018/5/5.
 */
public class SkipLinkedList<E extends Comparable<E>> {

    private class Node<E> {
        private final static byte NODE_TYPE_DATA = 0;
        private final static byte NODE_TYPE_HEAD = -1;
        private final static byte NODE_TYPE_TAIL = -1;

        private E value;
        private Node<E> up, down, left, right;
        private byte type;

        public Node(E value) {
            this(value, NODE_TYPE_DATA);
        }

        public Node(E value, byte type) {
            this.value = value;
            this.type = type;
        }
    }

    private Node<E> head, tail;
    private int size,height;
    private Random random;

    public SkipLinkedList() {
        this.head = new Node<E>(null, Node.NODE_TYPE_HEAD);
        this.tail = new Node<E>(null, Node.NODE_TYPE_TAIL);
        head.right=tail;
        tail.left=head;
        this.random = new Random();
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    private Node<E> find(E e) {
        Node<E> current  = head;
        while (true) {
            while (current.right.type != Node.NODE_TYPE_TAIL
                    && e.compareTo(current.value) >= 0) {
                current = current.right;
            }

            if (current.down != null) {
                current = current.down;
            } else {
                break;
            }
        }

        return current;
    }

    public boolean contains(E e) {
        Node<E> node = this.find(e);
        return e.equals(node.value);
    }


}
