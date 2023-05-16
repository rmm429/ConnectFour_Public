public class Game {

    public Board gameBoard;
    Player player1;
    Player player2;

    public Game()
    {
        gameBoard = new Board();
    }

    public Game(Board gb)
    {
        gameBoard = gb;

    }
    /**
     * Check if a player has won the game
     * @return char The winner of the game (if a winner was determined)
     */
    public char GameOver(){
        
        // The char storing the winner of the game
        char winner;
        
        // Set the winner of the game to the result of the Diagonal() method
        winner=Diagonal();
        // Return the winning player if there was a diagonal winner determined
        if(winner != 0)
        {
            return winner;
        }

        // Set the winner of the game to the result of the Horizontal() method
        winner = Horizontal();
        // Return the winning player if there was a horizontal winner determined
        if(winner != 0)
        {
            return winner;
        }

        // Set the winner of the game to the result of the Vertical() method
        winner = Vertical();
        // Return the winning player if there was a vertical winner determined
        if(winner != 0)
        {
            return winner;
        }
        
        // Return that no player has won the game yet
        return 0;
    }
    
    /**
     * Check for Diagonal four in a row
     * @return char The winner of the game (if a diagonal four in a row is found)
     */
    public char Diagonal(){
        
        // Variable to hold the piece color
        char check = 0; 
        // Variable to hold the status of a winning game
        boolean winner = false;

        // Check diagonal Right
        for(int i=0; i<4;i++){
            for(int j=0;j<3;j++)
            {
                check = gameBoard.getLocation(i,j).color;
                if(check != '0')
                {
                    //check to see if next 3 are the same color
                    for(int k=1;k<4;k++)
                    {
                        if(gameBoard.getLocation(i+k,j+k).color != check)
                        {
                            winner=false;
                            break;
                        }
                        else
                        {
                            winner = true;
                        }
                    }
                    if(winner)
                    {
                        return check;
                    }
                }
            }
        }
        // Check for diagonal left
        for(int i=6; i>2;i--){
            for(int j=0;j<3;j++)
            {
                check = gameBoard.getLocation(i,j).color;
                if(check != '0') {
                    for (int k = 1; k < 4; k++) {
                        if (gameBoard.getLocation(i - k, j + k).color != check) {
                            winner = false;
                            break;
                        } else {
                            winner = true;
                        }
                    }
                    if (winner) {
                        return check;
                    }
                }
            }
        }

        return 0;

    }
    
    /**
     * Check for horizontal four in a row
     * @return char The winner of the game (if a horizontal four in a row is found)
     */
    public char Horizontal(){

        // Variable to hold the piece color
        char check = 0;
        // Variable to hold the status of a winning game
        boolean winner = false;

        for(int i=0; i<5;i++){

            for(int j=0;j<6;j++) {

                check = gameBoard.getLocation(i,j).color;

                if(check != '0') {

                    for (int k = 1; k < 4; k++) {

                        // Was previously checking columns that were out-of-bounds without this statement
                        if ( (i+k) < 7) { // Ensures that the current column being checked is in bounds

                            if (gameBoard.getLocation(i+k, j).color != check) {
                                winner = false;
                                break;
                            } else {
                                winner = true;
                            }

                        } else {
                            winner = false;
                            break;
                        }

                    }

                    if (winner) {
                        return check;
                    }

                }

            }

        }

        return 0;

    }
    
    /**
     * Check for a Vertical four in a row
     * @return char The winner of the game (if a vertical four in a row is found)
     */
    public char Vertical(){

        // Variable to hold the piece color
        char check = 0;
        // Variable to hold the status of a winning game
        boolean winner = false;

        for(int i=0; i<7;i++) {

            for(int j=0;j<4;j++) {

                check = gameBoard.getLocation(i,j).color;

                if(check != '0') {

                    for (int k = 1; k < 4; k++) {

                        // Was previously checking rows that were out-of-bounds without this statement
                        if ( (j+k) < 6 ) { // Ensures that the current row being checked is in bounds

                            if (gameBoard.getLocation(i, j + k).color != check) {
                                winner = false;
                                break;
                            } else {
                                winner = true;
                            }

                        } else {
                            winner = false;
                            break;
                        }

                    }

                    if (winner) {
                        return check;
                    }

                }

            }

        }

        return 0;
    }

    /**
     * Add a game piece to the game board controller
     * @param col The column to add the piece to
     * @param row The row to add the piece to
     * @param color The color of the piece to add
     * @return Nothing
     */
    public void addGamePiece(int col, int row, char color) {
        // Set the location of the current game piece in the game board controller
        gameBoard.setLocation(col, row, color);
//        //DELETE LATER ---------
//        gameBoard.getBoard();
//        // ----------------------
    }

    /**
     * Get the color of a game piece from the game board controller
     * @param col The column to add the piece to
     * @param row The row to add the piece to
     * @return char The color of the game piece
     */
    public char getLocationStatus(int col, int row) {
        return gameBoard.getLocation(col, row).color;
    }

    /**
     * Get the column fill status from the game board controller
     * @param col The column to add the piece to
     * @return boolean Whether or not the column is full
     */
    public boolean isColumnFull(int col) {
        return gameBoard.isColumnFull(col);
    }

    /**
     * Get the board fill status
     * @param col The column to check the fill status of
     * @return boolean Whether or not the board is full
     */
    public boolean isBoardFull(int col) {

        // Checking every column to see if any are empty
        for (int i = 0; i < col; i++) {

            if (isColumnFull(i) == false) {
                return false;
            }

        }

        // If no columns are empty, then the board is full
        return true;

    }

}
