
//*******************************************************************
// king
// Inherits traits from piece. If this gets captured, it's all over for you!
// +++
// +k+
// +++
//*******************************************************************
public class king extends piece 
{
    public king(boolean promoted, boolean captured, boolean forwardFacing, String type, String originalType, int player, int col, int row) 
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
     * ensures that a move is allowed for a king type piece
     * @param c column
     * @param r row
     */
    public boolean validateMove(int c, int r) 
    {
        if (c < 0 || c > 4 || r < 0 || r > 4) 
        {
            return false;
        }
        int column = this.getColumn();
        int row = this.getRow();
        if (c == column && r == row) 
        {
            return false;
        }
        if ((r == row + 1 && (c == column || c == column + 1 || c == column - 1))
                || ((r == row - 1) && (c == column || c == column + 1 || c == column - 1))
                || ((r == row) && (c == column || c == column + 1 || c == column - 1))) 
        {
            return true;
        }
        return false;
    }
}