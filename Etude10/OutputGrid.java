import java.util.*;

public class OutputGrid {

    public static void main(String[] args) {

        // Aim of this program is to output grids with varying levels
        // of initially sick people
        // All grids will be 8x8
        // All grids will contain zero immune individuals to begin with
        int sickInd = 0;
        int immuneInd = 0;
        Random rand = new Random();
        while (immuneInd <= 30) {
            immuneInd += 2;
            // Create sickInd unique numbers
            ArrayList<Integer> immuneArr = new ArrayList<Integer>();
            for (int i = 0; i < immuneInd; i++) {
                int r = rand.nextInt(64);   // Create a random number between 0 and 63
                while (immuneArr.contains(r)) r = rand.nextInt(64);
                immuneArr.add(r);
            }
            while (sickInd < 50) {
                sickInd++;
                // Create sickInd unique numbers
                ArrayList<Integer> sickArr = new ArrayList<Integer>();
                for (int i = 0; i < sickInd; i++) {
                    int r = rand.nextInt(64);   // Create a random number between 0 and 63
                    while (sickArr.contains(r) || immuneArr.contains(r)) r = rand.nextInt(64);
                    sickArr.add(r);
                }
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (sickArr.contains((i*8)+j)) {
                            System.out.print("S");
                        } else if (immuneArr.contains((i*8)+j)) {
                            System.out.print("I");
                        } else {
                            System.out.print(".");
                        }
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }
        
        

    }


}