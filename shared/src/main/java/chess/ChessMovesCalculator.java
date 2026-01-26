package chess;
import java.util.ArrayList;
import chess.ChessGame.TeamColor;
import chess.ChessPiece.PieceType;

public class ChessMovesCalculator {
    public static ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ChessPiece myPiece = board.getPiece(position);
        ChessPiece.PieceType pieceType = myPiece.getPieceType();
        TeamColor myColor = myPiece.getTeamColor();

        switch(pieceType) {
            case KING:
                return moveKing(board, position, myColor);
            case QUEEN:
                return moveQueen(board, position, myColor);
            case BISHOP:
                return moveBishop(board, position, myColor);
            case ROOK:
                return moveRook(board, position, myColor);
            case KNIGHT:
                return moveKnight(board, position, myColor);
            default:
                return movePawn(board, position, myColor);
        }
    }

    public static ArrayList<ChessMove> moveKing(ChessBoard board, ChessPosition myPosition, TeamColor myColor) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPosition [] possiblePositions = new ChessPosition[8];

        //Top Left
        possiblePositions[0] = new ChessPosition(row+1, col-1);
        //Top
        possiblePositions[1] = new ChessPosition(row+1, col);
        //Top Right
        possiblePositions[2] = new ChessPosition(row+1, col+1);
        //Middle Right
        possiblePositions[3] = new ChessPosition(row, col+1);
        //Bottom Right
        possiblePositions[4] = new ChessPosition(row-1, col+1);
        //Bottom
        possiblePositions[5] = new ChessPosition(row-1, col);
        //Bottom Left
        possiblePositions[6] = new ChessPosition(row-1, col-1);
        //Left
        possiblePositions[7] = new ChessPosition(row, col-1);

        return targetMovement(board, myColor, myPosition, possiblePositions);
    }

    public static ArrayList<ChessMove> moveQueen(ChessBoard board, ChessPosition myPosition, TeamColor myColor) {
        ArrayList<ChessMove> allMoves = new ArrayList<ChessMove>();
        allMoves.addAll(moveRook(board, myPosition, myColor));
        allMoves.addAll(moveBishop(board, myPosition, myColor));
        return allMoves;
    }

    public static ArrayList<ChessMove> moveBishop(ChessBoard board, ChessPosition myPosition, TeamColor myColor) {
        ArrayList<ChessMove> allMoves = new ArrayList<ChessMove>();

        //Up-Left
        int [] modifier = {1,-1};
        allMoves.addAll(lineMovement(board, myPosition, myColor, modifier));

        //Up-Right
        modifier[1] = 1;
        allMoves.addAll(lineMovement(board, myPosition, myColor, modifier));

        //Down-Right
        modifier[0] = -1;
        allMoves.addAll(lineMovement(board, myPosition, myColor, modifier));

        //Down-Left
        modifier[1] = -1;
        allMoves.addAll(lineMovement(board, myPosition, myColor, modifier));

        return allMoves;
    }

    public static ArrayList<ChessMove> moveRook(ChessBoard board, ChessPosition myPosition, TeamColor myColor) {
        ArrayList<ChessMove> allMoves = new ArrayList<ChessMove>();

        //Up
        int [] modifier = {1,0};
        allMoves.addAll(lineMovement(board, myPosition, myColor, modifier));

        //Down
        modifier[0] = -1;
        allMoves.addAll(lineMovement(board, myPosition, myColor, modifier));

        //Left
        modifier[0] = 0;
        modifier[1] = -1;
        allMoves.addAll(lineMovement(board, myPosition, myColor, modifier));

        //Right
        modifier[1] = 1;
        allMoves.addAll(lineMovement(board, myPosition, myColor, modifier));

        return allMoves;
    }

    public static ArrayList<ChessMove> moveKnight(ChessBoard board, ChessPosition myPosition, TeamColor myColor) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPosition [] possiblePositions = new ChessPosition[8];

        //Left-Down
        possiblePositions[0] = new ChessPosition(row-1, col-2);
        //Left-Up
        possiblePositions[1] = new ChessPosition(row+1, col-2);
        //Up-Left
        possiblePositions[2] = new ChessPosition(row+2, col-1);
        //Up-Right
        possiblePositions[3] = new ChessPosition(row+2, col+1);
        //Right-Up
        possiblePositions[4] = new ChessPosition(row+1, col+2);
        //Right-Down
        possiblePositions[5] = new ChessPosition(row-1, col+2);
        //Down-Right
        possiblePositions[6] = new ChessPosition(row-2, col+1);
        //Down-Left
        possiblePositions[7] = new ChessPosition(row-2, col-1);

        return targetMovement(board, myColor, myPosition, possiblePositions);
    }

    public static ArrayList<ChessMove> movePawn(ChessBoard board, ChessPosition myPosition, TeamColor myColor) {
        ArrayList<ChessMove> allMoves = new ArrayList<ChessMove>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        //Set direction of travel
        int direction = 1;
        if(myColor == TeamColor.BLACK) {
            direction = -1;
        }

        //Set Promotions if relevant
        ArrayList<PieceType> promotionTypes = new ArrayList<PieceType>();
        if(myColor == TeamColor.BLACK && row == 2) {
            promotionTypes.add(PieceType.QUEEN);
            promotionTypes.add(PieceType.KNIGHT);
            promotionTypes.add(PieceType.ROOK);
            promotionTypes.add(PieceType.BISHOP);
        } else if(myColor == TeamColor.WHITE && row == 7) {
            promotionTypes.add(PieceType.QUEEN);
            promotionTypes.add(PieceType.KNIGHT);
            promotionTypes.add(PieceType.ROOK);
            promotionTypes.add(PieceType.BISHOP);
        } else {
            promotionTypes.add(null);
        }

        //Move Forward
        ChessPosition possibleMove = new ChessPosition(row+direction, col);
        if(squareStatus(board, possibleMove, myColor) == null) {
            allMoves.addAll(pawnMoveAdd(myPosition, possibleMove, promotionTypes));

            //Move 2
            possibleMove = new ChessPosition(row+(2*direction), col);
            if((row == 2 || row == 7) && squareStatus(board, possibleMove, myColor) == null) {
                allMoves.addAll(pawnMoveAdd(myPosition, possibleMove, promotionTypes));
            }
        }

        //Attack
        //Left
        possibleMove = new ChessPosition(row+direction, col-1);
        TeamColor status = squareStatus(board, possibleMove, myColor);
        if(status != null && status != myColor) {
            allMoves.addAll(pawnMoveAdd(myPosition, possibleMove, promotionTypes));
        }

        //Left
        possibleMove = new ChessPosition(row+direction, col+1);
        status = squareStatus(board, possibleMove, myColor);
        if(status != null && status != myColor) {
            allMoves.addAll(pawnMoveAdd(myPosition, possibleMove, promotionTypes));
        }

        return allMoves;
    }

    public static ArrayList<ChessMove> pawnMoveAdd(ChessPosition startPosition, ChessPosition endPosition, ArrayList<PieceType> promotionTypes) {
        ArrayList<ChessMove> allMoves = new ArrayList<ChessMove>();
        for(PieceType promotionPiece : promotionTypes) {
            allMoves.add(new ChessMove(startPosition, endPosition, promotionPiece));
        }
        return allMoves;
    }

    public static ArrayList<ChessMove> lineMovement(ChessBoard board, ChessPosition myPosition, TeamColor myColor, int [] directionModifier) {
        ArrayList<ChessMove> allMoves = new ArrayList<ChessMove>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPosition currentPosition;
        do {
            row += directionModifier[0];
            col += directionModifier[1];
            currentPosition = new ChessPosition(row, col);
            if(squareStatus(board, currentPosition, myColor) != myColor) {
                allMoves.add(new ChessMove(myPosition, currentPosition, null));
            }
        } while(squareStatus(board, currentPosition, myColor) == null);
        return allMoves;
    }

    public static ArrayList<ChessMove> targetMovement(ChessBoard board, TeamColor myColor, ChessPosition myPosition, ChessPosition [] possiblePositions) {
        ArrayList<ChessMove> allMoves = new ArrayList<ChessMove>();
        for(ChessPosition possiblePosition : possiblePositions) {
            if(squareStatus(board, possiblePosition, myColor) != myColor) {
                allMoves.add(new ChessMove(myPosition, possiblePosition, null));
            }
        }
        return allMoves;
    }



    //Returns null is space empty
    //Returns opponent color if occupied by opponent
    //Returns own color if occupied by friendly piece or if space is out of bounds
    public static TeamColor squareStatus(ChessBoard board, ChessPosition position, TeamColor myColor) {
        //Checking if position is in-bounds
        int row = position.getRow();
        int col = position.getColumn();
        if(row < 1 || row > 8) {
            return myColor;
        } else if(col < 1 || col > 8) {
            return myColor;
        }

        ChessPiece occupyingPiece = board.getPiece(position);
        //Checking if space is empty;
        if(occupyingPiece == null) {
            return null;
        }

        //Return the color of the piece on the space
        return occupyingPiece.getTeamColor();
    }
}
