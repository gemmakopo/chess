
public class king extends piece
{


    public king(boolean promoted, boolean captured, 
        boolean forwardFacing, String type, int player, int col, int row)
    {
        super(promoted, captured, forwardFacing, type, player, col, row);
    }

    public boolean move(int c, int r)
    {
        int column = this.getColumn();
        int row = this.getRow();

        if(c<0 || c>4 || r<0 || r>4)
        {
            return false;
        }
        if((r==row+1 && (c==column||c==column+1||c==column-1))||
        ((r==row-1) && (c==column||c==column+1||c==column-1))||
        ((r==row) && (c==column||c==column+1||c==column-1)))
        {
            this.setColumn(c);
            this.setRow(r);
            return true;
        }
        return false;
    }

}