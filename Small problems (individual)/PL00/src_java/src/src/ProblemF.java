import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ProblemF {
    public static void main(String[] args) throws IOException {
        int numTasks;
        int prevEndTime = -1;
        int totalTime = 0;
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        line = in.readLine();
        numTasks = Integer.parseInt(line);

        for (int i = 0; i < numTasks; i++) {
            line = in.readLine();
            st = new StringTokenizer(line);
            int initTime = Integer.parseInt(st.nextToken());
            int duration = Integer.parseInt(st.nextToken());

            if (prevEndTime == -1) {
                totalTime += initTime + duration;
                prevEndTime = initTime + duration;
            } else if (prevEndTime >= initTime) {
                totalTime += duration;
                prevEndTime += duration;
            } else {
                int time = initTime - prevEndTime + duration;
                totalTime += time;
                prevEndTime += time;
            }
        }

        System.out.println(totalTime);
    }
}
