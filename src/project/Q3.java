package project;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Q3 {

    static Rook[] xRooks;
    static Rook[] yRooks;

    static int xPointer = 0;
    static int yPointer = 0;

    static int[] xSegmentValue;
    static int[] ySegmentValue;



    public static void main(String[] args) throws IOException {
        Scanner sca = new Scanner(System.in);
        int  n = sca.nextInt();
        xRooks = new Rook[n];
        yRooks = new Rook[n];

        xSegmentValue = new int[4*n];
        ySegmentValue = new int[4*n];

        int []xAns = new int [n];
        int []yAns = new int [n];


        for (int i = 0; i < 4*n; i++) {
            xSegmentValue[i] = ySegmentValue[i] = -1;
        }

        for (int i = 0; i < n; i++) {
            int x1 = sca.nextInt();
            int y1 = sca.nextInt();
            int x2 = sca.nextInt();
            int y2 = sca.nextInt();
            xRooks[i] = new Rook(x1, x2, i);
            yRooks[i] = new Rook(y1, y2, i);
        }

        int []sortedXRooks = new int[n];
        int []sortedYRooks = new int[n];

        Rook[]xClone = xRooks.clone();
        Rook[]yClone = yRooks.clone();

        Arrays.sort(xRooks, Comparator.comparingInt(a -> a.second));
        Arrays.sort(yRooks, Comparator.comparingInt(a -> a.second));

        for (int i = 0; i < n; i++) {
            sortedXRooks[xRooks[i].ind] = i;
            sortedYRooks[yRooks[i].ind] = i;
        }

        Arrays.sort(xRooks, Comparator.comparingInt(a -> a.first));
        Arrays.sort(yRooks, Comparator.comparingInt(a -> a.first));

        for (int i = 1; i <= n; i++) {
            while (xPointer < n && xRooks[xPointer].first <= i) {
                update(1, sortedXRooks[xRooks[xPointer].ind], 0, n, xRooks[xPointer].ind, xSegmentValue);
                xPointer++;
            }

            if(xSegmentValue[1] == -1)
                continue;
            xAns[xSegmentValue[1]] = i;
            if(xClone[xSegmentValue[1]].second < i){
                System.out.println("impossible");
                System.exit(0);
            }
            update(1, sortedXRooks[xSegmentValue[1]], 0, n, -1, xSegmentValue);
        }

        for (int i = 1; i <= n; i++) {
            while (yPointer < n && yRooks[yPointer].first <= i) {
                update(1, sortedYRooks[yRooks[yPointer].ind], 0, n, yRooks[yPointer].ind, ySegmentValue);
                yPointer++;
            }
            if(ySegmentValue[1] == -1)
                continue;
            yAns[ySegmentValue[1]] = i;
            if(yClone[ySegmentValue[1]].second < i){
                System.out.println("impossible");
                System.exit(0);
            }
            update(1, sortedYRooks[ySegmentValue[1]], 0, n, -1, ySegmentValue);
        }

        for (int i = 0; i < n; i++) {
            System.out.println(xAns[i] + " " + yAns[i]);
        }

    }


    static void update(int v, int a, int l, int r, int value, int[] arr) {
        if (a < l || a >= r)
            return;
        if (r - l == 1) {
            arr[v] = value;
            return;
        }
        int mid = (l + r) / 2;
        update(2 * v, a, l, mid, value, arr);
        update(2 * v + 1, a, mid ,r, value, arr);
        if(arr[2*v] == -1)
            arr[v] = arr[2*v + 1];
        else
            arr[v] = arr[2*v];
    }

    static class Rook {
        int first;
        int second;
        int ind;
        public Rook(int first, int second, int ind){
            this.first = first;
            this.second = second;
            this.ind = ind;
        }
    }
}
