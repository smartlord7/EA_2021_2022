import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class ProblemH {
    public static void main(String[] args) throws IOException {
        int value;
        int prevVal;
        int id;
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        StringTokenizer st;

        while ((line = in.readLine()) != null && line.length() > 0) {
            st = new StringTokenizer(line);
            st.nextToken();

            if (line.startsWith("ADD")) {
                value = Integer.parseInt(st.nextToken());
                id = Integer.parseInt(st.nextToken());

                if (map.containsKey(id)) {
                    prevVal = map.get(id);
                    map.put(id, prevVal + value);
                } else {
                    map.put(id, value);
                }
            } else if (line.startsWith("REM")) {
                value = Integer.parseInt(st.nextToken());
                id = Integer.parseInt(st.nextToken());

                if (!map.containsKey(id)) {
                    System.out.println("removal refused");
                    continue;
                }

                prevVal = map.get(id);

                if (value > prevVal) {
                    System.out.println("removal refused");
                } else {
                    map.put(id, prevVal - value);
                }
            } else {
                id = Integer.parseInt(st.nextToken());

                if (!map.containsKey(id)) {
                    System.out.println(0);
                } else {
                    System.out.println(map.get(id));
                }
            }
        }
    }
}
