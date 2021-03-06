import java.util.*;

/**
 * Main method for working with pentominoes. Change the parameter of the
 * pentomino constructor to change the selected pentomino representation.
 * 
 * Group members: Lachlan Macartney (5129496), Suppanut Chaimathikul (6991678)
 * Toby Meyrick (1998580), Jake Perkins (2713308)
 */
public class Solver {

	static ArrayList<Character> pentominoTypes = new ArrayList<>();
	static char[][] grid;
	static boolean reusablePentominoes = false;
	static Scanner sc = new Scanner(System.in);

    public static void main (String [] args) {
    	// Take in input - line showing possible pentominoes and the .'s and *'s 
		// While there are more pentomino puzzles to solve
		while (sc.hasNextLine()) {
			try {
				readInput();
				/*updates grid, reusablePentominoes, pentominoTypes in place*/
			} catch(IllegalArgumentException e){
				System.out.println("Input is invalid");
				return;
			}

			/*node contains the board state and the pentominos left to place,
			represented by char[][] and ArrayList<Character> respectively*/
			GridNode firstNode = new GridNode(grid, pentominoTypes);
			GridNode solution = aSolution(firstNode, reusablePentominoes);

			if (solution != null){
				System.out.println(solution);
			} else {
				// Print the grid
				for (char[] c : grid) {
					for (char x : c) {
						System.out.print(x);
					}
					System.out.println();
				}
				System.out.println("Impossible");
				System.out.println();
			}
		}
		sc.close();
    	return;
    }

    private static GridNode aSolution(GridNode firstNode, boolean reusablePentominoes){
    	/*Depth first search for a solution*/
		GridNode node = firstNode;
		Stack<GridNode> stack = new Stack<GridNode>();
		HashSet<String> seen = new HashSet<>();

		stack.add(node);

		while (stack.size()>0){
			node = stack.pop();

			ArrayList<Pentomino> allPermutations = node.generateEdges();

			// System.out.println(node);

			if (node.getCoordinateToPlace() == null){
				return node;
			}

			/*hash the board printout to avoid traversing explored nodes*/
			if (seen.contains(node.toString())){
				continue;
			} else {
				seen.add(node.toString());
			}

			for (Pentomino p : allPermutations){

				if (p.canPlaceOn(node)){

					GridNode newNode = node.copy();
					p.placeOn(newNode);

					if (!reusablePentominoes){
						newNode.removePentominoType(p.getType());
					}

					stack.push(newNode);
				}
			}

		}
		return null;
	}
    
    private static void readInput() {
		// Get first line of input - possible pentominoes, assume only single use
		String firstLine = sc.nextLine();
		if (firstLine.equals("") && sc.hasNextLine()) firstLine = sc.nextLine();
		for (int letter = 0; letter < firstLine.length(); letter++) {
			pentominoTypes.add(firstLine.charAt(letter));
		}
		if (pentominoTypes.contains('*')) {
			reusablePentominoes = true;
			pentominoTypes.remove(pentominoTypes.indexOf('*'));
		}
		for (char pt : pentominoTypes) {
			System.out.print(pt);
		}
		System.out.println();
		// Next input will be a grid of .'s and *'s to fill with pentominoes
		int dotCounter = 0;
		ArrayList<char[]> lines = new ArrayList<>();
		while (sc.hasNextLine()) {
			String currentLine = sc.nextLine();
			if (currentLine.equals("")) break;
			char[] line = new char[currentLine.length()];
			for (int letter = 0; letter < currentLine.length(); letter++) {
				if (currentLine.charAt(letter) == '.') {
					dotCounter++;
				}
				line[letter] = currentLine.charAt(letter);
			}
			lines.add(line);
		}
		grid = new char[lines.size()][];
		for (int i = 0; i < lines.size(); i ++) grid[i] = lines.get(i);
		if (dotCounter % 5 != 0) throw new IllegalArgumentException(
				"Grid not divisible by 5, so it is impossible to solve."
		);
		return;
    }
    
}
