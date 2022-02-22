import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ProblemG {
    public static void placeInOrder(ArrayList<Integer> list, int element) {
        int l = list.size();

        if (list.isEmpty() || element >= list.get(l - 1)) {
            list.add(element);
            return;
        }

        int currIndex = 0;

        while (currIndex < l - 1 && element >= list.get(currIndex)) {
            currIndex++;
        }

        list.add(currIndex, element);
    }

    public static int getEqualOrHigher(ArrayList<Integer> list, int query) {
        int start = 0, end = list.size() - 1;

        int ans = -1;
        while (start <= end) {
            int mid = (start + end) / 2;

            if (list.get(mid) < query) {
                start = mid + 1;
            } else {
                ans = mid;
                end = mid - 1;
            }
        }

        if (ans == -1) {
            return -1;
        } else {
            int temp = list.get(ans);
            list.remove(ans);

            return temp;
        }
    }

    public static void main(String[] args) throws IOException {
        int size;
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        ArrayList<Integer> shoes = new ArrayList<Integer>(200000 );

        while ((line = in.readLine()) != null && line.length() > 0) {
            st = new StringTokenizer(line);
            st.nextToken();
            size = Integer.parseInt(st.nextToken());

            if (line.startsWith("ADD")) {
                placeInOrder(shoes, size);
            } else {
                int search = getEqualOrHigher(shoes, size);

                if (search == -1) {
                    System.out.println("impossible");
                } else {
                    System.out.println(search);
                }
            }
        }
    }
}
