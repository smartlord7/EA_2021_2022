import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ProblemA {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        StringTokenizer st;
        int num1;
        int num2;
        int prod;

        line = in.readLine();
        st = new StringTokenizer(line);

        num1 = Integer.parseInt(st.nextToken());
        num2 = Integer.parseInt(st.nextToken());

        prod = num1 * num2;

        System.out.println(prod);
    }
}
