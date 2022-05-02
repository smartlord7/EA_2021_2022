import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static class Graph {
        public static class Node {
            private int x;
            private int y;

            public Node() {
            }

            public Node(int x, int y) {
                this.x = x;
                this.y = y;
            }

            public double getDistance(Node other) {
                return (double) Math.sqrt((x - other.x * (x - other.x) + (x - other.y) * (x - other.y)));
            }
        }

        public static class Edge implements Comparable<Edge> {
            private int src;
            private int dest;
            private double weight;

            public int compareTo(Edge compareEdge) {
                return (int) (this.weight - compareEdge.weight);
            }
        }

        public static class Subset {
            int parent, rank;
        }

        private final int V;
        private final Edge[] edge;

        public Graph(int v, int e) {
            V = v;
            edge = new Edge[e];

            for (int i = 0; i < e; i++) {
                edge[i] = new Edge();
            }
        }

        public int find(Subset[] subsets, int i) {
            if (subsets[i].parent != i) {
                subsets[i].parent = find(subsets, subsets[i].parent);
            }

            return subsets[i].parent;
        }

        public void union(Subset[] subsets, int x, int y) {
            int xRoot = find(subsets, x);
            int yRoot = find(subsets, y);

            if (subsets[xRoot].rank < subsets[yRoot].rank) {
                subsets[xRoot].parent = yRoot;
            } else if (subsets[xRoot].rank > subsets[yRoot].rank) {
                subsets[yRoot].parent = xRoot;
            } else {
                subsets[yRoot].parent = xRoot;
                subsets[xRoot].rank++;
            }
        }

        public void getMinimumSpanningTree() {
            Edge[] result = new Edge[V];

            int e = 0;

            int i;
            for (i = 0; i < V; ++i) {
                result[i] = new Edge();
            }

            Arrays.sort(edge);

            Subset[] subsets = new Subset[V];
            for (i = 0; i < V; ++i) {
                subsets[i] = new Subset();
            }

            for (int v = 0; v < V; ++v) {
                subsets[v].parent = v;
                subsets[v].rank = 0;
            }

            i = 0;

            while (e < V - 1) {
                Edge next_edge = edge[i++];

                int x = find(subsets, next_edge.src);
                int y = find(subsets, next_edge.dest);

                if (x != y) {
                    result[e++] = next_edge;
                    union(subsets, x, y);
                }
            }

            System.out.println("Following are the edges in "
                    + "the constructed MST");
            int minimumCost = 0;
            for (i = 0; i < e; ++i) {
                System.out.println(result[i].src + " -- "
                        + result[i].dest
                        + " == " + result[i].weight);
                minimumCost += result[i].weight;
            }
            System.out.println("Minimum Cost Spanning Tree "
                    + minimumCost);
        }
    }

    public Main() throws IOException {
        int nNodes;
        int i;
        int x;
        int y;
        int nConnections;
        int idNode1;
        int idNode2;
        double distance;
        Graph.Node node1;
        Graph.Node node2;
        Graph.Node[] nodes;
        Graph graph;
        String line;
        BufferedReader in;
        StringTokenizer st;

        in = new BufferedReader(new InputStreamReader(System.in));

        while ((line = in.readLine()) != null && line.length() > 0) {
            nNodes = Integer.parseInt(line);
            nodes = new Graph.Node[nNodes];

            i = 0;
            while (i < nNodes) {
                line = in.readLine();
                st = new StringTokenizer(line);

                x = Integer.parseInt(st.nextToken());
                y = Integer.parseInt(st.nextToken());
                nodes[i] = new Graph.Node(x, y);

                i++;
            }

            line = in.readLine();
            nConnections = Integer.parseInt(line);
            graph = new Graph(nNodes, nConnections);

            i = 0;
            while (i < nConnections) {
                line = in.readLine();
                st = new StringTokenizer(line);

                idNode1 = Integer.parseInt(st.nextToken()) - 1;
                idNode2 = Integer.parseInt(st.nextToken()) - 1;
                node1 = nodes[idNode1];
                node2 = nodes[idNode2];
                distance = node1.getDistance(node2);

                graph.edge[i].src = idNode1;
                graph.edge[i].dest = idNode2;
                graph.edge[i].weight = distance;

                i++;
            }

            graph.getMinimumSpanningTree();
        }
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }
}
