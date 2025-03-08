package pkgCHUtils;

import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.*;

public class CHWindowManager {
    private GLFWFramebufferSizeCallback resizeWindow;
    private static CHWindowManager my_window = null;
    private static long glfwWindow;
    private int win_height;
    private int win_width;

    CHWindowManager() {

    }

    CHWindowManager(int width, int height, int pos_x, int pos_y) {}


    public static void setWindowPosition(int orgX, int orgY) {
        if (glfwWindow > 0) {
            glfwSetWindowPos(glfwWindow, orgX, orgY);
        }  //  if (glfwWindow > 0)
    }  //  public void setWindowPosition(...)

    public static CHWindowManager get(int width, int height, int orgX, int orgY) {
        get(width, height);
        setWindowPosition(orgX, orgY);
        return my_window;
    }  //  public SlWindowManager get(...)

    public static void get(int width, int height) {}

    public static CHWindowManager get() {
        if (my_window == null) {
            new CHWindowManager();
        }
        return my_window;
    }

    public void updateContextToThis() {}

    public void destroyGlfwWindow() {}

    protected void setWinWidth(int width, int height) {}

    public void enableResizeWindowCallback() {}

    public void swapBuffers() {}

    private void initGlfwWindow() {}

    public int[] getWindowSize() {
        return new int[] { win_height, win_width };
    }

    public boolean isGlfWindowClosed() {

        return false;
    }



}
