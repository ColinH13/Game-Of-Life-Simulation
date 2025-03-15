
package pkgCHCSC133;
import pkgCHRenderEngine.SlRenderer;
import pkgCHUtils.CHWindowManager;
public class Driver {
    public static void main(String[] args) {
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
    } // public static void main(String[] args)
} // public class Driver
