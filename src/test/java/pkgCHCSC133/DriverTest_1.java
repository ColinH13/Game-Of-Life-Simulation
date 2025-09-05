package pkgCHCSC133;

import pkgCHRenderEngine.CHRenderer;
import pkgCHUtils.CHGoLArray;
import pkgCHUtils.CHPingPongArray;
import pkgCHUtils.CHPingPongArrayLive;
import pkgCHUtils.CHWindowManager;



class DriverTest_1 {

    // All debug statements will print if this is true
    private static final boolean ULT_DEBUG = false;

    // Print debug statements for individual ULTs
    private static final boolean ULT_A_DEBUG = false;
    private static final boolean ULT_B_DEBUG = false;
    private static final boolean ULT_C_DEBUG = false;
    private static final boolean ULT_D_DEBUG = false;

    private static final boolean ULT_0_DEBUG = false;
    private static final boolean ULT_1_DEBUG = false;
    private static final boolean ULT_2_DEBUG = false;
    private static final boolean ULT_3_DEBUG = false;
    private static final boolean ULT_4_DEBUG = false;
    private static final boolean ULT_5_DEBUG = false;

    private static final boolean DISPLAY_SIM = false;

    public static void main(String[] args) {
        // Old ULTs
        //ult_a();
        //ult_b();
        //ult_c();
        //ult_d();


        // New ULTs
        ult_0(); // swapLiveAndNext
        ult_1(); // Verify method that determines number of nearest neighbors
        ult_2(); // Ensures a large array with a 2x1 cluster of live cells disappears after onTickUpdate()
        ult_3(); // onTickUpdate, ensures oscillating 3x1 and 1x3 cluster of live cells.
        ult_4(); // Ensures a large array with a 2x2 cluster of live cells remains alive after onTickUpdate()
        ult_5(); // Ensure proper comparison of arrays


        if (DISPLAY_SIM) {
            // Essentially a copy of the driver, displays an oscillating line of length 3.
            visual_test();
        }
    }

    // Ensures that goLArray onTickUpdate() method works properly. Checks with an oscillating 3x3 pattern
    // Relies on proper nearest Neighbor and swap implementation.
    private static boolean ult_3() {
        // Create GoLArray object
        CHGoLArray goLArray;
        goLArray = new CHGoLArray("input_files/gol_input_2.txt");
        int L = goLArray.LIVE;
        int D = goLArray.DEAD;
        boolean retVal = true;

        // initialize three arrays to compare, one for the empty array, then the following two for the
        // two states the array will oscillate through based on the input file.
        int[][] emptyState = {
                {D, D, D, D, D},
                {D, D, D, D, D},
                {D, D, D, D, D},
                {D, D, D, D, D},
                {D, D, D, D, D}
        };

        int[][] initialState = {
                {D, D, D, D, D},
                {D, D, L, D, D},
                {D, D, L, D, D},
                {D, D, L, D, D},
                {D, D, D, D, D}
        };

        int[][] nextState = {
                {D, D, D, D, D},
                {D, D, D, D, D},
                {D, L, L, L, D},
                {D, D, D, D, D},
                {D, D, D, D, D}
        };

        if (ULT_3_DEBUG || ULT_DEBUG) {
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
            goLArray = new CHGoLArray("input_files/gol_input_2.txt");

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
        CHGoLArray goLArray = new CHGoLArray("input_files/gol_input_2.txt");
        boolean retVal = true;

        int L = goLArray.LIVE;
        int D = goLArray.DEAD;

        // empty array and initialState array matching the input file
        int[][] emptyState = {
                {D, D, D, D, D},
                {D, D, D, D, D},
                {D, D, D, D, D},
                {D, D, D, D, D},
                {D, D, D, D, D}
        };

        int[][] initialState = {
                {D, D, D, D, D},
                {D, D, L, D, D},
                {D, D, L, D, D},
                {D, D, L, D, D},
                {D, D, D, D, D}
        };


        // array should be empty
        boolean equals = goLArray.liveArrayEquals(emptyState);
        if (!equals) {retVal = false;}
        // goLArray.printArray();
        if (ULT_0_DEBUG || ULT_DEBUG) {goLArray.printArray();}

        goLArray.swapLiveAndNext();
        equals = goLArray.liveArrayEquals(initialState);
        if (!equals) {retVal = false;}
        // array should be 1's down center
        if (ULT_0_DEBUG || ULT_DEBUG) {goLArray.printArray();}

        goLArray.swapLiveAndNext();
        //goLArray.swapLiveAndNext();
        //equals = goLArray.liveArrayEquals(emptyState);
        // array should be empty
        if (ULT_0_DEBUG || ULT_DEBUG) {goLArray.printArray();}

        // Swaps arrays 10 more times for a robust check
        for (int i = 0 ; i < 10 ; i++) {
            goLArray.swapLiveAndNext();
            equals = goLArray.liveArrayEquals(initialState);
            if (!equals) {retVal = false;}

            goLArray.swapLiveAndNext();
            equals = goLArray.liveArrayEquals(initialState);
            if (!equals) {retVal = false;}
        }

        if (retVal == true) {
            System.out.println("ult_0: PASS");
        }else {System.out.println("ult_0: FAIL");}
        return retVal;
    }

    // ult_1 used to validate nearestNeighbor calculation.
    private static boolean ult_1() {
        boolean retVal = true;

        // Create and initialize the CHGoLArray
        CHGoLArray goLArray = new CHGoLArray("input_files/gol_input_2.txt");
        int numRows = goLArray.getNumRows(), numCols = goLArray.getNumCols();
        goLArray.swapLiveAndNext();

        int[][] numNeighbors = { {0, 1, 1, 1, 0},
                                {0, 2, 1, 2, 0},
                                {0, 3, 2, 3, 0},
                                {0, 2, 1, 2, 0},
                                {0, 1, 1, 1, 0}
        };

        // Compute nearest neighbors for each cell, then check that the number of nearest neighbors meets the expected value
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int numProjectedNeighbors = goLArray.numLiveNeighbors(goLArray.getNearestNeighborsArray(row, col));
                if (numProjectedNeighbors != numNeighbors[row][col]) {
                    if (ULT_1_DEBUG || ULT_DEBUG) {
                        // Displays when there is a mismatch in the calculated and expected value
                        System.out.printf("Row, Col: %d, %d Calculated: %d Expected: %d\n", row, col, numProjectedNeighbors, numNeighbors[row][col]);
                    }
                    System.out.println(retVal);
                    retVal = false;
                }
            }
        }

        // Compare with expected
        if (retVal == true) {
            System.out.println("ult_1: PASS");
        }
        else {System.out.println("ult_1: FAIL");}
        return retVal;
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

    private static boolean ult_2() {
        boolean retVal = true;

        // Create golArray, initiated to array with only two live cells next to each other
        CHGoLArray golArray = new CHGoLArray(5, 5, 0);
        golArray.swapLiveAndNext();

        // set [1][1] and [1][2] to live
        golArray.set(1, 1, golArray.LIVE);
        golArray.set(1, 2, golArray.LIVE);

        golArray.swapLiveAndNext();
        if (ULT_2_DEBUG || ULT_DEBUG) {golArray.printArray(); }
        golArray.onTickUpdate();

        // Check that the two nearby cells have disappeared, as they don't have the proper conditions to survive.
        if (ULT_2_DEBUG || ULT_DEBUG) {golArray.printArray(); }

        // Ensure they both disappear, confirming onTickUpdate() logic
        golArray.set(1, 1, golArray.LIVE);
        for (int row = 0; row < golArray.getNumRows(); row++) {
            for (int col = 0; col < golArray.getNumCols(); col++) {
                if (golArray.getVal(row, col) != 0) {
                    System.out.println("ERROR: Cell != 0 at Row, Col: " + row + ", " + col);
                    retVal = false;
                }

            }
        }

        if (retVal == true) {
            System.out.println("ult_2: PASS");
        }
        else {System.out.println("ult_2: FAIL");}
        return retVal;
    }

    private static boolean ult_4() {
        boolean retVal = true;


        // Create golArray, initiated to array with only two live cells next to each other
        CHGoLArray golArray = new CHGoLArray(5, 5, 0);

        int L = golArray.LIVE;
        int D = golArray.DEAD;

        int[][] nextState = {
                {D, D, D, D, D},
                {D, L, L, D, D},
                {D, L, L, D, D},
                {D, D, D, D, D},
                {D, D, D, D, D}
        };

        golArray.swapLiveAndNext();

        // set [1][1] and [1][2] to live
        golArray.set(1, 1, golArray.LIVE);
        golArray.set(1, 2, golArray.LIVE);
        golArray.set(2, 1, golArray.LIVE);
        golArray.set(2, 2, golArray.LIVE);

        golArray.swapLiveAndNext();
        if (ULT_4_DEBUG || ULT_DEBUG) {golArray.printArray(); }
        golArray.onTickUpdate();

        // Check that the two nearby cells have disappeared, as they don't have the proper conditions to survive.
        if (ULT_4_DEBUG || ULT_DEBUG) {golArray.printArray(); }

        // Ensure that all 4 stay alive, and all other cells remain dead.
        retVal = golArray.liveArrayEquals(nextState);

        if (retVal == true) {
            System.out.println("ult_4: PASS");
        }
        else {System.out.println("ult_4: FAIL");}
        return retVal;
    }

    // Test liveArrayEquals method
    private static boolean ult_5() {
        boolean retVal = true;

        CHGoLArray goLArray = new CHGoLArray("input_files/gol_input_2.txt");

        int L = goLArray.LIVE;
        int D = goLArray.DEAD;

        int[][] initialState = {
                {D, D, D, D, D},
                {D, D, L, D, D},
                {D, D, L, D, D},
                {D, D, L, D, D},
                {D, D, D, D, D}
        };

        boolean equal1 = compareArrays(initialState, goLArray.getArray());
        boolean equal2 = goLArray.liveArrayEquals(initialState);

        if (equal1 != equal2) {
            retVal = false;
        }

        initialState[0][0] = 1;

        equal1 = compareArrays(initialState, goLArray.getArray());
        equal2 = goLArray.liveArrayEquals(initialState);

        if (equal1 != equal2) {
            retVal = false;
        }

        if (retVal == true) {
            System.out.println("ult_5: PASS");
        }
        else {System.out.println("ult_5: FAIL");}
        return retVal;

    }




    // ULTs from previous assignment, included to ensure proper functionality is maintained

    // ult_a used to validate file load-unload
    private static boolean ult_a() {
        boolean retVal;

        if (ULT_DEBUG || ULT_A_DEBUG) {
            System.out.println("Placeholder text for ult_a debug statement");
        }

        final int ROWS = 7, COLS = 7;
        final int numLiveCells = 7;
        CHPingPongArrayLive ppa_1 = new CHPingPongArrayLive(ROWS, COLS, numLiveCells);
        CHPingPongArrayLive ppa_2 = new CHPingPongArrayLive(ROWS, COLS, numLiveCells);

        ppa_1.loadFile("input_files/ult_a_input.txt");
        ppa_2.loadFile("input_files/ult_a_verify.txt");
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
        } else {System.out.println("ult_b: FAIL");}
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
        } else {System.out.println("ult_c: FAIL");}
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
        else {System.out.println("ult_d: FAIL");}
        return retVal;
    } // private static boolean ult_d()
}