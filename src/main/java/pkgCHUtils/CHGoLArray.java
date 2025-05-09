package pkgCHUtils;

// TODO: May need to extend CHPingPongArray instead of the Live version
public class CHGoLArray extends CHPingPongArrayLive{

    public int liveCellCount;
    private int numLiveCells;

    public CHGoLArray(final String myDataFile) {
        super(5, 5, 0);

        // read from file, filling in the
        readFromFile(myDataFile);
    }

    // TODO: Change number of live cells in constructor
    public CHGoLArray(final int rows, final int cols) {
        super(rows, cols, 18);
    }

    public CHGoLArray(int numRows, int numCols, int numAlive) {
        super(numRows, numCols, numAlive);
    }

    // Logic for determining whether each cell becomes alive or dead in the next update, or "tick"
    public void onTickUpdate() {
        //this.swapLiveAndNext();
        // iterate over liveArray, depending on nearest neighbors in liveArray:
        // set corresponding index of nextArray to LIVE or DEAD.

        // 1. Live Neighbors < 2 --> Kill
        // 2. Live Neighbors == 2 || Live Neighbors == 3 --> Retain
        // 3. Live Neighbors > 3 --> Kill
        // 4. Dead with Live Neighbors == 3 --> Alive again

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int liveNeighborsCount = numLiveNeighbors(getNearestNeighborsArray(row, col));
                // Doesn't check for alive or dead, just looks at the value
                if (isAlive(row, col)) {
                    if (liveNeighborsCount < 2 || liveNeighborsCount > 3) {
                        kill(row, col);
                    } // else if 2 or 3, retain (do nothing)
                    }
                else if (isDead(row, col) && liveNeighborsCount == 3) {
                    revive(row, col);
                }
            }
        }
        //System.out.println("liveCellCount: " + liveCellCount());
        this.swapLiveAndNext();
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
                if (nextArray[row][col] == LIVE) {
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

}
