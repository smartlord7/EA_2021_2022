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
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PieceSide pieceSide = (PieceSide) o;

            if (n1 != null ? !n1.equals(pieceSide.n1) : pieceSide.n1 != null) return false;
            return n2 != null ? n2.equals(pieceSide.n2) : pieceSide.n2 == null;
        }

        @Override
        public int hashCode() {
            int result = n1 != null ? n1.hashCode() : 0;
            result = 31 * result + (n2 != null ? n2.hashCode() : 0);
            return result;
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
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PieceSides that = (PieceSides) o;

            if (left != null ? !left.equals(that.left) : that.left != null) return false;
            return up != null ? up.equals(that.up) : that.up == null;
        }

        @Override
        public int hashCode() {
            int result = left != null ? left.hashCode() : 0;
            result = 31 * result + (up != null ? up.hashCode() : 0);
            return result;
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
        boolean fixed;

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

        preProcess_(p1, r, up);
        preProcess_(p2, r, left);
        preProcess2_(new PieceSides(p1, p2), r, upLeft);

        r = RotatedPiece.rotate(piece, 1);
        p1 = new PieceSide(r.topLeft, r.topRight);
        p2 = new PieceSide(r.topLeft, r.bottomLeft);

        preProcess_(p1, r, up);
        preProcess_(p2, r, left);
        preProcess2_(new PieceSides(p1, p2), r, upLeft);

        r = RotatedPiece.rotate(piece, 2);
        p1 = new PieceSide(r.topLeft, r.topRight);
        p2 = new PieceSide(r.topLeft, r.bottomLeft);

        preProcess_(p1, r, up);
        preProcess_(p2, r, left);
        preProcess2_(new PieceSides(p1, p2), r, upLeft);

        r = RotatedPiece.rotate(piece, 3);
        p1 = new PieceSide(r.topLeft, r.topRight);
        p2 = new PieceSide(r.topLeft, r.bottomLeft);

        preProcess_(p1, r, up);
        preProcess_(p2, r, left);
        preProcess2_(new PieceSides(p1, p2), r, upLeft);
    }

    static RotatedPiece[][] board;
    static HashMap<PieceSide, ArrayList<RotatedPiece>> up;
    static HashMap<PieceSide, ArrayList<RotatedPiece>> left;
    static HashMap<PieceSides, ArrayList<RotatedPiece>> upLeft;
    static ArrayList<Piece> pieces;
    static int nRows;
    static int nCols;
    static int n;

    public static boolean solve_(RotatedPiece current, int x, int y) {

        if (x >= nRows - 1 || y >= nCols - 1) {
            if (x == nRows - 1 && y == nCols - 1) {
                board[x][y] = current;
            }
            // base case
            return true;
        }

        board[x][y] = current;

        // get the right and bottom side of the current piece
        PieceSide rightSide = new PieceSide(current.topRight, current.bottomRight);
        PieceSide bottomSide = new PieceSide(current.bottomLeft, current.bottomRight);
        boolean rightSol = false;
        boolean bottomSol = false;

        ArrayList<RotatedPiece> rightCandidates;
        ArrayList<RotatedPiece> bottomCandidates;

        if (x > 0 && y > 0 && x < nRows - 1 && board[x + 1][y - 1] != null) {
            RotatedPiece left = board[x + 1][y - 1];
            PieceSide leftPieceRightSide = new PieceSide(left.topRight, left.bottomRight);
            bottomCandidates = upLeft.get(new PieceSides(bottomSide, leftPieceRightSide));
        } else {
            bottomCandidates = up.get(bottomSide);
        }

        if (y > 0 && x > 0 && y < nCols - 1 && board[x - 1][y + 1] != null) {
            RotatedPiece up = board[x - 1][y + 1];
            PieceSide upPieceBottomSide = new PieceSide(up.bottomLeft, up.bottomRight);
            rightCandidates = upLeft.get(new PieceSides(upPieceBottomSide, rightSide));
        } else {
            rightCandidates = left.get(rightSide);
        }

        //expand to the bottom
        if (bottomCandidates != null) {
            for (RotatedPiece bottomCandidate : bottomCandidates) {
                if (!bottomCandidate.piece.used) {
                    bottomCandidate.piece.used = true;
                    int nextX;
                    int nextY;
                    if (x == nRows - 1) {
                        nextX = 0;
                        nextY = y + 1;
                    } else {
                        nextX = x + 1;
                        nextY = y;
                    }
                    bottomSol = solve_(bottomCandidate, nextX, nextY);
                    if (!bottomSol) {
                        bottomCandidate.piece.used = false;
                    }
                }
            }
        }

        // expand to the right
        if (rightCandidates != null) {
            for (RotatedPiece rightCandidate : rightCandidates) {
                if (!rightCandidate.piece.used) {
                    rightCandidate.piece.used = true;
                    rightSol = solve_(rightCandidate, x, y + 1);
                    if (!rightSol) {
                        rightCandidate.piece.used = false;
                    }
                }
            }
        }

        return rightSol || bottomSol;
    }

    public static boolean solve(int n) {
        boolean sol = false;

        // solve for every piece as a start piece
        for (Piece piece : pieces) {
            RotatedPiece rot;

            // solve for every rotation of the start piece

            // solve for the normal initial piece
            piece.used = true;
            sol = solve_(RotatedPiece.rotate(piece, 0), 0, 0);
            if (sol) {
                return sol;
            }

            // solve for the 90 deg rotated initial piece CW
            piece.used = true;
            sol = solve_(RotatedPiece.rotate(piece, 1), 0, 0);
            if (sol) {
                return sol;
            }

            // solve for the 180 deg rotated initial piece CW
            piece.used = true;
            sol = solve_(RotatedPiece.rotate(piece, 1), 0, 0);
            if (sol) {
                return sol;
            }

            // solve for the 270 deg rotated initial piece CW
            piece.used = true;
            sol = solve_(RotatedPiece.rotate(piece, 1), 0, 0);
            if (sol) {
                return sol;
            }

            piece.used = false;
        }

        return sol;
    }

    public static void printBoard() {
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                RotatedPiece current = board[i][j];

                System.out.print(current.topLeft + " " + current.topRight);

                if (j < nCols - 1) {
                    System.out.print("  ");
                }
            }

            System.out.print("\n");

            for (int j = 0; j < nCols; j++) {
                RotatedPiece current = board[i][j];

                System.out.print(current.bottomLeft + " " + current.bottomRight);

                if (j < nCols - 1) {
                    System.out.print("  ");
                }
            }

            System.out.print("\n\n");
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

            n = Integer.parseInt(st.nextToken());
            nRows = Integer.parseInt(st.nextToken());
            nCols = Integer.parseInt(st.nextToken());

            board = new RotatedPiece[nRows][nCols];
            up = new HashMap<PieceSide, ArrayList<RotatedPiece>>();
            left = new HashMap<PieceSide, ArrayList<RotatedPiece>>();
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
