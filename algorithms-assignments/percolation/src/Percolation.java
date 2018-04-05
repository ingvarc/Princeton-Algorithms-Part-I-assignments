import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * A data type to model a percolation system.
 */
public class Percolation {

    /**
     * The grid of union–find data type.
     */
    private WeightedQuickUnionUF grid;

    /**
     * The state of percolation in a form of 2D array.
     */
    private boolean[] state;

    /**
     * The length of the grid side.
     */
    private int gridSideLength;

    /**
     * The number of open sites.
     */
    private int openedSites;

    /**
     * Creates n-by-n grid, with all sites blocked.
     *
     * @param n the dimension of the grid side
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(String.format("Can't create grid of %d x %d dimension.", n, n));
        }
        int sites = n * n;
        this.gridSideLength = n;

        grid = new WeightedQuickUnionUF(sites + 2);
        state = new boolean[sites + 2];

        for (int index = 1; index <= sites; index++) {
            state[index] = false;
        }
        state[0] = true;
        state[sites + 1] = true;
    }

    /**
     * Converts row and column indexes of the site into an array index.
     *
     * @param row the row index
     * @param col the column index
     * @return an array index of the site with coordinates (row, col)
     */
    private int toIndex(int row, int col) {
        if (row <= 0 || row > gridSideLength) {
            throw new IndexOutOfBoundsException(String.format("Row %d is out of bound.", row));
        }
        if (col <= 0 || col > gridSideLength) {
            throw new IndexOutOfBoundsException(String.format("Column %d is out of bound.", col));
        }
        return (row - 1) * gridSideLength + col;
    }

    /**
     * Checks whether the site with the given index is in top row.
     *
     * @param index the index of the site
     * @return true if the site is in top row, false otherwise
     */
    private boolean isTopRowSite(int index) {
        return index <= gridSideLength;
    }

    /**
     * Checks whether the site with the given index is in bottom row.
     *
     * @param index the index of the site
     * @return true if the site is in bottom row, false otherwise
     */
    private boolean isBottomRowSite(int index) {
        return index >= (gridSideLength - 1) * gridSideLength + 1;
    }

    /**
     * Opens site (row, col) if it is not open already.
     *
     * @param row the row index
     * @param col the column index
     */
    public void open(int row, int col) {
        int index = toIndex(row, col);
        state[index] = true;
        openedSites++;

        if (row != 1 && isOpen(row - 1, col)) {
            grid.union(index, toIndex(row - 1, col));
        }
        if (row != gridSideLength && isOpen(row + 1, col)) {
            grid.union(index, toIndex(row + 1, col));
        }
        if (col != 1 && isOpen(row, col - 1)) {
            grid.union(index, toIndex(row, col - 1));
        }
        if (col != gridSideLength && isOpen(row, col + 1)) {
            grid.union(index, toIndex(row, col + 1));
        }
        if (isTopRowSite(index)) {
            grid.union(0, index);
        }
        if (isBottomRowSite(index)) {
            grid.union(state.length - 1, index);
        }
    }

    /**
     * Checks whether the site (row, col) is open.
     *
     * @param row the row index
     * @param col the column index
     * @return true if the site (row, col) is open, false otherwise
     */
    public boolean isOpen(int row, int col) {
        int index = toIndex(row, col);
        return state[index];
    }

    /**
     * Returns the number of open sites.
     *
     * @return the number of open sites
     */
    public int numberOfOpenSites() {
        return openedSites;
    }

    /**
     * Checks whether the site (row, col) is full.
     *
     * @param row the row index
     * @param col the column index
     * @return true if the site (row, col) is full, false otherwise
     */
    public boolean isFull(int row, int col) {
        int index = toIndex(row, col);
        return grid.connected(0, index);
    }

    /**
     * Checks whether the system percolates.
     *
     * @return true if the system percolates, false otherwise
     */
    public boolean percolates() {
        return grid.connected(0, state.length - 1);
    }
}
