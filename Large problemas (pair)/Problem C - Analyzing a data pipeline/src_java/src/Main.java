import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private boolean[] hasIncomingEdges;
    private byte[] nodeCosts;
    private short nNodes;
    private short source;
    private short sink;
    private short nVisited;
    private ArrayList<ArrayList<Short>> graph;
    private ArrayList<ArrayList<Short>> reverseGraph;

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

    private boolean hasGraphCycles(short node, boolean[] visited, boolean[] recStack) {
        List<Short> neighbours;

        if (recStack[node]) {
            return true;
        }

        if (visited[node]) {
            return false;
        }

        visited[node] = true;
        nVisited++;
        recStack[node] = true;
        neighbours = graph.get(node);

        for (Short c: neighbours) {
            if (hasGraphCycles(c, visited, recStack)) {
                return true;
            }
        }

        recStack[node] = false;

        return false;
    }

    private boolean isGraphConnectedAndHasNoCycles() {
        boolean[] visited;
        boolean[] recStack;

        visited = new boolean[nNodes];
        recStack = new boolean[nNodes];

        return !hasGraphCycles(source, visited, recStack) && nVisited == nNodes;
    }

    private LinkedList<Short> getSerialExecutionCost(boolean print) {
        short i;
        short current;
        short[] inDegrees;
        int totalCost;
        StringBuilder sb;
        TreeSet<Short> multiset;
        LinkedList<Short> l;

        inDegrees = new short[nNodes];
        totalCost = 0;
        sb = new StringBuilder();
        multiset = new TreeSet<Short>((j, k) -> (j < k) ? -1 : 1);
        l = new LinkedList<Short>();

        i = 0;
        while (i < nNodes) {
            for (Short neighbour : graph.get(i)) {
                inDegrees[neighbour]++;
            }

            i++;
        }

        multiset.add(source);

        while (!multiset.isEmpty()) {
            current = multiset.pollFirst();
            l.add(current);

            for (Short neighbour : graph.get(current)) {
                inDegrees[neighbour]--;

                if (inDegrees[neighbour] == 0) {
                    multiset.add(neighbour);
                }
            }
        }

        if (print) {
            i = 0;
            while (i < l.size()) {
                totalCost += nodeCosts[i];
                sb.append(l.get(i) + 1);

                if (i != l.size() - 1) {
                    sb.append("\n");
                }

                i++;
            }

            System.out.println(totalCost + "\n" + sb);

            return null;
        }

        return l;
    }

    private void getParallelExecutionCost_(short node, int[] dp, boolean[] visited) {
        visited[node] = true;

        for (Short neighbour : graph.get(node)) {
            if (!visited[neighbour]) {
                getParallelExecutionCost_(neighbour, dp, visited);
            }

            dp[node] = Math.max(dp[node], nodeCosts[node] + dp[neighbour]);
        }
    }

    private void getParallelExecutionCost() {
        boolean[] visited;
        short i;
        int minCost;
        int[] memo;

        minCost = 0;
        memo = new int[nNodes];
        visited = new boolean[nNodes];

        memo[sink] = nodeCosts[sink];

        i = 0;
        while (i < nNodes) {
            getParallelExecutionCost_(i, memo, visited);

            i++;
        }

        i = 0;
        while (i < nNodes) {
            minCost = Math.max(minCost, memo[i]);

            i++;
        }

        System.out.println(minCost);
    }

    private void getParallelizationBottlenecks_(ArrayList<ArrayList<Short>> graph, short node, short end, boolean[][] reachable) {
        reachable[node][node] = true;

        if (node == end) {
            return;
        }

        for (Short neighbour : graph.get(node)) {
            getParallelizationBottlenecks_(graph, neighbour, end, reachable);

            for (int i = 0; i < nNodes; i++) {
               if (reachable[neighbour][i]) {
                   reachable[node][i] = true;
               }
            }
        }
    }

    private void getParallelizationBottlenecks() {
        boolean[] bottlenecks;
        boolean[][] reachable;
        boolean[][] reachable2;
        LinkedList<Short> orderedNodes;

        reachable = new boolean[nNodes][nNodes];
        reachable2 = new boolean[nNodes][nNodes];
        orderedNodes = getSerialExecutionCost(false);
        bottlenecks = new boolean[nNodes];

        getParallelizationBottlenecks_(graph, source, sink, reachable);
        getParallelizationBottlenecks_(reverseGraph, sink, source, reachable2);

        for (short i = 0; i < nNodes; i++) {
            int count = 0;

            for (short j = 0; j < nNodes; j++) {
                reachable[i][j] = reachable[i][j] || reachable2[i][j];

                if (reachable[i][j]) {
                    count++;
                }

                if (count == nNodes) {
                    bottlenecks[i] = true;
                }
            }
        }

        for (Short node : orderedNodes) {
            if (bottlenecks[node]) {
                System.out.println(node + 1);
            }
        }
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
        while ((line = in.readLine()) != null && line.length() > 0) {
            nVisited = 0;
            nNodes = Short.parseShort(line);
            hasIncomingEdges = new boolean[nNodes];
            nodeCosts = new byte[nNodes];
            graph = new ArrayList<ArrayList<Short>>(nNodes);
            reverseGraph = new ArrayList<ArrayList<Short>>(nNodes);

            i = 0;
            while (i < nNodes) {
                graph.add(new ArrayList<Short>());
                reverseGraph.add(new ArrayList<Short>());

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
                    reverseGraph.get(i).add(id);
                    hasIncomingEdges[i] = true;

                    j++;
                }

                i++;
            }

            line = in.readLine();

            if (hasGraphOneSourceAndSink() && isGraphConnectedAndHasNoCycles()) {
                switch (line) {
                    case "0":
                        System.out.println("VALID");
                        break;
                    case "1":
                        getSerialExecutionCost(true);
                        break;
                    case "2":
                        getParallelExecutionCost();
                        break;
                    case "3":
                        getParallelizationBottlenecks();
                        break;
                }

            } else {
                System.out.println("INVALID");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }
}
