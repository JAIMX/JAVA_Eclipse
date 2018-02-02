import java.util.Stack;

public class Board {
    private int[][] blocks;
    private int n;

    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks
        this.blocks = new int[blocks.length][blocks[0].length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }

        n = blocks.length;
    }

    public int dimension() { // board dimension n
        return n;
    }

    public int hamming() { // number of blocks out of place
        int num = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != i * n + j + 1)
                    num++;
            }
        }
        return num - 1;
    }

    public int manhattan() { // sum of Manhattan distances between blocks and
                             // goal
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != 0) {
                    int x = (blocks[i][j] - 1) / n;
                    int y = (blocks[i][j] - 1) % n;
                    sum += Math.abs(x - i) + Math.abs(y - j);
                }
            }
        }
        return sum;
    }

    public boolean isGoal() { // is this board the goal board?
        return (hamming() == 0);
    }

    public Board twin() { // a board that is obtained by exchanging any pair of
                          // blocks
        int[][] twinblocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                twinblocks[i][j] = blocks[i][j];
            }
        }

        if (twinblocks[0][0] == 0) {
            int swap = twinblocks[0][1];
            twinblocks[0][1] = twinblocks[1][0];
            twinblocks[1][0] = swap;
        } else if (twinblocks[0][1] == 0) {
            int swap = twinblocks[0][0];
            twinblocks[0][0] = twinblocks[1][0];
            twinblocks[1][0] = swap;
        } else {
            int swap = twinblocks[0][0];
            twinblocks[0][0] = twinblocks[0][1];
            twinblocks[0][1] = swap;
        }
        return new Board(twinblocks);

    }

    public boolean equals(Object y) { // does this board equal y?
        if (y == this)
            return true;
        if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;

        Board that = (Board) y;
        if (that.n != n)
            return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.blocks[i][j] != that.blocks[i][j])
                    return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() { // all neighboring boards
        int x = 0;
        int y = 0;
        int[][] changeblocks;
        Stack<Board> neighbor = new Stack<Board>();

        changeblocks = new int[n][n];
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < n; k++) {
                changeblocks[j][k] = blocks[j][k];
            }
        }

        boolean getspace = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    x = i;
                    y = j;
                    getspace = true;
                    break;
                }
            }
            if (getspace)
                break;
        }

        int[] xchange = { -1, 1, 0, 0 };
        int[] ychange = { 0, 0, -1, 1 };
        for (int i = 0; i < 4; i++) {
            int xx = x + xchange[i];
            int yy = y + ychange[i];
            if (xx >= 0 && xx < n && yy >= 0 && yy < n) {
                changeblocks[x][y] = changeblocks[xx][yy];
                changeblocks[xx][yy] = 0;
                neighbor.push(new Board(changeblocks));
                changeblocks[xx][yy] = changeblocks[x][y];
                changeblocks[x][y] = 0;
            }

        }

        return neighbor;
    }

    public String toString() { // string representation of this board (in the
                               // output format specified below)

        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d", blocks[i][j]));
                if (j != n - 1)
                    s.append(" ");
            }
            s.append("\n");
        }

        return s.toString();
    }

    public static void main(String[] args) {
        // create initial board from file

        int[][] test = { { 1, 0, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };
        Board board = new Board(test);
        System.out.println(board.twin());
    }

}
