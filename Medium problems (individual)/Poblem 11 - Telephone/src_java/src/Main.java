import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    private int t;

    private void createEdge(ArrayList<ArrayList<Integer>> graph, int u, int v) {
        graph.get(u).add(v);
        graph.get(v).add(u);
    }

    private void solve_(ArrayList<ArrayList<Integer>> nNodes, int u, boolean[] visited, int[] dfs, int[] low, int parent, boolean[] articulationPoints) {
        int children = 0;
        visited[u] = true;
        dfs[u] = low[u] = ++t;

        for (Integer v : nNodes.get(u)) {
            if (!visited[v]) {
                children++;
                solve_(nNodes, v, visited, dfs, low, u, articulationPoints);

                low[u] = Math.min(low[u], low[v]);

                if (parent != -1 && low[v] >= dfs[u]) {
                    articulationPoints[u] = true;
                }
            } else if (v != parent) {
                low[u] = Math.min(low[u], dfs[v]);
            }
        }

        if (parent == -1 && children > 1) {
            articulationPoints[u] = true;
        }
    }

    private int solve(ArrayList<ArrayList<Integer>> graph, int nNodes) {
        boolean[] visited;
        int[] dfs;
        int[] low;
        boolean[] articulationPoints;
        int parent;
        int counter = 0;

        visited = new boolean[nNodes];
        dfs = new int[nNodes];
        low = new int[nNodes];
        articulationPoints = new boolean[nNodes];
        parent = -1;

        for (int i = 0; i < nNodes; i++)
            if (!visited[i]) {
                solve_(graph, i, visited, dfs, low, parent, articulationPoints);
            }

        for (int i = 0; i < nNodes; i++) {
            if (articulationPoints[i]) {
                counter++;
            }
        }

        return counter;
    }

    public Main() throws IOException {
        int nNodes;
        int id;
        int id2;
        String line;
        BufferedReader in;
        StringTokenizer st;
        ArrayList<ArrayList<Integer>> graph;

        in = new BufferedReader(new InputStreamReader(System.in));

        while (!(line = in.readLine()).equals("0")) {
            nNodes = Integer.parseInt(line);
            graph = new ArrayList<ArrayList<Integer>>(nNodes);

            for (int i = 0; i < nNodes; i++) {
                graph.add(new ArrayList<Integer>());
            }

            while (!(line = in.readLine()).equals("0")) {
                st = new StringTokenizer(line);
                id = Integer.parseInt(st.nextToken()) - 1;

                while (st.hasMoreTokens()) {
                    id2 = Integer.parseInt(st.nextToken()) - 1;
                    createEdge(graph, id, id2);
                }
            }

            System.out.println(solve(graph, nNodes));
        }
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }
}
