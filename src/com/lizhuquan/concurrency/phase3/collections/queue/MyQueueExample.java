package com.lizhuquan.concurrency.phase3.collections.queue;

/**
 * Created by lizhuquan on 2018/5/10.
 */
public class MyQueueExample {

    public static void main(String[] args) {
        MyQueue<String> queue = new MyQueue<>();
        queue.addLast("Hello");
        queue.addLast("World");
        queue.addLast("Java");

        String result = null;
        while ((result = queue.removeFirst()) != null) {
            System.out.println(result);
        }
    }

    private static class Node<E> {
        private E element;
        private Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return element == null ? "" : element.toString();
        }
    }

    /**
     * un-threadsafe
     * @param <E>
     */
    public static class MyQueue<E> {
        private Node<E> first,last;
        private int size;

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size() == 0;
        }

        public E peekFirst() {
            return first == null ? null : first.getElement();
        }

        public E peekLast() {
            return last == null ? null : last.getElement();
        }

        public void addLast(E e) {
            Node<E> newNode = new Node<E>(e, null);
            if (isEmpty()) {
                first = newNode;
            } else {
                last.setNext(newNode);
            }
            last = newNode;
            size++;
        }

        public E removeFirst() {
            if (isEmpty()) {
                return null;
            }
            E result = first.getElement();
            first = first.next;
            size--;
            if (isEmpty()) {
                last = null;
            }
            return result;
        }
    }
}
