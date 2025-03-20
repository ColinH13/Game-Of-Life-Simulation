package pkgCHRenderEngine;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
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
        winWidthHeight = myWM.getWindowSize();
    }

    private float[] generateTileVertices(final int rowTiles, final int columnTiles) {
        // VPT = 4; // Vertices per tile
        // FPV = 2; // Number of floats (coordinates) per tile

        //TODO: Change back from testing
        float[] myVertices = new float[rowTiles * columnTiles * FPV * VPT];
        //float[] myVertices = new float[(rowTiles + 1) * (columnTiles + 1) * 2];

        for (int row = 0; row < rowTiles; row++) {
            for (int col = 0; col < columnTiles; col++) {

                int myIndx = (row * columnTiles + col) * VPT * FPV;
                int xmin = OFFSET + col * (SIZE + PADDING);
                int ymin = winWidthHeight[1] - (OFFSET + SIZE + row * (SIZE + PADDING));
                //int ymin = OFFSET + row * (SIZE + PADDING);

                /*System.out.println("myVertices Size: " + myVertices.length);
                for (int i = 0; i < myVertices.length; i++) {
                    System.out.println("myVertices[" + i + "]: " + myVertices[i]);
                }*/

                // Vertices of (columnTiles, rowTiles):
                // (xmin, ymin), (xmin+SIZE, ymin), (xmin+SIZE, ymin-SIZE), (xmin, ymin-SIZE)
                myVertices[myIndx] = xmin;
                myVertices[myIndx + 1] = ymin;

                myVertices[myIndx + 2] = xmin + SIZE;
                myVertices[myIndx + 3] = ymin;

                myVertices[myIndx + 4] = xmin + SIZE;
                myVertices[myIndx + 5] = ymin + SIZE;

                myVertices[myIndx + 6] = xmin;
                myVertices[myIndx + 7] = ymin + SIZE;
            }
        }

        System.out.println("myVertices Size: " + myVertices.length);
        for (int i = 0; i < myVertices.length; i++) {
            System.out.println("myVertices[" + i + "]: " + myVertices[i]);
        }

        return myVertices;
    }

    void initOpenGL() {
        GL.createCapabilities();
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
    private void renderObjects() {
        int vbo = glGenBuffers();
        int ibo = glGenBuffers();

        float[] vertices = generateTileVertices(NUM_ROWS, NUM_COLS);
        int[] indices = generateTileIndices(NUM_ROWS, NUM_COLS);
        FloatBuffer myFloatBuffer = BufferUtils.createFloatBuffer(OGL_MATRIX_SIZE);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) BufferUtils
                .createFloatBuffer(vertices.length)
                .put(vertices).flip(), GL_STATIC_DRAW);
        glEnableClientState(GL_VERTEX_ARRAY);
        glVertexPointer(2, GL_FLOAT, 0, 0L);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, (IntBuffer) BufferUtils
                .createIntBuffer(indices.length)
                .put(indices).flip(), GL_STATIC_DRAW);

        while (!myWM.isGlfwWindowClosed()) {
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            winWidthHeight = myWM.getWindowSize();
            viewProjMatrix.setOrtho(0, winWidthHeight[0], 0, winWidthHeight[1], 0, 10);
            glUniformMatrix4fv(vpMatLocation, false, viewProjMatrix.get(myFloatBuffer));

            glUniform3f(renderColorLocation, 1.0f, 0.498f, 0.153f);
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

            glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0L);

            myWM.swapBuffers();
            glDisableVertexAttribArray(0);
        }
    }

    private int[] generateTileIndices(final int rows, final int cols) {
        final int IPV = 6;  // Indices Per Tile (6 for 2 triangles)
        final int VPT = 4;  // Vertices Per Tile (4 for a square)

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


    public void render(final int offset, final int padding,
                       final int size, final int numRows, final int numCols) {

        NUM_COLS = numCols;
        NUM_ROWS = numRows;
        OFFSET = offset;
        SIZE = size;
        PADDING = padding;


        //myWM.updateContextToThis();
        myFloatBuffer = BufferUtils.createFloatBuffer(OGL_MATRIX_SIZE);
        renderLoop();
        //myWM.destroyGlfwWindow();
    }

    private void renderLoop() {

        glfwPollEvents();
        initOpenGL();
        //renderObjects();
        /* Process window messages in the main thread */
        while (!myWM.isGlfwWindowClosed()) {
            renderObjects();
            glfwWaitEvents();
        }
    }

}
