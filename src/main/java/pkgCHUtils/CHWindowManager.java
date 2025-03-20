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
    }

    public static CHWindowManager get() {
        if (my_window == null) {
            my_window = new CHWindowManager();
        }
        return my_window;
    }

    public static CHWindowManager get(int width, int height, int orgX, int orgY) {
        if (my_window == null) {
            my_window = new CHWindowManager(width, height, orgX, orgY);
        }
        return my_window;

    }  //  public SlWindowManager get(...)

    public static CHWindowManager get(int width, int height) {
        if (my_window == null) {
            my_window = new CHWindowManager(width, height);
        }
        return my_window;
    } // public CHWindowManager get(...)



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

        //destroyGlfwWindow();
    }

    public void enableResizeWindowCallback() {
        glfwSetFramebufferSizeCallback(glfwWindow, resizeWindow);
    }

    public void swapBuffers() {
        glfwSwapBuffers(glfwWindow);
    }

    private void initGlfwWindow() {

        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_SAMPLES, 8);
        glfwWindow = glfwCreateWindow(win_width, win_height, "CSC 133", NULL, NULL);
        if (glfwWindow == NULL)
            throw new RuntimeException("Failed to create the GLFW window");
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        glfwSetWindowPos(glfwWindow, 0, 0);
        // TODO: May need to change xpos and ypos

        glfwMakeContextCurrent(glfwWindow);
        int VSYNC_INTERVAL = 1;
        glfwSwapInterval(VSYNC_INTERVAL);
        glfwShowWindow(glfwWindow);
    }

    public int[] getWindowSize() {
        return new int[] { win_width, win_height };
    }

    public boolean isGlfwWindowClosed() {
        return glfwWindowShouldClose(glfwWindow);
    }

}
