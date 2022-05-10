import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    private boolean[] hasIncomingEdges;
    private byte[] nodeCosts;
    private ArrayList<ArrayList<Short>> graph;

    private boolean hasGraphCycles() {
        return false;
    }

    private boolean hasGraphOneSourceAndSink() {
        return false;
    }

    private boolean isGraphConnected() {
        return false;
    }

    private void topologicalSort() {

    }

    private void getParallelizableNodes() {

    }

    private void getParallelizationBottlenecks() {

    }

    public Main() throws IOException {
        short nNodes;
        byte cost;
        short id;
        short i;
        short j;
        short nDependencies;
        String line;
        BufferedReader in;
        StringTokenizer st;

        in = new BufferedReader(new InputStreamReader(System.in));
        line = in.readLine();

        nNodes = Short.parseShort(line);
        hasIncomingEdges = new boolean[nNodes];
        nodeCosts = new byte[nNodes];
        graph = new ArrayList<ArrayList<Short>>(nNodes);

        i = 0;
        while (i < nNodes) {
            graph.add(new ArrayList<Short>());

            i++;
        }

        i = 0;
        while (i < nNodes) {
            line = in.readLine();
            st = new StringTokenizer(line);
            cost = Byte.parseByte(st.nextToken());
            nDependencies = Short.parseShort(st.nextToken());
            nodeCosts[i] = cost;

            j = 0;
            while (j < nDependencies) {
                id = (short) (Short.parseShort(st.nextToken()) - 1);
                graph.get(i).add(id);
                hasIncomingEdges[id] = true;

                j++;
            }

            i++;
        }

        line = in.readLine();

        if (hasGraphCycles() && hasGraphOneSourceAndSink() && isGraphConnected()) {
            switch (line) {
                case "0":
                    System.out.println("VALID");
                    break;
                case "1":
                    topologicalSort();
                    break;
                case "2":
                    getParallelizableNodes();
                    break;
                case "3":
                    getParallelizationBottlenecks();
                    break;
            }

        } else {
            System.out.println("INVALID");
        }
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }
}
