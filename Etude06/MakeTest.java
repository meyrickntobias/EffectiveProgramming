import java.util.*;

/*
    Building a test file for voters
    Make N voters who vote for X candidates between the range of 5-12 letters and have a random distribution
*/

public class MakeTest {

    public static void main(String[] args) {
        // First job - create an arraylist of candidates
        int N = Integer.parseInt(args[0]);
        int X = Integer.parseInt(args[1]);
        Random r = new Random();
        Set<String> candidatePool = new LinkedHashSet<>();
        Scanner sc = new Scanner(System.in);
        int lines = 0;
        while (sc.hasNextLine() && lines < 300) lines++;
        sc.reset();
        for (int i = 0; i < X; i++) {
            /* Create a random string of characters
            int randomLength = r.nextInt(5)+7;
            char[] cName = new char[randomLength];
            for (int j = 0; j < randomLength; j++) cName[j] = (char)(r.nextInt(26) + 'a');
            candidatePool.add(new String(cName));
            */
            int howManyLines = r.nextInt(lines);
            for (i = 0; i < howManyLines; i++) sc.nextLine();
            String c = sc.nextLine();
            candidatePool.add(c);
        }
        ArrayList<String> candidateList = new ArrayList<String>(candidatePool);
        // To print out candidates, one each line: for (String candidate : candidatePool) System.out.println(candidate);
        // Each voter will vote for a random number of candidates between 1 and X, and will vote for K candidates, each unique
        for (int i = 0; i < N; i++) {
            // Create a random string of characters
            Set<String> votedFor = new LinkedHashSet<>();
            int K = r.nextInt(X);
            if (K == 0) K = 1;
            for (int j = 0; j < K; j++) {
                int a = r.nextInt(X);
                votedFor.add(candidateList.get(a));
            }
            for (String s : votedFor) System.out.print(s + " ");
            System.out.println();
        }

    }

}