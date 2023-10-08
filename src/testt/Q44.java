package testt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Q44 {
    private static BigInteger mode =new BigInteger( "1000000007");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        Tree tree= new Tree(0,n);
        for (int i = 0; i < n - 1; i++) {
            int p = scanner.nextInt();
            tree.nodes.get(p).children.add(tree.nodes.get(i+1));
            tree.nodes.get(i+1).parent=tree.nodes.get(p);
        }
        int hasKaj[] = new int[n];
        for (int i = 0; i < n; i++) {
            int b = scanner.nextInt();
            hasKaj[i] = b;
        }

        for (int i = n - 1; i >= 0; i--) {
            if (tree.nodes.get(i).children.size() != 0) {
                tree.nodes.get(i).dv = f2(tree,  i , hasKaj) ;
                tree.nodes.get(i).cv = f1(tree, i, hasKaj) ;
            } else {
                tree.nodes.get(i).dv = new BigInteger("1");
                tree.nodes.get(i).cv = new BigInteger("0");
            }
        }

        if (hasKaj[0]==1){
            System.out.println(tree.root.dv.mod(mode));

        }else {
            System.out.println(tree.root.cv.mod(mode));

        }

    }

    private static BigInteger f2(Tree tree, int s, int[] hasKAj) {
        ArrayList<Tree.Node> child = tree.nodes.get(s).children;
        BigInteger ans = new BigInteger("1");
        for (int i = 0; i < child.size(); i++) {
            ans = ans.multiply(calCV(i,child,hasKAj)) ;
        }
        return ans;
    }
    private static BigInteger calCV(int i , ArrayList<Tree.Node> child , int[] hasKAj ){
        BigInteger dd;
        if (hasKAj[child.get(i).data] == 0) {
            dd = (child.get(i).cv).add(child.get(i).dv);
        }else {
            dd = child.get(i).dv;
        }
        return dd;
    }


    private static BigInteger f1(Tree tree, int s, int[] hasKAj) {
        ArrayList<Tree.Node> child =  tree.nodes.get(s).children;
        BigInteger ans = new BigInteger("0");
        for (int i = 0; i < child.size(); i++) {


            BigInteger xx=hasKAj[child.get(i).data]==1?child.get(i).dv:child.get(i).cv;

            BigInteger du =  xx.multiply(tree.nodes.get(s).dv).divide( calCV(i,child,hasKAj));

            ans = (ans.add(du));
        }
        return ans;
    }

//    private static long varon(long l) {
//        return power(l, mode - 2, mode);
//
//    }

//    static long power(long x, long y, BigInteger p) {
//        long res = 1;
//        x = x % p;
//        while (y > 0) {
//            if ((y & 1) > 0)
//                res = (res * x) % p;
//            y = y >> 1;
//            x = (x * x) % p;
//        }
//        return res;
//    }
}
class Tree {
    Node root;
    ArrayList<Node> nodes;
    public Tree(int rootData, int b) {
        root = new Node(rootData);
        nodes= new ArrayList<>();
        nodes.add(root);
        for (int i = 0; i < b-1; i++) {
            nodes.add(new Node(i+1));
        }
    }
    public static class Node {
        int data;
        Node parent;
        ArrayList<Node> children;
        BigInteger cv=new BigInteger("0");
        BigInteger dv=new BigInteger("0");
        public Node(int rootData) {
            this.data = rootData;
            this.children = new ArrayList<Node>();
        }
    }

}