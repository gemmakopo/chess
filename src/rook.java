
public class rook extends piece
{

    public rook(boolean promoted, boolean captured, 
        boolean forwardFacing, String type, int player, int col, int row)
    {
        super(promoted, captured, forwardFacing, type, player, col, row);
    }

    public boolean move(int c, int r)
    {
        int column = this.getColumn();
        int row = this.getRow();

        if(c==column && r==row)
        {
            //do something?
        }
        if(c<0 || c>4 || r<0 || r>4)
        {
            return false;
        }
        if(!this.getPromoted())
        {
            if((c==column && (r==row+1||r==row-1||r==row+2||r==row-2))||
            (r==row && (c==column+1||c==column+2||c==column-1||c==column-2)))
            {
                this.setColumn(c);
                this.setRow(r);
                return true;
            }
        }
        else
        {
            if((c==column && (r==row+1||r==row-1||r==row+2||r==row-2))||
            (r==row && (c==column+1||c==column+2||c==column-1||c==column-2))||
            ((r==row+1 || r==row-1) && (c==column +1 || c==column-1)))
            {
                this.setColumn(c);
                this.setRow(r);
                return true;
            }
        }
        return false;
    }
}