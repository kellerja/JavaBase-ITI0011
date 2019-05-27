/**
 * Created by jakell on 13.05.2016.
 */
public class KT4 {
    /**
     * @param input - coded string.
     * @return decoded string.
     */
    public static String unpack(String input) {
        if (input == null) return null;
        String result = "", number = "";
        char letter;
        int numberOfCharsToFollow = 1;
        for (int i = input.length() - 1; i >= 0; i--) {
            letter = input.charAt(i);
            if (Character.isDigit(letter)) {
                number = letter + number;
            } else {
                if (number.length() > 0) {
                    numberOfCharsToFollow = Integer.parseInt(number);
                    number = "";
                }
                for (int j = 0; j < numberOfCharsToFollow; j++) {
                    result = letter + result;
                }
            }
        }
        return result;
    }

    /**
     * @param args - command line arguments.
     */
    public static void main(String[] args) {
        System.out.println(unpack(null));
        System.out.println(unpack("abc"));
        System.out.println(unpack("A2A"));
        System.out.println(unpack(""));
        System.out.println(unpack("a12b"));
    }
}
