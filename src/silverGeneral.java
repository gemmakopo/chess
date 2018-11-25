import javax.lang.model.util.ElementScanner6;

public class silverGeneral extends piece
{
    
    public silverGeneral(boolean promoted, boolean captured, 
        boolean forwardFacing, String type, int player, int col, int row)
    {
        super(promoted, captured, forwardFacing, type, player, col, row);
    }

    public boolean move(int c, int r)
    {
        int column = c;
        int row = r;
        if(c<0 || c>4 || r<0 || r>4)
        {
            return false;
        }
        if(this.getForwardFacing())
        {
            if(!this.getPromoted())
            {
                if((c==column && r==row+1) ||
                ((c==column+1 || c==column-1) && (r==row+1 || r==row-1)))
                {
                    this.setColumn(c);
                    this.setRow(r);
                    return true;
                }
            }
            else
            {
                if((c==column && r==row+1) ||
                ((c==column+1 || c==column-1) && (r==row+1 || r==row-1))||
                (r==row && (c==c+1 || c==c-1)))
                {
                    this.setColumn(c);
                    this.setRow(r);
                    return true;
                }
            }
        }
        else
        {
            if(!this.getPromoted())
            {
                if((c==column && r==row-1) ||
                ((c==column+1 || c==column-1) && (r==row+1 || r==row-1)))
                {
                    this.setColumn(c);
                    this.setRow(r);
                    return true;
                }
            }
            else
            {
                if((c==column && r==row-1) ||
                ((c==column+1 || c==column-1) && (r==row+1 || r==row-1))||
                (r==row && (c==c+1 || c==c-1)))
                {
                    this.setColumn(c);
                    this.setRow(r);
                    return true;
                }
            }
        }
        return false;
    }   
}