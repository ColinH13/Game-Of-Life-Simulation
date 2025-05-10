package pkgCHRenderEngine;

import pkgCHUtils.CHGoLArray;
import pkgCHUtils.CHPingPongArray;

public class CHGeometryManager {

    private int NUM_COLS;
    private int NUM_ROWS;
    private int TOTAL_ROWS;
    private int SIZE;
    private int[] winWidthHeight;
    private int OFFSET;

    //Change PPArray to GOLArray
    private CHGoLArray golArray;
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

        //golArray = new CHGoLArray();
    }

    public float[] generateTileVertices(final int rowTiles, final int columnTiles) {
        // VPT = Vertices per tile
        // FPV = Number of floats (coordinates) per tile
        //TODO: Change back from testing
        float[] myVertices = new float[rowTiles * columnTiles * FPV * VPT];
        //float[] myVertices = new float[(rowTiles + 1) * (columnTiles + 1) * 2];

        for (int row = 0; row < rowTiles; row++) {
            for (int col = 0; col < columnTiles; col++) {
                System.out.println("golArray is Alive. vertex generated.");
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

        // TODO: Remove Test
        /*
        System.out.println("myVertices Size: " + myVertices.length);
        for (int i = 0; i < myVertices.length; i++) {
            System.out.println("myVertices[" + i + "]: " + myVertices[i]);
        }*/

        return myVertices;
    }

    protected boolean fillArrayWithTileVertices(float[] vertices, int startIndex, float
            xmin, float ymin) {

        if (vertices == null || startIndex >= vertices.length || startIndex < 0) {
            System.out.println("Error: fillArrayWithTileVertices failed");
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

    protected boolean generateTilesVertices(final CHGoLArray myGoLA, float[] vertices) {
        if (myGoLA == null || vertices == null) {
            System.out.println("Error: generateTilesVertices failed");
            return false;
        }
        int[][] liveArray = myGoLA.getArray();
        int myIndx = 0;

        if (liveArray.length != NUM_ROWS || liveArray[0].length != NUM_COLS) {
            this.NUM_ROWS = liveArray.length;
            this.NUM_COLS = liveArray[0].length;
            System.out.println("Error: generateTilesVertices failed");
        }

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                if (myGoLA.isAlive(row, col)) {

                    float xMin = OFFSET + col * (SIZE + PADDING);
                    float ymin = winWidthHeight[1] - (OFFSET + SIZE + row * (SIZE + PADDING));

                    if (!fillArrayWithTileVertices(vertices, myIndx, xMin, ymin)) {
                        return false;
                    }
                    myIndx += 8;
                }
            }
        }
        return true;
    }

    public int getVertexCount() {
        return VPT * FPV * NUM_ROWS * NUM_COLS;
    }



    protected int[] generateTileIndices(int totalTiles) {
        int[] indices = new int[totalTiles * 6];
        for (int i = 0; i < totalTiles; i++) {
            int baseIndex = i * 4;
            int indexOffset = i * 6;

            // First triangle: bottom-left, bottom-right, top-right
            indices[indexOffset] = baseIndex;
            indices[indexOffset + 1] = baseIndex + 1;
            indices[indexOffset + 2] = baseIndex + 2;

            // Second triangle: bottom-left, top-right, top-left
            indices[indexOffset + 3] = baseIndex;
            indices[indexOffset + 4] = baseIndex + 2;
            indices[indexOffset + 5] = baseIndex + 3;
        }
        return indices;
    }

}