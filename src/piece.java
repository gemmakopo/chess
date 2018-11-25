import java.util.*;

import javax.lang.model.util.ElementScanner6;

import java.io.*;
public class piece{
    //king, rook, bishop, goldGeneral, silverGeneral, pawn

    private String type;
    private boolean promoted;
    private int player;
    private int column;
    private int row;
    private boolean forwardFacing = true;
    private boolean captured;

    public piece(boolean promoted, boolean captured,
        boolean forwardFacing, String type, int player, int col, int row)
    {
       this.promoted = promoted;
       this.captured = captured;
       this.forwardFacing = forwardFacing;
       this.type = type;
       this.player = player;
       this.column = col;
       this.row = row;
    }

    public void setPromoted(boolean promo){
		this.promoted = promo;
    }
    public boolean getPromoted(){
		return this.promoted;
    }
    public String getPieceType()
    {
        return this.type;
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
 
    public boolean move(int c, int r)
    {
        this.column = c;
        this.row = r;

        if(this.type.equals("K") || this.type.equals("k"))
        {
           return ((king)this).move(c, r);
        }
        else if(this.type.equals("G") || this.type.equals("g"))
        {
            return ((goldGeneral)this).move(c, r);
        }
        else if(this.type.equals("S") || this.type.equals("s"))
        {
           return ((silverGeneral)this).move(c, r);
        }
        else if(this.type.equals("B") || this.type.equals("b"))
        {
            return ((bishop)this).move(c, r);
        }
        else if(this.type.equals("R") || this.type.equals("r"))
        {
            return ((rook)this).move(c, r);
        }
        else
        {
            return((pawn)this).move(c, r);
        }
    }

}