
import java.util.ArrayList;
import java.util.Random;

public class AI extends Player{
    public AI(String name, char c) {
        super(name, c);
    }

    //Function that returns computes a heuristic and returns the best move
  public int[] getScore(Board board, char opponentColor){
      int value =0;

      //Value that will be returned
      int[] returnValue = new int[2];

      //Holds all values with the some heuristic value so we can randomly select one
      ArrayList<Integer> colRandom = new ArrayList<Integer>();

      //Go through each column and get the score for each move candidate
      for(int i=0;i<7;i++)
      {
          Board copy = new Board();
          copy = board;

          //Find the next open move in the column specified
          int row = board.nextOpenMove(i);

          //Only if the column isn't full
          if(row != -1) {

              //If there is a winning move
              //Always do winning move
              if (copy.getLocation(i, row).color == '0') {
                  copy.setLocation(i, row, getColor());

                  //Check if this move produces a win
                  char win = winner(copy);

                  //if we can win by selecting this column
                  if (win == getColor()) {
                      value = 50;
                      returnValue[0] = value;
                      returnValue[1] = i;
                      copy.setLocation(i, row, '0');
                      return returnValue;
                  }

                  //Reset the location to what it was originally
                  copy.setLocation(i, row, '0');

                  //If the other team can win
                  //Checks to see if it can block a winning move
                  copy.setLocation(i, row, opponentColor);

                  win = winner(copy);

                  //If the opponent can win on this column selection
                  if (win == opponentColor) {
                      value = 40;
                      returnValue[0] = value;
                      returnValue[1] = i;
                      copy.setLocation(i, row, '0');
                      return returnValue;
                  }

                  //Reset the location to what it was originally
                  copy.setLocation(i, row, '0');
              }


                  int score = 0; //Stores the heuristic value

                    //Check if we can make a two in a row
                  score = twoInARow(board, i, row);

                  //if there is a better score set it
                  if (score > returnValue[0]) {
                      returnValue[0] = score;
                      returnValue[1] = i;

                      //Clear arraylist
                      colRandom.clear();
                      colRandom.add(i);
                  }
                  //if there are the same heuristic score add to arrau
                  else if(score == returnValue[0])
                  {
                      colRandom.add(i);
                  }

                  //check if there is a three in a row
                  score = threeInARow(board, i, row);

                  //If there is a better score select it
                  if (score > returnValue[0]) {
                      returnValue[0] = score;
                      returnValue[1] = i;

                      //Clear list
                      colRandom.clear();
                      colRandom.add(i);
                  }
                  //If there is the same heurstic score add
                  else if(score == returnValue[0])
                  {
                      colRandom.add(i);
                  }
          }
      }
      //If the list is longer than 1
      //Randomly select a col since they all have the same heuristic score
      if(colRandom.size() > 1)
      {
          Random rand = new Random();
          int index = rand.nextInt(colRandom.size());

          //Set column value to the randomly generated number
          returnValue[1] = colRandom.get(index);
      }
      return returnValue;

  }

    //Checks how many in a row this piece would give
    public int twoInARow(Board board,int col,int row)
    {

        //two in a row vertical (down one)
        if(row <5)
        {
            char piece = board.getLocation(col, row + 1).color;
            if(piece == getColor())
            {
                return 20;
            }
        }

        //Two horizontal right
        if(col <6)
        {
            char piece = board.getLocation(col+1, row).color;

            if(piece == getColor())
            {
                return 20;
            }
        }

        //Horizontal left
        if(col >0)
        {
            char piece = board.getLocation(col-1, row).color;
            if(piece == getColor())
            {
                return 20;
            }
        }

        //Diagonal up right
        if(col<6 && row>0)
        {
            char piece = board.getLocation(col+1, row-1).color;
            if(piece == getColor())
            {
                return 20;
            }
        }

        //Diagonal up left
        if(col>0 && row>0)
        {
            char piece = board.getLocation(col-1, row-1).color;
            if(piece == getColor())
            {
                return 20;
            }
        }

        //Diagonal down left
        if(col>0 && row<5)
        {
            char piece = board.getLocation(col-1, row+1).color;
            if(piece == getColor())
            {
                return 20;
            }
        }

        //Diagonal down right
        if(col<6 && row<5)
        {
            char piece = board.getLocation(col+1, row+1).color;
            if(piece == getColor())
            {
                return 20;
            }
        }

        //If no two in a rows were found
        return 0;

    }
    // Check 3 in a row
    public int threeInARow(Board board,int col,int row) {


      //Vertical check
        if (row < 4)
        {
            char piece = board.getLocation(col, row+1).color;
            char piece1 = board.getLocation(col, row+2).color;

            if(piece == getColor() && piece1 == getColor())
            {
                return 30;
            }
        }

        //Horiontal Check in Between
        if (col<6 && col>0)
        {
            char piece = board.getLocation(col+1, row).color;
            char piece1 = board.getLocation(col-1, row).color;

            if(piece == getColor() && piece1 == getColor())
            {
                return 30;
            }
        }

        //Horiontal Check Left
        if (col>1)
        {
            char piece = board.getLocation(col-1, row).color;
            char piece1 = board.getLocation(col-2, row).color;

            if(piece == getColor() && piece1 == getColor())
            {
                return 30;
            }
        }

        //Diagonal up right
        if (col<5 && row>1)
        {
            char piece = board.getLocation(col+1, row-1).color;
            char piece1 = board.getLocation(col+2, row-2).color;

            if(piece == getColor() && piece1 == getColor())
            {
                return 30;
            }
        }

        //Diagonal right in between
        if (col>0 && col<6 && row<5 && row >0)
        {
            char piece = board.getLocation(col+1, row-1).color;
            char piece1 = board.getLocation(col-1, row+1).color;

            if(piece == getColor() && piece1 == getColor())
            {
                return 30;
            }
        }
        //Diagonal left in between
        if (col>0 && col<6 && row<5 && row >0)
        {
            char piece = board.getLocation(col+1, row+1).color;
            char piece1 = board.getLocation(col-1, row-1).color;

            if(piece == getColor() && piece1 == getColor())
            {
                return 30;
            }
        }
        //Diagonal up left
        if (col>1 && row>1)
        {
            char piece = board.getLocation(col-1, row-1).color;
            char piece1 = board.getLocation(col-2, row-2).color;

            if(piece == getColor() && piece1 == getColor())
            {
                return 30;
            }
        }

        //Diagonal down left
        if (col>1 && row<4)
        {
            char piece = board.getLocation(col-1, row+1).color;
            char piece1 = board.getLocation(col-2, row+2).color;

            if(piece == getColor() && piece1 == getColor())
            {
                return 30;
            }
        }

        //Diagonal down right
        if (col<5 && row<4)
        {
            char piece = board.getLocation(col+1, row).color;
            char piece1 = board.getLocation(col+2, row).color;

            if(piece == getColor() && piece1 == getColor())
            {
                return 30;
            }
        }

        return 0;
    }

    //Calls game over to check if the board input has a winner
    //Used to simulate the column selection to check if the AI or opponent can win
    public char winner(Board gameBoard){
       Board testBoard = gameBoard;
       Game testGame = new Game(testBoard);

       if(testGame.GameOver() != '0')
       {
           return testGame.GameOver();
       }
       return '0';
    }
}
