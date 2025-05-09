package pkgCHUtils;

import java.util.Random;

public class CHPingPongArrayLive extends CHPingPongArray {

    public final int DEAD = 0;
    public final int LIVE = 1;
    int numLiveCells = 0;

     public CHPingPongArrayLive(int numRows, int numCols, int numLiveCells) {
        super(numRows, numCols, 0, 1);
        this.numLiveCells = numLiveCells;

        // Below code is to initialize the nextArray
        // Initialize array with DEAD (0)
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                nextArray[i][j] = DEAD;
            }
        }

        // Create random array to assist in randomizing the nextArray
        int arraySize = numRows * numCols;
        int[] randomizeArray = new int[arraySize];

        for (int i = 0; i < arraySize; i++) {
            randomizeArray[i] = i;
        }

        // Randomize via FYK Algorithm
         Random rand = new Random();
        // TODO: test implementation for decrementing, then compare to incrementing values.
        for (int i = 1; i < arraySize; i++) {
            int j = rand.nextInt(i + 1);
            // Swap elements at i and j
            int temp = randomizeArray[i];
            randomizeArray[i] = randomizeArray[j];
            randomizeArray[j] = temp;
        }

        // Take the first n = numLiveCells elements, and fill in the corresponding indices of the nextArray
         for (int i = 0; i < numLiveCells; i++) {
             int index = randomizeArray[i];
             int row = index / numCols;
             int col = index % numCols;
             nextArray[row][col] = LIVE;
         }

    }

    public int countLiveDegreeTwoNeighbors(int row, int col) {
        int numLiveD2Numbers = 0;

        int prevRow = (row - 2 + numRows) % numRows;
        int nextRow = (row + 2) % numRows;
        int prevCol = (col - 2 + numCols) % numCols;
        int nextCol = (col + 2) % numCols;

        int[] neighbors = new int[16];

        neighbors[0]  = liveArray[(prevRow - 2 + numRows) % numRows][(prevCol - 2 + numCols) % numCols];
        neighbors[1]  = liveArray[(prevRow - 2 + numRows) % numRows][(prevCol - 1 + numCols) % numCols];
        neighbors[2]  = liveArray[(prevRow - 2 + numRows) % numRows][(prevCol + numCols) % numCols];
        neighbors[3]  = liveArray[(prevRow - 2 + numRows) % numRows][(prevCol + 1 + numCols) % numCols];
        neighbors[4]  = liveArray[(prevRow - 2 + numRows) % numRows][(prevCol + 2 + numCols) % numCols];

        neighbors[5]  = liveArray[(prevRow - 1 + numRows) % numRows][(prevCol - 2 + numCols) % numCols];
        neighbors[6]  = liveArray[(prevRow - 1 + numRows) % numRows][(prevCol + 2 + numCols) % numCols];

        neighbors[7]  = liveArray[(prevRow + numRows) % numRows][(prevCol - 2 + numCols) % numCols];
        neighbors[8]  = liveArray[(prevRow + numRows) % numRows][(prevCol + 2 + numCols) % numCols];

        neighbors[9]  = liveArray[(prevRow + 1 + numRows) % numRows][(prevCol - 2 + numCols) % numCols];
        neighbors[10] = liveArray[(prevRow + 1 + numRows) % numRows][(prevCol + 2 + numCols) % numCols];

        neighbors[11] = liveArray[(prevRow + 2 + numRows) % numRows][(prevCol - 2 + numCols) % numCols];
        neighbors[12] = liveArray[(prevRow + 2 + numRows) % numRows][(prevCol - 1 + numCols) % numCols];
        neighbors[13] = liveArray[(prevRow + 2 + numRows) % numRows][(prevCol + numCols) % numCols];
        neighbors[14] = liveArray[(prevRow + 2 + numRows) % numRows][(prevCol + 1 + numCols) % numCols];
        neighbors[15] = liveArray[(prevRow + 2 + numRows) % numRows][(prevCol + 2 + numCols) % numCols];

        // Iterate over neighbors array, increment numLiveD2Numbers if the element is 1, or alive.
        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i] == LIVE) {
                numLiveD2Numbers++;
            }
        }

        // TODO: Remove test print statements
        /*
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                System.out.print(liveArray[row][col]+ " ");
            }
            System.out.println();
        }
        System.out.println("for " + row + ", " + col + " = " + liveArray[row][col] + ":");

        System.out.println("prevRow = " + prevRow);
        System.out.println("nextRow = " + nextRow);
        System.out.println("prevCol = " + prevCol);
        System.out.println("nextCol = " + nextCol);*/

        return numLiveD2Numbers;

    } // public int countLiveDegreeTwoNeighbors(int row, int col)

    public void printArray() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                System.out.print(liveArray[row][col]+ " ");
            }
            System.out.println();
        }
    }

    public int getValue(int row, int col) {
         return super.getVal(row, col);
    }
}
