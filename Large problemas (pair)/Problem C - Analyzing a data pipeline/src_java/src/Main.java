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

        for (short c: neighbours) {
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

    private void getSerialExecution(boolean detectBottlenecks) {
        short i;
        short current;
        short[] inDegrees;
        int totalCost;
        StringBuilder sb;
        PriorityQueue<Short> pQueue;
        LinkedList<Short> l;

        inDegrees = new short[nNodes];
        totalCost = 0;
        sb = new StringBuilder();
        pQueue = new PriorityQueue<Short>((j, k) -> (j < k) ? -1 : 1);
        l = new LinkedList<Short>();

        i = 0;
        while (i < nNodes) {
            for (short neighbour : graph.get(i)) {
                inDegrees[neighbour]++;
            }

            i++;
        }

        pQueue.add(source);

        while (!pQueue.isEmpty()) {
            current = pQueue.poll();

            if (!detectBottlenecks || (source == current || sink == current || isBottleneck(current))) {
                l.add(current);
            }
            for (short neighbour : graph.get(current)) {
                inDegrees[neighbour]--;

                if (inDegrees[neighbour] == 0) {
                    pQueue.add(neighbour);
                }
            }
        }

        i = 0;
        while (i < l.size()) {
            if (!detectBottlenecks) {
                totalCost += nodeCosts[i];
                sb.append(l.get(i) + 1);

                if (i != l.size() - 1) {
                    sb.append("\n");
                }
            } else {
                System.out.println(l.get(i) + 1);
            }

            i++;
        }


        if (!detectBottlenecks) {
            System.out.println(totalCost + "\n" + sb);
        }
    }

    private void getParallelExecutionCost_(short node, int[] dp, boolean[] visited) {
        visited[node] = true;

        for (short neighbour : graph.get(node)) {
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

    private short isBottleneck_(ArrayList<ArrayList<Short>> graph, short node, boolean[] visited) {
        short count;
        visited[node] = true;

        count = 1;

        for (short neighbour : graph.get(node)) {
            if (!visited[neighbour]) {
                count += isBottleneck_(graph, neighbour, visited);
            }
        }

        return count;
    }

    private boolean isBottleneck(short node) {
        boolean[] visited;
        int totalNodes;

        visited = new boolean[nNodes];

        totalNodes = isBottleneck_(graph, node, visited) +
                isBottleneck_(reverseGraph, node, visited) - 1;

        return totalNodes == nNodes;
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
                        getSerialExecution(false);
                        break;
                    case "2":
                        getParallelExecutionCost();
                        break;
                    case "3":
                        getSerialExecution(true);
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
