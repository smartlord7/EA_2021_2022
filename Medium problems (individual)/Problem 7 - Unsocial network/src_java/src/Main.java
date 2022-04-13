import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private int bestSol = -1;

    private void getMaximumIndependentSet(int currNode, int size, int nNodes, int[] count, boolean[][] adjacencyMatrix) {
        int ub;

        if (size > bestSol) {
            bestSol = size;
        }

        ub = 0;
        for (int i = currNode + 1; i < nNodes; i++) {
            if (count[i] == 0) {
                ub++;
            }
        }

        if (size + ub <= bestSol) {
            return;
        }

        for (int i = currNode + 1; i < nNodes; i++) {
            if (adjacencyMatrix[i][currNode]) {
                count[i]++;
            }
        }

        for (int i = currNode + 1; i < nNodes; i++) {
            if (count[i] == 0) {
                getMaximumIndependentSet(i, size + 1, nNodes, count, adjacencyMatrix);
            }
        }

        for (int i = currNode + 1; i < nNodes; i++) {
            if (adjacencyMatrix[i][currNode]) {
                count[i]--;
            }
        }
    }

    public Main() throws IOException {
        int nNodes;
        int nConnections;
        int node1;
        int node2;
        boolean[][] adjMatrix;
        int[] count;
        String line;
        BufferedReader in;
        StringTokenizer st;

        in = new BufferedReader(new InputStreamReader(System.in));

        while ((line = in.readLine()) != null && line.length() != 0) {
            st = new StringTokenizer(line);
            nNodes = Integer.parseInt(st.nextToken());
            nConnections = Integer.parseInt(st.nextToken());
            adjMatrix = new boolean[nNodes][nNodes];
            count = new int[nNodes];

            for (int i = 0; i < nConnections; i++) {
                line = in.readLine();
                st = new StringTokenizer(line);
                node1 = Integer.parseInt(st.nextToken());
                node2 = Integer.parseInt(st.nextToken());
                adjMatrix[node1][node2] = true;
                adjMatrix[node2][node1] = true;
            }

            for (int i = 0; i < nNodes; i++) {
                getMaximumIndependentSet(i, 1, nNodes, count, adjMatrix);
            }

            System.out.println(bestSol);
        }
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }
}
