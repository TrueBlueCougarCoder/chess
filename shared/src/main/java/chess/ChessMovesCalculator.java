package chess;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import chess.ChessPiece.PieceType;

/**
 * Identifies all possible moves for a piece.
 * <p>
 * Note: This class exists to simplify the ChessPiece class.
 */
public class ChessMovesCalculator {
    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger.
     *
     * @return Collection of valid moves
     */
    public static Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition currentPosition) {
        PieceType moveType = board.getPiece(currentPosition).getPieceType();
        switch(moveType) {
            case PieceType.KING:
                return moveKing(board, currentPosition);
            case PieceType.QUEEN:
                return moveQueen(board, currentPosition);
            case PieceType.BISHOP:
                return moveBishop(board, currentPosition);
            case PieceType.KNIGHT:
                return moveKnight(board, currentPosition);
            case PieceType.ROOK:
                return moveRook(board, currentPosition);
            case PieceType.PAWN:
                return movePawn(board, currentPosition);
        }
        throw new RuntimeException("Provided piece type not recognized.");
    }

    /**
     * @return Possible moves if the given piece is a King.
     */
    public static Collection<ChessMove> moveKing(ChessBoard board, ChessPosition currentPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * @return Possible moves if the given piece is a Queen.
     */
    public static Collection<ChessMove> moveQueen(ChessBoard board, ChessPosition currentPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * @return Possible moves if the given piece is a Bishop.
     */
    public static Collection<ChessMove> moveBishop(ChessBoard board, ChessPosition currentPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * @return Possible moves if the given piece is a Knight.
     */
    public static Collection<ChessMove> moveKnight(ChessBoard board, ChessPosition currentPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * @return Possible moves if the given piece is a Rook.
     */
    public static Collection<ChessMove> moveRook(ChessBoard board, ChessPosition currentPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * @return Possible moves if the given piece is a Pawn.
     */
    public static Collection<ChessMove> movePawn(ChessBoard board, ChessPosition currentPosition) {
        Collection<ChessMove> allPossibleMoves = new ArrayList<ChessMove>();
        ChessPiece piece = board.getPiece(currentPosition);
        PieceType promotion = null;

        //Defining direction of movement based on color.
        int direction = 1;
        if(piece.getTeamColor() == ChessGame.TeamColor.BLACK){
            direction = -1;
        }

        //Move Forward
        ChessPosition possibleMove = new ChessPosition(currentPosition.getRow() + direction, currentPosition.getColumn());
        if(isSquareOpen(board, possibleMove)) {
            allPossibleMoves.add(new ChessMove(currentPosition, possibleMove, promotion));
        }

        //Move diagonals
        possibleMove = new ChessPosition(currentPosition.getRow() + direction, currentPosition.getColumn()+1);
        if(isSquareValidPawn(board, possibleMove, piece.getTeamColor())) {
            allPossibleMoves.add(new ChessMove(currentPosition, possibleMove, promotion));
        }
        possibleMove = new ChessPosition(currentPosition.getRow() + direction, currentPosition.getColumn()-1);
        if(isSquareValidPawn(board, possibleMove, piece.getTeamColor())) {
            allPossibleMoves.add(new ChessMove(currentPosition, possibleMove, promotion));
        }

        return allPossibleMoves;
    }


    /**
     * @return Is the provided square valid as a position to move to.
     * Checks if the position is in bounds and unoccupied,
     * or in bounds and occupied by an enemy piece.
     * Is NEVER used for PAWNs.
     */
    public static boolean isSquareValid(ChessBoard board, ChessPosition position, ChessGame.TeamColor attackerColor) {
        //Check if in bounds
        if(!isSquareInBounds(position)) {
            return false;
        }
        //Check if space is unoccupied.
        if(isSquareOpen(board, position)){
            return true;
        }
        //Check if occupying piece is enemy piece
        if(isEnemyPosition(board, position, attackerColor)) {
            return true;
        }
        //Position is occupied by a friendly piece.
        return false;
    }

    /**
     * @return Is the provided square valid as a position to move to.
     * Checks if the position is in bounds and unoccupied,
     * or in bounds and occupied by an enemy piece.
     * Is ONLY used for PAWN attacks.
     */
    public static boolean isSquareValidPawn(ChessBoard board, ChessPosition position, ChessGame.TeamColor attackerColor) {
        //Check if in bounds
        if(!isSquareInBounds(position)) {
            return false;
        }
        //Check if space is unoccupied.
        if(isSquareOpen(board, position)){
            return false;
        }
        //Check if occupying piece is enemy piece
        if(isEnemyPosition(board, position, attackerColor)) {
            return true;
        }
        //Position is occupied by a friendly piece.
        return false;
    }


    /**
     * @return Is the provided chess square in the bounds of the board.
     */
    public static boolean isSquareInBounds(ChessPosition position) {
        if(position.getRow() < 1) {
            return false;
        } else if(position.getRow() > 8) {
            return false;
        } else if(position.getColumn() < 1) {
            return false;
        } else if(position.getColumn() > 8) {
            return false;
        }
        return true;
    }

    /**
     * @return Is the provided chess square empty.
     */
    public static boolean isSquareOpen(ChessBoard board, ChessPosition position) {
        if(board.getPiece(position) == null) {
            return true;
        }
        return false;
    }

    /**
     * @return Is the provided chess square occupied by an enemy piece.
     */
    public static boolean isEnemyPosition(ChessBoard board, ChessPosition position, ChessGame.TeamColor attackerColor) {
        if(board.getPiece(position).getTeamColor() == attackerColor) {
            return false;
        }
        return true;
    }
}
