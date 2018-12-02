
public class bishop extends piece {

    public bishop(boolean promoted, boolean captured, boolean forwardFacing, String type, String originalType, int player, int col,
            int row) {
        super(promoted, captured, forwardFacing, type, originalType, player, col, row);
    }

    public boolean move(int c, int r) {
        if (validateMove(c, r)) {
            this.setColumn(c);
            this.setRow(r);
            return true;
        }

        return false;
    }

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
        if (this.getPromoted() == false) 
        {
            for(int i = 1; i<5; i++)
            {
            if (((c == column + i) && (r == row + i)) || ((c == column - i) && (r == row - i))
                    || ((c == column - i) && (r == row + i))|| ((c == column + i) && (r == row - i)))
                {
                    return true;
                }
            }
        } 
        else 
        {
            for(int i =1; i<5; i++)
            {
                if (   ((c == column + i) && (r == row + i)) 
                    || ((c == column - i) && (r == row - i)) 
                    || ((c == column - i) && (r == row + i)) 
                    || ((c == column + i) && (r == row - i)) 
                    || ((c == column + 1) && (r == row)) || ((c == column - 1) && (r == row))
                    || ((c == column) && (r == row + 1)) || ((c == column) && (r == row - 1))) 
                {
                    return true;
                }
            }

        }
        return false;
    }

}