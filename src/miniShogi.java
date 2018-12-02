import java.util.*;
import java.util.regex.Pattern;

import javax.lang.model.util.ElementScanner6;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.io.*;

public class miniShogi 
{
    boolean gameState = false;
    boolean fileVersion = false;
    boolean player1 = true;
    String currentBoard;
    int endPlays = 0;
    String[][] board;
    board msBoard;

    public static void main(String[] args) throws Exception{
    //    String[] lo = new String[2]; 
    //     lo[0] = "-f";
    //      lo[1] = "captureDrop.in";
        if(0 < args.length)
            if (args[0].equals("-i"))
            {
                miniShogi a = new miniShogi();
            }
            else if(args[0].equals("-f"))
            {
                
                runFileMode(args[1]);
                
            }
            else 
            {
                System.exit(0);
            }
        else 
        {
            System.exit(0);
        }
    }
    private static void runFileMode(String path) throws Exception
    {
        Utils.TestCase tc = Utils.parseTestCase(path);
        miniShogi b = new miniShogi(tc);
    }
    public miniShogi() 
    {
        gameState = true;

        msBoard = new board();
        msBoard.drawBoard();
        msBoard.printCaptures();
        startGame();
    }
    public miniShogi(Utils.TestCase tc)
    {
        fileVersion = true;
        gameState = true;
        msBoard = new board(tc);
        for(int i=0; i<tc.lowerCaptures.size(); i++)
        {
            if(msBoard.player2Captured.contains( msBoard.findPiece(tc.lowerCaptures.get(i))))
            {
                msBoard.player2Captured.add(i, msBoard.findPiece(tc.lowerCaptures.get(i).toUpperCase()));
            }
            else
            {
                if(msBoard.currentlyActive.contains( msBoard.findPiece(tc.lowerCaptures.get(i))))
                {
                    msBoard.player2Captured.add(i, msBoard.findPiece(tc.lowerCaptures.get(i).toUpperCase()));
                
                }
                else
                {
                 msBoard.player2Captured.add(i, msBoard.findPiece(tc.lowerCaptures.get(i)));
                }
            }
            if(msBoard.player2Captured.get(i)!=null)
            {
                piece j = msBoard.player2Captured.get(i);
                j.setCaptured(true);
                msBoard.player2Captured.remove(i);
                msBoard.player2Captured.add(i, j);
            }
        }
        
      
        for(int k=0; k< tc.upperCaptures.size(); k++)
        {
            if(msBoard.player1Captured.contains( msBoard.findPiece(tc.upperCaptures.get(k))))
            {
               
                msBoard.player1Captured.add(k, msBoard.findPiece(tc.upperCaptures.get(k).toLowerCase()));
                

            }
            else
            {
                if(msBoard.currentlyActive.contains( msBoard.findPiece(tc.upperCaptures.get(k))))
                {
                    msBoard.player1Captured.add(k, msBoard.findPiece(tc.upperCaptures.get(k).toLowerCase()));
                
                }
                else
                {
                 msBoard.player1Captured.add(k, msBoard.findPiece(tc.upperCaptures.get(k)));
                }
            }
            if(msBoard.player1Captured.get(k)!=null)
            {
                piece j = msBoard.player1Captured.get(k);
                j.setCaptured(true);
                msBoard.player1Captured.remove(k);
                msBoard.player1Captured.add(k, j);
            }
        }
        // for(piece p:msBoard.player1Captured)
        // {
        //     p.setCaptured(true);
        // }
        // for(piece p:msBoard.player2Captured)
        // {
        //     p.setCaptured(true);
        // }
        makeMoves(tc.moves);
    
        
    }
    private void makeMoves(List<String> f)
    {
        
        for(int i = 0; i< f.size(); i++)
        {
          // msBoard.drawBoard();///////////////////////////
            boolean inCheck=false;
            if (endPlays == 200) 
            {
                System.out.println("Tie game. Too many moves.");
                System.exit(0);
            }
            if(i%2 !=0)
            {
                endPlays++;
            }
            ArrayList<String> checked = msBoard.checkCheckMate(player1);
            Collections.sort(checked, String.CASE_INSENSITIVE_ORDER);
            String kingMove = null;
            if (checked.size() !=0) 
            { 
                inCheck = true;
                int[] kingDest = new int[2];
                //String initMove = returnBoardLocation(msBoard.checkCheckMate(true));
                if(player1)
                {
                    kingDest[0] = msBoard.king2.getColumn();
                    kingDest[1] = msBoard.king2.getRow();
                }
                else
                {
                    kingDest[0] = msBoard.king1.getColumn();
                    kingDest[1] = msBoard.king1.getRow();
                }
                kingMove = returnBoardLocation(kingDest);
            }
            
            if (Pattern.compile("move [a-e][1-5] [a-e][1-5] promote").matcher(f.get(i)).matches()) 
            {
                if(f.size()-1 == i)
                {
                if(player1)
                {
                    System.out.println("lower player action: " + f.get(i));
                }
                else
                {
                    System.out.println("UPPER player action: " + f.get(i));
                }
            }
                promotePiece(player1, f.get(i));
            }
            else if (Pattern.compile("move [a-e][1-5] [a-e][1-5]").matcher(f.get(i)).matches())
            {
                if(f.size()-1 == i)
                {
                if(player1)
                {
                    System.out.println("lower player action: " + f.get(i));
                }
                else
                {

                    System.out.println("UPPER player action: " + f.get(i));
                }
                }
                if(inCheck)
                {
                    boolean check = false;
                    for (int p = 0; p < checked.size(); p++)
                    {
                        
                        if (f.get(i).equals("move "+kingMove + " "+ checked.get(i)))
                        {
                            
                            check = true;
                        }
                    }
                    if(!check)
                    {
                        msBoard.printError(player1, f.get(i));
                    }
                }
                
                piece[] j = move(player1, f.get(i));
                if((j[1].getPieceType().equals("p") && j[1].getRow() ==4)|| (j[1].getPieceType().equals("P") && j[1].getRow() ==0))
                {
                    msBoard.promote(j[1]);
                }
            }
            else if (Pattern.compile("drop [kgsbrp] [a-e][1-5]").matcher(f.get(i)).matches()) 
            {
                if(player1)
                {
                    System.out.println("lower player action: " + f.get(i));
                }
                else
                {
                    System.out.println("UPPER player action: " + f.get(i));
                }
                if(!player1)
                {
                    msBoard.printError(player1, "");
                }
            }
            else if (Pattern.compile("drop [KGSBRP] [a-e][1-5]").matcher(f.get(i)).matches()) 
            {
                if(player1)
                {
                    System.out.println("lower player action: " + f.get(i));
                }
                else
                {
                    System.out.println("UPPER player action: " + f.get(i));
                }
                if(player1)
                {
                    msBoard.printError(player1, "");
                }
            }
            else 
            {
                msBoard.printError(player1, null);
            }
            if(i!=f.size()-1)
            {
                player1 = !player1;
            }
        }
        msBoard.drawBoard();
        msBoard.printCaptures();
        gameState=true;
        startGame();
    }


    private void startGame() 
    {
        
        
        String p1Input;
        String p2Input;
        
        boolean inCheck=false;

        while (gameState == true) 
        {
            if (endPlays == 200) 
            {
                System.out.println("Tie game. Too many moves.");
                System.exit(0);
            }
            ArrayList<String> checked = msBoard.checkCheckMate(player1);
            Collections.sort(checked, String.CASE_INSENSITIVE_ORDER);
            String kingMove = null;
            if (checked.size() !=0) 
            { 
                inCheck = true;
                String p;
                int[] kingDest = new int[2];
                //String initMove = returnBoardLocation(msBoard.checkCheckMate(true));
                if(player1)
                {
                    kingDest[0] = msBoard.king2.getColumn();
                    kingDest[1] = msBoard.king2.getRow();
                    p = "UPPER";
                }
                else
                {
                    kingDest[0] = msBoard.king1.getColumn();
                    kingDest[1] = msBoard.king1.getRow();
                    p = "lower";
                }
                kingMove = returnBoardLocation(kingDest);

                System.out.println(p+" player is in check!");
                System.out.println("Available moves:");
                for (int i = 0; i < checked.size(); i++)
                {
                   
                    System.out.println("move " + kingMove + " " + checked.get(i));
                }
            }
            player1=!player1;
            Scanner scanner = new Scanner(System.in);
            if (player1) 
            {
                System.out.print("lower> ");
                p1Input = null;
                if(!fileVersion)
                {
                     p1Input = scanner.nextLine();
                     
                }
                else
                {
                    System.exit(0);
                }

                if (Pattern.compile("move [a-e][1-5] [a-e][1-5] promote").matcher(p1Input).matches()) 
                {
                    promotePiece(player1, p1Input);
                }
                else if (Pattern.compile("move [a-e][1-5] [a-e][1-5]").matcher(p1Input).matches()) 
                {
                    System.out.println("lower player action: " + p1Input);
                    
                    if(inCheck)
                    {
                        boolean check = false;
                        for (int i = 0; i < checked.size(); i++)
                        {
                            if (p1Input.equals("move "+kingMove + " "+ checked.get(i)))
                            {
                                check = true;
                            }
                        }
                        if(!check)
                        {
                            msBoard.printError(player1, p1Input);
                        }
                    }
                    
                    piece[] j = move(player1, p1Input);
                    if((j[1].getPieceType().equals("p") && j[1].getRow() ==4)|| (j[1].getPieceType().equals("P") && j[1].getRow() ==0))
                    {
                        msBoard.promote(j[1]);
                    }
                } 
                else if (Pattern.compile("drop [kgsbrp] [a-e][1-5]").matcher(p1Input).matches()) 
                {
                    p1Input = p1Input.substring(5);
                    String[] moves1 = p1Input.split(" ");
                    String[] movesToTranslate = new String[2];
                    String pieceType = moves1[0];

                    movesToTranslate[0] = moves1[1];
                    movesToTranslate[1] = moves1[2];

                    // finish this later;
                } 
                else 
                {
                    msBoard.printError(player1, p1Input);
                }
            } 
            else 
            {
                endPlays++;
                System.out.print("UPPER> ");
                p2Input = null;
                if(!fileVersion)
                {
                    p2Input = scanner.nextLine();
            
                }
                else
                {
                    System.exit(0);
                }

                if (Pattern.compile("move [a-e][1-5] [a-e][1-5] promote").matcher(p2Input).matches()) 
                {
                    promotePiece(player1, p2Input);
                }
                else if (Pattern.compile("move [a-e][1-5] [a-e][1-5]").matcher(p2Input).matches()) 
                {
                    piece[] j = move(player1, p2Input);
                    System.out.println("UPPER player action: " + p2Input);
                    if(inCheck)
                    {
                        boolean check = false;
                        for (int i = 0; i < checked.size(); i++)
                        {
                            if (p2Input.equals("move "+kingMove + " "+ checked.get(i)))
                            {
                                check = true;
                            }
                        }
                        
                        if(!check)
                        {
                            msBoard.printError(player1, p2Input);
                        }
                    }
                    
                    if((j[1].getPieceType().equals("p") && j[1].getRow() ==4)|| (j[1].getPieceType().equals("P") && j[1].getRow() ==0))
                    {
                        msBoard.promote(j[1]);
                    }
                }
                else if (Pattern.compile("drop [kgsbrp] [a-e][1-5]").matcher(p2Input).matches()) 
                {
                    p2Input = p2Input.substring(5);
                    String[] moves1 = p2Input.split(" ");
                    String[] movesToTranslate = new String[2];
                    String pieceType = moves1[0];

                    movesToTranslate[0] = moves1[1];
                    movesToTranslate[1] = moves1[2];

                    // finish this later;
                }
                else 
                {
                    msBoard.printError(player1, null);
                }
            }
           
            msBoard.drawBoard();
            msBoard.printCaptures();
            
        }
    }


    private piece[] move(boolean player1, String p1Input)
    {
        p1Input = p1Input.substring(5);
        String[] moves1 = p1Input.split(" ");
        String[] movesToTranslate = new String[4];
        movesToTranslate[0] = moves1[0].substring(0, 1);
        movesToTranslate[1] = moves1[0].substring(1);
        movesToTranslate[2] = moves1[1].substring(0, 1);
        movesToTranslate[3] = moves1[1].substring(1);

        piece[] toReturn = msBoard.movePieces(player1, movesToTranslate, p1Input);

        return toReturn;
    }

    private void promotePiece(boolean player1, String input)
    {
        piece[] toPromote = move(player1, input);
        if(toPromote[1].equals("k") || toPromote[1].equals("K")|| toPromote[1].equals("g") || toPromote[1].equals("G"))
        {
            msBoard.printError(player1, input);
        }
        if((toPromote[0].getRow()==4 && toPromote[0].getPlayer() == 1) 
        || (toPromote[1].getRow()==4 && toPromote[1].getPlayer()==1) 
        || (toPromote[0].getRow()==0 && toPromote[0].getPlayer() == 2)
        || (toPromote[1].getRow()==0 && toPromote[1].getPlayer() == 2))
        {
            msBoard.promote(toPromote[1]);
        }
        else
        {
            msBoard.printError(player1, input);
        }
    }

    private String returnBoardLocation(int[] nums) 
    {
        String[] sideBoard = { "a", "b", "c", "d", "e" };
        String returnVal = sideBoard[nums[0]];
        return returnVal + Integer.toString(nums[1] + 1);
    }

    public void changeGamestate() 
    {
        gameState = !gameState;
    }
}