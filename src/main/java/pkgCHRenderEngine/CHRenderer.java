package pkgCHRenderEngine;

import org.lwjgl.BufferUtils;
import pkgCHUtils.CHGoLArray;
import pkgCHUtils.CHWindowManager;

import org.joml.Matrix4f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwWaitEvents;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;

public class CHRenderer {

    private int shader_program;
    private int NUM_COLS;
    private Matrix4f viewProjMatrix = new Matrix4f();
    private final int VPT = 4; // TODO: Move to GeometryManager
    private int[] winWidthHeight;
    private final int OGL_MATRIX_SIZE = 16;
    private FloatBuffer myFloatBuffer;
    private int NUM_ROWS;
    private int PADDING;
    private int EPT = 6;
    private int SIZE;
    private CHWindowManager myWM;
    private int OFFSET;
    private final int FPV = 2; // TODO: Move to GeometryManager
    private int vpMatLocation;
    private int renderColorLocation;

    private final int SLEEP_INTERVAL = 1; // milliseconds


    CHGeometryManager geometryManager;
    CHGoLArray golArray;

    public CHRenderer(CHWindowManager windowManager) {
        myWM = windowManager;
        winWidthHeight = myWM.getWindowSize();
    }

    public CHRenderer(CHWindowManager windowManager, CHGoLArray golArray) {
        myWM = windowManager;
        winWidthHeight = myWM.getWindowSize();

        this.golArray = golArray;
    }


    void initOpenGL() {
        //GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glViewport(0, 0, winWidthHeight[0], winWidthHeight[1]);
        glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
        this.shader_program = glCreateProgram();
        int vs = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vs,
                "uniform mat4 viewProjMatrix;" +
                        "void main(void) {" +
                        " gl_Position = viewProjMatrix * gl_Vertex;" +
                        "}");

        glCompileShader(vs);
        glAttachShader(shader_program, vs);
        int fs = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fs,
                "uniform vec3 color;" +
                        "void main(void) {" +
                        " gl_FragColor = vec4(0.6f, 0.7f, 0.1f, 1.0f);" +
                        "}");
        glCompileShader(fs);
        glAttachShader(shader_program, fs);
        glLinkProgram(shader_program);
        glUseProgram(shader_program);
        vpMatLocation = glGetUniformLocation(shader_program, "viewProjMatrix");
        return;
    } // void initOpenGL()

    // Perhaps modify to use for loop instead of while loop,
    // or put for loop inside while loop and break when complete
    // TODO: Modify renderObjects to use
    private void renderObjects() {
        while (!myWM.isGlfwWindowClosed()) {
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            if (golArray != null) {
                golArray.onTickUpdate();
            }
            int vbo = glGenBuffers();
            int ibo = glGenBuffers();
            float[] vertices;
            int[] indices;

                int maxVertices = NUM_ROWS * NUM_COLS * VPT * FPV;
                vertices = new float[maxVertices];
                geometryManager.generateTilesVertices(golArray, vertices);
                int liveCells = golArray.liveCellCount();
                indices = geometryManager.generateTileIndices(liveCells);
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) BufferUtils.
                    createFloatBuffer(vertices.length).
                    put(vertices).flip(), GL_STATIC_DRAW);
            glEnableClientState(GL_VERTEX_ARRAY);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, (IntBuffer) BufferUtils.
                    createIntBuffer(indices.length).
                    put(indices).flip(), GL_STATIC_DRAW);
            glVertexPointer(2, GL_FLOAT, 0, 0L);
            int[] size = myWM.getWindowSize();
            viewProjMatrix.setOrtho(0, size[0], 0, size[1], 0, 10);
            glUniformMatrix4fv(vpMatLocation, false,
                    viewProjMatrix.get(myFloatBuffer));
            glUniform3f(renderColorLocation, 1.0f, 0.5f, 0.0f);
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

            glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0L);
            myWM.swapBuffers();

            try {
                Thread.sleep(SLEEP_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //glDeleteBuffers(vbo);
            //glDeleteBuffers(ibo);
        }
    }



    public void render(final int offset, final int padding,
                       final int size, final int numRows, final int numCols) {

        NUM_COLS = numCols;
        NUM_ROWS = numRows;
        OFFSET = offset;
        SIZE = size;
        PADDING = padding;

        NUM_ROWS = golArray.getNumRows();
        NUM_COLS = golArray.getNumCols();

        // Initialize geometry manager

        geometryManager = new CHGeometryManager(NUM_ROWS, NUM_COLS, OFFSET, SIZE, PADDING, winWidthHeight);

        // TODO: I believe I need to update the context from within the driver
        //myWM.updateContextToThis();

        myFloatBuffer = BufferUtils.createFloatBuffer(OGL_MATRIX_SIZE);
        renderLoop();
        //myWM.destroyGlfwWindow();
    }

    private void renderLoop() {
        glfwPollEvents();
        initOpenGL();
        /* Process window messages in the main thread */
        while (!myWM.isGlfwWindowClosed()) {
            renderObjects();
            glfwWaitEvents();
        }
    }

}