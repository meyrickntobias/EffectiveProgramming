import java.util.*;

public class SortMatrix {

    public static void main(String[] args) {
        ArrayList<ArrayList<String>> test = new ArrayList<ArrayList<String>>();
        ArrayList<String> innerOne = new ArrayList<String>();
        innerOne.add("1");
        innerOne.add("3");
        innerOne.add("2");
        innerOne.add("1");
        ArrayList<String> innerTwo = new ArrayList<String>();
        innerTwo.add("3");
        innerTwo.add("2");
        innerTwo.add("2");
        innerTwo.add("1");
        ArrayList<String> innerThree = new ArrayList<String>();
        innerThree.add("2");
        innerThree.add("1");
        innerThree.add("3");
        innerThree.add("1");
        test.add(innerOne);
        test.add(innerTwo);
        test.add(innerThree);
        ArrayList<String> sortedStr = sortMatrix(test);
        for (String s : sortedStr) {
            System.out.println(s);
        }
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