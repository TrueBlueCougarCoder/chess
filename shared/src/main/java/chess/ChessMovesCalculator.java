package chess;

import java.util.ArrayList;
import chess.ChessGame.TeamColor;
import chess.ChessPiece.PieceType;

public class ChessMovesCalculator {
    public static ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        TeamColor myColor = piece.getTeamColor();
        PieceType type = piece.getPieceType();
        switch(type) {
            case KING:
                return moveKing(board, myPosition, myColor);
            case QUEEN:
                return moveQueen(board, myPosition, myColor);
            case BISHOP:
                return moveBishop(board, myPosition, myColor);
            case KNIGHT:
                return moveKnight(board, myPosition, myColor);
            case ROOK:
                return moveRook(board, myPosition, myColor);
            default: //PAWN
                return movePawn(board, myPosition, myColor);
        }
    }


    public static ArrayList<ChessMove> moveKing(ChessBoard board, ChessPosition myPosition, TeamColor myColor) {
        ChessPosition[] possiblePositions = new ChessPosition[8];

        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        //Up-Left
        possiblePositions[0] = new ChessPosition(row+1, col-1);
        //Up
        possiblePositions[1] = new ChessPosition(row+1, col);
        //Up-Right
        possiblePositions[2] = new ChessPosition(row+1, col+1);
        //Right
        possiblePositions[3] = new ChessPosition(row, col+1);
        //Down-Right
        possiblePositions[4] = new ChessPosition(row-1, col+1);
        //Down
        possiblePositions[5] = new ChessPosition(row-1, col);
        //Down-Left
        possiblePositions[6] = new ChessPosition(row-1, col-1);
        //Left
        possiblePositions[7] = new ChessPosition(row, col-1);

        return setMoves(board, myPosition, possiblePositions, myColor);
    }

    public static ArrayList<ChessMove> moveQueen(ChessBoard board, ChessPosition myPosition, TeamColor myColor) {
        ArrayList<ChessMove> allMoves = new ArrayList<ChessMove>();
        //Straight Moves
        allMoves.addAll(moveRook(board, myPosition, myColor));
        //Diagonal Moves
        allMoves.addAll(moveBishop(board, myPosition, myColor));

        return allMoves;
    }

    public static ArrayList<ChessMove> moveBishop(ChessBoard board, ChessPosition myPosition, TeamColor myColor) {
        ArrayList<ChessMove> allMoves = new ArrayList<ChessMove>();

        //Up-Left
        int[] modifier = {1,-1};
        allMoves.addAll(lineMove(board, myPosition, modifier, myColor));
        //Up-Right
        modifier[1] = 1;
        allMoves.addAll(lineMove(board, myPosition, modifier, myColor));
        //Down-Right
        modifier[0] = -1;
        allMoves.addAll(lineMove(board, myPosition, modifier, myColor));
        //Down-Left
        modifier[1] = -1;
        allMoves.addAll(lineMove(board, myPosition, modifier, myColor));

        return allMoves;
    }

    public static ArrayList<ChessMove> moveKnight(ChessBoard board, ChessPosition myPosition, TeamColor myColor) {
        ChessPosition[] possiblePositions = new ChessPosition[8];

        int row = myPosition.getRow();
        int col = myPosition.getColumn();
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

        return setMoves(board, myPosition, possiblePositions, myColor);
    }

    public static ArrayList<ChessMove> moveRook(ChessBoard board, ChessPosition myPosition, TeamColor myColor) {
        ArrayList<ChessMove> allMoves = new ArrayList<ChessMove>();

        //Up
        int[] modifier = {1,0};
        allMoves.addAll(lineMove(board, myPosition, modifier, myColor));
        //Down
        modifier[0] = -1;
        allMoves.addAll(lineMove(board, myPosition, modifier, myColor));
        //Left
        modifier[0] = 0;
        modifier[1] = -1;
        allMoves.addAll(lineMove(board, myPosition, modifier, myColor));
        //Right
        modifier[1] = 1;
        allMoves.addAll(lineMove(board, myPosition, modifier, myColor));

        return allMoves;
    }

    public static ArrayList<ChessMove> movePawn(ChessBoard board, ChessPosition myPosition, TeamColor myColor) {
        ArrayList<ChessMove> allMoves = new ArrayList<ChessMove>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        //Check if next move will create a promotion
        ArrayList<PieceType> promotionTypes = new ArrayList<PieceType>();
        if(myColor == TeamColor.BLACK && row == 2) {
            promotionTypes.add(PieceType.QUEEN);
            promotionTypes.add(PieceType.BISHOP);
            promotionTypes.add(PieceType.KNIGHT);
            promotionTypes.add(PieceType.ROOK);
        } else if(myColor == TeamColor.WHITE && row == 7) {
            promotionTypes.add(PieceType.QUEEN);
            promotionTypes.add(PieceType.BISHOP);
            promotionTypes.add(PieceType.KNIGHT);
            promotionTypes.add(PieceType.ROOK);
        } else {
            promotionTypes.add(null);
        }

        //Determine Direction of Movement and Opponent Color
        int direction = 1;
        TeamColor opponentColor = TeamColor.BLACK;
        if(myColor == TeamColor.BLACK) {
            direction = -1;
            opponentColor = TeamColor.WHITE;
        }

        //Forward
        ChessPosition nextPosition = new ChessPosition(row+direction, col);
        if(squareStatus(board, nextPosition, myColor) == null) {
            allMoves.addAll(addPawnMoves(myPosition, nextPosition, promotionTypes));

            //Forward 2
            nextPosition = new ChessPosition(row+(2*direction), col);
            if((row == 2 || row == 7) && (squareStatus(board, nextPosition, myColor) == null)) {
                allMoves.addAll(addPawnMoves(myPosition, nextPosition, promotionTypes));
            }
        }

        //Attack
        //Left
        nextPosition = new ChessPosition(row+direction, col-1);
        if(squareStatus(board, nextPosition, myColor) == opponentColor) {
            allMoves.addAll(addPawnMoves(myPosition, nextPosition, promotionTypes));
        }
        //Right
        nextPosition = new ChessPosition(row+direction, col+1);
        if(squareStatus(board, nextPosition, myColor) == opponentColor) {
            allMoves.addAll(addPawnMoves(myPosition, nextPosition, promotionTypes));
        }

        return allMoves;
    }

    public static ArrayList<ChessMove> addPawnMoves(ChessPosition myPosition, ChessPosition nextPosition, ArrayList<PieceType> promotionTypes) {
        ArrayList<ChessMove> allMoves = new ArrayList<ChessMove>();
        for(PieceType promotionPiece : promotionTypes) {
            allMoves.add(new ChessMove(myPosition, nextPosition, promotionPiece));
        }
        return allMoves;
    }


    public static ArrayList<ChessMove> setMoves(ChessBoard board, ChessPosition myPosition, ChessPosition[]possiblePositions, TeamColor myColor){
        ArrayList<ChessMove> allMoves = new ArrayList<ChessMove>();

        for(ChessPosition nextPosition : possiblePositions) {
            TeamColor positionStatus = squareStatus(board, nextPosition, myColor);
            if(positionStatus != myColor) {
                allMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        return allMoves;
    }

    public static ArrayList<ChessMove> lineMove(ChessBoard board, ChessPosition myPosition, int[]modifier, TeamColor myColor) {
        ArrayList<ChessMove> allMoves = new ArrayList<ChessMove>();

        ChessPosition nextPosition;
        TeamColor positionStatus;
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        do {
            row += modifier[0];
            col += modifier[1];
            nextPosition = new ChessPosition(row, col);
            positionStatus = squareStatus(board, nextPosition, myColor);
            if(positionStatus != myColor) {
                allMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        } while(positionStatus == null);

        return allMoves;
    }

    //null = empty space
    //myColor = occupied by fiendly piece OR out-of-bounds
    //opponent's color = occupied by opponent's piece
    public static TeamColor squareStatus(ChessBoard board, ChessPosition position, TeamColor myColor) {
        int row = position.getRow();
        int col = position.getColumn();

        //Check out of bounds
        if(row < 1 || row > 8) {
            return myColor;
        } else if(col < 1 || col > 8) {
            return myColor;
        }

        ChessPiece occupyingPiece = board.getPiece(position);
        //Return the status of the space
        if(occupyingPiece == null) {
            return null;
        }

        //Space is occupied, return team of space;
        return occupyingPiece.getTeamColor();
    }
}
