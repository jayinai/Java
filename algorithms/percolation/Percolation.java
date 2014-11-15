/**
 * The Percolation class models a percolation system.
 * 
 * @author Shuai Wang
 */
public class Percolation {
    private boolean[][] grid; // grid[i][j] = site at row i, column j
    private int len; // length of the grid
    private int top = 0; // virtual top
    private int bottom; // virtual bottom
    private WeightedQuickUnionUF uf; // a WeightedQuickUnionUF instance

    /**
     * Create a N-by-N grid, with all sites blocked
     */
    public Percolation(int N) {
        if (N <= 0) throw new IllegalArgumentException();
        len = N;
        grid = new boolean[N][N];
        bottom = N * N + 1;
        uf = new WeightedQuickUnionUF(N * N + 2);
    }

    /**
     * Open site (row i, column j) if it is not open already, also union sites
     */
    public void open(int i, int j) {
        grid[i - 1][j - 1] = true;

        if (i == 1) { // union virtual top
            uf.union(getSiteIndex(i, j), top);
        }
        if (i == len) { // union virtual bottom
            uf.union(getSiteIndex(i, j), bottom);
        }

        // union possible neighbor(s)
        if (i > 1 && isOpen(i - 1, j)) {
            uf.union(getSiteIndex(i, j), getSiteIndex(i - 1, j));
        }
        if (i < len && isOpen(i + 1, j)) {
            uf.union(getSiteIndex(i, j), getSiteIndex(i + 1, j));
        }
        if (j > 1 && isOpen(i, j - 1)) {
            uf.union(getSiteIndex(i, j), getSiteIndex(i, j - 1));
        }
        if (j < len && isOpen(i, j + 1)) {
            uf.union(getSiteIndex(i, j), getSiteIndex(i, j + 1));
        }
    }

    /**
     * Is site (row i, column j) open?
     * 
     * @return true if site (row i, column j) is open; false otherwise
     * @throws java.lang.IndexOutofBoundsException
     *             unless both 1<= i <= N and 1 <= j <= N
     */
    public boolean isOpen(int i, int j) {
        return grid[i - 1][j - 1];
    }

    /**
     * Is site (row i, column j) full?
     * 
     * @return true if site (row i, column j) is full; false otherwise
     * @throws java.lang.IndexOutofBoundsException
     *             unless both 1<= i <= N and 1 <= j <= N
     */
    public boolean isFull(int i, int j) {
        if (i <= 0 || i > len || j <= 0 || j > len) throw new IndexOutOfBoundsException(); 
        return uf.connected(getSiteIndex(i, j), top);
    }

    /**
     * Does the system percolate?
     * 
     * @return true of the system percolates; false otherwise
     */
    public boolean percolates() {
        return uf.connected(top, bottom);
    }

    /**
     * Return the index at site(i, j)
     * 
     * @return index at site(i, j)
     */
    private int getSiteIndex(int i, int j) {
        return len * (i - 1) + j;
    }

    public static void main(String[] args) {

    }
}
