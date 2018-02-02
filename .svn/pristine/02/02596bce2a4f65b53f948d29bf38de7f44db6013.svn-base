import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node start, end;
    private int n = 0;

    private class Node {
        Item item;
        Node last;
        Node next;
    }

    public Deque() { // construct an empty deque
        start = null;
        end = null;
    }

    public boolean isEmpty() {
        return (start == null && end == null);
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();
        if (start == null) {
            start = new Node();
            start.item = item;
            start.next = null;
            start.last = null;
            end = start;
            n++;
            return;
        }
        Node oldstart = start;
        start = new Node();
        start.item = item;
        start.next = oldstart;
        oldstart.last = start;
        n++;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();
        if (end == null) {
            end = new Node();
            end.item = item;
            end.next = null;
            end.last = null;
            start = end;
            n++;
            return;
        }
        Node oldend = end;
        end = new Node();
        end.item = item;
        end.last = oldend;
        oldend.next = end;
        n++;
    }

    public Item removeFirst() {
        if (start == null)
            throw new java.util.NoSuchElementException();
        Item item = start.item;
        start = start.next;
        if (start == null) {
            end = start;
        } else {
            start.last = null;
        }
        n--;
        return item;
    }

    public Item removeLast() {
        if (end == null)
            throw new java.util.NoSuchElementException();
        Item item = end.item;
        end = end.last;
        if (end == null) {
            start = end;
        } else {
            end.next = null;
        }
        n--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node first = start;

        public boolean hasNext() {
            return first != null;
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            Item item = first.item;
            first = first.next;
            return item;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<Integer> test = new Deque<Integer>();
        test.addFirst(5);

        test.removeLast();

        Iterator<Integer> ii = test.iterator();
        while (ii.hasNext()) {
            int s = ii.next();
            StdOut.println(s);
        }
        StdOut.println(test.size());

    }
}
