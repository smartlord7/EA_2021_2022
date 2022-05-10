import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private final boolean[] hasIncomingEdges;
    private final byte[] nodeCosts;
    private final short nNodes;
    private short source;
    private short sink;
    private final ArrayList<ArrayList<Short>> graph;

    private boolean hasGraphOneSourceAndSink() {
        boolean hasSource;
        boolean hasSink;
        short i;

        hasSource = false;
        hasSink = false;
        i = 0;

        while (i < nNodes) {
            if (!hasIncomingEdges[i]) {
                if (hasSource) {
                    return false;
                }

                source = i;
                hasSource = true;
            }

            if (graph.get(i).size() == 0) {
                if (hasSink) {
                    return false;
                }

                sink = i;
                hasSink = true;
            }

            i++;
        }

        return true;
    }

    private boolean isGraphConnected() {
        boolean[] visited;
        short next;
        int nVisited;
        Queue<Short> queue;

        visited = new boolean[nNodes];
        queue = new LinkedList<Short>();

        nVisited = 1;
        visited[source] = true;
        queue.add(source);

        while (!queue.isEmpty()) {
            next = queue.poll();

            for (Short neighbour : graph.get(next)) {
                if (!visited[neighbour]) {
                    visited[neighbour] = true;
                    queue.add(neighbour);
                    nVisited++;
                }
            }
        }

        return nVisited == nNodes;
    }

    private boolean hasGraphCycle_(short node, boolean[] visited, boolean[] recStack) {
        List<Short> neighbours;

        if (recStack[node]) {
            return true;
        }

        if (visited[node]) {
            return false;
        }

        visited[node] = true;
        recStack[node] = true;
        neighbours = graph.get(node);

        for (Short c: neighbours) {
            if (hasGraphCycle_(c, visited, recStack)) {
                return true;
            }
        }

        recStack[node] = false;

        return false;
    }

    private boolean hasGraphCycle() {
        boolean[] visited;
        boolean[] recStack;
        short i;

        visited = new boolean[nNodes];
        recStack = new boolean[nNodes];
        i = 0;

        while (i < nNodes) {
            if (hasGraphCycle_(i, visited, recStack)) {
                return true;
            }

            i++;
        }

        return false;
    }

    private void topologicalSort() {

    }

    private void getParallelizableNodes() {

    }

    private void getParallelizationBottlenecks() {

    }

    public Main() throws IOException {
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
                graph.get(id).add(i);
                hasIncomingEdges[i] = true;

                j++;
            }

            i++;
        }

        line = in.readLine();

        if (hasGraphOneSourceAndSink() && isGraphConnected() && !hasGraphCycle()) {
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
