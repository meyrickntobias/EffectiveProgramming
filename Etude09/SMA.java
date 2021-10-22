import java.util.*;

public class SMA {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> samples = new ArrayList<Integer>();
        while (sc.hasNextInt()) samples.add(sc.nextInt());
        sc.close();
        int index = 0;
        for (Integer sample : samples) {
            if (index > 4 && index % 5 == 0) {
                // Get previous sample and current sample, add up and spit out
                double avg = (samples.get(index-4) + samples.get(index-3) + samples.get(index-2) + samples.get(index-1)) / 5;
                System.out.println(avg);
            } 
            index++;
            // For every second sample, get the previous
        }

    }

}