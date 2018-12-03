
//*******************************************************************
// rook
// Inherits traits from piece
//   +
//   +
// ++r++
//   +
//   +
//*******************************************************************

class rook extends piece {

    public rook(boolean promoted, boolean captured, boolean forwardFacing, String type, String originalType, int player, int col, int row) 
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
     * ensures that a move is allowed for a rook type piece
     * @param c column
     * @param r row
     */
    public boolean validateMove(int c, int r) 
    {
        int column = this.getColumn();
        int row = this.getRow();

        if (c == column && r == row) 
        {
            return false;
        }
        if (c < 0 || c > 4 || r < 0 || r > 4) 
        {
            return false;
        }
        if (!this.getPromoted())
        {
            if ((c == column)|| (r == row )) 
            {
                return true;
            }
        } 
        else {
            if ((c == column)|| (r == row)
                    || ((r == row + 1 || r == row - 1) && (c == column + 1 || c == column - 1)))
            {
                return true;
            }
        }
        return false;
    }
}