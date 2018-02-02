import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private Digraph digraph;
    private int recordAncestor;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new java.lang.NullPointerException();
        digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || v > digraph.V() - 1 || w < 0 || w > digraph.V() - 1)
            throw new java.lang.IndexOutOfBoundsException();

        BreadthFirstDirectedPaths path1 = new BreadthFirstDirectedPaths(
                digraph, v);
        BreadthFirstDirectedPaths path2 = new BreadthFirstDirectedPaths(
                digraph, w);

        recordAncestor = -1;
        int recordLength = Integer.MAX_VALUE;
        for (int i = 0; i < digraph.V(); i++) {
            if (path1.hasPathTo(i) && path2.hasPathTo(i)) {

                int templength = path1.distTo(i) + path2.distTo(i);
                if (templength < recordLength) {
                    recordLength = templength;
                    recordAncestor = i;
                }
            }
        }

        if (recordAncestor == -1)
            return -1;
        else
            return recordLength;
    }

    // a common ancestor of v and w that participates in a shortest ancestral
    // path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || v > digraph.V() - 1 || w < 0 || w > digraph.V() - 1)
            throw new java.lang.IndexOutOfBoundsException();

        length(v, w);
        return recordAncestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex
    // in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new java.lang.NullPointerException();
        for (int test : v) {
            if (test < 0 || test > digraph.V() - 1)
                throw new java.lang.IndexOutOfBoundsException();
        }
        for (int test : w) {
            if (test < 0 || test > digraph.V() - 1)
                throw new java.lang.IndexOutOfBoundsException();
        }

        BreadthFirstDirectedPaths path1 = new BreadthFirstDirectedPaths(
                digraph, v);
        BreadthFirstDirectedPaths path2 = new BreadthFirstDirectedPaths(
                digraph, w);

        recordAncestor = -1;
        int recordLength = Integer.MAX_VALUE;
        for (int i = 0; i < digraph.V(); i++) {
            if (path1.hasPathTo(i) && path2.hasPathTo(i)) {

                int templength = path1.distTo(i) + path2.distTo(i);
                if (templength < recordLength) {
                    recordLength = templength;
                    recordAncestor = i;
                }
            }
        }

        if (recordAncestor == -1)
            return -1;
        else
            return recordLength;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no
    // such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new java.lang.NullPointerException();
        for (int test : v) {
            if (test < 0 || test > digraph.V() - 1)
                throw new java.lang.IndexOutOfBoundsException();
        }
        for (int test : w) {
            if (test < 0 || test > digraph.V() - 1)
                throw new java.lang.IndexOutOfBoundsException();
        }

        length(v, w);
        return recordAncestor;
    }

    public static void main(String[] args) {
        In in = new In("digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
