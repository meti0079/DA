import java.util.ArrayList;
import java.util.Scanner;

public class Q2 {
    private static long mode = 1000000000 + 7;

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

//        for (int i = n - 1; i >= 0; i--) {
////            if (tree.nodes.get(i).children.size() != 0) {
////                tree.nodes.get(i).dv = f2(tree,  i , hasKaj) ;
////                tree.nodes.get(i).cv = f1(tree, i, hasKaj) ;
////            } else {
////                  tree.nodes.get(i).dv = 1;
////                  tree.nodes.get(i).cv = 0;
////                }
//        }
        DFS(0,n,tree,hasKaj);
        if (hasKaj[0]==1){
            System.out.println(tree.root.dv);

        }else {
            System.out.println(tree.root.cv);

        }

    }

    private static long f2(Tree tree,  int s, int[] hasKAj) {
        ArrayList<Tree.Node> child = tree.nodes.get(s).children;
        long ans = 1;
        for (int i = 0; i < child.size(); i++) {
            ans = (ans * calCV(i,child,hasKAj)) % mode;
        }
        return ans;
    }
    private static long calCV(int i ,ArrayList<Tree.Node> child ,  int[] hasKAj ){
        long dd;
        if (hasKAj[child.get(i).data] == 0) {
            dd = (child.get(i).cv+child.get(i).dv)% mode;
        }else {
            dd = child.get(i).dv;
        }
        return dd;
    }


    private static long f1(Tree tree, int s, int[] hasKAj) {
        ArrayList<Tree.Node> child =  tree.nodes.get(s).children;
        long ans = 0;
        for (int i = 0; i < child.size(); i++) {


            long xx=hasKAj[child.get(i).data]==1?child.get(i).dv:child.get(i).cv;

            long du =  (((xx  * tree.nodes.get(s).dv)%mode) * varon(calCV(i,child,hasKAj)))% mode;

            ans = (ans + du) % mode;
        }
        return ans;
    }

    private static long varon(long l) {
        return power(l, mode - 2, mode);

    }

    static long power(long x, long y, long p) {
        long res = 1;
        x = x % p;
        while (y > 0) {
            if ((y & 1) > 0)
                res = (res * x) % p;
            y = y >> 1;
            x = (x * x) % p;
        }
        return res;
    }


       static void DFSUtil(int v, boolean visited[], Tree tree, int[] hasKaj)
        {
            visited[v] = true;
            ArrayList<Tree.Node> child = tree.nodes.get(v).children;
            for (Tree.Node node:child) {
                if (!visited[node.data])
                    DFSUtil(node.data, visited, tree,hasKaj);
            }
            if (tree.nodes.get(v).children.size() != 0) {
                tree.nodes.get(v).dv = f2(tree,  v, hasKaj) ;
                tree.nodes.get(v).cv = f1(tree, v, hasKaj) ;
            } else {
                tree.nodes.get(v).dv = 1;
                tree.nodes.get(v).cv = 0;
            }
        }


        static void DFS(int v, int n ,Tree tree,int[] hasKaj)
        {
            boolean visited[] = new boolean[n];
            DFSUtil(v, visited,tree, hasKaj);
        }

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
        long cv=0;
        long dv=0;
        public Node(int rootData) {
            this.data = rootData;
            this.children = new ArrayList<Node>();
        }
    }

}