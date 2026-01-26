package chess;

import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] squares = new ChessPiece[8][8];
    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int row = position.getRow();
        int col = position.getColumn();
        squares[row-1][col-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        return squares[row-1][col-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        //For PAWNs
        ChessPiece blackPiece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPiece whitePiece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        for(int i = 0; i < 8; i++) {
            squares[6][i] = blackPiece;
            squares[1][i] = whitePiece;
        }

        //For All Other Pieces
        ChessPiece.PieceType[] pieceTypes = {ChessPiece.PieceType.KING, ChessPiece.PieceType.QUEEN, ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.KNIGHT, ChessPiece.PieceType.ROOK};
        int [][] positions = {{4}, {3}, {2,5}, {1,6}, {0,7}};
        for(int i = 0; i < pieceTypes.length; i++) {
            blackPiece = new ChessPiece(ChessGame.TeamColor.BLACK, pieceTypes[i]);
            whitePiece = new ChessPiece(ChessGame.TeamColor.WHITE, pieceTypes[i]);
            for(int col : positions[i]) {
                squares[7][col] = blackPiece;
                squares[0][col] = whitePiece;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(this == obj) {
            return true;
        }

        if(this.getClass() != obj.getClass()) {
            return false;
        }

        ChessBoard comp = (ChessBoard)obj;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(squares[i][j] != null && !squares[i][j].equals(comp.getPiece(new ChessPosition(i+1, j+1)))) {
                    return false;
                } else if(squares[i][j] == null && comp.getPiece(new ChessPosition(i+1, j+1)) != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public int hashCode() {
        int hash = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                hash = hash*31 + Objects.hashCode(squares[i][j]);
            }
        }
        return hash;
    }
}
