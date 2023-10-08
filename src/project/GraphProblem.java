package project;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.*;

public class GraphProblem {

    static List<MyNode> myNode2;

    static MyNode[] myNode;



    public static void main(String[] args) throws IOException {
        Reader scanner = new Reader();
        int n = scanner.nextInt();
        int dist[][]= new int[n][n];
        Long myDist[][]= new Long[n][n];
        Long inf=(long) Integer.MAX_VALUE;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                dist[i][j]= scanner.nextInt();
            }
        }
        int ans=0;
        myNode2= new ArrayList<>(n*n);

        if (!isValid(dist,n)) {
            System.out.println("-1");
            System.exit(0);
        }
        myNode = new MyNode[myNode2.size()];
        myNode = myNode2.toArray(myNode);
        for (int i = 0; i < n; ++i) {
            Arrays.fill(myDist[i],inf);
            myDist[i][i]=0L;
        }
        Arrays.sort(myNode);

        for (MyNode m:myNode) {
            if (myDist[m.i][m.j] > m.d){
                myDist[m.i][m.j]= m.d;
                myDist[m.j][m.i]= m.d;
                ans++;
                update(m.i,m.j, myDist,n);
            }else if (myDist[m.i][m.j] < m.d){
                System.out.println("-1");
                System.exit(0);
            }
        }
        System.out.println(ans);

    }

    private static void update(int s, int d, Long[][] dist, int n ) {
        for (int i = 0; i < n; ++i) {
            for (int j = i+1; j < n; ++j) {
                long dd= dist[s][d]+dist[i][s]+dist[d][j];
                long dd1= dist[s][d]+dist[i][d]+dist[s][j];
                long min=Math.min(dd,dd1);
                if (min < dist[i][j]){
                    dist[i][j]=min;
                    dist[j][i]=min;
                }
            }
        }
    }

    private static boolean isValid(int[][] dist , int n ) {
        for (int i = 0; i < n; ++i) {
            for (int j = i+1; j < n; ++j) {
                myNode2.add(new MyNode(i,j,dist[i][j]));
                if (dist[i][j]!=dist[j][i])
                    return false;
            }
        }
        return true;
    }
   static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }
        public int nextInt() throws IOException
        {
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
        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0,
                    BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }


    }

}
class MyNode implements Comparable<MyNode>{
    int i;
    int j ;
    long d;

    public MyNode(int ii, int jj, int dd){
        i=ii;
        j=jj;
        d=dd;
    }

    @Override
    public int compareTo(MyNode o) {
        if (o.d>d){
            return -1;
        }else if (d>o.d){
            return 1;
        }
        return 0;
    }
}
