import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ProblemD {
    public static void main(String[] args) throws IOException {
        int pointer = 0;
        int element;
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        ArrayList<Integer> list = new ArrayList<Integer>();

        list.add(0);

        while ((line = in.readLine()) != null && line.length() > 0) {
            st = new StringTokenizer(line);

            if (line.startsWith("INSERT RIGHT")) {
                st.nextToken();
                st.nextToken();
                element = Integer.parseInt(st.nextToken());
                list.add(pointer + 1, element);
            } else if (line.startsWith("INSERT LEFT")) {
                st.nextToken();
                st.nextToken();
                element = Integer.parseInt(st.nextToken());
                list.add(pointer, element);
                pointer++;
            } else if (line.startsWith("MOVE RIGHT")) {
                pointer++;
            } else if (line.startsWith("MOVE LEFT")) {
                pointer--;
            }
        }

        for (int value : list) {
            System.out.println(value);
        }
    }
}
