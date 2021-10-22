import java.util.*;

public class Woof {

    static final Character[] woof_original = {'p', 'q', 'r', 's'};
    static final Character[] leader = {'C', 'A', 'K', 'E'};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String s = sc.nextLine().trim();
            boolean woof = recursiveWoof(s, true);
            if (woof) {
                System.out.println("woof");
            } else {
                System.out.println("not woof");
            }
        }
    }

    static boolean recursiveWoof(String str, boolean firstGo) {
        if (str.equals("*")) return true;
        StringBuilder cpy = new StringBuilder(str);
        // First round check, original woof's and leaders search, only do first go
        if (firstGo) {
            for (int i = 0; i < cpy.length(); i++) {
                // System.out.println(cpy.charAt(i));
                if (Arrays.asList(woof_original).contains(cpy.charAt(i))) {
                    cpy.setCharAt(i, '*');
                } else if (Arrays.asList(leader).contains(cpy.charAt(i))) {
                    cpy.setCharAt(i, '%');
                }
            }
        }  
        // Now check for N*'s and %**'s and convert them
        for (int i = 0; i < cpy.length(); i++) {
            // System.out.println(cpy.charAt(i));
            // System.out.println(cpy.charAt(i-1));
            if (i > 0) {
                if ((cpy.charAt(i) == '*') && (cpy.charAt(i-1) == 'N')) {
                    // Remove charAt(i-1)
                    cpy.deleteCharAt(i-1);
                }
            }
            if (i < cpy.length()-2) {
                if (cpy.charAt(i) == '%' && i <= cpy.length()-2) {
                    if ((cpy.charAt(i+1) == '*') && (cpy.charAt(i+2) == '*')) {
                        cpy.deleteCharAt(i);
                        cpy.deleteCharAt(i+1);
                    }
                    // if str[i] == % and index < size-3, check str[i+1] and str[i+2] for *
                    // if so, delete str[i] and str[i+1]
                }
            }
            
        }
        if (!cpy.toString().equals(str)) {
            return recursiveWoof(cpy.toString(), false);
        } else {
            return false;
        }
    }
}