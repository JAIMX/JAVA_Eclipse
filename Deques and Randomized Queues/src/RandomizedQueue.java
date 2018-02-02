import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n;
    private Item[] queue;

    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
        n = 0;
    }

    public boolean isEmpty() {
        return (n == 0);
    }

    public int size() {
        return n;
    }

    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < n; i++)
            temp[i] = queue[i];
        queue = temp;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();
        if (n == queue.length)
            resize(2 * queue.length);
        queue[n++] = item;
    }

    public Item dequeue() { // remove and return a random item
        if (n == 0)
            throw new java.util.NoSuchElementException();
        int index = StdRandom.uniform(n);
        Item value = queue[index];
        queue[index] = queue[n - 1];
        queue[n - 1] = null;
        n--;
        if (n > 0 && n == queue.length / 4)
            resize(queue.length / 2);
        return value;
    }

    public Item sample() { // return (but do not remove) a random item
        if (n == 0)
            throw new java.util.NoSuchElementException();
        int index = StdRandom.uniform(n);
        return queue[index];
    }

    public Iterator<Item> iterator() { // return an independent iterator over
                                       // items in random order
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        Item[] temp;
        int count = n;

        public ArrayIterator() {
            temp = (Item[]) new Object[n];
            for (int i = 0; i < n; i++)
                temp[i] = queue[i];
            int index;
            Item swap;

            for (int i = 0; i < n; i++) {
                index = StdRandom.uniform(i, n);
                swap = temp[i];
                temp[i] = temp[index];
                temp[index] = swap;
            }

        }

        public boolean hasNext() {
            return (count > 0);
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            return temp[n - count--];
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        test.enqueue(1);
        test.dequeue();

        Iterator<Integer> i = test.iterator();
        while (i.hasNext()) {
            int s = i.next();
            StdOut.println(s);
        }
        StdOut.println(test.size());

    }

}
