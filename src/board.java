import java.util.*;
import java.io.*;

public class board
{
    king king1 = new king(false, false, true, "k", 1, 0, 0);
    goldGeneral goldGeneral1 = new goldGeneral(false, false, true,"g", 1, 1, 0);
    silverGeneral silverGeneral1 = new silverGeneral(false, false, true, "s", 1, 2, 0);
    bishop bishop1 = new bishop(false, false, true, "b", 1, 3, 0);
    rook rook1 = new rook(false, false, true, "r", 1, 4, 0);
    pawn pawn1 = new pawn(false, false, true, "p", 1, 0, 1);

    king king2 = new king(false, false, false, "K", 2, 4, 4);
    goldGeneral goldGeneral2 = new goldGeneral(false, false, false, "G", 2, 3, 4);
    silverGeneral silverGeneral2 = new silverGeneral(false, false, false, "S", 2, 2, 4);
    bishop bishop2 = new bishop(false, false, false, "B", 2, 1, 4);
    rook rook2 = new rook(false, false, false, "R", 2, 0, 4);
    pawn pawn2 = new pawn(false, false, false, "P", 2, 4, 3);

    piece gameBoard[][] = new piece[5][5];
    ArrayList<piece> player1Captured = new ArrayList<piece>();
    ArrayList<piece> player2Captured = new ArrayList<piece>();
    String board[][] = new String[5][5];
    String currentBoard;

    public board()
    {
        newBoard();
    }

    private void newBoard()
    {
        gameBoard[0][0]= king1;
        gameBoard[1][0]= goldGeneral1;
        gameBoard[2][0]= silverGeneral1;
        gameBoard[3][0]= bishop1;
        gameBoard[4][0]= rook1;
        gameBoard[0][1]= pawn1;

        gameBoard[4][4]= king2;
        gameBoard[3][4]= goldGeneral2;
        gameBoard[2][4]= silverGeneral2;
        gameBoard[1][4]= bishop2;
        gameBoard[0][4]= rook2;
        gameBoard[4][3]= pawn2;

        drawMyBoard(gameBoard);
    }
    public void movePieces(boolean player1, String[] h)
    {
        int[] result = new int[4];

        result[0] = findVal(h[0]);
        result[1] = findVal(h[1]);
        result[2] = findVal(h[2]);
        result[3] = findVal(h[3]);

        piece init = gameBoard[result[0]][result[1]];


        if (init != null)
        {
            if(player1 && (init.getPlayer() != 1))
            {
                System.out.println("UPPER player wins. Illegal move.");
                System.exit(0);
            }
            if(!player1 && (init.getPlayer() == 1))
            {
                System.out.println("lower player wins. Illegal move.");
                System.exit(0);
            }
            //set init to null
            gameBoard[result[0]][result[1]] = null;

            piece dest = gameBoard[result[2]][result[3]];

            if (dest != null)
            {
                if((init.getPlayer()==1) && (dest.getPlayer()==1))
                {
                    System.out.println("UPPER player wins. Illegal move.");
                    System.exit(0);
                }
                if((init.getPlayer()==2) && (dest.getPlayer()==2))
                {
                    System.out.println("lower player wins. Illegal move.");
                    System.exit(0);
                }
                dest.setCaptured(true);
                
                if(init.getPlayer() == 2 && dest.getPlayer()== 1)
                {
                    dest.setPlayer(2);
                    player2Captured.add(dest);
                }
                else 
                {
                    dest.setPlayer(1);
                    player1Captured.add(dest);
                }
            }

            boolean success = init.move(result[2], result[3]);
            if(!success)
            {   
                if (player1)
                {
                    System.out.println("UPPER player wins. Illegal move.");
                    System.exit(0);
                }
                else
                {
                    System.out.println("lower player wins. Illegal move");
                    System.exit(0);
                }
              
            }
            gameBoard[result[2]][result[3]] = init;
        }
        else
        {
            if (player1)
            {
                System.out.println("UPPER player wins. Illegal move.");
                System.exit(0);
            }
            else
            {
                System.out.println("lower player wins. Illegal move.");
                System.exit(0);
            }
        }
    }

    private int findVal(String j)
    {
        if(j.equals("a") || j.equals("1"))
            return 0;
        else if(j.equals("b") || j.equals("2"))
            return 1;
        else if(j.equals("c") || j.equals("3"))
            return 2;
        else if(j.equals("d") || j.equals("4"))
            return 3;
        else
            return 4;
    }

    public void drawBoard()
    {
        drawMyBoard(gameBoard);
    }
    private void drawMyBoard(piece[][] b)
    {
        for(int i = 0; i<5; i++)
        {
            for(int j =0; j<5; j++)
            {
                board[i][j]="";
            }
        }

        for(int k = 0; k<5; k++)
        {
            for(int m =0; m<5; m++)
            {
                if(gameBoard[k][m] != null)
                {
                    board[k][m]=gameBoard[k][m].getPieceType();
                }
            }
        }

        currentBoard = Utils.stringifyBoard(board);
        System.out.println(currentBoard);
    }
    
}