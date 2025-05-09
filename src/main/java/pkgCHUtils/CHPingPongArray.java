package pkgCHUtils;

import java.util.Random;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class CHPingPongArray {

    int numRows;
    int numCols;

    int[][] liveArray;
    int[][] nextArray;

    int randMin;
    int randMax;

    Random rand = new Random();
    private int DEFAULT_VALUE = 99;

    protected record RCPair (int row, int col) {

    } // private record RCPair(...)

    RCPair pair;

    public RCPair[] getNearestNeighborsArray(int orgRow, int orgCol) {
        RCPair[] neighbors = new RCPair[8];

        int nextRow = (orgRow + 1) % numRows;
        int prevRow = (numRows + orgRow - 1) % numRows;
        int nextCol = (orgCol + 1) % numCols;
        int prevCol = (numCols + orgCol - 1) % numCols;

        neighbors[0] = new RCPair(prevRow, prevCol); // Top-left
        neighbors[1] = new RCPair(prevRow, orgCol); // Top-center
        neighbors[2] = new RCPair(prevRow, nextCol); // Top-right
        neighbors[3] = new RCPair(orgRow, prevCol); // Left
        neighbors[4] = new RCPair(orgRow, nextCol); // Right
        neighbors[5] = new RCPair(nextRow, prevCol); // Bottom-left
        neighbors[6] = new RCPair(nextRow, orgCol); // Bottom-center
        neighbors[7] = new RCPair(nextRow, nextCol); // Bottom-right

        return neighbors;
    } // getNearestNeighborsArray(...)


    public CHPingPongArray(int numRows, int numCols, int randMin, int randMax) {
        this.numRows = numRows;
        this.numCols = numCols;

        liveArray = new int[numRows][numCols];
        nextArray = new int[numRows][numCols];

        this.randMin = randMin;
        this.randMax = randMax;

        // Randomly initialize array
        /*
        for (int row = 0; row < numRows; ++row) {
            for (int col = 0; col < numCols; ++col) {
                nextArray[row][col] = 0;
            }
        }*/

        initArrays();
    } // CHPingPongArray(...)

    public void randomizeArray(int[] array) {
        // Randomize elements in randArray
        for (int i = 1; i < array.length; i++) {
            // swap element at index 0 with random element at index in range (i, length - 1)
            int index = rand.nextInt(i, array.length);
            int tmp = array[0];
            array[0] = array[index];
            array[index] = tmp;
        }

        // populate nextArray with randomized elements
        for (int i = 0; i < numRows * numCols; i++) {
            int row = (i / numCols);
            int col = i % numCols;
            nextArray[row][col] = array[i];
        }
    } // randomizeArray(...)

    // Does not replace the current values
    public void randomize() {
        int size = numRows * numCols;
        int[] randArray = new int[size];

        for (int i = 0; i < size; i++) {
            randArray[i] = liveArray[i / numCols][i % numCols];
        }

        randomizeArray(randArray);
    }

    public void randomizeViaFisherYatesKnuth() {
        randomize();
    } // randomizeViaFisherYatesKnuth()

    // Each number should be randomly distributed, each number appears exactly once.
    public void randomizeInRange() {
        int size = numRows * numCols;
        int[] randArray = new int[size];

        for (int i = 0; i < size; i++) {
            randArray[i] = i;
        }

        randomizeArray(randArray);
    } // randomizeInRange()

    public void save(String dataFileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(dataFileName))) {

            writer.println(DEFAULT_VALUE);
            writer.println(numRows + " " + numCols);

            for (int row = 0; row < numRows; row++) {
                writer.print(row);

                for (int col = 0; col < numCols; col++) {
                    int value = liveArray[row][col];
                    writer.print(" " + value);
                }
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // save(...)

    // Save to a file names ppa_data.txt
    public void save() {
        save("ppa_data.txt");
    } // save()

    private void initArrays() {
        liveArray = new int[numRows][numCols]; // Add this to reset liveArray as well.
        nextArray = new int[numRows][numCols];
        System.out.println(numRows + " " + numCols);
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                nextArray[row][col] = DEFAULT_VALUE;
                liveArray[row][col] = DEFAULT_VALUE;
            }
        }
    }


    public boolean loadFile(String dataFilePath) {
        boolean retVal = true;

        try (BufferedReader myReader = new BufferedReader(new FileReader(dataFilePath))) {
            String inputLine;
            DEFAULT_VALUE = Integer.parseInt(myReader.readLine());
            randMin = randMax = DEFAULT_VALUE;
            inputLine = myReader.readLine();
            int[] rowCol = Arrays.stream(inputLine.split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            numRows = rowCol[0]; numCols = rowCol[1];

            initArrays();

            int curRow = 0, rowLength = 0;
            while ((inputLine = myReader.readLine()) != null) {
                // Process each inputLine here
                int[] readRow = Arrays.stream(inputLine.split("\\s+"))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                curRow = readRow[0];

                for (int curCol = 1; curCol < readRow.length; ++curCol) {
                    nextArray[curRow][curCol-1] = readRow[curCol];
                }  //  for(int curCol = 0; curCol < readRow.length; ++curCol)
            }  //  while ((inputLine = myReader.readLine()) != null)
        } catch (IOException e) {
            e.printStackTrace();
            retVal = false;
            return retVal;
        }  // try ... catch.
        retVal = true;
        return retVal;
    }  //  public boolean loadFile(...)


    // TODO: May need to modify to return new array having iterated over liveArray
    public int[][] getArray() {return liveArray.clone();}

    public void copyToNextArray() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                nextArray[row][col] = liveArray[row][col]; // Copy value
            }
        }
    } // copyToNextArray()

    public void set(int row, int col, int newValue) {
        nextArray[row][col] = newValue;
    } // set(...)



    // Should update the nextArray to the sum of the nearest neighbor elements in the liveArray
    public void updateToNNSum() {
        // I think I basically do a for loop and iterate over all elements, and for each element set it to the sum of the nearest neighbor elements in the liveArray

        int NNIndex = 0;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int nextRow = (row + 1) % numRows;
                int prevRow = (numRows + row - 1) % numRows;
                int nextCol = (col + 1) % numCols;
                int prevCol = (numCols + col - 1) % numCols;

                RCPair[] NN = getNearestNeighborsArray(row, col);

                nextArray[row][col] = 0;
                for (int i = 0; i < NN.length; i++) {
                    nextArray[row][col] += liveArray[NN[i].row][NN[i].col];
                }
                NNIndex++;
            }
        }
    } // updateToNNSum()

    public void updateToNearestNNSum() {
        updateToNNSum();
    } // updateToNearestNNSum()

    public void printArray() {
        for (int row = 0; row < numRows; row++) {
            System.out.print(row + " ");
            for (int col = 0; col < numCols; col++) {
                System.out.print(liveArray[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    } // printArray()

    public void swapLiveAndNext() {
        int[][] temp = liveArray;
        liveArray = nextArray.clone();
        int[][] nextArray = temp.clone();
        //nextArray = temp;
    } // swapLiveAndNext()

    public int getNumRows() {
        return numRows;
    }
    public int getNumCols() {
        return numCols;
    }

    public int getVal(int row, int col) {
        return liveArray[row][col];
    }

    public int getDefaultValue() {
        return DEFAULT_VALUE;
    }

}
