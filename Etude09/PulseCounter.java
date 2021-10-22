import java.util.ArrayList;
import java.util.Scanner;

public class PulseCounter {

    public static void main(String[] args) {

        /* Read input, get SMA and count pulses */
        ArrayList<Double> samples = readInput();
        ArrayList<Double> reducedSamples = SMA(samples);
        double avg = getAverage(reducedSamples);

        int pulseCount = 0;
        int index = 0;
        for (Double sample : reducedSamples) {
            if (index == 0) {
                // if (sample > avg && reducedSamples.get(index+1) < sample) pulseCount++;
                index++;
                continue;
            } else if (index == reducedSamples.size()-1) {
                // if (sample > avg && reducedSamples.get(index-1) < sample) pulseCount++;
                index++;
                continue;
            }
            if (sample > avg && reducedSamples.get(index-1) < sample && reducedSamples.get(index+1) < sample) {
                pulseCount++;
            }
            index++;
        }
        System.out.println("There are  " + pulseCount + " pulses");
    }   

    private static double getAverage(ArrayList<Double> samples) {
        double sum = 0;
        for (Double sample : samples) {
            sum += sample;
        }
        return sum / samples.size();
    }

    private static ArrayList<Double> readInput() {
        /* Read input, get SMA and count pulses */
        Scanner sc = new Scanner(System.in);
        ArrayList<Double> samples = new ArrayList<Double>();
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
        return samples;
    }

    private static ArrayList<Double> SMA(ArrayList<Double> samples) {
        ArrayList<Double> reducedSamples = new ArrayList<Double>();
        int index = 0;
        for (Double sample : samples) {
            if (index > 2 && index < samples.size()-1) {
                // Get previous sample and current sample, add up and spit out
                double avg = (samples.get(index-1)*0.3 + sample*0.4 + samples.get(index+1)*0.3);
                reducedSamples.add(avg);
            } 
            index++;
            // For every second sample, get the previous
        }
        System.out.println(reducedSamples.size());
        return reducedSamples;
    }


}