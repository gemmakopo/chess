import java.util.*;

import javax.lang.model.util.ElementScanner6;

import java.io.*;
import java.util.regex.Pattern;
public class board {
    king king1 = new king(false, false, true, "k", "k", 1, 0, 0);
    goldGeneral goldGeneral1 = new goldGeneral(false, false, true, "g", "g", 1, 1, 0);
    silverGeneral silverGeneral1 = new silverGeneral(false, false, true, "s", "s", 1, 2, 0);
    bishop bishop1 = new bishop(false, false, true, "b", "b", 1, 3, 0);
    rook rook1 = new rook(false, false, true, "r", "r", 1, 4, 0);
    pawn pawn1 = new pawn(false, false, true, "p", "p", 1, 0, 1);

    king king2 = new king(false, false, false, "K", "K", 2, 4, 4);
    goldGeneral goldGeneral2 = new goldGeneral(false, false, false, "G", "G", 2, 3, 4);
    silverGeneral silverGeneral2 = new silverGeneral(false, false, false, "S", "S", 2, 2, 4);
    bishop bishop2 = new bishop(false, false, false, "B", "B", 2, 1, 4);
    rook rook2 = new rook(false, false, false, "R", "R", 2, 0, 4);
    pawn pawn2 = new pawn(false, false, false, "P", "P", 2, 4, 3);

    piece gameBoard[][] = new piece[5][5];
    public List<piece> player1Captured = new ArrayList<piece>();
    public List<piece> player2Captured = new ArrayList<piece>();
    String board[][] = new String[5][5];
    String currentBoard;
    String moveText = null;
    public List<piece> currentlyActive = new ArrayList<piece>();

    public board() 
    {
        newBoard();
    }
    public board(Utils.TestCase tc)
    {
        makeBoard(tc.initialPieces);
    }

    private void newBoard() {
        gameBoard[0][0] = king1;
        gameBoard[1][0] = goldGeneral1;
        gameBoard[2][0] = silverGeneral1;
        gameBoard[3][0] = bishop1;
        gameBoard[4][0] = rook1;
        gameBoard[0][1] = pawn1;

        gameBoard[4][4] = king2;
        gameBoard[3][4] = goldGeneral2;
        gameBoard[2][4] = silverGeneral2;
        gameBoard[1][4] = bishop2;
        gameBoard[0][4] = rook2;
        gameBoard[4][3] = pawn2;
    }
    private void makeBoard(List<Utils.InitialPosition> h)
    {
        for (int i = 0; i< h.size(); i++)
        {
            piece p;
            Utils.InitialPosition d = h.get(i);
            if (Pattern.compile("[+][K,k,B,b,P,p,R,r,G,g,S,s]").matcher(d.piece).matches())
            {
                p = findPiece(d.piece.substring(1));
                p.setPromoted(true);
                p.setPieceType(d.piece);

            }
            else
            {
                p = findPiece(d.piece);
            }
            
            p.setColumn(findVal(d.position.substring(0, 1)));
            p.setRow(findVal(d.position.substring(1)));
            currentlyActive.add(p);
            gameBoard[p.getColumn()][p.getRow()] = p;
        }
    }
    public piece findPiece(String pType)
    {
        if(pType.equals("K"))
            return king2;
        else if(pType.equals("G"))
            return goldGeneral2;
        else if(pType.equals("S"))
            return silverGeneral2;
        else if(pType.equals("B"))
            return bishop2;
        else if(pType.equals("P"))
            return pawn2;
        else if(pType.equals("R"))
            return rook2;
        else if(pType.equals("k"))
            return king1;
        else if(pType.equals("g"))
            return goldGeneral1;
        else if(pType.equals("s"))
            return silverGeneral1;
        else if(pType.equals("b"))
            return bishop1;
        else if(pType.equals("p"))
            return pawn1;
        else if(pType.equals("r"))
            return rook1;
        else 
            return null;
    }
    public piece[] movePieces(boolean player1, String[] h, String moveText, boolean promote) {
        int[] result = new int[4];
        piece[] toReturn = new piece[2];
        this.moveText=moveText;
        result[0] = findVal(h[0]);
        result[1] = findVal(h[1]);
        result[2] = findVal(h[2]);
        result[3] = findVal(h[3]);

        piece init = gameBoard[result[0]][result[1]];
      
        

        if (init != null) 
        {
            if(promote)
            {
                if(init.getPieceType().equals("k") || init.getPieceType().equals("K")|| init.getPieceType().equals("g") || init.getPieceType().equals("G"))
                {
                    this.printError(player1, "");
                }
            }
            toReturn[0]=init;
            if (player1 && (init.getPlayer() != 1)) 
            {
                System.out.println("UPPER player wins.  Illegal move.");
                System.exit(0);
            }
            if (!player1 && (init.getPlayer() == 1)) 
            {
                System.out.println("lower player wins.  Illegal move.");
                System.exit(0);
            }
            // set init to null
            
            piece dest = gameBoard[result[2]][result[3]];

            if (dest != null) 
            {
                verifySkipOvers(init, dest, player1);
                if(!checkVerifySkipOvers(init, dest, player1))
                {
                    printError(player1, moveText);
                }
                if ((init.getPlayer() == 1) && (dest.getPlayer() == 1))
                {
                    System.out.println("UPPER player wins.  Illegal move.");
                    System.exit(0);
                } 
                else if ((init.getPlayer() == 2) && (dest.getPlayer() == 2)) 
                {
                    System.out.println("lower player wins.  Illegal move.");
                    System.exit(0);
                } 
                else if (init.getPlayer() == 2 && dest.getPlayer() == 1) 
                {
                    boolean success = init.move(result[2], result[3]);
                    if (success==false) 
                    {
                        printError(player1, "move "+this.moveText);
        
                    }
                    if (dest.getPieceType() == "k") 
                    {
                        System.out.println("UPPER player wins. Checkmate.");
                        System.exit(0);
                    } else {
                        dest.setPlayer(2);
                        dest.setCaptured(true);
                        player1Captured.add(dest);
                    }
                } else 
                {
                    boolean success = init.move(result[2], result[3]);
                    if (!success) 
                    {
                        printError(player1, "move "+ this.moveText);
        
                    }
                    
                    ///validate move
                    if (dest.getPieceType() == "K" && success) 
                    {
                        System.out.println("lower player wins. Checkmate.");
                        System.exit(0);
                    } else {
                        dest.setPlayer(1);
                        dest.setCaptured(true);
                        player2Captured.add(dest);
                    }
                }

            }
           else
           {
            boolean success = init.move(result[2], result[3]);
            if (success==false) 
            {
                printError(player1, "move "+ this.moveText);

            }
           }
            gameBoard[result[2]][result[3]] = init;
            gameBoard[result[0]][result[1]] = null;
            toReturn[1]=init;
        } 
        else 
        {
            printError(player1,"move "+ this.moveText);
        }
        moveText = null;
        return toReturn;
    }

    private boolean validateAllMoves(boolean player1, int c, int r)
    {
        if(c>4||c<0||r>4||r<0)
            return true;
        if (player1) 
        {
            if (pawn1.getCaptured() ==false && (pawn1.validateMove(c, r))) 
            {
                return true;
            } 
            if (king1.getCaptured() ==false && king1.validateMove(c, r)) 
            {
                return true;
            } 
            if (bishop1.getCaptured() ==false && bishop1.validateMove(c, r)) 
            {
                return true;
            } 
            if (goldGeneral1.getCaptured() ==false && goldGeneral1.validateMove(c, r)) {
                return true;
            } 
            if (silverGeneral1.getCaptured() ==false && silverGeneral1.validateMove(c, r)) {
                return true;
            } 
            if (rook1.getCaptured() ==false && rook1.validateMove(c, r)) {
                if(checkVerifySkipOvers(rook1, king2, player1))
                {
                    return true;
                }
            }
     
        } 
        else
        {
            if (pawn2.getCaptured() ==false && pawn2.validateMove(c, r)) {
                return true;
            }
            if (king2.getCaptured() ==false && king2.validateMove(c, r)) {
                return true;
            } 
            if (bishop2.getCaptured() ==false && bishop2.validateMove(c, r)) {
                return true;
            } 
            if (goldGeneral2.getCaptured() ==false && goldGeneral2.validateMove(c, r)) {
                return true;
            } 
            if (silverGeneral2.getCaptured() ==false && silverGeneral2.validateMove(c, r)) {
                return true;
            } 
            if (rook2.getCaptured() ==false &&   rook2.validateMove(c, r)) {
                if(checkVerifySkipOvers(rook2, king1, player1))
                {
                    return true;
                }
            }
        
        }
        return false;
    }
    public ArrayList<String> checkCheckMate(boolean player1, boolean test) 
    {
        ArrayList<String> toReturn = new ArrayList<String>();
        boolean kingIsOnBoard = false;
        int c;
        int r;
        if (player1) 
        {
            if(!king2.getCaptured())
                kingIsOnBoard = true;
            c = king2.getColumn();
            r = king2.getRow();
        } 
        else
        {
            if(!king1.getCaptured())
                kingIsOnBoard = true;
            c = king1.getColumn();
            r = king1.getRow();
        }
        if(kingIsOnBoard)
        {
            if(validateAllMoves(player1, c, r))
            {
                toReturn = validateKingsMoves(player1, c, r);
                if(toReturn.size()==0)
                {
                    if(!test)
                    {
                    if(player1)
                        System.out.println("lower player wins.  Checkmate.");
                    else
                         System.out.println("UPPER player wins.  Checkmate.");
                    System.exit(0);
                    }
                    
                }
            }
        }
        return toReturn;
    }
    private ArrayList<String> validateKingsMoves(boolean player1, int c, int r)
    {
        ArrayList<String> toReturn = new ArrayList<String>();

        if(!validateAllMoves(player1, c, r+1))
        {
            
            toReturn.add(getColumnVal(c)+Integer.toString(r+1+1));
        }
        if(!validateAllMoves(player1, c, r-1))
        {  
            toReturn.add(getColumnVal(c)+Integer.toString(r-1+1));
        }
        if(!validateAllMoves(player1, c+1, r))
        {
            toReturn.add(getColumnVal(c+1)+Integer.toString(r+1));
        }
        if(!validateAllMoves(player1, c-1, r))
        {
            toReturn.add(getColumnVal(c-1)+Integer.toString(r+1));
        }
        if(!validateAllMoves(player1, c+1, r+1))
        {
            toReturn.add(getColumnVal(c+1)+Integer.toString(r+1+1));
        }
        if(!validateAllMoves(player1, c+1, r-1))
        {
            toReturn.add(getColumnVal(c+1)+Integer.toString(r-1+1));
        }
        if(!validateAllMoves(player1, c-1, r+1))
        {
            toReturn.add(getColumnVal(c-1)+Integer.toString(r+1+1));
        }
        if(!validateAllMoves(player1, c-1, r-1))
        {
            toReturn.add(getColumnVal(c-1)+Integer.toString(r-1+1));
        }
        return toReturn;
    }
    public void promote(piece a, boolean player1)
    {
        a.setPromoted(true);
        gameBoard[a.getColumn()][a.getRow()]=a;
    }

    public int findVal(String j) {
        if (j.equals("a") || j.equals("1"))
            return 0;
        else if (j.equals("b") || j.equals("2"))
            return 1;
        else if (j.equals("c") || j.equals("3"))
            return 2;
        else if (j.equals("d") || j.equals("4"))
            return 3;
        else
            return 4;
    }
    private String getColumnVal(int c)
    {
        String alphacols = "abcde";
        return Character.toString(alphacols.charAt(c));
    }

    private boolean checkVerifySkipOvers(piece init, piece dest, boolean player1)
    {
        if(init.getOriginalType().equals("r") || init.getOriginalType().equals("R"))
        {
            if(init.getColumn()==dest.getColumn())
            {
                if(init.getRow()>dest.getRow())
                {
                    for (int i = dest.getRow()+1; i<init.getRow(); i++)
                    {
                        if(!(this.gameBoard[init.getColumn()][i]==null))
                        {
                            return false;
                        }
                 
                    }
                }
                if(init.getRow()<dest.getRow())
                {
                    for (int i = init.getRow()+1; i<dest.getRow(); i++)
                    {
                        if(!(this.gameBoard[init.getColumn()][i]==null))
                        {
                            return false;
                        }
                    }
                 
                }
            }
            if(init.getRow()==dest.getRow())
            {
                if(init.getColumn()>dest.getColumn())
                {
                for (int i = dest.getColumn()+1; i<init.getColumn(); i++)
                {
                    if(!(this.gameBoard[i][init.getRow()]==null))
                    {
                        return false;
                    }
                   
                }
                }
                if(init.getColumn()+1<dest.getColumn())
                {
                    for (int i = init.getColumn(); i<dest.getColumn(); i++)
                    {
                    if(!(this.gameBoard[i][init.getRow()]==null))
                    {
                        return false;
                    }
                    }
                 
                }
            }
            
        }
       return true;
     
    }
    private void verifySkipOvers(piece init, piece dest, boolean player1)
    {
        if(init.getPieceType().equals("b") || init.getPieceType().equals("B"))
        {
            if(dest.getColumn() == init.getColumn()+2)
            {
                if(dest.getRow() == init.getRow()+2)
                {
                    if(!gameBoard[init.getColumn()+1][init.getRow()+1].equals(""))
                    {
                        printError(player1, this.moveText);
                    }
                }
                if(dest.getRow() == init.getRow()-2)
                {
                    if(!gameBoard[init.getColumn()+1][init.getRow()-1].equals(""))
                    {
                        printError(player1, this.moveText);
                    }
                }
            }
            if(dest.getColumn() == init.getColumn()-2)
            {
                if(dest.getRow() == init.getRow()+2)
                {
                    if(!gameBoard[init.getColumn()-1][init.getRow()+1].equals(""))
                    {
                        printError(player1, this.moveText);
                    }
                }
                if(dest.getRow() == init.getRow()-2)
                {
                    if(!gameBoard[init.getColumn()-1][init.getRow()-1].equals(""))
                    {
                        printError(player1, this.moveText);
                    }
                }
            }
        }
        
    }

    public void drawBoard() {
        drawMyBoard(gameBoard);
    }

    private void drawMyBoard(piece[][] b) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = "";
            }
        }

        for (int k = 0; k < 5; k++) {
            for (int m = 0; m < 5; m++) {
                if (gameBoard[k][m] != null) {
                    board[k][m] = gameBoard[k][m].getPieceType();
                }
            }
        }

        currentBoard = Utils.stringifyBoard(board);
        System.out.println(currentBoard);
    }

    public void printError(boolean player1, String moveText)
    {
        
        // if(moveText != null)
        // {
        //     if(player1)
        //     {
        //         System.out.println("lower player action: " + moveText);
        //     }
        //     else
        //     {
        //         System.out.println("UPPER player action: " + moveText);
        //     }

        // }
        this.drawBoard();
        printCaptures();
        if(player1)
            System.out.println("UPPER player wins.  Illegal move.");
        else
            System.out.println("lower player wins.  Illegal move.");
       
            System.exit(0);
    }

    public void printCaptures()
    {
        System.out.print("Captures UPPER: ");
        List toPrint = this.player1Captured;
        for (int i = 0; i < toPrint.size(); i++) 
        {
            if(toPrint.get(i) != null)
            {
            piece h = (piece) toPrint.get(i);
            System.out.print(h.getOriginalType().toUpperCase() + " ");
            }
        }

        System.out.println();
        System.out.print("Captures lower: ");
        toPrint = this.player2Captured;
        for (int i = 0; i < toPrint.size(); i++) 
        {
            if(toPrint.get(i) != null)
            {
            piece h = (piece) toPrint.get(i);
            System.out.print(h.getOriginalType().toLowerCase() + " ");
            }
        }

        System.out.println("");
        System.out.println();
    }

}