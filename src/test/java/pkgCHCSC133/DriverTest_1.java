package pkgCHCSC133;

import pkgCHRenderEngine.CHRenderer;
import pkgCHUtils.CHGoLArray;
import pkgCHUtils.CHPingPongArray;
import pkgCHUtils.CHPingPongArrayLive;
import pkgCHUtils.CHWindowManager;

import java.io.FileWriter;
import java.io.PrintWriter;

class DriverTest_1 {
    private static boolean ULT_DEBUG = false;
    private static boolean ULT_A_DEBUG = false;
    private static boolean ULT_B_DEBUG = false;
    private static boolean ULT_C_DEBUG = false;
    private static boolean ULT_D_DEBUG = false;

    private static boolean ULT_1_DEBUG = false;
    private static boolean ULT_2_DEBUG = false;
    private static boolean ULT_3_DEBUG = false;
    private static boolean ULT_0_DEBUG = true;

    private static boolean VISUAL_TEST = true;

    public static void main(String[] args) {
        // Old ULTs from previous assignment
        //ult_a();
        //ult_b();
        //ult_c();
        //ult_d();


        // New ULTs
        //ult_0();
        ult_1();
        //ult_3();


        if (VISUAL_TEST) {
            // Essentially a copy of the driver, displays an oscillating line of length 3.
            visual_test();
        }
    }

    // Ensures that goLArray onTickUpdate() method works properly. Checks with an oscillating 3x3 pattern
    // ult_3 will not PASS if ult_1 does not PASS as well, as it relies on proper nearest Neighbor and swap implementation.
    private static boolean ult_3() {
        // Create GoLArray object
        CHGoLArray goLArray;
        goLArray = new CHGoLArray("gol_input_2.txt");
        boolean retVal = true;

        // initialize three arrays to compare, one for the empty array, then the following two for the
        // two states the array will oscillate through based on the input file.
        int[][] emptyState = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };

        int[][] initialState = {
                {0, 1, 0},
                {0, 1, 0},
                {0, 1, 0}
        };

        int[][] nextState = {
                {0, 0, 0},
                {1, 1, 1},
                {0, 0, 0}
        };

        // TODO: Move test prints to debug block

        if (ULT_3_DEBUG) {
            // Check that the live array is initialized empty when reading from a file
            boolean equals = goLArray.liveArrayEquals(emptyState);
            if (!equals) {retVal = false;}

            System.out.println("goLArray is all zeroes: " + equals);
            goLArray.printArray();
            goLArray.onTickUpdate();

            equals = goLArray.liveArrayEquals(initialState);
            if (!equals) {retVal = false;}

            System.out.println("goLArray is down the middle: " + equals);
            goLArray.printArray();
            goLArray.onTickUpdate();

            equals = goLArray.liveArrayEquals(nextState);
            if (!equals) {retVal = false;}

            System.out.println("goLArray is across the center: " + equals);
            goLArray.printArray();
            goLArray.onTickUpdate();

            equals = goLArray.liveArrayEquals(initialState);
            if (!equals) {retVal = false;}

            System.out.println("goLArray is down the middle: " + equals);
            goLArray.printArray();
        } else {
            // Same code as above, just without print statements
            boolean equals = goLArray.liveArrayEquals(emptyState);
            if (!equals) {retVal = false;}
            goLArray.onTickUpdate();

            equals = goLArray.liveArrayEquals(initialState);
            if (!equals) {retVal = false;}
            goLArray.onTickUpdate();

            equals = goLArray.liveArrayEquals(nextState);
            if (!equals) {retVal = false;}
            goLArray.onTickUpdate();

            equals = goLArray.liveArrayEquals(initialState);
            if (!equals) {retVal = false;}
        }

        if (retVal == true) {
            System.out.println("ult_3: PASS");
        }else {System.out.println("ult_3: FAIL");}
        return retVal;
    }

    // Test method to check logic in nextArray and nearestNeighbor implementation.
    // Based on the file given, always oscillates. Never reaches equilibrium.
    private static boolean visual_test() {

        // initialize primitive variables
        int numRows = 100, numCols = 100, polyLength = 50, polyOffset = 10,
                polyPadding = 1;


        // Create GoLArray object
        CHGoLArray goLArray;

            // takes file input
            goLArray = new CHGoLArray("gol_input_2.txt");

            numRows = goLArray.getNumRows();
            numCols = goLArray.getNumCols();


        final int winWidth = (polyLength + polyPadding) * numCols + 2 * polyOffset;

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
        return false;
    }


    // ult_0 just checks that the swapLiveAndNext() method works properly without altering the arrays.
    // will check with a simple array loaded from input, then swap between that and the empty array without using onTickUpdate()
    private static boolean ult_0() {

        // Create GoLArray object
        CHGoLArray goLArray;
        goLArray = new CHGoLArray("gol_input_2.txt");
        boolean retVal = true;

        // empty array and initialState array matching the input file
        int[][] emptyState = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };

        int[][] initialState = {
                {0, 1, 0},
                {0, 1, 0},
                {0, 1, 0}
        };

        if (ULT_0_DEBUG) {
            // array should be empty
            boolean equals = goLArray.liveArrayEquals(emptyState);
            if (!equals) {retVal = false;}
            goLArray.printArray();

            goLArray.swapLiveAndNext();
            equals = goLArray.liveArrayEquals(initialState);
            if (!equals) {retVal = false;}
            // array should be 1's down center
            goLArray.printArray();

            goLArray.swapLiveAndNext();
            goLArray.swapLiveAndNext();
            equals = goLArray.liveArrayEquals(emptyState);
            // array should be empty
            goLArray.printArray();
        } else {
            boolean equals = goLArray.liveArrayEquals(emptyState);
            if (!equals) {retVal = false;}
            goLArray.swapLiveAndNext();

            equals = goLArray.liveArrayEquals(initialState);
            if (!equals) {retVal = false;}

            // Swaps arrays 10 more times for a robust check
            for (int i = 0 ; i < 10 ; i++) {
                goLArray.swapLiveAndNext();
                equals = goLArray.liveArrayEquals(emptyState);
                if (!equals) {retVal = false;}

                goLArray.swapLiveAndNext();
                equals = goLArray.liveArrayEquals(initialState);
                if (!equals) {retVal = false;}
            }
        }

        if (retVal == true) {
            System.out.println("ult_0: PASS");
        }else {System.out.println("ult_0: FAIL");}
        return retVal;
    }

    // ult_1 used to validate nearestNeighbor calculation.
    private static boolean ult_1() {
        // Initial configuration: a 3x3 blinker (vertical line)
        int[][] initialState = {
                {0, 1, 0},
                {0, 1, 0},
                {0, 1, 0}
        };

        // Expected next state after 1 iteration: a horizontal line
        int[][] expectedState = {
                {0, 0, 0},
                {1, 1, 1},
                {0, 0, 0}
        };

        // Create and initialize the CHGoLArray
        CHGoLArray goLArray = new CHGoLArray(3, 3, 3);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                goLArray.set(row, col, initialState[row][col]);

            }
        }

        // Compute nearest neighbors then swap live and next arrays

        goLArray.swapLiveAndNext();

        // Retrieve the resulting array
        int[][] resultState = goLArray.getArray();

        // Compare with expected
        boolean testPassed = compareArrays(resultState, expectedState);
        System.out.println("ult_1: " + (testPassed ? "PASS" : "FAIL"));

        return testPassed;
    }





    // Helper method to compare two 2D arrays
    private static boolean compareArrays(int[][] a, int[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) return false;

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if (a[i][j] != b[i][j]) return false;
            }
        }
        return true;
    }


    // ult_a used to validate file load-unload
    private static boolean ult_a() {
        boolean retVal = false;

        if (ULT_DEBUG || ULT_A_DEBUG) {
            System.out.println("Placeholder text for ult_a debug statement");
        }

        final int ROWS = 7, COLS = 7;
        final int numLiveCells = 7;
        CHPingPongArrayLive ppa_1 = new CHPingPongArrayLive(ROWS, COLS, numLiveCells);
        CHPingPongArrayLive ppa_2 = new CHPingPongArrayLive(ROWS, COLS, numLiveCells);

        ppa_1.loadFile("ult_a_input.txt");
        ppa_2.loadFile("ult_a_verify.txt");
        ppa_2.swapLiveAndNext();


        for (int row = 0; row < ppa_2.getNumRows(); ++row) {
            for (int col = 0; col < ppa_2.getNumCols(); ++col) {
                if (ppa_2.getVal(row, col) == ppa_2.LIVE) {
                    ppa_2.set(row, col, ppa_2.DEAD);}
                else if (ppa_2.getVal(row, col) == ppa_2.DEAD ) {ppa_2.set(row, col, ppa_2.LIVE);}
            }
        }

        if (ULT_DEBUG || ULT_A_DEBUG) {
            System.out.println("Before Buffer Swap: ");
            ppa_1.printArray();
            System.out.println();
            ppa_2.printArray();

        }
        ppa_2.swapLiveAndNext();
        ppa_1.swapLiveAndNext();
        if (ULT_DEBUG || ULT_A_DEBUG) {
            System.out.println("Before Buffer Swap: ");
            ppa_1.printArray();
            System.out.println();
            ppa_2.printArray();

        }
        boolean equal = true;
        for (int row = 0; row < ppa_2.getNumRows(); ++row) {
            for (int col = 0; col < ppa_2.getNumCols(); ++col) {
                if (ppa_1.getVal(row, col) != ppa_2.getVal(row, col)) {
                    if (ULT_DEBUG || ULT_A_DEBUG) {System.out.printf("Mismatch at (row, col): %d, %d\n", row, col);
                    }
                    equal = false;
                    break;
                }
            }
            if (!equal) {break;}
        }

        retVal = equal;
        if (retVal) {System.out.println("ult_a: PASS");}
        else {System.out.println("ult_a: FAIL");}

        return retVal;
    } // private boolean ult_a()

    // ult_b used to validate the default value for the array is followed
    private static boolean ult_b() {
        //boolean ULT_B_DEBUG = false;
        boolean retVal = true;

        final int ROWS = 7, COLS = 7;
        final int numLiveCells = 7;
        CHPingPongArray ppa = new CHPingPongArray(ROWS, COLS, 0, 1);
        ppa.swapLiveAndNext();

        for (int row = 0; row < ppa.getNumRows(); ++row) {
            for (int col = 0; col < ppa.getNumCols(); ++col) {
                if (ppa.getVal(row, col) != ppa.getDefaultValue()) {
                    if (ULT_DEBUG || ULT_B_DEBUG) {System.out.println("Error in ult_b, value is not DEFAULT_VAL at row: " + row + ", col: " + col);
                        ppa.printArray();
                    }
                    retVal = false;
                    break;
                }
            }
            if (!retVal) {break;}
        }
        if (retVal) {
            System.out.println("ult_b: PASS");
        } else if (!retVal) {System.out.println("ult_b: FAIL");}
        return retVal;
    } // private static boolean ult_b()

    // ult_c used to validate swapLiveAndNext()
    private static boolean ult_c() {
        //boolean ULT_C_DEBUG = false;
        boolean retVal = true;

        final int ROWS = 7, COLS = 7;
        final int numLiveCells = 7;

        CHPingPongArrayLive ppaLive = new CHPingPongArrayLive(ROWS, COLS, numLiveCells);
        // Initialize specific values to nextArray
        int i = 0;
        for (int row = 0; row < ppaLive.getNumRows(); ++row) {
            for (int col = 0; col < ppaLive.getNumCols(); ++col) {
                ppaLive.set(row, col, i);
                i++;
            }
        }

        ppaLive.swapLiveAndNext();
        // Check that the values in the liveArray are the same as what was previously the nextArray
        i = 0;
        for (int row = 0; row < ppaLive.getNumRows(); ++row) {
            for (int col = 0; col < ppaLive.getNumCols(); ++col) {
                if (ppaLive.getVal(row, col) != i) {
                    retVal = false;
                    if (ULT_DEBUG || ULT_C_DEBUG) {System.out.println("Error in ult_c, value is not i at row: " + row + ", col: " + col);}
                }
                i++;
            }
            if (!retVal) {break;}
        }
        if (retVal) {
            System.out.println("ult_c: PASS");
        } else if (!retVal) {System.out.println("ult_c: FAIL");}
        return retVal;
    } // private static boolean ult_c()

    // ult_d used to validate randomization of the negative and positive actions. Test randomization over 5+ cycles
    private static boolean ult_d() {
        //boolean ULT_D_DEBUG = false;
        boolean retVal = false;
        final int ROWS = 7, COLS = 7, numLiveCells = 7;
        CHPingPongArrayLive ppaL1;
        CHPingPongArrayLive ppaL2;

        // Check for randomness 5 times
        for (int i = 0; i < 5; ++i) {
            ppaL1 = new CHPingPongArrayLive(ROWS, COLS, numLiveCells);
            ppaL2 = new CHPingPongArrayLive(ROWS, COLS, numLiveCells);
            ppaL1.swapLiveAndNext();
            ppaL2.swapLiveAndNext();

            // Verify that the two arrays are different
            boolean equal = true;
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    if (ppaL1.getVal(row, col) != ppaL2.getVal(row, col)) {
                        equal = false;
                        if (ULT_DEBUG || ULT_D_DEBUG) {System.out.println("The two arrays are not equal, at row: " + row + ", col: " + col);}
                        break;
                    }
                }
                if (!equal) {break;}
            }
            retVal = !equal;
        }

        if (retVal) {System.out.println("ult_d: PASS");}
        else if (!retVal) {System.out.println("ult_d: FAIL");}
        return retVal;
    } // private static boolean ult_d()
}