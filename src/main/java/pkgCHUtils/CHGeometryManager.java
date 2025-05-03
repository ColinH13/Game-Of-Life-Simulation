package pkgCHUtils;

public class CHGeometryManager {

    private int NUM_COLS;
    private int NUM_ROWS;
    private int TOTAL_ROWS;
    private int SIZE;
    private int[] WinWidthHeight;
    private int OFFSET;
    private CHPingPongArray myPPArray;
    private int PADDING;



    protected CHGeometryManager(int maxRows, int maxCols, int offset, int size, int
            padding, int[] winWidthHeight) {}

    protected float[] generateTilesVertices(final int rowTiles, final int columnTiles) {return new float[0];}

    protected boolean fillArrayWithTileVertices(float[] vertices, int startIndex, float
            xmin, float ymin) {return false;}

    protected int[] generateTileIndices(final int totalTiles) {return new int[0];}

    protected boolean generateTilesVertices(final CHGoLArray myGoLA, float[]
            vertices) {return false;}





}
