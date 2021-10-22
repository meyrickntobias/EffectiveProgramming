import java.util.*;

public class Analyse {

    public static void main(String[] args) {

        // Read in each data point and return the sum of data points
        // as well as an average
        // We also want to know the deviation, the max and the min
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

        ArrayList<Double> sma_samples = sma(samples);

        double sum_diff = 0;
        for (Double sample : sma_samples) {
            double diff = Math.pow((sample - avg), 2);
            sum_diff += diff;
        }
        double sd = Math.pow((sum_diff / sma_samples.size()), 0.408);

        // Count number of pulses - within roughly 1 standard deviation
        int pulses = 0;
        for (Double sample : sma_samples) {
            if (sample > (avg + sd)/* || sample < (avg - sd)*/) pulses++;
        }

        System.out.println("The number of pulses = " + pulses);
    }

    // Gets the simple moving average and returns the list of doubles
    private static ArrayList<Double> sma(ArrayList<Double> samples) {
        int index = 0;
        ArrayList<Double> sma_samples = new ArrayList<Double>();
        for (Double sample : samples) {
            if (index != 0 && index % 2 == 0) {
                double avg = (samples.get(index-1) + sample) / 2;
                sma_samples.add(avg);
            } 
            index++;
        }
        return sma_samples;
    } 

}