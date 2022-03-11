import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int best = Integer.MAX_VALUE;
    static boolean[] visited = new boolean[12];
    static int[] nConnections = new int[12];
    static int[][] costMatrix;

    public static void solve(int n, int k, int v, int c) {
        if (c >= best) {
            return;
        }

        if (v == n) {
            best = c;
            return;
        }

        for (int i = 0; i < n; i++) {
            if (visited[i] && nConnections[i] < k) {
                for (int j = 0; j < n; j++) {
                    if (!visited[j] && costMatrix[i][j] > 0) {
                        nConnections[i]++;
                        nConnections[j]++;
                        visited[j] = true;
                        solve(n, k,v + 1,c + costMatrix[i][j]);
                        visited[j] = false;
                        nConnections[j]--;
                        nConnections[i]--;
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int n, m, k;
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        while ((line = in.readLine()) != null && line.length() > 0) {
            st = new StringTokenizer(line);
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            k = Integer.parseInt(st.nextToken());

            costMatrix = new int[n][n];

            for (int i = 0; i < m; i++) {
                int id1, id2, cost;
                line = in.readLine();
                st = new StringTokenizer(line);

                id1 = Integer.parseInt(st.nextToken());
                id2 = Integer.parseInt(st.nextToken());
                cost = Integer.parseInt(st.nextToken());

                costMatrix[id1 - 1][id2 - 1] = cost;
                costMatrix[id2 - 1][id1 - 1] = cost;
            }

            visited[0] = true;
            best = Integer.MAX_VALUE;
            solve(n, k, 1, 0);

            if (best == Integer.MAX_VALUE) {
                System.out.println("NO WAY!");
            } else {
                System.out.println(best);
            }
        }
    }
}
