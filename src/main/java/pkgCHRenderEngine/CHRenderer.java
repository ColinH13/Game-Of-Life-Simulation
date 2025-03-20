package pkgCHRenderEngine;

import pkgCHUtils.CHWindowManager;

import org.joml.Matrix4f;

import java.nio.FloatBuffer;

public class CHRenderer {

    private int shader_program;
    private int NUM_COLS;
    private Matrix4f viewProjMatrix;
    private final int VPT = 4;
    private int[] winWidthHeight;
    private final int OGL_MATRIX_SIZE = 16;
    private FloatBuffer myFloatBuffer;
    private int NUM_ROWS;
    private int PADDING;
    private int EPT = 6;
    private int SIZE;
    private CHWindowManager myWM;
    private int OFFSET;
    private final int FPV = 2;
    private int vpMatLocation;
    private int renderColorLocation;

    public CHRenderer(CHWindowManager windowManager) {
        myWM = windowManager;
    }

    private float[] generateTileVertices(final int rowTiles,
                                         final int columnTiles) {

        // VPT = 4; // Vertices per tile
        // FPV = 2; // Number of floats (coordinates) per tile
        int myIndx = (rowTiles * NUM_COLS + columnTiles) * VPT * FPV;
        int xmin = OFFSET + columnTiles * (SIZE + PADDING);
        int ymin = winWidthHeight[1] - (OFFSET + SIZE + columnTiles * (SIZE + PADDING));

        // Vertices of (columnTiles, rowTiles):
        // (xmin, ymin), (xmin+SIZE, ymin), (xmin+SIZE, ymin-SIZE), (xmin, ymin-SIZE)

        float[] myVertices = new float[rowTiles * columnTiles * FPV * VPT];

        myVertices[myIndx] = xmin;
        myVertices[myIndx + 1] = ymin;

        myVertices[myIndx + 2] = xmin+SIZE;
        myVertices[myIndx + 3] = ymin;

        myVertices[myIndx + 4] = xmin+SIZE;
        myVertices[myIndx + 5] = ymin+SIZE;

        myVertices[myIndx + 6] = xmin;
        myVertices[myIndx + 7] = ymin+SIZE;

        return myVertices;
    }

    private void initOpenGL() {}

    private void renderObjects() {}

    private int[] generateTileIndices(final int rows, final int cols) {
        int IPV = 6;// Indices Per Tile
        // VPT = 4 // Vertices Per Tile

        int tileNum = rows * NUM_COLS + cols;
        int startIndex = tileNum * IPV;
        int startIV = tileNum * VPT;

        // (rows, cols) indices are as follows:
        //(startIV, startIV+1, startIV+2, startIV, startIV+2, startIV+3)

        int[] myIndices = new int[NUM_ROWS * NUM_COLS * IPV];

        myIndices[startIndex] = startIV;
        myIndices[startIndex + 1] = startIV + 1;
        myIndices[startIndex + 2] = startIV + 2;
        myIndices[startIndex + 3] = startIV;
        myIndices[startIndex + 4] = startIV + 2;
        myIndices[startIndex + 5] = startIV + 3;

        return myIndices;
    }

    public void render(int var1, int var2, int var3, int var4, int var5) {}

    private void renderLoop() {}







}
