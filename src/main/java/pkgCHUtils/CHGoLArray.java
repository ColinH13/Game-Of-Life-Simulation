package pkgCHUtils;

import java.util.Random;

// TODO: May need to extend CHPingPongArray instead of the Live version
public class CHGoLArray extends CHPingPongArray{

    public int liveCellCount;
    private int numLiveCells;
    public final int DEAD = 0;
    public final int LIVE = 1;

    public CHGoLArray(final String myDataFile) {
        super(0, 0, 0, 1);

        // read from file, filling in the
        readFromFile(myDataFile);
    }

    public CHGoLArray(int numRows, int numCols, int numAlive) {
        super(numRows, numCols, 0, 1);

        this.numLiveCells = numAlive;

        // Initialize nextArray with DEAD (0)
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                nextArray[i][j] = DEAD;
            }
        }

        // Create array to randomize cell positions
        int arraySize = numRows * numCols;
        int[] randomizeArray = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            randomizeArray[i] = i;
        }

        // Shuffle using Fisher-Yates-Knuth (FYK) algorithm
        Random rand = new Random();
        for (int i = 1; i < arraySize; i++) {
            int j = rand.nextInt(i + 1);
            int temp = randomizeArray[i];
            randomizeArray[i] = randomizeArray[j];
            randomizeArray[j] = temp;
        }

        // Activate first `numLiveCells` cells in nextArray
        for (int i = 0; i < numLiveCells; i++) {
            int index = randomizeArray[i];
            int row = index / numCols;
            int col = index % numCols;
            nextArray[row][col] = LIVE;
        }

        // Copy nextArray into liveArray so it renders correctly on first draw
        swapLiveAndNext();
    }


    // Logic for determining whether each cell becomes alive or dead in the next update, or "tick"
    public void onTickUpdate() {
        //clearNextArray();


        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int liveNeighbors = numLiveNeighbors(getNearestNeighborsArray(row, col));

                if (liveArray[row][col] == LIVE) {
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        kill(row, col);
                    } else {
                        // Retain — copy current cell to nextArray
                        nextArray[row][col] = LIVE;
                    }
                } else if (liveArray[row][col] == DEAD && liveNeighbors == 3) {
                    revive(row, col);
                }
            }
        }

        swapLiveAndNext();
    }


    private void clearNextArray() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                nextArray[row][col] = /*liveArray[row][col]*/ LIVE;
            }
        }
    }


    public boolean isAlive(final int row, final int col) {
        if (getVal(row, col) == LIVE) {
            return true;
        }
        else {return false;}
    }

    public boolean isDead(final int row, final int col) {
        if (getVal(row, col) == DEAD) {
            return true;
        } else {
            return false;
        }
    }

    private void kill(int row, int col) {
        set(row, col, DEAD);
    }

    private void revive(int row, int col) {
        set(row, col, LIVE);
    }

    // returns the number of live degree one neighbors given the array of nearest neighbors
    protected int numLiveNeighbors(RCPair[] nearestNeighborsArray) {
        int liveNeighbors = 0;
        //System.out.println("nearestNeighborsArray length = " + nearestNeighborsArray.length);
        for (int i = 0; i < nearestNeighborsArray.length; i++) {
            int row = nearestNeighborsArray[i].row();
            int col = nearestNeighborsArray[i].col();
            if (liveArray[row][col] == LIVE) {
                liveNeighbors++;
            }
        }
        //System.out.println("liveNeighbors = " + liveNeighbors);
        return liveNeighbors;
    }

    // simply reuses save() method from CHPingPongArray
    public void readFromFile(String fileName) {
        loadFile(fileName);
    }

    public int liveCellCount() {
        liveCellCount = 0;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (liveArray[row][col] == LIVE) {
                    liveCellCount++;
                }
            }
        }
        return liveCellCount;
    }

    public void printLiveCells() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (nextArray[row][col] == LIVE) {
                    //System.out.println("Alive Row: " + row + ", Col: " + col);
                }
            }
        }
    }

    public void printNextArraySize() {
        System.out.println(nextArray.length);
    }
    public void printLiveArraySize() {
        System.out.println(liveArray.length);
    }
    public void printNextArray() {
        System.out.println("Next Array:");
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                System.out.print(nextArray[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean liveArrayEquals(int[][] array) {
        boolean equals = true;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                // return false if there is a cell that isn't equal
                if (array[row][col] != liveArray[row][col]) {return false;}
            }
        }
        // Only reaches this if all cells are found to be equal
        return equals;
    }
}