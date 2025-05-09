
package pkgCHCSC133;
import pkgCHRenderEngine.CHRenderer;
import pkgCHUtils.CHGoLArray;
import pkgCHUtils.CHPingPongArray;
import pkgCHUtils.CHWindowManager;

public class CHDriver {
        public static void main(String[] args) {

                // initialize primitive variables
                // TODO: Modify to 100x100 default
                int numRows = 100, numCols = 100, polyLength = 50, polyOffset = 10,
                        polyPadding = 20;


                // Create GoLArray object
                CHGoLArray goLArray;
                if (args.length != 1) {
                        goLArray = new CHGoLArray(numRows, numCols, (int)(numRows * numCols * 0.2f+0.5));
                }
                else {
                        // TODO: modify to take file input
                        goLArray = new CHGoLArray(args[0]);

                        numRows = goLArray.getNumRows();
                        numCols = goLArray.getNumCols();
                }

                final int winWidth = (polyLength + polyPadding) * numCols + 2 * polyOffset;
                System.out.println("Win Width: " + winWidth);

                final int winHeight = (polyLength + polyPadding) * numRows + 2 *
                        polyOffset;
                final int winOrgX = 50, winOrgY = 80;

                // Create WindowManager object
                final CHWindowManager myWM = CHWindowManager.get(winWidth, winHeight,
                        winOrgX, winOrgY);

                // Create Renderer object with rendering parameters and GoLArray object as parameter
                final CHRenderer myRenderer = new CHRenderer(myWM, goLArray);

                // Send message to WindowManager to update the OpenGL Rendering Context to the Driver object
                myWM.updateContextToThis();

                myRenderer.render(polyOffset, polyPadding, polyLength, numRows, numCols);


                myWM.destroyGlfwWindow();
        } // public static void main(String[] args)
} // public class Driver