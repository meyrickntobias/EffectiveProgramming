import java.util.*;

/**
 * The_Comparator is a custom comparator for the treeset which orders the tree based
 * on string length first and then alphabetically for strings of the same length. The
 * tree is kept in reverse order (backwards alphabetically and word length increasing)
 * to work efficiently with the stack search.
 */
class The_Comparator implements Comparator<String>{
	public int compare(String str1, String str2){

		// words are different lengths, store in increasing order
		if (str1.length() != str2.length())
			return str1.length() - str2.length();

		// words the same length, store backwards alphabetically
		return str2.compareTo(str1);
	}
}


/**
 * Anagrams takes input text files of words seperated by a blank line. Those below the
 * blank line are added to a dictionary and used to find anagrams for those words above
 * the blank line. In the case that many anagrams exist, the best and only to be
 * printed, is that starting with the longest word or the first alphabetically.
 * 
 * @author Toby Meyrick (1998580) and Jake Perkins (2713308) 26.09.21
 */
public class Anagrams {

	private static ArrayList<String> searchStrings = new ArrayList<String>();
	private static TreeSet<String> dictionary = new TreeSet<String>
			(new The_Comparator());
	private static ArrayList<String> anagrams = new ArrayList<>();

    public static void main(String[] args){

		// read in input
		getInput();

		// find anagrams
		for (String word : searchStrings){
			String ana = findAnagram(word);

			// print anagrams
			System.out.print(word + ": ");
			if (ana != null)
				System.out.println(ana);
			else
				System.out.println();

			anagrams.clear();
		}
	}

    /**
     * getInput reads the file given on the command line and writes the words from the
     * file to an arraylist of words to be assessed or a dictionary based on whether
     * it has passed the blank line yet.
     */
	private static void getInput(){
		Boolean passedBlank = false;
		Scanner sc = new Scanner(System.in);

		// get input
		while (sc.hasNextLine()){

			// get line
			String line = sc.nextLine();
			if (line.isEmpty())
				passedBlank = true;

			// add to right arraylist
			else if (!passedBlank)
				searchStrings.add(line);
			else
				dictionary.add(line);
		}
		sc.close();
	}
	
	/**
	 * findAnagrams finds the anagrams of the search word by searching through the
	 * dictionary from the first word that is equal length or shorter than the search
	 * word. The first anagram it finds will be the best given the sorted dictionary.
	 * 
	 * @param target - the search word to look for an anagram for
	 * @return str - an anagram of the search word
	 * @return null - no more anagrams to find
	 */
	private static String findAnagram(String target){
		Stack<String> possibleAnagrams = new Stack<String>();
		possibleAnagrams.push("");

		// search for anagrams
		while (!possibleAnagrams.isEmpty()){
			String str = possibleAnagrams.pop();
			String strTest = str.replaceAll("\\s", "");

			// check for match
			if (isAnagram(strTest, target)){
				anagrams.add(str);
				return str;
			}
			// keep searching
			int maxSearchLength = target.length() - strTest.length();
			TreeSet<String> sub_set = new TreeSet<String>();
			String a = "a";
			String fromElement = new String(new char[maxSearchLength]).replace("\0", a);
			
			// search the subset of possible dictionary words
			sub_set = (TreeSet<String>)dictionary.headSet(fromElement);
			for (String dictWord : sub_set)
				if (containsSameLetters(strTest + dictWord, target)){
					if (str.length() == 0)
						possibleAnagrams.push(dictWord);
					if (str.length() > 0)
						possibleAnagrams.push(str + " " + dictWord);
				}
		}
		return null;
	}

	/**
	 * isAnagram sorts the two given words and checks if they are equal indicating that
	 * the two unsorted words are anagrams of each other.
	 * 
	 * @param str1 - the first word for comparison
	 * @param str2 - the second word for comparison
	 * @return boolean - indicating whether the words are anagrams or not
	 */
	private static boolean isAnagram(String str1, String str2){
		String sortedStr1 = alphabetiseWord(str1);
		String sortedStr2 = alphabetiseWord(str2);

		if (sortedStr1.equals(sortedStr2))
			return true;
		return false;
	}

	/**
	 * alphabetiseWord sorts the given word alphabetically and returns it.
	 * 
	 * @param word - the word to be sorted
	 * @return sortedWord - the alphabetically sorted word
	 */
	private static String alphabetiseWord(String word){
		char[] temp = word.toCharArray();
		Arrays.sort(temp);
		String sortedWord = new String(temp);
		
		return sortedWord;
	}

	/**
	 * containsSameLetters checks the two given words to see if the letters of the
	 * first word are all within the letters of the second. The second word can have
	 * other letters too but it must contain all of the letters of the first word.
	 * 
	 * @param str1 - the dictionary word, we are seeing if it is within the second word
	 * @param str2 - the search word, we are seeing if the first word is in it
	 * @return boolean - indicating whether the first word is within the second
	 */
	private static boolean containsSameLetters(String str1, String str2){

		// the first word cannot be in the second if it is longer than it
		if (str1.length() > str2.length())
			return false;
		
		StringBuilder sbStr1 = new StringBuilder(str1);
		StringBuilder sbStr2 = new StringBuilder(str2);

		// check off each letter
		while (sbStr1.length() != 0){
			int index = sbStr2.indexOf(String.valueOf(sbStr1.charAt(0)));

			// if the search string does not contain the dictionary letter
			if (index == -1)
				return false;
				
			// remove letters
			sbStr1.deleteCharAt(0);
			sbStr2.deleteCharAt(index);
		}
		// the dictionary word is within the search word
		return true;
	}
}
