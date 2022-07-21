import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class replace {
    public static void main(String[] args) throws IOException {
        String str;
        String [] str1 ;
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        str = bf.readLine();
        System.out.println(str.replace("o", "*"));
        str1 =str.split("-");
        System.out.println(str1);
    }
}
