/*
 * Home assignment 3.
 * Made by Jaanus.
 */
public class EX03 {

    static final int ALPHABET_LENGTH = 26;
    static final int LOWER_OFFSET = 97;
    static final int LOWER_Z_VALUE = 122;
    static final int UPPER_OFFSET = 65;
    static final int UPPER_Z_VALUE = 90;
    /**
     * Given text and a rotation, encrypts text.
     * @param plainText plain text, readable by humanoids
     *                  with relative ease.
     * @param rotation int of rotation.
     * @return encrypted text
     */
    private static String encryptCore(String plainText, int rotation) {
        String encryptedText = "";
        for (int i = 0; i < plainText.length(); i++) {
            int plainChar = plainText.charAt(i);
            if (plainChar <= LOWER_Z_VALUE && plainChar >= LOWER_OFFSET) {
                int encryptedChar = ((plainChar - LOWER_OFFSET + rotation)
                        % ALPHABET_LENGTH + LOWER_OFFSET);
                encryptedText += (char) encryptedChar;
            } else if (plainChar <= UPPER_Z_VALUE && plainChar >= UPPER_OFFSET) {
                int encryptedChar = ((plainChar - UPPER_OFFSET + rotation)
                        % ALPHABET_LENGTH + LOWER_OFFSET);
                encryptedText += (char) encryptedChar;
            } else {
                encryptedText += (char) plainChar;
            }
        }
        return encryptedText;
    }
    public static String encrypt(String plainText, int rotation) {
        if (plainText == null) {
            return null;
        }
        rotation = rotation % ALPHABET_LENGTH;
        plainText = minimizeText(plainText);
        String encryptedText = encryptCore(plainText, rotation);
        return encryptedText;
    }

    /**
     * Finds the most frequently occurring letter in text.
     * @param text either plain or encrypted text.
     * @return the most frequently occurring letter in text.
     */
    public static String findMostFrequentlyOccurringLetter(String text) {
        /*Array's first element corresponds to occurrences of a, second to b, ect.*/
        if (text == null) {
            return null;
        }
        int[] letterOccurrences = new int[ALPHABET_LENGTH];
        int maximum = 0;
        int maximumIndex = -1;
        String mostFrequentLetter = "";
        int temp;
        for (int i = 0; i < text.length(); i++) {
            int textChar = text.charAt(i);
            if (textChar <= LOWER_Z_VALUE && textChar >= LOWER_OFFSET) {
                letterOccurrences[textChar - LOWER_OFFSET] += 1;
            } else if (textChar <= UPPER_Z_VALUE && textChar >= UPPER_OFFSET) {
                letterOccurrences[textChar - UPPER_OFFSET] += 1;
            }
        }
        for (int i = 0; i < ALPHABET_LENGTH; i++) {
            temp = letterOccurrences[i];
            if (maximum < temp && temp != 0) {
                maximum = temp;
                maximumIndex = i;
            }
        }
        if (maximumIndex == -1) {
            return "";
        }
        mostFrequentLetter += (char) (maximumIndex + LOWER_OFFSET);
        return mostFrequentLetter;
    }

    /**
     * Removes the most prevalent letter from text.
     * @param text either plain or encrypted text.
     * @return text in which the most prevalent letter has been removed.
     */
    public static String minimizeText(String text) {
        if (text == null) {
            return null;
        }
        text = encryptCore(text, 0);
        String minimizedText = "";
        String frequentLetter = findMostFrequentlyOccurringLetter(text);
        if (frequentLetter == "") {
            return text;
        }
        char letterToRemove = frequentLetter.charAt(0);
        for (int i = 0; i < text.length(); i++) {
            if (!(letterToRemove == text.charAt(i))) {
                minimizedText += text.charAt(i);
            }
        }
        return minimizedText;
    }

    /**
     * Given the initial rotation and the encrypted text, this method
     * decrypts said text.
     * @param cryptoText Encrypted text.
     * @param rotation How many letters to the right the alphabet was
     *                 shifted in order to encrypt text.
     * @return Decrypted text.
     */
    public static String decrypt(String cryptoText, int rotation) {
        if (cryptoText == null) {
            return null;
        } else {
            return encryptCore(cryptoText, ALPHABET_LENGTH - rotation % ALPHABET_LENGTH);
        }
    }

    /**
     * The main method, which is the entry point of the program.
     * @param args Arguments from the command line
     */
    public static void main(String[] args) {
        System.out.println(encrypt("you too Brutus?", 1)); // => zpv upp csvuvt?
        System.out.println(decrypt("zpv upp csvuvt?", 120)); // => you too brutus?
        System.out.println(findMostFrequentlyOccurringLetter("you too Brutus?")); // => o
        System.out.println(minimizeText("you too Brutus?")); // yu t brutus?
    }
}
