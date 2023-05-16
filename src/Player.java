public class Player {

    String Name;
    char Color;

    public Player()
    {

    }
    public Player(String name, char c)
    {
        Name = name;
        Color = c;
    }
    public Player(char c)
    {
        Color = c;
    }

    public String getPlayerName(){
        return Name;
    }

    public void setPlayerName(String name){
        Name = name;
    }

    public char getColor(){
        return Color;
    }

    public void setColor(char color)
    {
        Color = color;
    }

    public int[] getScore(Board board, char opponentColor)
    {
        return null;
    }

}
