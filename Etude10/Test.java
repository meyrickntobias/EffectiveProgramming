import java.util.*;

public class Test {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Test if one grid is the same as the other
        // Compile all possible grids of MxN where M = width and N = height of grid, and a vector I with immune cells at positions of I
        // HashSets contain unique elements only, if we create a Grid class with an equals override method that checks whether the two grid values are equal
        // E.G:
        /*
            private static HashSet<Grid> allPermutations(int m, int n, ArrayList<Integer> I) {
                construct a frame of size MxN
                for i = 1, until i = M, i++

            }
        */
        Grid grid1 = new Grid();
        Grid grid2 = new Grid();
        grid1.getInput(sc);
        grid2.getInput(sc);
        grid1.printGrid();
        grid2.printGrid();
        if (grid1.equals(grid2)) {
            System.out.println("These two are the same");
        } else {
            System.out.println("Not the same");
        }
    }

    private static HashSet<Grid> allPermutations(int m, int n, ArrayList<Integer> I) {
        ArrayList<ArrayList<Character>> frame = new ArrayList<ArrayList<Character>>();
        int max_sick = (m * n) - I.size();
        for (int sick = 0; sick < max_sick; sick++) {
            for (int i = 0; i < m; i++) {
                frame.add(new ArrayList<Character>());
                for (int j = 0; j < n; j++) {
                    if (I.contains(i*j)) {
                        frame.get(i).add("I"); 
                    } else {
                        frame.add(".");
                    }
                }
            }
        }
        
    }


}