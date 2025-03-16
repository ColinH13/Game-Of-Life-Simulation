package pkgCHRendererEngine;

import pkgCHUtils.CHWindowManager;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import pkgCHUtils.CHWindowManager;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.*;

public class CHRenderer {

    private int shader_program;
    private int NUM_COLS;
    private Matrix4f viewProjMatrix;
    private static int VPT;
    private int[] winWidthHeight;
    private final int OGL_MATRIX_SIZE = 16;
    private FloatBuffer myFloatBuffer;
    private int NUM_ROWS;
    private int PADDING;
    private int EPT;
    private int SIZE;
    private CHWindowManager myWM;
    private int OFFSET;
    private static int FPV;
    private int vpMatLocation;
    private int renderColorLocation;

    CHRenderer(CHWindowManager windowManager) {

    }

    private float[] generateTileVertices(int x, int y) {
        return new float[0];
    }

    private void initOpenGL() {}

    private void renderObjects() {}

    private int[] generateTileIndices(int x, int y) {
        return new int[0];
    }

    public void render(int var1, int var2, int var3, int var4, int var5) {}

    private void renderLoop() {}







}
