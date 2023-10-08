package project;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Q5 {


    static int LCIS(int arr1[], int n, int arr2[], int m) {
        int dp[][][] = new int[n + 1][m + 1][2];
        int max=0;
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {

                dp[i][j][0] = Math.max(dp[i - 1][j][0],dp[i - 1][j][1]) ;

                if (max<dp[i][j][0])
                    max=dp[i][j][0];

                if (arr1[i - 1] == arr2[j - 1]) {
                    for (int k = i - 1; k >= 0; k--) {
                        if (dp[k+1][j-1][1] > dp[i][j][1] && arr1[k] < arr1[i-1] )
                            dp[i][j][1] = dp[k+1][j-1][1];
                    }
                    dp[i][j][1] = dp[i][j][1] + 1;
                    if (max<dp[i][j][1])
                        max=dp[i][j][1];

                } else {
                            dp[i][j][1] = dp[i][j-1][1];
                        if (max<dp[i][j][1])
                            max=dp[i][j][1];

                }
            }
        }


        return max;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int arr1[] = new int[n];
        for (int i = 0; i < n; i++) {
            arr1[i] = scanner.nextInt();
        }
        int m = scanner.nextInt();
        int arr2[] = new int[m];
        for (int i = 0; i < m; i++) {
            arr2[i] = scanner.nextInt();
        }
        if (n > m) {
            System.out.println(LCIS(arr1, n, arr2, m));

        } else {
            System.out.println(LCIS(arr2, m, arr1, n));
        }
    }
}
