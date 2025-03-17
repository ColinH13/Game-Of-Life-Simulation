package pkgCHRenderEngine;

import pkgCHUtils.CHWindowManager;
import org.joml.Matrix4f;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import java.nio.IntBuffer;

public class CHRenderer {

    private int shader_program;
    private int NUM_COLS;
    private Matrix4f viewProjMatrix = new Matrix4f();
    private static int VPT;
    private int[] winWidthHeight;
    private final int OGL_MATRIX_SIZE = 16;
    private FloatBuffer myFloatBuffer = BufferUtils.createFloatBuffer(OGL_MATRIX_SIZE);
    private int NUM_ROWS;
    private int PADDING;
    private int EPT;
    private int SIZE;
    private CHWindowManager myWM;
    private int OFFSET;
    private static int FPV;
    private int vpMatLocation;
    private int renderColorLocation;

    public CHRenderer(CHWindowManager windowManager) {
        myWM = windowManager;
    }

    private float[] generateTileVertices(int x, int y) {
        return new float[0];
    }

    private void initOpenGL() {}

    private void renderObjects() {}

    private int[] generateTileIndices(int x, int y) {
        return new int[0];
    }

    public void render(final int offset, final int padding, final int size, final int numRows, final int numCols) {}

    private void renderLoop() {}







}
