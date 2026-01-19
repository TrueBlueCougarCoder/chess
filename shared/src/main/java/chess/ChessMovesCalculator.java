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
                break;
            case PieceType.QUEEN:
                break;
            case PieceType.BISHOP:
                break;
            case PieceType.KNIGHT:
                break;
            case PieceType.ROOK:
                break;
            case PieceType.PAWN:
                break;
        }
        throw new RuntimeException("Provided piece type not recognized.");
    }

    public static Collection<ChessMove> moveKing(ChessBoard board, ChessPosition currentPosition) {
        throw new RuntimeException("Not implemented");
    }

    public static Collection<ChessMove> moveQueen(ChessBoard board, ChessPosition currentPosition) {
        throw new RuntimeException("Not implemented");
    }

    public static Collection<ChessMove> moveBishop(ChessBoard board, ChessPosition currentPosition) {
        throw new RuntimeException("Not implemented");
    }

    public static Collection<ChessMove> moveKnight(ChessBoard board, ChessPosition currentPosition) {
        throw new RuntimeException("Not implemented");
    }

    public static Collection<ChessMove> moveRook(ChessBoard board, ChessPosition currentPosition) {
        throw new RuntimeException("Not implemented");
    }

    public static boolean squareValid(ChessPosition position) {
        throw new RuntimeException("Not implemented");
    }

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
}
