import java.util.*;

public class TestTwo {

    public static void main(String[] args) {
        if (containsSameLetters("app", "apple")) {
            System.out.println("Yes");
        } else {
            System.out.println("Nope");
        }
    }

    private static boolean isAnagram(String str1, String str2){
		String sortedStr1 = alphabetiseWord(str1);
		String sortedStr2 = alphabetiseWord(str2);

		// anagrams of each other
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