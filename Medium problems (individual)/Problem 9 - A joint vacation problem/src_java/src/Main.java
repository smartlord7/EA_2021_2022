import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public class Node implements Comparable<Node> {
        private int id;
        private int distance;
        private HashMap<Node, Integer> neighbours;

        public Node() {
            neighbours = new HashMap<Node, Integer>();
        }

        public Node(int id) {
            this();
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public HashMap<Node, Integer> getNeighbours() {
            return neighbours;
        }

        public void setNeighbours(HashMap<Node, Integer> neighbours) {
            this.neighbours = neighbours;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            return getId() == node.getId();
        }

        @Override
        public int hashCode() {
            return getId();
        }

        public int compareTo(Node o) {
            return Integer.compare(this.distance, o.distance);
        }
    }

    private int getShortestPathDijkstra(List<Node> graph, Node source, Node target) {
        int totalDistCurrent;
        int totalDistNeighbour;
        int distanceNeighbour;
        Node closest;
        PriorityQueue<Node> pQueue;

        pQueue = new PriorityQueue<Node>();
        for (Node node : graph) {
            if (node.getId() != source.getId()) {
                node.setDistance(Integer.MAX_VALUE);
            }

            pQueue.add(node);
        }

        source.setDistance(0);

        while (!pQueue.isEmpty()) {
            closest = pQueue.poll();

            if (closest.equals(target)) {
                break;
            }

            totalDistCurrent = closest.getDistance();

            for (Node neighbour : closest.getNeighbours().keySet()) {
                totalDistNeighbour = neighbour.getDistance();
                distanceNeighbour = closest.getNeighbours().get(neighbour);

                if (totalDistNeighbour > totalDistCurrent + distanceNeighbour) {
                    neighbour.setDistance(totalDistCurrent + distanceNeighbour);
                    pQueue.remove(neighbour);
                    pQueue.add(neighbour);
                }
            }
        }

        return target.getDistance();
    }

    public Main() throws IOException {
        int i;
        int j;
        int nNodes;
        int target;
        int nodeId;
        int distance;
        Node node;
        Node node2;
        String line;
        BufferedReader in;
        StringTokenizer st;
        List<Node> graph;

        in = new BufferedReader(new InputStreamReader(System.in));

        while ((line = in.readLine()) != null && line.length() > 0) {
            st = new StringTokenizer(line);

            nNodes = Integer.parseInt(st.nextToken());
            target = Integer.parseInt(st.nextToken());
            graph = new ArrayList<Node>();

            i = 0;
            while (i < nNodes) {
                graph.add(new Node(i + 1));
                i++;
            }

            i = 0;
            while (i < nNodes) {
                line = in.readLine();
                st = new StringTokenizer(line);

                nodeId = Integer.parseInt(st.nextToken());
                node = graph.get(nodeId - 1);

                j = 0;
                while (j < nNodes) {
                    distance = Integer.parseInt(st.nextToken());

                    if (distance != -1) {
                        node2 = graph.get(j);
                        node.getNeighbours().put(node2, distance);
                    }

                    j++;
                }

                i++;
            }

            node = graph.get(0);
            node2 = graph.get(target - 1);

            System.out.println(getShortestPathDijkstra(graph, node, node2));

        }
    }


    public static void main(String[] args) throws IOException {
        new Main();
    }
}
