import java.util.*;

public class Convolution {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> samples = new ArrayList<Integer>();
        while (sc.hasNextInt()) samples.add(sc.nextInt());
        sc.close();
        int index = 0;
        double conv_a = 0.1;
        double conv_b = 0.15;
        double conv_c = 0.5;
        double conv_d = 0.15;
        double conv_e = 0.1;
        ArrayList<Integer> convolved_samples = new ArrayList<Integer>();
        for (Integer sample : samples) {
            if (index <= 1 || index >= samples.size()-2) {
                index++;
                continue;
            }
            // Apply a convolution, using index-1, index and index+1
            double convolved = (samples.get(index-2) * conv_a);
            convolved += (samples.get(index-1) * conv_b);
            convolved += (sample * conv_c);
            convolved += (samples.get(index+1) * conv_d);
            convolved += (samples.get(index+2) * conv_e);
            int converted_conv = (int) convolved;
            convolved_samples.add(converted_conv);
            index++;
        }

        for (Integer conv : convolved_samples) System.out.println(conv);

    }

}