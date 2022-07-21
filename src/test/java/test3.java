import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class test3 {
    public static void main(String[] args) throws IOException {
        String str;
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        str =bf.readLine();
        System.out.println(str.toUpperCase());
    }
}
