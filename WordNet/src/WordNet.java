import java.util.ArrayList;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.ST;

public class WordNet {
    private ST<String, Integer> st; // String to index
    private Bag<Integer>[] idSynsets; // idsets for one certain word(noun)
    private ArrayList<String> keys; // index to String
    private int length; // length of keys
    private int amountOfWords;
    private Digraph digraph;
    private SAP sapp;

    // constructor takes the name of the two input files
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new java.lang.NullPointerException();

        // read synsets.txt and build keys,st,id_synsets
        st = new ST<String, Integer>();
        keys = new ArrayList<String>();

        In in = new In(synsets);
        int index;
        int lastindex = -1;
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(",");
            index = Integer.parseInt(a[0]);
            assert (lastindex + 1 == index) : "wrong id number:" + index;
            lastindex = index;
            keys.add(a[1]);

            String[] temp = a[1].split(" ");
            for (String str : temp) {
                if (!st.contains(str)) {
                    st.put(str, amountOfWords);
                    amountOfWords++;
                }
            }
        }

        length = keys.size();
        idSynsets = (Bag<Integer>[]) new Bag[amountOfWords];

        for (int i = 0; i < amountOfWords; i++)
            idSynsets[i] = new Bag<Integer>();

        for (int i = 0; i < length; i++) {
            String[] temp = keys.get(i).split(" ");
            for (String str : temp) {
                idSynsets[st.get(str)].add(i);
            }
        }

        // read hypernyms.txt and construct a new digraph
        digraph = new Digraph(length);
        In input = new In(hypernyms);
        while (input.hasNextLine()) {
            String[] a = input.readLine().split(",");
            if (a.length > 1) {
                for (int i = 1; i < a.length; i++) {
                    digraph.addEdge(Integer.parseInt(a[0]),
                            Integer.parseInt(a[i]));
                }
            }
        }

        // check if digraph has exactly one root
        int root = 0;
        for (int i = 0; i < digraph.V(); i++) {
            if (digraph.outdegree(i) == 0)
                root++;
        }

        DirectedCycle test = new DirectedCycle(digraph);
        if (test.hasCycle() || root != 1)
            throw new java.lang.IllegalArgumentException();

        sapp = new SAP(digraph);

    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return st.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new java.lang.NullPointerException();
        return st.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new java.lang.NullPointerException();
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new java.lang.IllegalArgumentException();

        return sapp.length(idSynsets[st.get(nounA)], idSynsets[st.get(nounB)]);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of
    // nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new java.lang.NullPointerException();
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new java.lang.IllegalArgumentException();

        int anc = sapp.ancestor(idSynsets[st.get(nounA)],
                idSynsets[st.get(nounB)]);
        return keys.get(anc);
    }

    public static void main(String[] args) {
        WordNet test = new WordNet("synsets15.txt", "hypernyms15Path.txt");
        System.out.println(test.amountOfWords);
    }

}
