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

        private static void preProcess_(PieceSide side, Piece piece, HashMap<PieceSide, ArrayList<Piece>> map) {
            if (!map.containsKey(side)) {
                ArrayList<Piece> list = new  ArrayList<Piece>();
                list.add(piece);
                map.put(side, list);
            } else {
                map.get(side).add(piece);
            }
        }

        private static void preProcess2_(PieceSides sides, Piece piece, HashMap<PieceSides, ArrayList<Piece>> map) {
            if (!map.containsKey(sides)) {
                ArrayList<Piece> list = new  ArrayList<Piece>();
                list.add(piece);
                map.put(sides, list);
            } else {
                map.get(sides).add(piece);
            }
        }

        public static void preProcess(Piece piece) {
            pieces.add(piece);

            PieceSide p1, p2;
            p1 = new PieceSide(piece.topLeft, piece.topRight);
            p2 = new PieceSide(piece.bottomLeft, piece.topLeft);

            preProcess_(p1, piece, up);
            preProcess_(p2, piece, left);
            preProcess2_(new PieceSides(p1, p2), piece, upLeft);

            p1 = new PieceSide(piece.bottomLeft, piece.topLeft);
            p2 = new PieceSide(piece.bottomRight, piece.bottomLeft);

            preProcess_(p1, piece, up);
            preProcess_(p2, piece, left);
            preProcess2_(new PieceSides(p1, p2), piece, upLeft);

            p1 = new PieceSide(piece.bottomRight, piece.bottomLeft);
            p2 = new PieceSide(piece.topRight, piece.bottomRight);

            preProcess_(p1, piece, up);
            preProcess_(p2, piece, left);
            preProcess2_(new PieceSides(p1, p2), piece, upLeft);

            p1 = new PieceSide(piece.topRight, piece.bottomRight);
            p2 = new PieceSide(piece.topLeft, piece.topRight);

            preProcess_(p1, piece, up);
            preProcess_(p2, piece, left);
            preProcess2_(new PieceSides(p1, p2), piece, upLeft);
        }
    }

    static Piece[][] board;
    static HashMap<PieceSide, ArrayList<Piece>> up;
    static HashMap<PieceSide, ArrayList<Piece>> left;
    static HashMap<PieceSides, ArrayList<Piece>> upLeft;
    static ArrayList<Piece> pieces;
    static int nRows;
    static int nCols;
    static int n;

    public static boolean solve_(Piece current, int x, int y, int n) {
        if (n == 0) {
            // no pieces left to use, meaning they were all spent and so there is a solution
            return true;
        }

        // the search trespasses the grid limits
        if (y == nCols - 1 || x > nRows - 1) {
            return false;
        }

        board[x][y] = current;

        // get the right and bottom side of the current piece
        PieceSide rightSide = new PieceSide(current.topRight, current.bottomRight);
        PieceSide bottomSide = new PieceSide(current.bottomRight, current.bottomLeft);
        boolean rightSol = false;
        boolean bottomSol = false;

        ArrayList<Piece> rightCandidates;
        ArrayList<Piece> bottomCandidates;

        if (x > 0 && board[x - 1][y + 1] != null) {
            Piece left = board[x - 1][y + 1];
            PieceSide leftPieceRightSide = new PieceSide(left.topRight, left.bottomRight);
            bottomCandidates = upLeft.get(new PieceSides(bottomSide, leftPieceRightSide));
        } else {
            bottomCandidates = up.get(bottomSide);
        }

        if (y > 0 && board[x][y - 1] != null) {
            Piece up = board[x][y - 1];
            PieceSide upPieceBottomSide = new PieceSide(up.bottomRight, up.bottomLeft);
            rightCandidates = upLeft.get(new PieceSides(upPieceBottomSide, rightSide));
        } else {
            rightCandidates = left.get(rightSide);
        }

        // expand to the right
        if (rightCandidates != null) {
            for (Piece rightCandidate : rightCandidates) {
                if (!rightCandidate.used) {

                    rightCandidate.used = true;
                    rightCandidate.fixed = true;
                    rightSol = solve_(rightCandidate, x + 1, y, n - 1);
                    rightCandidate.fixed = false;
                    rightCandidate.used = false;
                }
            }
        }

        //expand to the bottom
        if (bottomCandidates != null) {
            for (Piece bottomCandidate : bottomCandidates) {
                if (!bottomCandidate.used) {
                    bottomCandidate.used = true;
                    bottomCandidate.fixed = false;
                    bottomSol = solve_(bottomCandidate, x, y + 1, n - 1);
                    bottomCandidate.fixed = false;
                    bottomCandidate.used = false;
                }
            }
        }

        return rightSol || bottomSol;
    }

    public static boolean solve(int n) {
        boolean sol = false;

        // solve for every piece as a start piece
        for (Piece piece : pieces) {
            Piece rot;

            // solve for every rotation of the start piece

            // solve for the normal initial piece
            piece.used = true;
            sol = solve_(piece, 0, 0, n - 1);
            if (sol) {
                return sol;
            }

            // solve for the 90 deg rotated initial piece CW
            rot = new Piece("4", "1", "2", "3");
            piece.used = true;
            sol = solve_(rot, 0, 0, n - 1);
            if (sol) {
                return sol;
            }

            // solve for the 180 deg rotated initial piece CW
            piece.used = true;
            rot = new Piece("3", "4", "1", "2");
            sol = solve_(rot, 0, 0, n - 1);
            if (sol) {
                return sol;
            }

            // solve for the 270 deg rotated initial piece CW
            piece.used = true;
            rot = new Piece("2", "3", "4", "1");
            sol = solve_(rot, 0, 0, n - 1);
            if (sol) {
                return sol;
            }

            piece.used = false;
        }

        return sol;
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

            board = new Piece[nRows][nCols];
            up = new HashMap<PieceSide, ArrayList<Piece>>();
            left = new HashMap<PieceSide, ArrayList<Piece>>();
            upLeft = new HashMap<PieceSides, ArrayList<Piece>>();
            pieces = new ArrayList<Piece>();

            for (int j = 0; j < n; j++) {
                line = in.readLine();
                st = new StringTokenizer(line);

                String n1 = st.nextToken();
                String n2 = st.nextToken();
                String n3 = st.nextToken();
                String n4 = st.nextToken();

                Piece p = new Piece(n1, n2, n3, n4);
                Piece.preProcess(p);
            }

            boolean hasSol = solve(n);

            if (hasSol) {
                System.out.println("possible puzzle!");
            } else {
                System.out.println("impossible puzzle!");
            }
        }
    }
}
