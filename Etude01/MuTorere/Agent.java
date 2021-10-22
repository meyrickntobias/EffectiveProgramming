package MuTorere;

import java.util.*;

/**
 * Agent plays the game Mu Torere by using strategy and avoidance.
 * Group members: Toby Meyrick 1998580, Lachlan Mcartney,
 * Arm Chaimathikul, Jake Perkins 2713308
 */
public class Agent extends Player {
    private Random rng = new Random();
    
    /**
     * Agent's constructor method accesses the superclass constructor.
     * @param boardReader: gives access to who is at what position
     * @param playerID: gives the number indicating who is who
     */
    public Agent(BoardReader boardReader, Board.Piece playerID){
        super(boardReader, playerID);
    }

    /**
     *  GetMove finds all the valid moves and uses strategy to find the best
     *  option. A random valid move is selected if the agent does not recognise
     *  the board.
     *  @return move: the position from which we want to move
     */
    public int getMove(){
        // From NaivePlayer - gets all valid moves
        ArrayList<Integer> validMoves = new ArrayList<Integer>();
        for (int i = 0; i < 9; ++i)
            for (int j = 0; j < 9; ++j)
                if (isValid(i,j)){
                    validMoves.add(i);
                    continue;
                }
        
        switch(validMoves.size()){
            // One avaliable move - give only valid move
            case 1:
                return validMoves.get(0);

            // Two avaliable moves - check for flag arrangment 
            case 2:
                getRotations();
                if (is_flag(validMoves)){
                    if (validMoves.get(0) == 8) return validMoves.get(1);
                    return validMoves.get(0);
                }
                return three_in_a_row(validMoves);
            
            // Three avaliable moves - check for the known board
            case 3:
                // If either of these arrangements then move index 0
                // If we are player 1
                ArrayList<Integer> keyMove =
                    new ArrayList<Integer>(Arrays.asList(1, 1, 1, 2, 2, 2, 1, 2));
                // ... or if we are player 2
                ArrayList<Integer> keyMove2 =
                    new ArrayList<Integer>(Arrays.asList(2, 2, 2, 1, 1, 1, 2, 1));
                
                if (("" + playerID).equals("ONE") &&
                        currentStateIsARotationOf(keyMove)){
                    // Do not move the lone player to the center
                    for (int piece : validMoves)
                        if (boardReader.pieceAt((piece + 1) % 8) == playerID)
                            return piece;
                }

                if (("" + playerID).equals("TWO") &&
                        currentStateIsARotationOf(keyMove2)){
                    // Do not move the lone player to the center
                    for (int piece : validMoves)
                        if (boardReader.pieceAt((piece+1)%8) == playerID)
                            return piece;
                }

                return validMoves.get(rng.nextInt(validMoves.size()));
            
            // Four avaliable moves - acting random
            case 4:
                return validMoves.get(rng.nextInt(validMoves.size()));
            
            // No avaliable moves - return 0 for lost game
            default:
                return 0;
        }
    }

    /**
     * From NaivePlayer - isValid tests to see whether the move is valid given the
     * possible moveFrom and moveTo positions.
     * @param moveFrom: the board position we are testing if we can move from
     * @param moveTo: the board position we are testing if we can move to
     * @return boolean: indicating whether the move is valid or not
     */
    boolean isValid(int moveFrom, int moveTo){
        if (boardReader.pieceAt(moveTo) != Board.Piece.BLANK) return false;
        
        if (boardReader.pieceAt(moveFrom) != playerID) return false;
        if (moveTo == 8){
            // Move to center, check for valid neighbour
            int prev = moveFrom - 1;
            if (prev < 0) prev = 7;
            int next = moveFrom + 1;
            if (next > 7) next = 0;
            if (boardReader.pieceAt(prev) == playerID &&
                    boardReader.pieceAt(next) == playerID) return false;
        } else {
            // Either move from center to kewai...
            if (moveFrom == 8) return true;
            // ... or from one kewai to next, make sure they are neighbours
            int prev = moveFrom - 1;
            if (prev < 0) prev = 7;
            int next = moveFrom + 1;
            if (next > 7) next = 0;
            if (boardReader.pieceAt(prev) != Board.Piece.BLANK &&
                boardReader.pieceAt(next) != Board.Piece.BLANK) {
                return false;
            }     
        }
        return true;
    }

    /**
     * GetRotations finds all possible rotations of the board so we don't have to
     * check for as many arrangments. GetRotations does not include the putahi.
     * @return rotations: an array of all possilbe rotations of the board
     */
    private ArrayList<ArrayList<Integer>> getRotations(){
        ArrayList<ArrayList<Integer>> rotations =
                new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> board = getBoard();
        int i;
        rotations.add(board);
        for (i = 0; i <= 8; i++){
            board = leftRotatebyOne(board);
            rotations.add(board);
        }
        return rotations;
    }

    /**
     *  LeftRotateByOne performs a left rotation on an arraylist of integers.
     *  @param init: the initial arrangment of the board
     *  @return lrot: an array that has been left rotated by one
     */
    private ArrayList<Integer> leftRotatebyOne(ArrayList<Integer> init){
        int i;
        int n = init.size();
        ArrayList<Integer> lRot = new ArrayList<Integer>(init);
        for (i = 0; i < n - 1; i++)
            lRot.set(i, init.get(i + 1));
        lRot.set(n-1, init.get(0));
        return lRot;
    }

    /**
     * GetBoard circulates the board and reads who is where.
     * @return boardArr: an array representing the board
     */
    private ArrayList<Integer> getBoard(){
        ArrayList<Integer> boardArr = new ArrayList<Integer>();
        for (int i = 0; i < 8; i++){
            String piece = "" + boardReader.pieceAt(i);
            if (piece.equals("ONE")) boardArr.add(1);
            if (piece.equals("TWO")) boardArr.add(2);
        }
        System.out.println(boardArr);
        return boardArr;
    }

    /**
     *  IsFlag looks for the flag arrangment, where there is two adjacent kawai of
     *  the same colour as well as the putahi.
     *  @param options: an arraylist of all valid moves
     *  @return boolean: indicating whether the arrangment is a flag or not
     */
    private boolean is_flag(ArrayList<Integer> options){
        // Counter at putahi should be player_1
        int count_i = 0;
        if (boardReader.pieceAt(8) != playerID) return false;
        // Counter at count_i + 1 should be player_1
        // Counter at count_i + 2 should be player_1
        // Counter at either count_i + 3 or count_i should be blank
        while (count_i <= 7){
            // Putahi must be your colour counter for flag to occur
            int kawai_one = count_i;
            int kawai_two = count_i + 1;
            int blank_option_one = count_i - 1;
            if (count_i == 0) blank_option_one = 7;
            int blank_option_two = count_i + 2;
            if (count_i == 6) blank_option_two = 0;
            if (count_i == 7) {
                kawai_two = 0;
                blank_option_two = 1;
            }
            if (boardReader.pieceAt(kawai_one) == playerID &&
                    boardReader.pieceAt(kawai_two) == playerID)
                // Two adjacent kawai counters
                if (boardReader.pieceAt(blank_option_one) == Board.Piece.BLANK ||
                        boardReader.pieceAt(blank_option_two) == Board.Piece.BLANK)
                    return true;
            count_i += 1;
        }
        return false;
    }

    /**
     * ThreeInARow looks for moves that will not result in a definite win for the
     * opponent.
     * @param options: an arraylist of all valid moves
     * @return move: the best for the arrangment of the board or a random valid
     *                  move if there is none more suitable than another
     */
    private int three_in_a_row(ArrayList<Integer> options){
        // For every possible valid move
        for (int option = 0; option < options.size(); option++){
            // For each possible move, check if the move will create a 3-in-a-row
            // Store the position
            int position = options.get(option);
            // If the position is the putahi (center), will not be three in a row
            if (position == 8) continue;
            if (position == 0) {
                // Check 1 and 7
                if (boardReader.pieceAt(7) == playerID) return 0;
                else if (boardReader.pieceAt(1) == playerID) return 0;
            } else if (position == 7) {
                // Check 6 and 0
                if (boardReader.pieceAt(6) == playerID) return 7;
                else if (boardReader.pieceAt(1) == playerID) return 7;
            }
            if (boardReader.pieceAt(position + 1) == playerID ||
                    boardReader.pieceAt(position - 1) == playerID) return position;
        }
        // If no moves will result in three in a row, just return a random move
        return options.get(rng.nextInt(options.size()));
    }

    /**
     * CurrentStateIsARotationOf tests to see whether the arrangment is a rotation
     * of one of our key states from which we know what player to move.
     * @param test: an array representing the board in a key state
     * @return boolean: indicating whether the board is a rotaiton of the key state
     */
    private boolean currentStateIsARotationOf(ArrayList<Integer> test){
        for (ArrayList<Integer> rotation : getRotations())
            if (test.equals(rotation)) return true;
        return false;
    }
}
