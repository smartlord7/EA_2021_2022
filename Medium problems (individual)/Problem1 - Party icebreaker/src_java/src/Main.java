import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    private static boolean threeSum(int[] array) {
        int l = array.length;
        int i = 0;
        int j;
        int k;

        Arrays.sort(array);

        while (i < l - 1) {
            j = i + 1;
            k = l - 1;

            int currI = array[i];

            while (j < k) {
                int currJ = array[j];
                int currK = array[k];
                int sum = currI + currJ + currK;

                if (sum < 0) {
                    j++;
                } else if (sum > 0) {
                    k--;
                } else {
                    return true;
                }
            }

            i++;
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;

        while ((line = in.readLine()) != null && line.length() != 0) {
            int numGuests = Integer.parseInt(line);
            int[] array = new int[numGuests];
            int i = 0;

            while (i < numGuests) {
                line = in.readLine();
                int num = Integer.parseInt(line);
                array[i] = num;

                i++;
            }

            in.readLine();

            if (threeSum(array)) {
                System.out.println("Fair");
            } else {
                System.out.println("Rigged");
            }
        }
    }
}
