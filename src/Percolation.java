import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean [][] grid;
    private int counterOfOpenedSites = 0;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private int size;
    public Percolation(int n) {                // create n-by-n grid, with all sites blocked
        if (n <= 0){
            throw new IllegalArgumentException("n must be more than zero");
        }
        this.grid = new boolean[n][n];
        this.size = n;
        weightedQuickUnionUF = new WeightedQuickUnionUF(n*n + 2);
        for (int i = 0; i < n; i++) {
            weightedQuickUnionUF.union(0, findIdByCell(0, i));
            weightedQuickUnionUF.union(n*n + 1, findIdByCell(n - 1, i));
        }
    }
    public void open(int row, int col) {    // open site (row, col) if it is not open already
        if (!grid[row][col]) {
            grid[row][col] = true;
            connectWithNeighbours(row, col);
            counterOfOpenedSites++;
        }
    }



    public boolean isOpen(int row, int col){  // is site (row, col) open?

        return grid[row][col];
    }
    public boolean isFull(int row, int col){  // is site (row, col) full?
        return weightedQuickUnionUF.connected(findIdByCell(row, col), 0);}
    public     int numberOfOpenSites(){return counterOfOpenedSites;}       // number of open sites
    public boolean percolates(){return weightedQuickUnionUF.connected(2*size + 1, 0);}

    private void connectWithNeighbours(int row, int col) {
        if (row > 0 && isOpen (row - 1, col)){
            weightedQuickUnionUF.union(findIdByCell(row, col), findIdByCell(row - 1, col)); // left Neighbour
        }
        if (col > 0 && isOpen (row, col - 1)){
            weightedQuickUnionUF.union(findIdByCell(row, col), findIdByCell(row, col - 1));  //top Neighbour
        }
        if (row < size - 1 && isOpen (row + 1, col)){
            weightedQuickUnionUF.union(findIdByCell(row, col), findIdByCell(row + 1, col)); //right Neighbour
        }
        if (col < size - 1 && isOpen (row, col + 1)){
            weightedQuickUnionUF.union(findIdByCell(row, col), findIdByCell(row, col + 1)); // bottom Neighbour
        }
    }
    private int findIdByCell(int row, int col)
    {
        return size * row + col + 1;
    }

    public static void main(String[] args) {
        int n = 3;
        Percolation percolation = new Percolation(n);
        for (int i = 0; i < n; i++){
            percolation.open(i , 1);
        }

        System.out.println(percolation.percolates());

    }
}
