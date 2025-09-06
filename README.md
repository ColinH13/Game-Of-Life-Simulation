<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
</head>
<body>
  <h1>Game of Life Simulation</h1>

  <p>
    <a href="#the-basics">The Basics</a> |
    <a href="#quick-start-guide">Running the Simulation</a> |
    <a href="#how-it-works">How it Works</a>
  </p>

  <h2 id="the-basics">The Basics</h2>
  <p>
    This project is a Game Of Life Simulation, which is an implementation of a model called cellular automata.
    A cellular automata model is made up of a grid of cells, where each cell can exist in one of a finite number of states. 
    The Game of Life cellular automaton itself was created by the mathematician John Conway in 1970, and was designed to simulate complex patterns from simple rules. <br>
    <br>
    In this simulation, there are only two possible states for each cell: Alive (0) and Dead (1). After each "tick", or discrete update in the simulation, the state of every cell is subject to change. 
    To put it simply, if a cell has too many or too few adjacent cells, or "neighbors", the cell will switch states in the next tick of the simulation.
    The method that determines whether a cell will keep or change states will be discussed more in-depth later in this document, but for now this general explanation will suffice.
  </p>

  <h2 id="quick-start-guide">Quick Start Guide</h2>
  <p>
    <h3>Installation</h3>
      <p> 
        <h4>Pre-requisites:</h4>
          Before you can properly run the code, you must have installed: <br> <br>
          1. Java JDK 17+ <br>
          2. Your choice of IDE (I used IntelliJ with Gradle)
        <br> <br> Once you have installed your JDK and IDE, clone this repository onto your machine using your preferred method.
      </p>
    <h3>Running the Simulation</h3>
      To run the simulation, you can run the CHDriver.java file with 1 or 0 arguments. <br>
      <h3> One Argument </h3>
      The only argument is the name of an input file to start the simulation from a predetermined point based on contents of the file, similar to the below example from gol_input_2.txt : <br> <br>
      <img width="229" height="215" alt="image" src="https://github.com/user-attachments/assets/253e6f3b-a9dc-45b4-8661-80e90e58762a" /> <br> <br>
      On line 1, there must be a single number indicating the default value of the array. If any row or column is missing values, the grid will still be populated with the default value. <br>
      On line 2, there must be two inputs, which determine the dimensions  (rows x columns) of the grid. <br>
      The remaining lines must be numbered according to what row they correspond to. <br> 
      <br>
      Running the program with the given input will result in the following start and end states:
      <img width="433" height="500" alt="image" src="https://github.com/user-attachments/assets/cb4ff83e-c8f4-4a78-9679-69a0b630410d" />
      <img width="835" height="875" alt="image" src="https://github.com/user-attachments/assets/617e0c0b-807b-47cf-a514-fc534a0e16b8" />


      <br>
      <h3> No Arguments </h3>
      Running the simulation with zero arguments is much simpler. Simply start the simulation by running the CHDriver.java file, and a 100 x 100 grid will be generated with random placement of live and dead cells.
      The distribution of live and dead cells is pseudo random, implemented using the Random class from the java.util package. <br> 
      However, the total number of live cells is deterministic, and based on the following formula: <br>
      <br>
      <img width="592" height="85" alt="image" src="https://github.com/user-attachments/assets/aec1c60a-fc95-469b-a12c-5622a310cddf" /> <br>
      This ensures that the simulation starts with approximatrely 20% of the cells alive, and the rest dead.

      
      
      

      
      
      
    </h3>
    Describe how to install, set up, and run the project here. 
    For example: what commands to type, any dependencies required, or how to view results.
  </p>

  <h2 id="how-it-works">How it Works</h2>
  <p>
    Add a high-level explanation of the project’s logic or structure here. 
    For example: describe the main components, algorithms, or flow of data.
  </p>
</body>
</html>
