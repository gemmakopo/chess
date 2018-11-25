import java.util.*;
import java.util.regex.Pattern;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.io.*;

public class miniShogi{

    boolean gameState = false;
    boolean player1 = true;
    String currentBoard;

    String[][] board;
    board msBoard;

    public miniShogi()
    {
        gameState = true;
   
        msBoard = new board();
        startGame();
    }

    private void startGame()
    {
        Scanner scanner = new Scanner(System.in);
        String p1Input;
        String p2Input;
        
        while (gameState == true)
        {
            if(player1)
            {  
                System.out.print("lower> ");
                p1Input = scanner.nextLine();
                if(Pattern.compile("move [a-e][1-5] [a-e][1-5]").matcher(p1Input).matches())
                {
                    p1Input=p1Input.substring(5);
                    String[] moves1 = p1Input.split(" ");
                    String[] movesToTranslate = new String[4];
                    movesToTranslate[0] = moves1[0].substring(0, 1);
                    movesToTranslate[1] = moves1[0].substring(1);
                    movesToTranslate[2] = moves1[1].substring(0, 1);
                    movesToTranslate[3] = moves1[1].substring(1);

                    msBoard.movePieces(player1, movesToTranslate);

                    System.out.println("lower player action: move "+p1Input);
                }
                else if(Pattern.compile("drop [kgsbrp] [a-e][1-5]").matcher(p1Input).matches())
                {
                    p1Input=p1Input.substring(5);
                    String[] moves1 = p1Input.split(" ");
                    String[] movesToTranslate = new String[2];
                    String pieceType = moves1[0];

                    movesToTranslate[0]=moves1[1];
                    movesToTranslate[1]=moves1[2];
                }
                else
                {
                    if(player1)
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
            else
            {
                System.out.print("UPPER> ");
                p2Input = scanner.nextLine();
                if(p2Input.contains("move "))
                {
                    p2Input=p2Input.substring(5);
                    String[] moves2 = p2Input.split(" ");
                    String[] movesToTranslate = new String[4];
                    movesToTranslate[0] = moves2[0].substring(0, 1);
                    movesToTranslate[1] = moves2[0].substring(1);
                    movesToTranslate[2] = moves2[1].substring(0, 1);
                    movesToTranslate[3] = moves2[1].substring(1);

                    msBoard.movePieces(player1, movesToTranslate);

                    System.out.println("UPPER player action: move "+p2Input); 
                   
                }
            }
            msBoard.drawBoard();
            //System.out.println();
            //System.out.println("Capture UPPER: ");
            //for (int i = 0; i<)
            player1 = !player1;
        }    
    }
    private void updateBoard(int col, int row, String thing)
    {
        board[col][row]=thing;
        currentBoard = Utils.stringifyBoard(board);
        System.out.println(currentBoard);
    }

    public void changeGamestate()
    {
        gameState = !gameState;
    }
}