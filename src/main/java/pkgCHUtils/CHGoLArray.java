package pkgCHUtils;

public class CHGoLArray extends CHPingPongArrayLive{

    public int liveCellCount;
    private int numLiveCells = 0; // default to 0

    public CHGoLArray(final String myDataFile) {
        super(16, 16, 0);

        // read from file
        readFromFile(myDataFile);
    }

    public CHGoLArray(final int rows, final int cols) {
        super(rows, cols, 0);
    }

    public CHGoLArray(int numRows, int numCols, int numAlive) {


    }

    public void onTickUpdate() {}

    public void readFromFile(String fileName) {}


    public int getLiveCellCount() {return liveCellCount;}

}
