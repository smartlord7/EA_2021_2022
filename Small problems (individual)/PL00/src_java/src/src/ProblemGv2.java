import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class ProblemGv2 {
    public static void main(String[] args) throws IOException {
        int size;
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        TreeMap<Integer, Integer> shoes = new TreeMap<Integer, Integer>();

        while ((line = in.readLine()) != null && line.length() > 0) {
            st = new StringTokenizer(line);
            st.nextToken();
            size = Integer.parseInt(st.nextToken());

            if (line.startsWith("ADD")) {
                if (!shoes.containsKey(size)) {
                    shoes.put(size, 1);
                } else {
                    shoes.put(size, shoes.get(size) + 1);
                }
            } else {
                int search = !shoes.containsKey(size) ? -1 : shoes.get(size) > 0 ? size : -1;

                if (search == -1) {
                    Integer higherKey = shoes.higherKey(size);

                    if (higherKey != null && shoes.get(higherKey) > 0) {
                        search = higherKey;
                    }
                }

                if (search == -1) {
                    System.out.println("impossible");
                } else {
                    System.out.println(search);

                    int newVal = shoes.get(search) - 1;

                    if (newVal == 0) {
                        shoes.remove(search);
                    } else {
                        shoes.put(search, newVal);
                    }
                }
            }
        }
    }
}
