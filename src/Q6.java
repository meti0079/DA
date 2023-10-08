import java.util.*;

public class Q6 {
    static class Graph{

         HashMap<Integer, ArrayList<Integer>> edges;
         boolean visited[]= new boolean[5001];
          int match[]= new int[5001];
        public Graph(){
            edges= new HashMap<>();
            Arrays.fill(match, -1);
        }
        public void  addEdge(int p, int c){
            if (edges.containsKey(p))
                    edges.get(p).add(c);
            else {
                edges.put(p, new ArrayList<>());
                edges.get(p).add(c);
            }

        }
          boolean dfs(int x){
            if (edges.get(x)==null) return false;
            for (int i : edges.get(x) ) {
                int y = i;
                if (!visited[y]){
                    visited[y]=true;
                    if (match[y]==-1 || dfs(match[y])){
                        match[y]=x;
                        return  true;
                    }
                }

            }
            return  false;
        }

    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[] p = new int[n];
        int[] c = new int[n];
        for (int i = 0; i < n; i++) {
            p[i] = scanner.nextInt();
        }
        for (int i = 0; i < n; i++) {
            c[i] = scanner.nextInt();
        }
        boolean[] isRemoved= new boolean[n];
        int q = scanner.nextInt();
        ArrayList<Integer> queries= new ArrayList<>();
        for (int i = 0; i < q; i++) {
            int x= scanner.nextInt();
            isRemoved[x-1]=true;
            queries.add(x-1);
        }
        Graph graph= new Graph();
        for (int i = 0; i < n; i++) {
            if (!isRemoved[i]){
                graph.addEdge(p[i],c[i]);
            }
        }
        ArrayList<Integer> ans= new ArrayList<>();
        int i=0;
        for (int j = q-1; j >=0 ; j--) {
            while (i<5001){
                Arrays.fill(graph.visited, false);
                if (graph.dfs(i)) {
                    i++;
                } else {
                    ans.add(i);
                    break;
                }
            }
            graph.addEdge(p[queries.get(j)],c[queries.get(j)]);
        }
        for (int j = 0; j < q; j++) {
            System.out.println(ans.get(q-1-j));
        }
    }


}
