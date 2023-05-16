public class Human extends Player{

    public Human(String name, char c) {
        super(name, c);
    }

    public void insertGamePiece(char color, int column, Board gameBoard){
        for(int i =0;i<6;i++)
        {
            if(gameBoard.getLocation(column,i).color == '0')
            {
                gameBoard.setLocation(column,i,color);
            }
        }

    }
}
