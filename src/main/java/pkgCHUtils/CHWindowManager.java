package pkgCHUtils;

import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.*;

public class CHWindowManager {
    private static final GLFWFramebufferSizeCallback resizeWindow =
            new GLFWFramebufferSizeCallback(){
                @Override
                public void invoke(long window, int width, int height){
                    glViewport(0,0,width, height);
                }
            };
    private static CHWindowManager my_window = null;
    private static long glfwWindow;
    private int win_height;
    private int win_width;

    private CHWindowManager() {
        win_width = 1800;
        win_height = 1200;

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        glfwWindow = glfwCreateWindow(win_width, win_height, "CSC 133", NULL, NULL);

        setWindowPosition(0, 0);
        glfwMakeContextCurrent(glfwWindow);
        GL.createCapabilities();
        glfwSwapInterval(1);
        //glfwShowWindow(glfwWindow);
        System.out.println("Got to end of constructor");
    }

    private CHWindowManager(int width, int height) {
        win_width = width;
        win_height = height;

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        glfwWindow = glfwCreateWindow(width, height, "CSC 133", NULL, NULL);

        setWindowPosition(0, 0);
        glfwMakeContextCurrent(glfwWindow);
        GL.createCapabilities();
        glfwSwapInterval(1);
        //glfwShowWindow(glfwWindow);
        System.out.println("Got to end of constructor");
    }

    private CHWindowManager(int width, int height, int pos_x, int pos_y) {
        win_width = width;
        win_height = height;

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        glfwWindow = glfwCreateWindow(width, height, "CSC 133", NULL, NULL);

        setWindowPosition(pos_x, pos_y);
        glfwMakeContextCurrent(glfwWindow);
        GL.createCapabilities();
        glfwSwapInterval(1);
        //glfwShowWindow(glfwWindow);
        System.out.println("Got to end of constructor");
    }

    public static CHWindowManager get() {
        if (my_window == null) {
            my_window = new CHWindowManager();
        }
        System.out.println("Got to return statement");
        return my_window;
    }

    public static CHWindowManager get(int width, int height, int orgX, int orgY) {
        // Provided code
        System.out.println("Started get() statement");
        if (my_window == null) {
            my_window = new CHWindowManager(width, height, orgX, orgY);
        }
        System.out.println("Got to return statement");
        return my_window;
        // TODO: May need to update to singleton
        /*
        if (my_window == null) {
            my_window = new CHWindowManager(width, height, pos_x, pos_y);
        }
        return my_window;*/

    }  //  public SlWindowManager get(...)

    public static CHWindowManager get(int width, int height) {
        if (my_window == null) {
            my_window = new CHWindowManager(width, height);
        }
        System.out.println("Got to return statement");
        return my_window;
    }



    public static void setWindowPosition(int orgX, int orgY) {
        if (glfwWindow > 0) {
            glfwSetWindowPos(glfwWindow, orgX, orgY);
        }  //  if (glfwWindow > 0)
    }  //  public void setWindowPosition(...)

    public void updateContextToThis() {
        glfwMakeContextCurrent(glfwWindow);
    }

    public void destroyGlfwWindow() {
        glfwDestroyWindow(glfwWindow);
    }

    protected void setWinWidth(int width, int height) {
        this.win_width = width;
        this.win_height = height;

        // TODO: This may be incorrect

        destroyGlfwWindow();


    }

    public void enableResizeWindowCallback() {
        glfwSetFramebufferSizeCallback(glfwWindow, resizeWindow);
    }

    public void swapBuffers() {

        while (!glfwWindowShouldClose(glfwWindow)) {
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            int vbo = glGenBuffers();
            int ibo = glGenBuffers();
            float[] vertices = {-20f, -20f, 20f, -20f, 20f, 20f, -20f, 20f};
            int[] indices = {0, 1, 2, 0, 2, 3};
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
            /*
            viewProjMatrix.setOrtho(-100, 100, -100, 100, 0, 10);
            glUniformMatrix4fv(vpMatLocation, false,
                    viewProjMatrix.get(myFloatBuffer));
            glUniform3f(renderColorLocation, 1.0f, 0.498f, 0.153f);
            */
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            int VTD = 6; // need to process 6 Vertices To Draw 2 triangles
            glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0L);

            glfwSwapBuffers(glfwWindow);
        }
    }

    private void initGlfwWindow() {
        /*glfwSetErrorCallback(errorCallback =
                GLFWErrorCallback.createPrint(System.err));*/
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_SAMPLES, 8);
        glfwWindow = glfwCreateWindow(win_width, win_height, "CSC 133", NULL, NULL);
        if (glfwWindow == NULL)
            throw new RuntimeException("Failed to create the GLFW window");
        /*
        glfwSetKeyCallback(glfwWindow, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int
                    mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                    glfwSetWindowShouldClose(window, true);
            }
        });
        glfwSetFramebufferSizeCallback(glfwWindow, fbCallback = new
                GLFWFramebufferSizeCallback() {
                    @Override
                    public void invoke(long window, int w, int h) {
                        if (w > 0 && h > 0) {
                            win_width = w;
                            win_height = h;
                        }
                    }
                });
         */
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        glfwSetWindowPos(glfwWindow, 0, 0);
        // TODO: May need to change xpos and ypos

        glfwMakeContextCurrent(glfwWindow);
        int VSYNC_INTERVAL = 1;
        glfwSwapInterval(VSYNC_INTERVAL);
        glfwShowWindow(glfwWindow);
    }

    public int[] getWindowSize() {
        return new int[] { win_height, win_width };
    }

    public boolean isGlfwWindowClosed() {
        // TODO: This may be incorrect
        return glfwWindowShouldClose(glfwWindow);
    }





}
