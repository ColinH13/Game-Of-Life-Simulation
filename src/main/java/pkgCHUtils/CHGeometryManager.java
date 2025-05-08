package pkgCHUtils;

public class CHGeometryManager {

    private int NUM_COLS;
    private int NUM_ROWS;
    private int TOTAL_ROWS;
    private int SIZE;
    private int[] winWidthHeight;
    private int OFFSET;
    private CHPingPongArray myPPArray;
    private int PADDING;

    private final int VPT = 4;
    private final int FPV = 2;
    private int EPT = 6;
    private final int IPV = 6;

    // TODO: Update method implementations to use protected or private instead of public where necessary
    // Potentially mode under pkgCHRenderEngine folder to allow CHRenderer to use these protected methods

    protected CHGeometryManager(int maxRows, int maxCols, int offset, int size, int
            padding, int[] winWidthHeight) {

        NUM_COLS = maxCols;
        NUM_ROWS = maxRows;
        TOTAL_ROWS = NUM_ROWS * NUM_COLS;
        SIZE = size;
        OFFSET = offset;
        this.winWidthHeight = winWidthHeight;
        PADDING = padding;

        myPPArray = new CHPingPongArray(NUM_ROWS, NUM_COLS, 0, 1);
    }

    public float[] generateTileVertices(final int rowTiles, final int columnTiles) {
        // VPT = Vertices per tile
        // FPV = Number of floats (coordinates) per tile

        //TODO: Change back from testing
        float[] myVertices = new float[rowTiles * columnTiles * FPV * VPT];
        //float[] myVertices = new float[(rowTiles + 1) * (columnTiles + 1) * 2];

        for (int row = 0; row < rowTiles; row++) {
            for (int col = 0; col < columnTiles; col++) {

                int myIndx = (row * columnTiles + col) * VPT * FPV;
                int xmin = OFFSET + col * (SIZE + PADDING);
                int ymin = winWidthHeight[1] - (OFFSET + SIZE + row * (SIZE + PADDING));

                // replaced previous code with new method
                boolean result = fillArrayWithTileVertices(myVertices, myIndx, xmin, ymin);

                if (!result) {
                    System.out.println("Error: fillArrayWithTileVertices failed");
                    System.exit(0);
                }

            }
        }

        System.out.println("myVertices Size: " + myVertices.length);
        for (int i = 0; i < myVertices.length; i++) {
            System.out.println("myVertices[" + i + "]: " + myVertices[i]);
        }

        return myVertices;
    }

    protected boolean fillArrayWithTileVertices(float[] vertices, int startIndex, float
            xmin, float ymin) {

        if (vertices == null || startIndex >= vertices.length || startIndex < 0) {
            return false;
        }

        // Testing Statements
        /*System.out.println("myVertices Size: " + myVertices.length);
                for (int i = 0; i < myVertices.length; i++) {
                    System.out.println("myVertices[" + i + "]: " + myVertices[i]);
                }*/

        // Vertices of (columnTiles, rowTiles):
        // (xmin, ymin), (xmin+SIZE, ymin), (xmin+SIZE, ymin-SIZE), (xmin, ymin-SIZE)

        vertices[startIndex] = xmin;
        vertices[startIndex + 1] = ymin;

        vertices[startIndex + 2] = xmin + SIZE;
        vertices[startIndex + 3] = ymin;

        vertices[startIndex + 4] = xmin + SIZE;
        vertices[startIndex + 5] = ymin + SIZE;

        vertices[startIndex + 6] = xmin;
        vertices[startIndex + 7] = ymin + SIZE;

        return true;}

    public int[] generateTileIndices(final int rows, final int cols) {
        //final int VPT = Vertices Per Tile (4 for a square)

        int[] myIndices = new int[rows * cols * EPT];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int tileNum = row * cols + col;
                int startIndex = tileNum * IPV;
                int startIV = tileNum * VPT;

                // Two triangles forming a square
                myIndices[startIndex] = startIV;
                myIndices[startIndex + 1] = startIV + 1;
                myIndices[startIndex + 2] = startIV + 2;

                myIndices[startIndex + 3] = startIV;
                myIndices[startIndex + 4] = startIV + 2;
                myIndices[startIndex + 5] = startIV + 3;
            }
        }
        return myIndices;
    }

    protected boolean generateTilesVertices(final CHGoLArray myGoLA, float[]
            vertices) {
        if (myGoLA == null) {return false;}

        // Use same method with different parameters to fill the float array
        // Use the CHGoLArray's rows and cols # as the parameter
        // TODO: Ensure proper getRows() and getCols() methods
        vertices = generateTileVertices(myGoLa.getRows(), myGoLA.getCols());

        // success
        return true;
    }

}
