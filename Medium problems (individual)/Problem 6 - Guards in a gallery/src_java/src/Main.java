import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import java.util.StringTokenizer;

public class Main {

    public static int solve(int[] obj, int guardRadius) {
        int l = obj.length;
        int[] guards = new int[l];
        int i = 0;

        guards[0] = obj[i] + guardRadius;

        for (int j = 1; j < obj.length; j++) {
            if (obj[j] > guards[i] + guardRadius) {
                guards[++i] = obj[j] + guardRadius;
            }
        }

        return i + 1;
    }

    public static void main(String[] args) throws IOException {
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        line = in.readLine();
        int nCases = Integer.parseInt(line);

        for (int i = 0; i < nCases; i++) {
            line = in.readLine();
            st = new StringTokenizer(line);
            int nObj = Integer.parseInt(st.nextToken());
            int guardRadius = Integer.parseInt(st.nextToken());
            int[] obj = new int[nObj];


            for (int j = 0; j < nObj; j++) {
                line = in.readLine();
                obj[j] = Integer.parseInt(line);
            }

            Arrays.sort(obj);
            System.out.println(solve(obj, guardRadius));
        }
    }
}
