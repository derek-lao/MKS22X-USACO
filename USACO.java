import java.util.*;
import java.io.*;
import java.lang.Integer.*;
public class USACO{

  /**
  BRONZE Problem 12: Lake Making [Rob Kolstad, 2008]

  Farmer John wants his cows to help him make a lake. He has mapped
  the pasture where he wants to build the lake by creating a R (3 <=
  R <= 100) row by C (3 <= C <= 100) column grid of six foot by six
  foot squares and then by determining the average elevation (10 <=
  elev_rc <= 5000) in inches for each square.

  Additionally, he has trained the cows in "stomp digging". The burly
  bovines travel in a herd that just exactly covers a 3x3 grid of
  squares to a grid whose upper left coordinate is R_s,C_s (1 <= R_s
  <= R-2; 1 <= C_s <= C-2). The cows then stomp the ground to push
  it down D_s (1 <= D_s <= 40) inches. The cows are quite meticulous;
  the cows at lower elevations will not commence stomping until the
  rest of the herd has joined them. Thus, not all the 3x3 grid is
  necessarily stomped (or perhaps some part is stomped less than some
  other part).

  Given an initial set of elevations, an ordered set of N (1 <= N <=
  20000) stomp digging instructions, and an elevation E (0 <= E <=
  5000) for the lake's final water level, determine the volume of
  water (in cubic inches) that the lake will hold. It is guaranteed
  that the answer will not exceed 2,000,000,000.  Presume that the
  edge of the lake contains barriers so that water can not spill over
  the border.

  Consider a small 4 x 6 pasture to be turned into a lake. Its initial
  elevations (annotated with row/column numbers) are:

                        column
                    1  2  3  4  5  6
           row 1:  28 25 20 32 34 36
           row 2:  27 25 20 20 30 34
           row 3:  24 20 20 20 20 30
           row 4:  20 20 14 14 20 20

  Interpreting the map, we see a hill in the upper right corner that
  rises to elevation 36; a small hill also rises to elevation 28 in
  the upper left corner. The middle of row 4 has a slight depression.
  After the cow-stomping instruction "1 4 4", the pasture has these
  elevations:
                    1  2  3  4  5  6
           row 1:  28 25 20 32 32 32
           row 2:  27 25 20 20 30 32
           row 3:  24 20 20 20 20 30
           row 4:  20 20 14 14 20 20

  Note that only three squares were stomped down. The other six sets
  of cows were waiting for the stompers to get to their level, which
  never happened.  After stomping down the upper left corner with
  this instruction "1 1 10", the pasture looks like this:

                    1  2  3  4  5  6
           row 1:  18 18 18 32 32 32
           row 2:  18 18 18 20 30 32
           row 3:  18 18 18 20 20 30
           row 4:  20 20 14 14 20 20

  If the final elevation of the lake is to be 22 inches, the pasture
  has these depths:
                    1  2  3  4  5  6
           row 1:   4  4  4 -- -- --
           row 2:   4  4  4  2 -- --
           row 3:   4  4  4  2  2 --
           row 4:   2  2  8  8  2  2

  for a total aggregated depth of 66. Calculate the volume by multiplying
  by 6 feet x 6 feet = 66 x 72 inches x 72 inches = 342,144 cubic
  inches.

  Write a program to automate this calculation.

  PROBLEM NAME: makelake

  INPUT FORMAT:

  * Line 1: Four space-separated integers: R, C, E, N

  * Lines 2..R+1: Line i+1 describes row of squares i with C
          space-separated integers

  * Lines R+2..R+N+1: Line i+R+1 describes stomp-digging instruction i
          with three integers: R_s, C_s, and D_s

  SAMPLE INPUT (file makelake.in):

  4 6 22 2
  28 25 20 32 34 36
  27 25 20 20 30 34
  24 20 20 20 20 30
  20 20 14 14 20 20
  1 4 4
  1 1 10

  INPUT DETAILS:

  As per the example from the text.

  OUTPUT FORMAT:

  * Line 1: A single integer that is the volume of the new lake in cubic
          inches

  SAMPLE OUTPUT (file makelake.out):

  342144


  **********************************************************
  */
  public static int bronze(String filename) throws FileNotFoundException{
    File text = new File(filename);
    Scanner scanner = new Scanner(text);
    int lineCounter = 1;
    int row = 0;
    int col = 0;
    int elev = 0;
    int instr = 0;
    int[][] field = new int[0][0];
    int total = 0;
    while(scanner.hasNextLine())
    {
      String line = scanner.nextLine();
      String[] numArray = line.split(" ");
      if(lineCounter == 1)
      {
        row = Integer.parseInt(numArray[0]);
        col = Integer.parseInt(numArray[1]);
        elev = Integer.parseInt(numArray[2]);
        instr = Integer.parseInt(numArray[3]);
        field = new int[row][col];
      }
      if(lineCounter > 1 && lineCounter <= row + 1)
      {
        for(int i = 0 ; i < numArray.length; i ++)
        {
          field[lineCounter-2][i] = Integer.parseInt(numArray[i]);
        }
      }
      if(lineCounter > row + 1)
      {
        // parse and stomp
        int rowStart = Integer.parseInt(numArray[0]);
        int colStart = Integer.parseInt(numArray[1]);
        int stompDepth = Integer.parseInt(numArray[2]);
        int highestElevation = 0;
        int highestElevationAfterStomping;
        for(int r = rowStart ; r < rowStart + 3 ; r ++)
        {
          for(int c = colStart ; c < colStart + 3 ; c ++)
          {
            if(field[r-1][c-1] > highestElevation)
            highestElevation = field[r-1][c-1];
          }
        }

        if(highestElevation <= stompDepth)
        {
          highestElevationAfterStomping = 0;
        }
        else
        {
          highestElevationAfterStomping = highestElevation - stompDepth;
        }

        for(int r = rowStart ; r < rowStart + 3 ; r ++)
        {
          for(int c = colStart ; c < colStart + 3 ; c ++)
          {
            if(field[r-1][c-1] > highestElevationAfterStomping)
            {
              field[r-1][c-1] = highestElevationAfterStomping;
            }
          }
        }
      }
      // System.out.println(line);
      lineCounter ++;
    }
    for(int r = 0 ; r < row ; r ++)
    {
      for(int c = 0; c < col ; c ++)
      {
        if(field[r][c] < elev)
        {
          total += elev - field[r][c];
        }
      }
    }
    return total * 72 * 72;
  }
  /**
  SILVER Problem 7: Cow Travelling [Aram Shatakhtsyan, 2007]

  Searching for the very best grass, the cows are travelling about
  the pasture which is represented as a grid with N rows and M columns
  (2 <= N <= 100; 2 <= M <= 100). Keen observer Farmer John has
  recorded Bessie's position as (R1, C1) at a certain time and then
  as (R2, C2) exactly T (0 < T <= 15) seconds later. He's not sure
  if she passed through (R2, C2) before T seconds, but he knows she
  is there at time T.

  FJ wants a program that uses this information to calculate an integer
  S that is the number of ways a cow can go from (R1, C1) to (R2, C2)
  exactly in T seconds. Every second, a cow can travel from any
  position to a vertically or horizontally neighboring position in
  the pasture each second (no resting for the cows). Of course, the
  pasture has trees through which no cow can travel.

  Given a map with '.'s for open pasture space and '*' for trees,
  calculate the number of possible ways to travel from (R1, C1) to
  (R2, C2) in T seconds.

  PROBLEM NAME: ctravel

  INPUT FORMAT:

  * Line 1: Three space-separated integers: N, M, and T

  * Lines 2..N+1: Line i+1 describes row i of the pasture with exactly M
          characters that are each '.' or '*'

  * Line N+2: Four space-separated integers: R1, C1, R2, and C2.

  SAMPLE INPUT (file ctravel.in):

  4 5 6
  ...*.
  ...*.
  .....
  .....
  1 3 1 5

  INPUT DETAILS:

  The pasture is 4 rows by 5 colum. The cow travels from row 1, column
  3 to row 1, column 5, which takes exactly 6 seconds.

  OUTPUT FORMAT:

  * Line 1: A single line with the integer S described above.

  SAMPLE OUTPUT (file ctravel.out):

  1

  OUTPUT DETAILS:

  There is only one way from (1,3) to (1,5) in exactly 6 seconds (and
  it is the obvious one that travels around the two trees).
  */
  public static int silver(String filename) throws FileNotFoundException{
    File text = new File(filename);
    Scanner scanner = new Scanner(text);
    int lineCounter = 1;
    int row = 0;
    int col = 0;
    int time = 0;
    int rowDiff = 0;
    int colDiff = 0;
    int[][] field = new int[1][1];
    int[][] odd = new int[1][1];
    int[][] even = new int[1][1];
    int[] start = new int[2];
    int[] end = new int[2];
    int distance = 0;

    int[] rowInc = { 1, 0,-1, 0};
    int[] colInc = { 0, 1, 0,-1};
    while(scanner.hasNextLine())
    {
      String line = scanner.nextLine();
      String[] numArray = line.split(" ");
      if(lineCounter == 1)
      {
        row = Integer.parseInt(numArray[0]);
        col = Integer.parseInt(numArray[1]);
        time = Integer.parseInt(numArray[2]);
        field = new int[row][col];
        even = new int[row][col];
        odd = new int[row][col];
      }
      if(lineCounter >= 2 && lineCounter <= row + 1)
      {
        for(int i = 0; i < col; i ++)
        {
          if(line.charAt(i) == '.')
          {
            // System.out.println("i is now " + i);
            // System.out.println("The string at numArray[i] is " + numArray[i]);
            field[lineCounter-2][i] = 0;
          }
          else
          {
            field[lineCounter-2][i] = -1;
          }
        }
      }
      if(lineCounter == row + 2)
      {
        start[0] = Integer.parseInt(numArray[0]);
        // System.out.println("numArray[0] is " + start[0]);
        start[1] = Integer.parseInt(numArray[1]);
        // System.out.println("numArray[1] is " + start[1]);
        end[0] = Integer.parseInt(numArray[2]);
        // System.out.println("numArray[2] is " + end[0]);
        end[1] = Integer.parseInt(numArray[3]);
        rowDiff = Math.abs(start[0] - end[0]);
        colDiff = Math.abs(start[1] - end[1]);
        distance = rowDiff + colDiff;
        for(int r = 0; r < row; r ++)
        {
          for(int c = 0; c < col; c ++)
          {
            even[r][c] = field[r][c];
            odd[r][c] = field[r][c];
          }
        }
        field[start[0] - 1][start[1] - 1] = 1;
        even[start[0] - 1][start[1] - 1] = 1;
        for(int sec = 0; sec < time; sec ++)
        {
          for(int r = 0; r < row; r ++)
          {
            for(int c = 0; c < col; c ++)
            {
              if(sec % 2 == 1)
              {
                if(even[r][c] != -1)
                {
                  even[r][c] = 0;
                  for(int i = 0; i < 4; i ++) // loop to add from all four surrounding squares
                  {
                    int nextRow = r + rowInc[i];
                    int nextCol = c + colInc[i];
                    boolean inBounds = (nextRow > -1 && nextRow < row) &&
                    (nextCol > -1 && nextCol < col);
                    if(inBounds) // in bounds check
                    {
                      if(odd[nextRow][nextCol] != -1) // check to make sure not adding tree
                      {
                        even[r][c] += odd[nextRow][nextCol];
                      }
                    }
                  }
                }
                // System.out.print(odd[r][c] + " ");
              }
              if(sec % 2 == 0)
              {
                if(odd[r][c] != -1)
                {
                  odd[r][c] = 0;
                  for(int i = 0; i < 4; i ++)
                  {
                    int nextRow = r + rowInc[i];
                    int nextCol = c + colInc[i];
                    boolean inBounds = (nextRow > -1 && nextRow < row) &&
                    (nextCol > -1 && nextCol < col);
                    if(inBounds)
                    {
                      if(even[nextRow][nextCol] != -1)
                      {
                        odd[r][c] += even[nextRow][nextCol];
                      }
                    }
                  }
                }
                // System.out.print(even[r][c] + " ");
              }
            }
            // System.out.println();
          } // now we are done cycling between all the coordinates on the board
          // System.out.println(odd);
          // System.out.println(even);
        }
        if(time % 2 == 1)
        {
          return odd[end[0] - 1][end[1] - 1];
        }
        if(time % 2 == 0)
        {
          return even[end[0] - 1][end[1] - 1];
        }
      }
      lineCounter ++;
    }
    return -2; //so it compiles
  }
}
