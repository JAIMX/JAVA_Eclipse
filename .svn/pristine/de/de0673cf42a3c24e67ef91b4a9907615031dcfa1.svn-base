import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private Node min;

    private class Node implements Comparable<Node> {
        private Board board;
        private Node pre;
        private int step;
        private boolean isTwin;

        public Node(Board board, Node pre, int step, boolean isTwin) {
            this.board = board;
            this.pre = pre;
            this.step = step;
            this.isTwin = isTwin;
        }

        public int compareTo(Node that) {
            int predict1 = this.step + this.board.manhattan();
            int predict2 = that.step + that.board.manhattan();
            if (predict1 < predict2)
                return -1;
            if (predict1 > predict2)
                return 1;
            return 0;
        }

    }

    public Solver(Board initial) { // find a solution to the initial board
                                   // (using the A* algorithm)

        if (initial == null)
            throw new java.lang.NullPointerException();
        MinPQ<Node> queue = new MinPQ<Node>();
        queue.insert(new Node(initial, null, 0, false));
        queue.insert(new Node(initial.twin(), null, 0, true));

        min = queue.delMin();
        while (!min.board.isGoal()) {
            for (Board neighbor : min.board.neighbors()) {
                if (min.pre == null || !min.pre.board.equals(neighbor)) {
                    queue.insert(new Node(neighbor, min, min.step + 1, min.isTwin));
                }
            }
            min = queue.delMin();
        }

    }

    public boolean isSolvable() { // is the initial board solvable?
        if (!min.isTwin)
            return true;
        return false;
    }

    public int moves() { // min number of moves to solve initial board; -1 if
                         // unsolvable
        if (!isSolvable())
            return -1;
        return min.step;
    }

    public Iterable<Board> solution() { // sequence of boards in a shortest
                                        // solution; null if unsolvable
        if (!isSolvable())
            return null;

        Stack<Board> record = new Stack<Board>();
        Node temp = min;
        while (temp != null) {
            record.push(temp.board);
            temp = temp.pre;
        }

        return record;
    }

    public static void main(String[] args) {
        //
        // Scanner in = new
        // Scanner(Paths.get("data/8puzzle-testing/8puzzle/puzzle3x3-unsolvable1.txt"));
        // int n = in.nextInt();
        // int[][] blocks = new int[n][n];
        // for (int i = 0; i < n; i++)
        // for (int j = 0; j < n; j++)
        // blocks[i][j] = in.nextInt();
        // Board initial = new Board(blocks);

        // Solver solver = new Solver(initial);

        // if (!solver.isSolvable())
        // StdOut.println("No solution possible");
        // else {
        // StdOut.println("Minimum number of moves = " + solver.moves());
        // for (Board board : solver.solution())
        // StdOut.println(board);
        // }
    }

}
