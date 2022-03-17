import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static class PieceSide {
        final String n1;
        final String n2;

        public PieceSide(String n1, String n2) {
            this.n1 = n1;
            this.n2 = n2;
        }

        @Override
        public boolean equals(Object o) {
            PieceSide other = (PieceSide) o;

            return n1.equals(other.n1) && n2.equals(other.n2);
        }

        @Override
        public int hashCode() {
            return 31 * n1.hashCode() + n2.hashCode();
        }

        @Override
        public String toString() {
            return n1 + "|" + n2;
        }
    }

    public static class PieceSides {
        final PieceSide up;
        final PieceSide left;

        public PieceSides(PieceSide up, PieceSide left) {
            this.up = up;
            this.left = left;
        }

        @Override
        public boolean equals(Object o) {
            PieceSides other = (PieceSides) o;
            return left.equals(other.left) && up.equals(other.up);
        }

        @Override
        public int hashCode() {
            return 31 * left.hashCode() + up.hashCode();
        }

        @Override
        public String toString() {
            return up.n1 + "|" + up.n2 + "\n" +
                    left.n2;
        }
    }

    public static class Piece {
        String topLeft;
        String topRight;
        String bottomRight;
        String bottomLeft;
        boolean used;

        public Piece(String topLeft, String topRight, String bottomRight, String bottomLeft) {
            this.topLeft = topLeft;
            this.topRight = topRight;
            this.bottomRight = bottomRight;
            this.bottomLeft = bottomLeft;
        }

        @Override
        public String toString() {
            return topLeft + "|" + topRight + "\n" +
                    bottomLeft + "|" + bottomRight;
        }
    }

    public static class RotatedPiece {
        Piece piece;
        int rotState;
        String topLeft;
        String topRight;
        String bottomRight;
        String bottomLeft;

        public RotatedPiece() {
        }

        public static RotatedPiece rotate(Piece piece, int rotState) {
            RotatedPiece rotPiece = new RotatedPiece();
            rotPiece.piece = piece;
            rotPiece.rotState = rotState;

            switch (rotState) {
                case 0:
                    rotPiece.topLeft = piece.topLeft;
                    rotPiece.topRight = piece.topRight;
                    rotPiece.bottomRight = piece.bottomRight;
                    rotPiece.bottomLeft = piece.bottomLeft;
                    break;
                case 1:
                    rotPiece.topLeft = piece.bottomLeft;
                    rotPiece.topRight = piece.topLeft;
                    rotPiece.bottomRight = piece.topRight;
                    rotPiece.bottomLeft = piece.bottomRight;
                    break;
                case 2:
                    rotPiece.topLeft = piece.bottomRight;
                    rotPiece.topRight = piece.bottomLeft;
                    rotPiece.bottomRight = piece.topLeft;
                    rotPiece.bottomLeft = piece.topRight;
                    break;
                default:
                    rotPiece.topLeft = piece.topRight;
                    rotPiece.topRight = piece.bottomRight;
                    rotPiece.bottomRight = piece.bottomLeft;
                    rotPiece.bottomLeft = piece.topLeft;
                    break;
            }

            return rotPiece;
        }
    }

    private static void preProcess_(PieceSide side, RotatedPiece piece, HashMap<PieceSide, ArrayList<RotatedPiece>> map) {
        if (!map.containsKey(side)) {
            ArrayList<RotatedPiece> list = new ArrayList<RotatedPiece>();
            list.add(piece);
            map.put(side, list);
        } else {
            map.get(side).add(piece);
        }
    }

    private static void preProcess2_(PieceSides sides, RotatedPiece piece, HashMap<PieceSides, ArrayList<RotatedPiece>> map) {
        if (!map.containsKey(sides)) {
            ArrayList<RotatedPiece> list = new ArrayList<RotatedPiece>();
            list.add(piece);
            map.put(sides, list);
        } else {
            map.get(sides).add(piece);
        }
    }

    public static void preProcess(Piece piece) {
        pieces.add(piece);

        PieceSide p1, p2;
        RotatedPiece r;

        r = RotatedPiece.rotate(piece, 0);
        p1 = new PieceSide(r.topLeft, r.topRight);
        p2 = new PieceSide(r.topLeft, r.bottomLeft);

        preProcess_(p2, r, left);
        preProcess_(p1, r, up);
        preProcess2_(new PieceSides(p1, p2), r, upLeft);

        r = RotatedPiece.rotate(piece, 1);
        p1 = new PieceSide(r.topLeft, r.topRight);
        p2 = new PieceSide(r.topLeft, r.bottomLeft);

        preProcess_(p2, r, left);
        preProcess_(p1, r, up);
        preProcess2_(new PieceSides(p1, p2), r, upLeft);

        r = RotatedPiece.rotate(piece, 2);
        p1 = new PieceSide(r.topLeft, r.topRight);
        p2 = new PieceSide(r.topLeft, r.bottomLeft);

        preProcess_(p2, r, left);
        preProcess_(p1, r, up);
        preProcess2_(new PieceSides(p1, p2), r, upLeft);

        r = RotatedPiece.rotate(piece, 3);
        p1 = new PieceSide(r.topLeft, r.topRight);
        p2 = new PieceSide(r.topLeft, r.bottomLeft);

        preProcess_(p2, r, left);
        preProcess_(p1, r, up);
        preProcess2_(new PieceSides(p1, p2), r, upLeft);
    }

    static RotatedPiece[][] board;
    static HashMap<PieceSide, ArrayList<RotatedPiece>> left;
    static HashMap<PieceSide, ArrayList<RotatedPiece>> up;
    static HashMap<PieceSides, ArrayList<RotatedPiece>> upLeft;
    static ArrayList<Piece> pieces;
    static int nRows;
    static int nCols;

    public static boolean solve_(RotatedPiece current, int x, int y) {
        current.piece.used = true;
        board[x][y] = current;

        if (x == nRows - 1 && y == nCols - 1) {
            // base case
            return true;
        }

        // get the right and bottom side of the current piece
        boolean sol = false;

        ArrayList<RotatedPiece> candidates;

        if (y == nCols - 1) {
            // if we are in the last cell of the current row i.e. about to jump to the next row
            RotatedPiece prevRowUpPiece = board[x][0];
            PieceSide upPieceBottomSide = new PieceSide(prevRowUpPiece.bottomLeft, prevRowUpPiece.bottomRight);
            candidates = up.get(upPieceBottomSide);
        } else if (x > 0 && board[x - 1][y + 1] != null) {
            // if we aren't in the first row/column
            PieceSide rightSide = new PieceSide(current.topRight, current.bottomRight);
            RotatedPiece upPiece = board[x - 1][y + 1];
            PieceSide upPieceBottomSide = new PieceSide(upPiece.bottomLeft, upPiece.bottomRight);
            candidates = upLeft.get(new PieceSides(upPieceBottomSide, rightSide));
        } else {
            // if we are in the first row, including the first cell
            PieceSide rightSide = new PieceSide(current.topRight, current.bottomRight);
            candidates = left.get(rightSide);
        }

        // expand to the right
        if (candidates != null) {
            for (RotatedPiece candidate : candidates) {
                if (!candidate.piece.used) {
                    int nextX;
                    int nextY;

                    if (y == nCols - 1) {
                        nextX = x + 1;
                        nextY = 0;
                    } else {
                        nextX = x;
                        nextY = y + 1;
                    }
                    sol |= solve_(candidate, nextX, nextY);
                }
            }
        }

        if (!sol) {
            // if there is no solution with the current piece in this point, unmark it as used and remove it from the board
            current.piece.used = false;
            board[x][y] = null;
        }

        return sol;
    }

    public static boolean solve(int n) {
        boolean sol;

        // solve for every piece as a start piece
        for (Piece piece : pieces) {
            // solve for every rotation of the start piece

            // solve for the normal initial piece
            sol = solve_(RotatedPiece.rotate(piece, 0), 0, 0);
            
            if (sol) {
                return true;
            }

            // solve for the 90 deg rotated initial piece CW
            sol = solve_(RotatedPiece.rotate(piece, 1), 0, 0);
            
            if (sol) {
                return true;
            }

            // solve for the 180 deg rotated initial piece CW
            sol = solve_(RotatedPiece.rotate(piece, 1), 0, 0);
            
            if (sol) {
                return true;
            }

            // solve for the 270 deg rotated initial piece CW
            sol = solve_(RotatedPiece.rotate(piece, 1), 0, 0);
            
            if (sol) {
                return true;
            }
        }

        return false;
    }

    public static void printBoard() {
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                RotatedPiece current = board[i][j];

                if (current == null) {
                    continue;
                }

                System.out.print(current.topLeft + " " + current.topRight);

                if (j < nCols - 1) {
                    System.out.print("  ");
                }
            }

            System.out.print("\n");

            for (int j = 0; j < nCols; j++) {
                RotatedPiece current = board[i][j];

                if (current == null) {
                    continue;
                }

                System.out.print(current.bottomLeft + " " + current.bottomRight);

                if (j < nCols - 1) {
                    System.out.print("  ");
                }
            }

            if (i != nRows - 1) {
                System.out.print("\n\n");
            } else {
                System.out.print("\n");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        line = in.readLine();
        int nCases = Integer.parseInt(line);

        for (int i = 0; i < nCases; i++) {
            line = in.readLine();
            st = new StringTokenizer(line);

            int n = Integer.parseInt(st.nextToken());
            nRows = Integer.parseInt(st.nextToken());
            nCols = Integer.parseInt(st.nextToken());

            board = new RotatedPiece[nRows][nCols];
            left = new HashMap<PieceSide, ArrayList<RotatedPiece>>();
            up = new HashMap<PieceSide, ArrayList<RotatedPiece>>();
            upLeft = new HashMap<PieceSides, ArrayList<RotatedPiece>>();
            pieces = new ArrayList<Piece>();

            for (int j = 0; j < n; j++) {
                line = in.readLine();
                st = new StringTokenizer(line);

                String n1 = st.nextToken();
                String n2 = st.nextToken();
                String n3 = st.nextToken();
                String n4 = st.nextToken();

                Piece p = new Piece(n1, n2, n3, n4);
                preProcess(p);
            }

            boolean hasSol = solve(n);

            if (hasSol) {
                printBoard();
            } else {
                System.out.println("impossible puzzle!");
            }
        }
    }
}
