/**
 * Created by Jaanus on 10.05.16.
 */
public class EX19 {
    /**
     * @param stringToCheck - cound Koer and koer in the string.
     * @return koer and Koer count in a given string.
     */
    public static int[] testDog(String stringToCheck) {
        final int wordLength = 4;
        if (stringToCheck == null || stringToCheck.length() < wordLength) {
            return new int[]{0, 0};
        }
        int aliveDog = 0, deadDog = 0;
        String[] words = stringToCheck.split("[ ]+");
        for (String word: words) {
            if (word.contains("koer")) {
                deadDog++;
            }
            if (word.contains("Koer")) {
                aliveDog++;
            }
        }
        return new int[]{aliveDog, deadDog};
    }

    /**
     * @param stringToReverse - string that needs to be reversed.
     * @return input string reversed.
     */
    public static String reverse(String stringToReverse) {
        if (stringToReverse == null) {
            return null;
        }
        char[] newString = new char[stringToReverse.length()];
        int idx = newString.length - 1;
        for (char c: stringToReverse.toCharArray()) {
            if (Character.isLowerCase(stringToReverse.charAt(idx))) {
                newString[idx] = Character.toString(c).toLowerCase().charAt(0);
            } else if (Character.isUpperCase(stringToReverse.charAt(idx))) {
                newString[idx] = Character.toString(c).toUpperCase().charAt(0);
            } else {
                newString[idx] = c;
            }
            idx--;
        }
        return new String(newString);
    }

    /**
     * @param args - command line arguments.
     */
    public static void main(String[] args) {
        String test = "Koeratoit toidukoeratäpp toidukoor kalamaja TäpikKoer koe";
        for (int i: testDog(test)) {
            System.out.println(i);
        }
        System.out.println();
        for (int i: testDog("koerkoerkoer")) {
            System.out.println(i);
        }
        System.out.println();
        System.out.println(reverse("test"));
        System.out.println(reverse("a  b"));
        System.out.println(reverse("Tere"));
        System.out.println(reverse("Tere!"));
        System.out.println(reverse("tere!"));
    }
}
