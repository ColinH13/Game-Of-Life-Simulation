package pkgCHCSC133;

import pkgCHUtils.CHPingPongArray;
import pkgCHUtils.CHPingPongArrayLive;

import java.io.FileWriter;
import java.io.PrintWriter;

class DriverTest_1 {
    private static boolean ULT_DEBUG = false;

    public static void main(String[] args) {
        ult_a();
        ult_b();
        ult_c();
        ult_d();
    }

    // ult_a used to validate file load-unload
    private static boolean ult_a() {
        boolean ULT_A_DEBUG = false;
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
        boolean ULT_B_DEBUG = false;
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
        boolean ULT_C_DEBUG = false;
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
        boolean ULT_D_DEBUG = false;
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