import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static boolean threeSum(int[] array) {

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
