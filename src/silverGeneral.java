
//*******************************************************************
// silverGeneral
// Inherits traits from piece.
// +++
//  s
// + +
//*******************************************************************
public class silverGeneral extends piece {

    public silverGeneral(boolean promoted, boolean captured, boolean forwardFacing, String type, String originalType, int player, int col,
            int row) 
    {
        super(promoted, captured, forwardFacing, type, originalType, player, col, row);
    }

    public boolean move(int c, int r) 
    {
        if (validateMove(c, r)) 
        {
            this.setColumn(c);
            this.setRow(r);
            return true;
        }
        return false;
    }
    /**
     * Ensures that a requested location change is allowed
     * @param c column
     * @param r row
     * @return true if move is allowed
     */
    public boolean validateMove(int c, int r) 
    {
        int column = this.getColumn();
        int row = this.getRow();
        if (c == column && r == row) 
        {
            return false;
        }
        if(c==1 && r == 2)
        {
            int h = 9;
        }
        if (c < 0 || c > 4 || r < 0 || r > 4) 
        {
            return false;
        }
        if (this.getForwardFacing()) 
        {
            if (!this.getPromoted()) 
            {
                if ((c == column && r == row + 1)
                        || ((c == column + 1 || c == column - 1) && (r == row + 1 || r == row - 1))) 
                {
                    return true;
                }
            } 
            else 
            {
                if ((c == column && (r == row + 1||r==row-1))
                        || ((c == column + 1) && (r == row + 1 || r == row))
                        || ((c==column-1) && (r==row+1||r==row))) 
                {
                    return true;
                }
            }
        } 
        else 
        {
            if (!this.getPromoted()) 
            {
                if ((c == column && r == row - 1)
                        || ((c == column + 1 || c == column - 1) && (r == row + 1 || r == row - 1))) 
                {
                    return true;
                }
            } 
            else 
            {
                if ((c == column && (r == row - 1||r==row+1))
                        || ((c == column - 1) && (r == row - 1 || r == row))
                        || ((c==column+1) && (r==row-1||r==row))) 
                {
                    return true;
                }
            }
        }
        return false;
    }
}