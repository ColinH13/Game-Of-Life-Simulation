
package pkgCHCSC133;
import pkgCHRenderEngine.CHRenderer;
import pkgCHUtils.CHGoLArray;
import pkgCHUtils.CHWindowManager;

public class CHDriver {
    public static void main(String[] args) {

        // OLD Driver
        /*
        final int numRows = 6, numCols = 7, polyLength = 50, polyOffset = 10,
                polyPadding = 20;
        final int winWidth = (polyLength + polyPadding) * numCols + 2 * polyOffset;
        final int winHeight = (polyLength + polyPadding) * numRows + 2 *
                polyOffset;
        final int winOrgX = 50, winOrgY = 80;
        final CHWindowManager myWM = CHWindowManager.get(winWidth, winHeight,
                winOrgX, winOrgY);
        final CHRenderer myRenderer = new CHRenderer(myWM);
        myRenderer.render(polyOffset, polyPadding, polyLength, numRows, numCols);
        */


        // New Driver starts here


        final int numRows = 6, numCols = 7, polyLength = 50, polyOffset = 10,
                polyPadding = 20;
        final int winWidth = (polyLength + polyPadding) * numCols + 2 * polyOffset;
        final int winHeight = (polyLength + polyPadding) * numRows + 2 *
                polyOffset;
        final int winOrgX = 50, winOrgY = 80;

        // Create WindowManager object
        final CHWindowManager myWM = CHWindowManager.get(winWidth, winHeight,
                winOrgX, winOrgY);

        // Create GoLArray object
        CHGoLArray goLArray = new CHGoLArray(numRows, numCols);


        // Create Renderer object with rendering parameters and GoLArray object as parameter


        // Send message to WindowManager to update the OpenGL Rendering Context to the Driver object


        // Modify the driver to take the new constructor to GoLArray that takes a filename
        // and creates a GoLArray from it



    } // public static void main(String[] args)
} // public class Driver
