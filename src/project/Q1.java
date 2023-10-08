package project;

import java.util.*;

public class Q1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int s = scanner.nextInt()-1;
        int t = scanner.nextInt()-1;
        int k = scanner.nextInt();


        List<List<Node>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Node> item = new ArrayList<>();
            adj.add(item);
        }
        for (int i = 0; i < m; i++) {
            int u = scanner.nextInt()-1;
            int v = scanner.nextInt()-1;
            int l = scanner.nextInt();
            adj.get(u).add(new Node(v, l));
            adj.get(v).add(new Node(u, l));
        }
        ArrayList<Integer> gaurds = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            gaurds.add(scanner.nextInt()-1);
        }

        dijkstra(adj, t, n, gaurds, s);


    }


    public static void dijkstra(List<List<Node>> adj, int src, int V, ArrayList<Integer> gaurds, int s) {
        long dist[] = new long[V];
        Set<Integer> visited = new HashSet<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(new Node());
        for (int i = 0; i < V; i++)
            dist[i] = Long.MAX_VALUE/10;
        queue.add(new Node(src, 0));
        dist[src] = 0;


        while (visited.size() != V) {
            if (queue.isEmpty()) break;


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


        long min = Long.MAX_VALUE;
        for (int i = 0; i < gaurds.size(); i++) {
            if (dist[gaurds.get(i)] < min) min = dist[gaurds.get(i)];
        }
        if (min > dist[s]) {
            System.out.println(dist[s]);
        } else {
            System.out.println("impossible");

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
