import java.util.*;

public class BinaryPermutations {

    static final int X = 9;
    static TreeSet<String> possibleGrids = new TreeSet<String>();

    public static void main(String[] args) {
        generateGrids("", 2, 9);
        printGrids(3);
    }

    public static void printGrids(int rows) {
        for (String grid : possibleGrids) {
            printGrid(grid, rows);
        }
    }

    // What say we have an immune cell - represent with index
    public static void generateGrids(String soFar, int immuneIndex, int iterations) {
        if (iterations == 0) {
            List<String> myList = new ArrayList<String>(Arrays.asList(soFar.split("")));
            if (Collections.frequency(myList, "S") >= 2 && Collections.frequency(myList, ".") >= 1) possibleGrids.add(soFar);
        } else if(iterations == immuneIndex) {
            generateGrids(soFar + "I", immuneIndex, iterations - 1);
        } else {
            generateGrids(soFar + "S", immuneIndex, iterations - 1);
            generateGrids(soFar + ".", immuneIndex, iterations - 1);
        }
    }

    public static void printGrid(String grid, int rows) {
        char[] cells = grid.toCharArray();
        int counter = 0;
        for (char cell : cells) {
            if (counter % rows == 0) System.out.println();
            System.out.print(cell);
            counter++;
        }
        System.out.println();
    }



}