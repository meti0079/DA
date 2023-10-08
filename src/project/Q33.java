package project;

import java.util.*;

class Rook implements Comparable<Rook>{
     int start;
     int finish;
    int  id;

    public Rook(int start, int finish, int id){
        this.start=start;
        this.finish=finish;
        this.id=id;
    }

    @Override
    public int compareTo(Rook o) {
        if (this.finish < o.finish){
            return -1;
        }else if (this.finish > o.finish){
            return 1;
        }else {
            if (this.id < o.id){
                return -1;
            }else {
                return 1;
            }
        }
    }
}

public class Q33 {

    public static int [] findPossiblePlace(Rook[] rooks){
        PriorityQueue<Rook> p1 = new PriorityQueue<>();
        int ans[]= new int[rooks.length];
        Rook[] rooks1=rooks.clone();
        Arrays.sort(rooks);
        Arrays.sort(rooks1, Comparator.comparingInt(a -> a.start));
        int j=0;
        for(int i=1; i<=rooks1.length; i++){
            while (true  && j< rooks.length){
                if (rooks1[j].start<=i){
                    p1.add(rooks1[j]);

                    j++;
                }else {
                    break;
                }
            }
            if (!p1.isEmpty()){
               Rook rook= p1.poll();
               if (!p1.isEmpty() && p1.peek().finish==i){
                   System.out.println("impossible");
                   System.exit(0);
               }
               ans[rook.id]=i;
            }
        }

        return ans;
    }


    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        int n = scanner.nextInt();

        Rook[] horizontal= new Rook[n];
        Rook[] vertical= new Rook[n];
        for (int i = 0; i < n; i++) {
            int a= scanner.nextInt();
            int b= scanner.nextInt();
            int c= scanner.nextInt();
            int d= scanner.nextInt();
            horizontal[i]= new Rook(a,c,i);
            vertical[i]= new Rook(b,d,i);
        }

        int ans[] =findPossiblePlace(horizontal);
        int ans1[]=findPossiblePlace(vertical);

            for (int i = 0; i < n; i++) {
                System.out.println(ans[i]+" "+ans1[i]);
            }

    }
}