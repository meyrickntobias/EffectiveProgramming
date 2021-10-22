import java.util.*;

public class Epidemic {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (sc.hasNextLine()) {
            ArrayList<ArrayList<Character>> initialUniverse = getInput();
            // printGrid(initialUniverse);  
            // Make call to get final universe
            // getFinalUniverse will iterate over a given grid, calling a method called makeSick which return yes or no
            ArrayList<ArrayList<Character>> finalUniverse = getFinalUniverse(initialUniverse);
            printGrid(finalUniverse);
            System.out.println();
        }
    }

    private static ArrayList<ArrayList<Character>> getInput() {
        // Get input, multiple lines of .'s, I's, or S's
        // Read into chars array and then into array of arrays
        ArrayList<ArrayList<Character>> initialUniverse = new ArrayList<ArrayList<Character>>();
        int counter = 0;
        while (sc.hasNextLine()) {
            counter++;
            ArrayList<Character> row = new ArrayList<Character>();
            String l = sc.nextLine();
            if (l.equals("")) break;
            Scanner line = new Scanner(l).useDelimiter("");
            while (line.hasNext()) {
                char individual = line.next().charAt(0);      // noSuchElementException - 
                if (individual == ' ') continue;
                row.add(individual);
            }
            initialUniverse.add(row);
        }
        return initialUniverse;
    }

    private static void printGrid(ArrayList<ArrayList<Character>> universe) {
        for (int row = 0; row < universe.size(); row++) {
            ArrayList<Character> currentRow = universe.get(row);
            for (int col = 0; col < currentRow.size(); col++) System.out.print(currentRow.get(col));
            System.out.println();
        }
    }

    private static boolean makeSick(ArrayList<ArrayList<Character>> universe, char[] neighbours) {
        // in order of above, below, left, right
        int sickNeighbours = 0;
        for (char neighbour : neighbours) if (neighbour == 'S') sickNeighbours++;
        if (sickNeighbours >= 2) return true;
        return false;
    }

    private static ArrayList<ArrayList<Character>> getFinalUniverse(ArrayList<ArrayList<Character>> universe) {
        // Iterate over rows and cols, for each individual, check if first col, first row etc...
        // If individual is different from the last grid, change update variable, keep updating the grid until update variable is false
        boolean update = true;
        // While an update has not been made
        while (update) {
            boolean incrementalUpdate = false;
            for (int row = 0; row < universe.size(); row++) {
                ArrayList<Character> currentRow = universe.get(row);
                char above, below, right, left;
                for (int col = 0; col < currentRow.size(); col++) {
                    if (currentRow.get(col) == '.') {
                        // 0 represents off the grid
                        if (col == 0) {
                            // if on the first column, there will be no left neighbour
                            left = '0';
                        } else {
                            left = universe.get(row).get(col-1);
                        }

                        if (col == currentRow.size()-1) {
                            right = '0';
                        } else {
                            right = universe.get(row).get(col+1);
                        }

                        if (row == 0) {
                            above = '0';
                        } else {
                            above = universe.get(row-1).get(col);
                        }

                        if (row == universe.size()-1) {
                            below = '0';
                        } else {
                            below = universe.get(row+1).get(col);
                        }
                          
                        char[] neighbours = {above, below, left, right};
                        if (makeSick(universe, neighbours)) {
                            currentRow.set(col, 'S');
                            incrementalUpdate = true;
                        }
                    }
                }
            }
            if (incrementalUpdate == false) update = false;
        }
        return universe;
    }

}