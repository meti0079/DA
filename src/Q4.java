
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Q4 {



    public static void main(String[] args) throws IOException {
        Reader scanner = new Reader();
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        DisjointSet mst = new DisjointSet(n);
        DisjointSet wSet = new DisjointSet(n);
        ArrayList<Edge> edges = readEdge(scanner,m);
        ArrayList<Edge> sortEdges = (ArrayList<Edge>) edges.clone();
        Collections.sort(sortEdges);
        int nWeightes =find_nWeight(m,sortEdges);

        int queries = scanner.nextInt();
        String []pos = new String[queries];
        for (int i = 0; i < queries; i++) {
            pos[i]= "YES";
        }

        ArrayList<ArrayList<Subquery>> subqueries = new ArrayList<>();

        for (int i = 0; i < nWeightes; i++) {
            subqueries.add( new ArrayList<>());
        }


        readQueries(scanner,queries,nWeightes,edges,subqueries) ;
        ans(nWeightes, m, mst, wSet, pos, subqueries, sortEdges);
        for (String x:pos
             ) {
            System.out.println(x);
        }
    }
    static  int find_nWeight(int m, ArrayList<Edge> sortEdges){
        int asdW = -1;
        int orgW = -1;
        for (int i = 0; i < m; i++) {
            if (sortEdges.get(i).w != orgW) {
                orgW = sortEdges.get(i).w;
                sortEdges.get(i).w = ++asdW;
            } else {
                sortEdges.get(i).w = asdW;
            }
        }
        return asdW + 1;
    }
    static ArrayList<Edge>  readEdge(Reader scanner, int m) throws IOException {
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            edges.add(new Edge(u - 1, v - 1, w, i));
        }
        return edges;
    }
    static void readQueries(Reader scanner, int queries, int nWeights, ArrayList<Edge>edges , ArrayList<ArrayList<Subquery>> subqueries) throws IOException {
        ArrayList<Edge>[] edgeByWeight = new ArrayList[nWeights];
        for (int i = 0; i < queries; i++) {
            ArrayList<Integer> weights = new ArrayList<>();
            int q = scanner.nextInt();
            for (int j = 0; j < q; j++) {
                int ed=scanner.nextInt() - 1;
                Edge wE = edges.get(ed);
                if (edgeByWeight[wE.w] == null) {
                    edgeByWeight[wE.w] = new ArrayList<>();
                    weights.add(wE.w);
                }
                edgeByWeight[wE.w].add(wE);
            }
            updateQueries(i,weights,edgeByWeight,subqueries);
        }
    }
    static void updateQueries(int i ,ArrayList<Integer> weights,ArrayList<Edge>[] edgeByWeight, ArrayList<ArrayList<Subquery>>subqueries ){
        for (int ww : weights) {
            subqueries.get(ww).add(new Subquery(edgeByWeight[ww], i));
            edgeByWeight[ww] = null;
        }
    }

    static void ans(int nWeightes, int m, DisjointSet mst, DisjointSet wSet, String[] pos,ArrayList<ArrayList<Subquery>> subqueries, ArrayList<Edge> sortEdges) {
        int nEI = 0;

        for (int ww=0;ww < nWeightes;ww++) {
            loop:
            for (Subquery wSquery : subqueries.get(ww)) {
                ArrayList<Integer> mod = new ArrayList<>();
                for (Edge wE : wSquery.edges) {
                    int r1 = mst.fRoot(wE.u);
                    int r2 = mst.fRoot(wE.v);
                    mod.add(r1);
                    mod.add(r2);
                    if (r1 == r2) {
                        pos[wSquery.q]="NO";
                        wSet.reset(mod);
                        continue loop;
                    }
                    if (!wSet.merge(r1, r2)) {
                        pos[wSquery.q]="NO";
                        wSet.reset(mod);
                        continue loop;
                    }
                }
                wSet.reset(mod);
            }
            while (nEI < m && sortEdges.get(nEI).w == ww) {
                mst.merge(sortEdges.get(nEI).u, sortEdges.get(nEI).v);
                nEI++;
            }
        }

    }

    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0,
                    BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }


    }

    static class Edge implements Comparable<Edge> {
        int u;
        int v;
        int w;
        int i;

        Edge(int u, int v, int w, int i) {
            this.u = u;
            this.v = v;
            this.w = w;
            this.i = i;
        }

        @Override
        public int compareTo(Edge o) {
            return w - o.w;
        }
    }

    static class Subquery {
        ArrayList<Edge> edges;
        int q;

        Subquery(ArrayList<Edge> edges, int q) {
            this.edges = edges;
            this.q = q;
        }
    }

    static class DisjointSet {

        void reset(ArrayList<Integer> indices) {
            for (int i : indices) {
                parent[i] = i;
            }
        }


        int[] rank, parent;
        int n;

        public DisjointSet(int n)
        {
            rank = new int[n];
            parent = new int[n];
            this.n = n;
            makeSet();
        }
        void makeSet() {
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        int fRoot(int x) {
            if (parent[x] != x) {
                parent[x] = fRoot(parent[x]);
            }
            return parent[x];
        }


        boolean merge(int x, int y) {
            int xRoot = fRoot(x), yRoot = fRoot(y);
            if (xRoot != yRoot){
                parent[xRoot] = yRoot;
                return true;
            }else
                return false;

        }

    }


}