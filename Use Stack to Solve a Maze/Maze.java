import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
* Maze represents a maze that can be navigated. The maze
* should indicate its start and end squares, and where the
* walls are. 
*
* This class is able to load a maze from a file, and solve the maze.
* It can read and store the information about where the walls of the maze are.
*
*/
public class Maze { 
    //Number of rows in the maze.
    private int numRows;
    
    //Number of columns in the maze.
    private int numColumns;
    
    //Grid coordinates for the starting maze square
    private int startRow;
    private int startColumn;
    
    //Grid coordinates for the final maze square
    private int finishRow;
    private int finishColumn;
    
    //a 2D array of maze squares
    private MazeSquare[][] myMaze;
    //stores the currentSquare
    private MazeSquare currentSquare;
    //stores the maze squares that are on the solution path
    private Stack<MazeSquare> squareStack;
    //the state of square's movement possibility
    private boolean canMove;
    //if the maze is solvable or is solved
    private boolean hasSolution;
    
    /**
     * Creates an empty maze with no squares.
     */
    public Maze() {
        //initialize a new stack of solution squares
        squareStack = new MysteryStackImplementation<MazeSquare>();
        //initially assume it can move and has no solution
        canMove = true;
        hasSolution = false;
        
    } 
    
    /**
     * Loads the maze that is written in the given fileName.
     */
    public boolean load(String fileName) { 
        Scanner scanner = null;
        try {
            //Open a scanner to read the file
            scanner = new Scanner(new File(fileName));
            numColumns = scanner.nextInt();
            numRows = scanner.nextInt();
            startColumn = scanner.nextInt();
            startRow = scanner.nextInt();
            finishColumn = scanner.nextInt();
            finishRow = scanner.nextInt();
            
            //Check if the start or finish squares are out of bounds
            if(!isInRange(startRow, 0, numRows) 
                    || !isInRange(startColumn, 0, numColumns)
                    || !isInRange(finishRow, 0, numRows) 
                    || !isInRange(finishColumn, 0, numColumns)) {
                System.err.println("Start or finish square is not in maze.");
                scanner.close();
                return false;
            }
       
            //Create a new 2D array of mazeSquares
            myMaze = new MazeSquare[numRows][numColumns];
            //Skips white space
            scanner.nextLine(); 
            //Gets text string from each row and put them to a character array
            for (int i=0; i<numRows; i++){
                String row = scanner.nextLine();
                row = row.strip();
                char[] rowArray = row.toCharArray();
                //If there are too many or too few characters in the row of a maze file
                if (rowArray.length!=numColumns){
                    System.err.println("Maze file formatted incorrectly. Wrong number of descriptors.");
                    return false;
                }
                //Creates a new square from given description in given location
                for (int j=0; j<numColumns; j++){
                    char descriptor = rowArray[j];
                    MazeSquare myMazeSquare = new MazeSquare(descriptor, i, j);
                    //Add the new square to the 2D array
                    myMaze[i][j] = myMazeSquare; 
                }
            }
        //catch error and print usage statement
        } catch(FileNotFoundException e) {
            System.err.println("The requested file, " + fileName + ", was not found.");
            return false;
        } catch(InputMismatchException e) {
            System.err.println("Maze file not formatted correctly.");
            scanner.close();
            return false;
        } 
        
        return true;
    } 
    
    /**
     * Returns true if number is greater than or equal to lower bound
     * and less than upper bound. 
     * @param number
     * @param lowerBound
     * @param upperBound
     * @return true if lowerBound ≤ number < upperBound
     */
    private static boolean isInRange(int number, int lowerBound, int upperBound) {
        return number < upperBound && number >= lowerBound;
    }
    
    /**
     * Prints the maze with the start and finish squares marked. Does
     * not include a solution.
     */
    public void print() {
        //We'll print off each row of squares in turn.
        for(int row = 0; row < numRows; row++) {
            
            //Print each of the lines of text in the row
            for(int charInRow = 0; charInRow < 4; charInRow++) {
                //Need to start with the initial left wall.
                if(charInRow == 0) {
                    System.out.print("+");
                } else {
                    System.out.print("|");
                }
                
                for(int col = 0; col < numColumns; col++) {
                    MazeSquare curSquare = this.getMazeSquare(row, col);
                    if(charInRow == 0) {
                        //We're in the first row of characters for this square - need to print
                        //top wall if necessary.
                        if(curSquare.hasTopWall()) {
                            System.out.print(getTopWallString());
                        } else {
                            System.out.print(getTopOpenString());
                        }
                    } else if(charInRow == 1 || charInRow == 3) {
                        //These are the interior of the square and are unaffected by
                        //the start/final state.
                        if(curSquare.hasRightWall()) {
                            System.out.print(getRightWallString());
                        } else {
                            System.out.print(getOpenWallString());
                        }
                    } else {
                        //We must be in the second row of characters.
                        //This is the row where start/finish should be displayed if relevant
                        
                        //Check if we're in the start or finish state
                        if(startRow == row && startColumn == col) {
                            System.out.print("  S  ");
                        } else if(finishRow == row && finishColumn == col) {
                            System.out.print("  F  ");
                            
                        //If the maze has been solved, print * along the solution path
                        //and white spaces on other squares
                        } else if (hasSolution){
                            //Check if the current square is the solution square from the stack
                            if (curSquare.isSolutionSquare()){
                                System.out.print("  *  ");
                            } else {
                                System.out.print("     ");
                            }
                        //If the maze hasn't been solved, print white spaces on other squares
                        } else {
                            System.out.print("     ");
                        }
                        if(curSquare.hasRightWall()) {
                            System.out.print("|");
                        } else {
                            System.out.print(" ");
                        }
                    }     
                }
                
                //Now end the line to start the next
                System.out.print("\n");
            }           
        }
        
        //Finally, we have to print off the bottom of the maze, since that's not explicitly represented
        //by the squares. Printing off the bottom separately means we can think of each row as
        //consisting of four lines of text.
        printFullHorizontalRow(numColumns);
    }
    
    /**
     * Prints the very bottom row of characters for the bottom row of maze squares (which is always walls).
     * numColumns is the number of columns of bottom wall to print.
     */
    private static void printFullHorizontalRow(int numColumns) {
        System.out.print("+");
        for(int row = 0; row < numColumns; row++) {
            //We use getTopWallString() since bottom and top walls are the same.
            System.out.print(getTopWallString());
        }
        System.out.print("\n");
    }
    
    /**
     * Returns a String representing the bottom of a horizontal wall.
     */
    private static String getTopWallString() {
        return "-----+";
    }
    
    /**
     * Returns a String representing the bottom of a square without a
     * horizontal wall.
     */
    private static String getTopOpenString() {
        return "     +";
    }
    
    /**
     * Returns a String representing a left wall (for the interior of the row).
     */
    private static String getRightWallString() {
        return "     |";
    }
    
    /**
     * Returns a String representing no left wall (for the interior of the row).
     */
    private static String getOpenWallString() {
        return "      ";
    }
    
    /**
     * Returns the mazeSquare with given row and column
     */
    public MazeSquare getMazeSquare(int row, int col) {
        return myMaze[row][col];
    }
    
    /**
     * Checks for possible moves from the given current square
     * If there's a possible move, add the next square to solution stack and update the current square 
     * If there's no possible move, removed the top square from the solution stack
     */
    public void movement(MazeSquare square){
        int row = square.getRow();
        int column = square.getColumn();
        boolean hasMoved = false;
        //If the square is not along the top row
        if (row!=0){
            MazeSquare topSquare = myMaze[row-1][column];
            //Move up if the square above is not separated by a wall and was not visited before
            if (!square.hasTopWall()&& !topSquare.wasVisited()){
                currentSquare = topSquare;
                hasMoved = true;
            }
        }
        //If the square is not along the bottom row
        if (row!=(numRows-1)){
            MazeSquare bottomSquare = myMaze[row+1][column];
            //Move down if the square below is not separated by a wall and was not visited before
            if (!bottomSquare.hasTopWall()&& !bottomSquare.wasVisited() && !hasMoved){
                currentSquare = bottomSquare; 
                hasMoved = true;
            }
        }
        //If the square is not along the far right column
        if (column!=(numColumns-1)){
            MazeSquare rightSquare = myMaze[row][column+1];
            //Move right if the square on the right is not separated by a wall and was not visited before
            if (!square.hasRightWall()&& !rightSquare.wasVisited()&& !hasMoved){
                currentSquare = rightSquare; 
                hasMoved = true;
            }
        }
        //If the square is not along the far left column
        if (column!=0){
            MazeSquare leftSquare = myMaze[row][column-1];
            //Move left if the square on the left is not separated by a wall and was not visited before
            if (!leftSquare.hasRightWall()&& !leftSquare.wasVisited()&& !hasMoved){
                currentSquare = leftSquare; 
                hasMoved = true;
            }
        }
        
        //If the square can move, update the states of the square and add it to the solution stack
        if (hasMoved){
            currentSquare.isSolution();
            currentSquare.visit();
            squareStack.push(currentSquare);
        }        
        
        //If the square cannot move, we remove the square from the solution stack, and update its 
        //state
        if (!hasMoved){
            MazeSquare removedSquare = squareStack.pop();
            removedSquare.isNotSolution();
        }
        
    }
    
    /**
     * Computes and returns a solution to this maze. If there are multiple
     * solutions, only one is returned, and getSolution() makes no guarantees about
     * which one. However, the returned solution will not include visits to dead
     * ends or any backtracks, even if backtracking occurs during the solution
     * process. 
     *
     * @return a stack of MazeSquare objects containing the sequence of squares
     * visited to go from the start square (bottom of the stack) to the finish
     * square (top of the stack). If there is no solution, an empty stack is
     * returned.
     */ 
    public Stack<MazeSquare> getSolution() {
        //Sets initial conditions and add start square to the stack
        MazeSquare startSquare = myMaze[startRow][startColumn];
        MazeSquare finishSquare = myMaze[finishRow][finishColumn];
        currentSquare = startSquare;
        currentSquare.visit();
        squareStack.push(currentSquare);
        
        //Move the square until it gets stuck or finishes solving the maze
        while(canMove){
            this.movement(currentSquare);
            //If the maze is unsolvable
            if (squareStack.isEmpty()) {
                System.out.println("This maze is unsolvable.");
                return squareStack;
            }
            //If the stack contains a solution to the maze.
            if (squareStack.peek() == finishSquare) {
                hasSolution = true;
                return squareStack;
            }
            currentSquare = squareStack.peek();
        }
        return squareStack;
    }
 
    /**
     * The main method
     * If there is only one command line argument, it loads the maze and prints it
     * with no solution. If there are two command line arguments
     * and the second one is --solve,
     * it should load the maze, solve it, and print the maze
     * with the solution marked. No other command lines are valid.
     */ 
    public static void main(String[] args) { 
        if (args.length < 1 || args.length > 2){
            System.err.println("Required arguments: mazeFile (--solve)");
            System.exit(1);
        }
        if (args.length == 2 && !args[1].equals( "--solve")){
            System.err.println("Second argument formatted incorrectly. It should be --solve");
            System.exit(1);
        }
        //input fileName from command line
        String fileName = args[0];
        Maze maze = new Maze();
        if (maze.load(fileName)){
            if (args.length == 1){
                maze.print();
            }
            else if (args.length == 2){
                maze.getSolution();
                maze.print();
            }
        }
    } 
}