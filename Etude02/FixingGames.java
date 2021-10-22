import java.util.*;

public class FixingGames {

    static ArrayList<ArrayList<ArrayList<String>>> reconstructions = new ArrayList<ArrayList<ArrayList<String>>>();

    public static void main(String[] args) {
        /* Take as input the results, insert into 2D structure */
		Scanner sc = new Scanner(System.in);
		ArrayList<ArrayList<String>> r = new ArrayList<ArrayList<String>>();
		
		while (sc.hasNextLine()) {
			ArrayList<String> round = new ArrayList<String>();
			String line = sc.nextLine();
			Scanner lineScanner = new Scanner(line);
			while (lineScanner.hasNext()) {
				if (!lineScanner.hasNextInt()) {
					System.out.println("Bad format");
				}
				round.add(lineScanner.next());
			}
			r.add(round);
		}
		if (isBadFormatting(r)) {
			System.out.println("Bad format");
			System.exit(0);
		} 
		if (isBadValue(r)) {   
			System.out.println("Bad values");
			System.exit(0);
		}
        // Add round with underscores
        ArrayList<String> underscores = new ArrayList<String>();
        for (int under = 0; under < r.get(0).size(); under++) {
            underscores.add("_");
        }
        r.add(underscores);
		construct(r, 0);
        // Print out any valid reconstructions
        if (reconstructions.size() == 0) {
            System.out.println("Inconsistent results");
        } else {
            ArrayList<ArrayList<String>> sortedReconstructions = new ArrayList<ArrayList<String>>();
            for (int x = 0; x < reconstructions.size(); x++) {
                printMatrix(reconstructions.get(x));
                sortedReconstructions.add(sortMatrix(reconstructions.get(x)));
                System.out.println();
            }
            // How many different reconstructions?
            Set<ArrayList<String>> unique = new HashSet<ArrayList<String>>(sortedReconstructions);
            System.out.println("Different results: " + unique.size());
        }
    }

    /**
	 * Check if the string value is numeric
	 * @return true if numeric and false if not
	 */
	private static boolean isNumeric(String x) {
		if (x == null) {
			return false;
		} 
		try {
			int i = Integer.parseInt(x);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

    /**
	 * Check the 2D ArrayList for bad formatting
	 * Bad formatting arises if, no of players is not uniform, or no of rounds
	 * is not equal to no of players + 1, or if any placings are not numeric
	 * @return true if bad formatting, otherwise false
	 */
    private static boolean isBadFormatting(ArrayList<ArrayList<String>> a) {
        // Check for uniform players first across all round
		for (int round = 0; round < a.size(); round++) {
			if (a.get(round).size() != a.get(0).size()) {
				return true;
			}
			// Check every String to see if the value is not numeric which means bad format
			for (int player = 0; player < a.get(round).size(); player++) {
				if (!isNumeric(a.get(round).get(player))) {
					return true;
				}
			}
		}
		// Now check for nRounds being equal to nPlayers + 1
		if (a.size() != a.get(0).size()-1) {
			return true;
		}
		return false;
    }

    /**
	 * Check the 2D ArrayList for bad values
	 * A value is bad if it is not in the range of 1-nRounds
	 * @return true if bad value or false if good value
	 */
    private static boolean isBadValue(ArrayList<ArrayList<String>> a) {
        for (int round = 0; round < a.size(); round++) {
			// Parse every String into int and check range 1-nRounds
			for (int player = 0; player < a.get(round).size(); player++) {
				int val = Integer.parseInt(a.get(round).get(player));
				if (val < 1 || val > a.size()) {
					return true;
				}
			}
		}
		return false;
    }


    private static void printMatrix(ArrayList<ArrayList<String>> a) {
        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < a.get(i).size(); j++) {
                System.out.print(a.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    /**
     * Assumes a round only has two 'players' with matching scores
     * Returns which 'players' in the form of indices of the round array,
     * which can then be used to shift the player column down
     */
    private static int[] identicalIndices(ArrayList<String> round) {
        int[] pair = new int[2];
        for (int i = 0; i <= round.size(); i++) {
            int count = 0;
            int indexCount = 0;
            for (String playerScore : round) {
                if (playerScore.equals(Integer.toString(i))) {
                    count++;
                    if (count == 1) pair[0] = indexCount;
                    if (count == 2) {
                        pair[1] = indexCount;
                        return pair;
                    }
                }
                indexCount++;
            }
        }
        return pair;
    }

    /**
     * Given a round, work out how many 'players' have the same score and return that figure
     * e.g: if two players have the same score, return 2, if two players have same score and another two players - return 4
     */
    private static int identicalElements(ArrayList<String> round) {
        int globalCount = 0;
        for (int i = 0; i <= round.size(); i++) {
            int count = 0;
            for (String playerScore : round) {
                if (playerScore.equals(Integer.toString(i))) {
                    count++;
                }
            }
            if (count == 1) count = 0;
            globalCount += count;
        }
        return globalCount;
    }

    /**
	 * Copy a results matrix and return it, not just the reference but actual values
	 * @return copy of the results matrix
	 */
	private static ArrayList<ArrayList<String>> copyOfMatrix(ArrayList<ArrayList<String>> toCopy) {
		// Start at first inner ArrayList, copy individual Strings across
		ArrayList<ArrayList<String>> results_copy = new ArrayList<ArrayList<String>>();
		for (int rIterator = 0; rIterator < toCopy.size(); rIterator++) {
			ArrayList<String> round = new ArrayList<String>();
			for (int strCounter = 0; strCounter < toCopy.get(rIterator).size(); strCounter++) {
				round.add(toCopy.get(rIterator).get(strCounter));
			}
			results_copy.add(round);
		}
		return results_copy;
	}

    private static boolean canBeShifted(ArrayList<ArrayList<String>> original, int playerElement) {
        for (int roundCount = 0; roundCount < original.size()-2; roundCount++) {
            if (original.get(roundCount).get(playerElement).equals("_")) return false;
        }
        return true;
    }

    private static ArrayList<ArrayList<String>> shiftPlayerDown(ArrayList<ArrayList<String>> original, int round, int playerElement) {
        // Start from round original.size()-1, assuming you've added the row of underscores
        ArrayList<ArrayList<String>> shifted = copyOfMatrix(original);
        for (int roundCount = shifted.size()-1; roundCount > round; roundCount--) {
            shifted.get(roundCount).set(playerElement, shifted.get(roundCount-1).get(playerElement));
        }
        shifted.get(round).set(playerElement, "_");
        return shifted;
    }

    private static void construct(ArrayList<ArrayList<String>> current, int round) {
        // See if you can reconstruct any good scores
        // If so, add to a collection of reconstructions
        // If on the last round
        if (round == current.size()-1) {
            if (identicalElements(current.get(round)) == 0) {
                reconstructions.add(current);
            }
            return;
        }
        if (identicalElements(current.get(round)) != 2) return;
        // If not on the last round, find two players and shift down, then apply recursive method
        int[] pair = identicalIndices(current.get(round));
        ArrayList<ArrayList<String>> optionOne = shiftPlayerDown(current, round, pair[0]);
        ArrayList<ArrayList<String>> optionTwo = shiftPlayerDown(current, round, pair[1]);
        if (canBeShifted(current, pair[0])) construct(optionOne, round+1);
        if (canBeShifted(current, pair[1])) construct(optionTwo, round+1);
    }

    private static ArrayList<String> sortMatrix(ArrayList<ArrayList<String>> toSort) {
        // Convert to strings first then sort
        ArrayList<String> sorted = new ArrayList<String>();
        for (int round = 0; round < toSort.size(); round++) {
            String roundStr = "";
            for (int player = 0; player < toSort.get(round).size(); player++) {
                roundStr = roundStr.concat(toSort.get(round).get(player));
            }
            sorted.add(roundStr);
        }
        Collections.sort(sorted);
        return sorted;
    }

}