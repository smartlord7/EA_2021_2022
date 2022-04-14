import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static class Member {
        public int[] contacts;
        public int nContacts;
        public final int MAX_RECRUITED_MEMBERS = 10;

        public Member() {
            contacts = new int[MAX_RECRUITED_MEMBERS + 1];
            nContacts = 0;
        }

        public void addContact(int memberId) {
            if (nContacts < MAX_RECRUITED_MEMBERS + 1) {
                contacts[nContacts++] = memberId;
            }
        }
    }

    public static class Solution {
        public int nMembers;
        public int value;

        public Solution best(Solution other) {
            if (this.nMembers < other.nMembers) {
                return this;
            } else if (this.nMembers > other.nMembers) {
                return other;
            } else {
                if (this.value > other.value) {
                    return this;
                } else {
                    return other;
                }
            }
        }
    }

    private final int MAX_MEMBERS = 100001;
    private final int ROOT_MEMBER_PARENT = -1;
    private final String EOF = "-1";

    private Member[] pyramid;
    private Solution[][] memo;

    private void solve_(int memberId, int recruiterId) {
        int currContact;
        Member member;

        member = pyramid[memberId];
        for (int i = 0; i < member.nContacts; i++) {
            currContact = member.contacts[i];
            if (currContact != recruiterId)
                solve_(currContact, memberId);
        }

        for (int i = 0; i < member.nContacts; i++) {
            currContact = member.contacts[i];

            if (member.contacts[i] != recruiterId) {
                memo[memberId][0].nMembers += memo[currContact][1].nMembers;
                memo[memberId][0].value += memo[currContact][1].value;

                Solution best = memo[currContact][1].best(memo[currContact][0]);

                memo[memberId][1].nMembers += best.nMembers;
                memo[memberId][1].value += best.value;
            }
        }
    }

    private void solve() {
        solve_(0, ROOT_MEMBER_PARENT);
        Solution best = memo[0][0].best(memo[0][1]);

        System.out.println(best.nMembers + " " + best.value);
    }

    public Main() throws IOException {
        int idRecruiter;
        int idRecruited;
        int value;
        String token;
        String line;
        Member recruiter;
        BufferedReader in;
        StringTokenizer st;

        in = new BufferedReader(new InputStreamReader(System.in));

        while ((line = in.readLine()) != null && line.length() > 0) {
            pyramid = new Member[MAX_MEMBERS];
            memo = new Solution[MAX_MEMBERS][2];

            while (!line.equals(EOF)) {
                st = new StringTokenizer(line);
                idRecruiter = Integer.parseInt(st.nextToken());

                if (pyramid[idRecruiter] == null) {
                    pyramid[idRecruiter] = new Member();
                }

                if (memo[idRecruiter][0] == null) {
                    memo[idRecruiter][0] = new Solution();
                    memo[idRecruiter][1] = new Solution();
                }

                memo[idRecruiter][0].nMembers = 0;
                memo[idRecruiter][1].nMembers = 1;
                recruiter = pyramid[idRecruiter];

                while (true) {
                    token = st.nextToken();

                    if (!st.hasMoreTokens()) {
                        break;
                    }

                    idRecruited = Integer.parseInt(token);

                    if (pyramid[idRecruited] == null) {
                        pyramid[idRecruited] = new Member();
                    }

                    pyramid[idRecruited].addContact(idRecruiter);
                    recruiter.addContact(idRecruited);
                }

                value = Integer.parseInt(token);
                memo[idRecruiter][0].value = 0;
                memo[idRecruiter][1].value = value;

                line = in.readLine();
            }

            solve();
        }
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }
}
