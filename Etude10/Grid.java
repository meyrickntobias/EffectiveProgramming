import java.util.*;

public class Grid {

    private ArrayList<ArrayList<Character>> internalGrid;

    public Grid() {
        this.internalGrid = new ArrayList<ArrayList<Character>>();
    }

    public Grid(ArrayList<ArrayList<Character>> internalGrid) {
        this.internalGrid = internalGrid;
    }

    public void setGrid(ArrayList<ArrayList<Character>> internalGrid) {
        this.internalGrid = internalGrid;
    }

    public void getInput(Scanner sc) {
        int counter = 0;
        while (sc.hasNextLine()) {
            counter++;
            ArrayList<Character> row = new ArrayList<Character>();
            String l = sc.nextLine();
            if (l.equals("")) {
                l = sc.nextLine();
                if (l.equals("")) {
                    break;
                }
            }
            Scanner line = new Scanner(l).useDelimiter("");
            while (line.hasNext()) {
                char individual = line.next().charAt(0);
                if (individual == ' ') continue;
                row.add(individual);
            }
            this.internalGrid.add(row);
        }
    }

    public ArrayList<ArrayList<Character>> getGrid() {
        return this.internalGrid;
    }

    public void printGrid() {
        for (int al = 0; al < this.internalGrid.size(); al++) {
            for (int i = 0; i < this.internalGrid.get(al).size(); i++) {
                System.out.print(this.internalGrid.get(al).get(i));
            }
            System.out.println();
        }
    }

    @Override 
    public boolean equals(Object o) {
        // If the object is compared with itself then return true 
        if (o == this) {
            return false;
        }
 
        /* Check if o is an instance of Grid or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Grid)) {
            return false;
        }
         
        // typecast o to Complex so that we can compare data members
        Grid c = (Grid) o;
        ArrayList<ArrayList<Character>> g = c.getGrid();
         
        // Compare the data members and return accordingly
        for (int al = 0; al < g.size(); al++) {
            for (int i = 0; i < g.get(al).size(); i++) {
                if (this.internalGrid.get(al).get(i) != g.get(al).get(i)) {
                    return false;
                }
            }
        }
        return true;

    }

}