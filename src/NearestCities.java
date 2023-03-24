import java.util.*;

public class NearestCities {

    public static void dijkstra(List<List<Node>> adj, int src, int V) {
        long dist[] = new long[V];
        Set<Integer> visited = new HashSet<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(new Node());
        for (int i = 0; i < V; i++)
            dist[i] = Long.MAX_VALUE;
        queue.add(new Node(src, 0));
        dist[src] = 0;

        while (visited.size() != V && !queue.isEmpty()) {
            int u = queue.remove().city;
            if (visited.contains(u)) continue;
            visited.add(u);

            //relax for each vertex v in adj[u]
            long edgeDistance = -1;
            long newDistance = -1;
            for (int i = 0; i < adj.get(u).size(); i++) {
                Node v = adj.get(u).get(i);
                if (!visited.contains(v.city)) {
                    edgeDistance = v.distance;
                    newDistance = dist[u] + edgeDistance;
                    if (newDistance < dist[v.city])
                        dist[v.city] = newDistance;
                    queue.add(new Node(v.city, dist[v.city]));
                }
            }
        }
        long maxdist=0;
        int cnt=0;
        for(int j = 0; j < dist.length; j++) {
            if (dist[j] < V * 10){
                if (dist[j]>maxdist){
                    cnt=1;
                    maxdist=dist[j];
                }else if (dist[j]==maxdist) {
                    cnt++;
                }
            }

        } System.out.println("("+src + ", " + maxdist + ", "
                + cnt+")");

    }

    public static void main(String arg[]) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int Q = scanner.nextInt();

        List<List<Node>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Node> item = new ArrayList<>();
            adj.add(item);
        }
        for (int i = 0; i < m; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int l = scanner.nextInt();
            adj.get(u).add(new Node(v, l));
            adj.get(v).add(new Node(u, l));
        }
        for (int i = 0; i < Q; i++) {
            int source = scanner.nextInt();
            dijkstra(adj, source, n);
        }
    }
}

class Node implements Comparator<Node> {

    public int city;
    public long distance;

    public Node() {
    }

    public Node(int city, long distance) {
        this.city = city;
        this.distance = distance;
    }

    @Override
    public int compare(Node node1, Node node2) {
        if (node1.distance < node2.distance)
            return -1;
        if (node1.distance > node2.distance)
            return 1;
        return 0;
    }
}
