import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private int n;
    private boolean[][] graph;
    private int best;
    private int[] neighbours;

    private void solve_(int v, int size) {
        int upperBound;

        if (size > best) {
            best = size;
        }

        upperBound = 0;
        for (int i = v + 1; i < n; i++) {
            if (neighbours[i] == 0) {
                upperBound++;
            }
        }

        if (size + upperBound <= best) {
            return;
        }

        for (int i = v + 1; i < n; i++) {
            if (graph[i][v]) {
                neighbours[i]++;
            }
        }

        for (int i = v + 1; i < n; i++) {
            if (neighbours[i] == 0) {
                solve_(i, size + 1);
            }
        }

        for (int i = v + 1; i < n; i++) {
            if (graph[i][v]) {
                neighbours[i]--;
            }
        }
    }

    private void solve() {
        best = Integer.MIN_VALUE;
        neighbours = new int[n];
        for (int i = 0; i < n; i++) {
            solve_(i, 1);
        }
        System.out.println(best);
    }

    public Main() throws IOException {
        int m;
        int id1;
        int id2;
        String line;
        BufferedReader in;
        StringTokenizer st;

        in = new BufferedReader(new InputStreamReader(System.in));

        while ((line = in.readLine()) != null && line.length() != 0) {
            st = new StringTokenizer(line);
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            graph = new boolean[n][n];

            for (int i = 0; i < m; i++) {
                line = in.readLine();
                st = new StringTokenizer(line);
                id1 = Integer.parseInt(st.nextToken());
                id2 = Integer.parseInt(st.nextToken());
                graph[id1][id2] = true;
                graph[id2][id1] = true;
            }

            solve();
        }
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }
}
