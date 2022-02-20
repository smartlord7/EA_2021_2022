import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ProblemB {

    public static int[] reverseArray(int[] array) {
        int l = array.length;
        int[] reversedArray = new int[l];

        for (int i = 0; i < l; i++) {
            reversedArray[i] = array[l - i - 1];
        }

        return reversedArray;
    }

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);;

            if (i < array.length - 1) {
                System.out.print(" ");
            }
        }

        System.out.println("");
    }

    public static void main(String[] args) throws IOException {
        int numValues;
        int i = 0;
        int[] array;
        int[] reversedArray;
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        line = in.readLine();
        numValues = Integer.parseInt(line);
        array = new int[numValues];

        line = in.readLine();
        st = new StringTokenizer(line);

        while (i < numValues) {
            int value;

            value = Integer.parseInt(st.nextToken());
            array[i] = value;

            i++;
        }

        reversedArray = reverseArray(array);
        printArray(reversedArray);
    }
}
