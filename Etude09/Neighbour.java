import java.util.*;

public class Neighbour {

    public static void main(String[] args) {

        // Loop through points, if point is above mean by
        // certain amount and immediate neighbours are lower
        // increment pulse count

        // Step 1 - Insert pulse data into array of doubles
        ArrayList<Double> samples = new ArrayList<Double>();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextDouble()) samples.add(sc.nextDouble());
        sc.close();
        double sum = 0;
        double low = samples.get(0); 
        double high = samples.get(0);
        for (Double sample : samples) {
            sum += sample;
            if (sample < low) low = sample;
            if (sample > high) high = sample;
        }
        double avg = sum / samples.size();
        double middle = avg;
        int pulseCount = 0;
        int index = 0;
        for (Double sample : samples) {
            if (index == 0 || index == samples.size()-1) {
                index++;
                continue;
            }
            if (sample > middle && samples.get(index-1) < sample && samples.get(index+1) < sample) {
                pulseCount++;
            }
            index++;
        }

        System.out.println("There are  " + pulseCount + " pulses");

    }

}