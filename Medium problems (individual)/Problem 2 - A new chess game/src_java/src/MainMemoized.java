import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MainMemoized {

    public static boolean board[][] = new boolean[401][401];

    public static int solve(int x, int y, int plays) {
        if (x < -200 || y < -200 || x > 200 || y > 200) {
            return 0;
        }

        int counter = 0;

        if (!board[x + 200][y + 200]) {
            board[x + 200][y + 200] = true;
            counter = 1;
        }

        if (plays > 0) {
            counter += + solve(x + 1, y + 2, plays - 1) +
                    solve(x + 2, y + 1, plays - 1) +
                    solve(x + 2, y - 1, plays - 1) +
                    solve(x + 1, y - 2, plays - 1) +
                    solve(x - 1, y - 2, plays - 1) +
                    solve(x - 2, y - 1, plays - 1) +
                    solve(x - 2, y + 1, plays - 1) +
                    solve(x - 1, y + 2, plays - 1);
        }

        return counter;
    }

    public static void main(String[] args) throws IOException {
        int numHorses;
        int counter = 0;
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        line = in.readLine();
        numHorses = Integer.parseInt(line);

        while (numHorses > 0) {
            line = in.readLine();
            st = new StringTokenizer(line);

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int plays = Integer.parseInt(st.nextToken());
            counter += solve(x, y, plays);

            numHorses--;
        }

        System.out.println(counter);
    }
}
