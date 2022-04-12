import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static int getMinAbsDiff(int[] times) {
        int n = times.length;
        int total = 0;

        for (int cookingTime : times) {
            total += cookingTime;
        }

        int sum = total / 2;
        boolean[][] table = new boolean[n + 1][sum + 1];

        for (int j = 0; j <= n; j++) {
            table[j][0] = true;
        }

        for (int j = 1; j <= n; j++) {
            int current_time = times[j - 1];

            for (int k = 0; k <= sum; k++) {
                if (current_time > k) {
                    table[j][k] = table[j - 1][k];
                } else {
                    table[j][k] = table[j - 1][k] || table[j - 1][k - current_time];
                }
            }
        }

        for (int k = sum; k >= 0; k--) {
            if (table[n][k]) {
                return total - 2 * k;
            }
        }

        return -1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String data;

        while ((data = in.readLine()) != null && data.length() > 0) {
            int n = Integer.parseInt(data);
            int[] times = new int[n];

            for (int j = 0; j < n; j++) {
                times[j] = Integer.parseInt(in.readLine());
            }

            System.out.println(getMinAbsDiff(times));
        }
    }
}
