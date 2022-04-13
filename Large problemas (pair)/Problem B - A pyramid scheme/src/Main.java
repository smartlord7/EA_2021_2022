import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    public class Member {
        private int id;
        private int value;
        private int[] recruitedMembers;
        private int nRecruitedMembers;
        public final int MAX_RECRUITED_MEMBERS = 10;

        public Member() {
            recruitedMembers = new int[MAX_RECRUITED_MEMBERS];
            nRecruitedMembers = 0;
        }

        public Member(int id) {
            this();
            this.id = id;

        }

        public Member(int id, int value) {
            this();
            this.id = id;
            this.value = value;
        }

        public void addRecruitedMember(int memberId) {
            if (nRecruitedMembers < MAX_RECRUITED_MEMBERS) {
                recruitedMembers[nRecruitedMembers++] = memberId;
            }
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int[] getRecruitedMembers() {
            return recruitedMembers;
        }

        public void setRecruitedMembers(int[] recruitedMembers) {
            this.recruitedMembers = recruitedMembers;
        }

        public int getNRecruitedMembers() {
            return nRecruitedMembers;
        }

        public void setNRecruitedMembers(int nRecruitedMembers) {
            this.nRecruitedMembers = nRecruitedMembers;
        }

        @Override
        public boolean equals(Object o) {
            return getId() == ((Member) o).getId();
        }

        @Override
        public int hashCode() {
            return getId();
        }
    }

    public class Solution {
        private HashSet<Member> members;
        private int value;
    }

    private static int MAX_MEMBERS = 100001;
    private static String EOF = "-1";

    public Main() throws IOException {
        int idRecruiter;
        int idRecruited;
        int value;
        String token;
        String line;
        Member recruiter;
        Member[] pyramid;
        BufferedReader in;
        StringTokenizer st;

        in = new BufferedReader(new InputStreamReader(System.in));

        while ((line = in.readLine()) != null && line.length() > 0) {
            while (!line.equals(EOF)) {
                pyramid = new Member[MAX_MEMBERS];
                st = new StringTokenizer(line);
                idRecruiter = Integer.parseInt(st.nextToken());

                if (pyramid[idRecruiter] == null) {
                    pyramid[idRecruiter] = new Member(idRecruiter);
                }

                recruiter = pyramid[idRecruiter];

                while (true) {
                    token = st.nextToken();

                    if (!st.hasMoreTokens()) {
                        break;
                    }

                    idRecruited = Integer.parseInt(token);
                    pyramid[idRecruited] = new Member(idRecruited);
                    recruiter.addRecruitedMember(idRecruited);
                }

                value = Integer.parseInt(token);
                recruiter.setValue(value);

                line = in.readLine();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }
}
