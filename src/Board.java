import java.lang.reflect.Array;

public class Board {

    public final int COLS = 7;
    public final int ROWS = 6;

    //First array is the column second is the row
    BoardPieces [][] gameBoard;
   // BoardPieces [][] gameBoard;
    public Board()
    {

//        //DELETE LATER ---------
//        System.out.println("Board INIT");
//        // ----------------------

        gameBoard = new BoardPieces[COLS][ROWS];

        for(int r = 0; r < ROWS; r++) {

            for (int c = 0; c < COLS; c++ ) {

                BoardPieces piece = new BoardPieces(c,r,'0');
                gameBoard[c][r] = piece;

//                //DELETE LATER ---------
//                if (gameBoard[c][r] == 0) {
//                    System.out.print(".\t");
//                } else {
//                    System.out.print(gameBoard[c][r] + "\t");
//                }
//                // ----------------------

            }

//            //DELETE LATER ---------
//            System.out.println();
//            // ----------------------

        }

//        //DELETE LATER ---------
//        System.out.println();
//        // ----------------------

    }

    public BoardPieces getLocation(int column, int row)
    {
        return gameBoard[column][row];
    }

    public void setLocation(int column, int row, char color)
    {
        gameBoard[column][row].color = color;
    }

//    //DELETE LATER ---------
//    public void getBoard() {
//
//        System.out.println("Current Board");
//
//        for(int r = 0; r < ROWS; r++) {
//
//            for (int c = 0; c < COLS; c++ ) {
//
//                if (gameBoard[c][r] == 0) {
//                    System.out.print(".\t");
//                } else {
//                    System.out.print(gameBoard[c][r] + "\t");
//                }
//            }
//
//            System.out.println();
//
//        }
//
//        System.out.println();
//
//    }
//    // ----------------------

//    //DELETE LATER ---------
//    public boolean validColumn(int column) {
//
//        for (int i = 0; i < 6; i++)
//        {
//
//            if(GetLocation(column,i) != 0)
//            {
//                return false;
//            }
//        }
//
//        return true;
//    }
//    // ----------------------

    // The validColumn() function prohibited you from making a move even if only one piece was in a column
    public boolean isColumnFull(int column) { // Ensures that the column is only full if the topmost slot is filled

        if(getLocation(column,0).color != '0') {
            return true;
        }

        return false;
    }

    // Returns the next open move in the column specified unless there is none
    public int nextOpenMove(int column)
    {
        for(int i=0;i<6;i++)
        {
            //if column full return -1
            if(isColumnFull(column) == true)
            {
                return -1;
            }

            BoardPieces piece = getLocation(column,i);

            //If the piece isn't empty return the one above it
            if(piece.color != '0')
            {
                return i-1;
            }
        }
        //If the column is empty return the bottom cell
        return 5;
    }

}
