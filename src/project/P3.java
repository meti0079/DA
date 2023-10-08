package project;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class P3 {

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

        public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(
                    new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException
        {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n') {
                    if (cnt != 0) {
                        break;
                    }
                    else {
                        continue;
                    }
                }
                buf[cnt++] = (byte)c;
            }
            return new String(buf, 0, cnt);
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

        public long nextLong() throws IOException
        {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
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

        public double nextDouble() throws IOException
        {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

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

        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }

    public static void main(String[] args) {
        try {
            Reader scanner = new Reader();
            int n = scanner.nextInt();
            int m = n;
            ArrayList<ArrayList<Interval>> horizental = new ArrayList<>();
            ArrayList<ArrayList<Interval>> vertical = new ArrayList<>();
            ArrayList<Family> families = new ArrayList<>();
            for (int i = 0; i <= m; i++) {
                horizental.add(new ArrayList<Interval>());
                vertical.add(new ArrayList<Interval>());
            }

            for (int i = 1; i <= n; i++) {
                int x1 = scanner.nextInt();
                int y1 = scanner.nextInt();
                int x2 = scanner.nextInt();
                int y2 = scanner.nextInt();
                Interval hi = new Interval(x1, x2, i);
                Interval vi = new Interval(y1, y2, i);
                horizental.get(x1).add(hi);
                vertical.get(y1).add(vi);
                families.add(new Family(hi, vi, i));
            }

            PriorityQueue<Interval> hin = new PriorityQueue<>();
            for (int i = 0; i <= m; i++) {
//                if (!horizental.get(i).isEmpty()) {
//                    for (project.Interval interval : horizental.get(i)) {
//                        hin.add(interval);
//                    }
//                }
                if (!hin.isEmpty()) {
                    Interval poll = hin.poll();
                    if (!hin.isEmpty() && hin.peek().getRight() == i) {
                        System.out.println("impossible");
                        System.exit(0);
                    }
                    families.get(poll.getIndex() - 1).sethIndex(i);
                }
            }

            PriorityQueue<Interval> vin = new PriorityQueue<>();
            for (int i = 0; i <= m; i++) {
                if (!vertical.get(i).isEmpty()) {
                    for (Interval interval : vertical.get(i)) {
                        vin.add(interval);
                    }
                }
                if (!vin.isEmpty()) {
                    Interval poll = vin.poll();
                    if (!vin.isEmpty() && vin.peek().getRight() == i) {
                        System.out.println("impossible");
                        System.exit(0);
                    }
                    families.get(poll.getIndex() - 1).setvIndex(i);
                }
            }

            for (Family family : families) {
                System.out.println(family.gethIndex() + " " + family.getvIndex());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

class Family{
    private Interval horizantal;
    private Interval vertical;
    private int index;
    private int hIndex;
    private int vIndex;

    public Family(Interval horizantal, Interval vertical, int index) {
        this.horizantal = horizantal;
        this.vertical = vertical;
        this.index = index;
    }

    public Interval getHorizantal() {
        return horizantal;
    }

    public void setHorizantal(Interval horizantal) {
        this.horizantal = horizantal;
    }

    public Interval getVertical() {
        return vertical;
    }

    public void setVertical(Interval vertical) {
        this.vertical = vertical;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int gethIndex() {
        return hIndex;
    }

    public void sethIndex(int hIndex) {
        this.hIndex = hIndex;
    }

    public int getvIndex() {
        return vIndex;
    }

    public void setvIndex(int vIndex) {
        this.vIndex = vIndex;
    }
}

class Interval implements Comparable<Interval> {
    private int left;
    private int right;
    private int index;

    public Interval(int left, int right, int index) {
        this.left = left;
        this.right = right;
        this.index = index;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    @Override
    public int compareTo(Interval o) {
        if (this.right < o.right){
            return -1;
        }else if (this.right > o.right){
            return 1;
        }else {
            if (this.index < o.index){
                return -1;
            }else {
                return 1;
            }
        }
    }
}