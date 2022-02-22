import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class ProblemE {
    public static void main(String[] args) throws IOException {
        int numCases;
        String line;
        Stack<String> stack = new Stack<String>();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        line = in.readLine();
        numCases = Integer.parseInt(line);

        for (int i = 0; i < numCases; i++) {

            line = in.readLine();
            st = new StringTokenizer(line);

            while (st.hasMoreTokens()) {
                String token = st.nextToken();

                if (token.equals("-") || token.equals("+")) {
                    int op2 = Integer.parseInt(stack.pop());
                    int op1 = Integer.parseInt(stack.pop());
                    int result;

                    if (token.equals("+")) {
                        result = op1 + op2;
                    } else {
                        result = op1 - op2;
                    }

                    stack.push(Integer.toString(result));
                } else {
                    stack.push(token);
                }
            }

            System.out.println(stack.pop());
        }
    }
}
