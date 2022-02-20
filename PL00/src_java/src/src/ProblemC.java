import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class ProblemC {

    public static void printArray(List<Integer> array) {
        for (Integer integer : array) {
            System.out.println(integer);
        }
    }

    public static void main(String[] args) throws IOException {
        int num;
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        LinkedList<Integer> array = new LinkedList<Integer>();

        while ((line = in.readLine()) != null && line.length() != 0) {
            num = Integer.parseInt(line);

            array.add(num);

        }

        array.sort(null);
        printArray(array);
    }
}
