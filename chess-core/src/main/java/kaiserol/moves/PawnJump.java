package kaiserol.moves;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.pieces.Pawn;

public final class PawnJump extends Move {
    private final Pawn pawn;
    private final ChessField enPassantTarget;

    public PawnJump(ChessBoard board, ChessField startField, ChessField targetField) {
        super(board, startField, targetField);
        this.pawn = (Pawn) startField.getPiece();

        int enPassantY = (startField.getY() + targetField.getY()) / 2;
        this.enPassantTarget = board.getField(startField.getX(), enPassantY);
    }

    public ChessField getEnPassantTarget() {
        return enPassantTarget;
    }

    @Override
    public void execute() {
        // Moves the pawn two fields forward
        ChessBoard.occupyField(targetField, pawn);
    }

    @Override
    public void undo() {
        // Puts the pawn back
        ChessBoard.occupyField(startField, pawn);
    }
}