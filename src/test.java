import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class test {
    public static void main(String[] args) throws IOException {
        Random random= new Random();
        String x="";
        String y="";
        int n =200000;
        for (int i = 1; i < n; i++) {
            x+=random.nextInt(i)+" ";
        }
        for (int i = 0; i < n; i++) {
            y+=random.nextInt(2)+" ";
        }
        System.out.println(x);
        System.out.println(y);
        FileWriter fileWriter1= new FileWriter("test1.txt" );
        FileWriter fileWriter2= new FileWriter("test2.txt" );
        fileWriter1.write(x);
        fileWriter2.write(y);
        fileWriter1.flush();
        fileWriter2.flush();
    }
}
