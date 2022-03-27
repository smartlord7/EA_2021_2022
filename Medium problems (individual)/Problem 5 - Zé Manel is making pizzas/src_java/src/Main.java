import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static int solve(int[] cookingTimes, int sum) {
        int n = cookingTimes.length;
        int half_time = sum / 2;
        boolean[][] dp = new boolean[n + 1][half_time + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }

        for (int i = 1; i <= n; i++) {
            int t_i = cookingTimes[i - 1];

            for (int j = 0; j <= half_time; j++) {
                if (t_i > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - t_i];
                }
            }
        }

        for (int j = half_time; j >= 0; j--) {
            if (dp[n][j]) {
                return sum - 2 * j;
            }
        }

        return -1;
    }

    public static void main(String[] args) throws IOException {
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while ((line = in.readLine()) != null && line.length() > 0) {
            int nPizzas = Integer.parseInt(line);
            int[] cookingTimes = new int[nPizzas];
            int sum = 0;

            for (int i = 0; i < nPizzas; i++) {
                cookingTimes[i] = Integer.parseInt(in.readLine());
                sum += cookingTimes[i];
            }

            System.out.println(solve(cookingTimes, sum));
        }
    }
}
