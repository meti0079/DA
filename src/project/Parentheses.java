package project;

import java.util.LinkedList;
import java.util.Scanner;

public class Parentheses {

    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        char[] input = scanner.nextLine().toCharArray();
        LinkedList<Integer> open= new LinkedList<>();
        for (int i = 0; i < input.length; i++) {
            if (input[i]=='('){
                open.addLast(i);
            }
        }

        long counter = 0;
        long ans=0;
        for (int i = 0; i < input.length; i++) {
            if (!open.isEmpty() && open.getFirst()<i){
                open.removeFirst();
            }
            if (input[i]=='('){
            counter++;
            }else if (input[i]==')'){
                counter--;
            }
            if (counter<0){
                int ind=open.removeFirst();
                ans+=ind-i;
                counter+=2;
                input[ind]=')';
                input[i]='(';
            }

        }
        System.out.println(ans);

    }
}
