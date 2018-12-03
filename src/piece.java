import java.util.*;
import javax.lang.model.util.ElementScanner6;
import java.io.*;
//*******************************************************************
// piece
// This is where piece attributes can be retrieved and set for all piece types
//*******************************************************************
public class piece{

    private String type;
    private String originalType;
    private boolean promoted;
    private int player;
    private int column;
    private int row;
    private boolean forwardFacing = true;
    private boolean captured;

    public piece(boolean promoted, boolean captured, boolean forwardFacing, String type, String originalType, int player, int col, int row) 
    {
        this.promoted = promoted;
        this.captured = captured;
        this.forwardFacing = forwardFacing;
        this.type = type;
        this.originalType = originalType;
        this.player = player;
        this.column = col;
        this.row = row;
    }

    public void setPromoted(boolean promo) 
    {
        this.promoted = promo;
        if(promo && !this.type.contains("+"))
        {
            this.type = "+"+ this.getPieceType();
        }
    }

    public boolean getPromoted() 
    {
        return this.promoted;
    }

    public String getPieceType() 
    {
        return this.type;
    }
    
    public String getOriginalType()
    {
        return this.originalType;
    }

    public void setPieceType(String type) 
    {
        this.type = type;
    }

    public int getPlayer() 
    {
        return this.player;
    }

    public void setPlayer(int player) 
    {
        this.player = player;
    }

    public int getRow() 
    {
        return this.row;
    }

    public void setRow(int r) 
    {
        this.row = r;
    }

    public int getColumn() 
    {
        return this.column;
    }

    public void setColumn(int col) 
    {
        this.column = col;
    }

    public void setCaptured(boolean j) 
    {
        captured = j;
    }

    public boolean getCaptured() 
    {
        return captured;
    }

    public boolean getForwardFacing() 
    {
        return forwardFacing;
    }

    public void setForwardFacing(boolean dir) 
    {
        this.forwardFacing = dir;
    }

    /**
     * Helps us identify which piece's move method needs to be called based on type
     * @param c column
     * @param r row
     */
    public boolean move(int c, int r) 
    {
        this.column = c;
        this.row = r;

        if (this.originalType.equals("K") || this.originalType.equals("k")) 
        {
            return ((king) this).move(c, r);
        } 
        else if (this.originalType.equals("G") || this.originalType.equals("g")) 
        {
            return ((goldGeneral) this).move(c, r);
        } 
        else if (this.originalType.equals("S") || this.originalType.equals("s")) 
        {
            return ((silverGeneral) this).move(c, r);
        } 
        else if (this.originalType.equals("B") || this.originalType.equals("b")) 
        {
            return ((bishop) this).move(c, r);
        } 
        else if (this.originalType.equals("R") || this.originalType.equals("r")) 
        {
            return ((rook) this).move(c, r);
        } 
        else 
        {
            return ((pawn) this).move(c, r);
        }
    }

}