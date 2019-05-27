import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/***/
@RunWith(Parameterized.class)
public class EX18Test {
    /***/
    public EX18Interface ex18Interface;
    /***/
    static List<Object> manyEX18;

    /***/
    @Parameterized.Parameters
    public static Collection<Object> instancesToTest() {
        if (manyEX18 == null) return Arrays.asList(new EX18());
        return manyEX18;
    }

    /**
     * @param ex18Interface - interface.
     */
    public EX18Test(EX18Interface ex18Interface) {
        this.ex18Interface = ex18Interface;
    }

    /****/
    @Test
    public void testDecryptCorrect() {
        assertEquals("abc", ex18Interface.decrypt("bcd", 1));
        assertEquals("genius without education is like silver in the mine", ex18Interface.decrypt("travhf jvgubhg rqhpngvba vf yvxr fvyire va gur zvar", 13));
    }

    /****/
    @Test
    public void testDecryptBigRotation() {
        assertEquals("you too brutus?", ex18Interface.decrypt("zpv upp csvuvt?", 27));
        assertEquals("you too brutus?", ex18Interface.decrypt("you too brutus?", 26));
        assertEquals("genius without education is like silver in the mine", ex18Interface.decrypt("xvezlj nzkyflk vultrkzfe zj czbv jzcmvi ze kyv dzev", 43));
    }

    /****/
    @Test
    public void testDecryptOnlyNonAlphabetSymbols() {
        assertEquals("@@@", ex18Interface.decrypt("@@@", 6));
        assertEquals("@%&/()", ex18Interface.decrypt("@%&/()", 4));
        assertEquals("!\"#¤%&/()=?`@£$€{[]}\\´,.-;:_+,'ˇ~<>|*§", ex18Interface.decrypt("!\"#¤%&/()=?`@£$€{[]}\\´,.-;:_+,'ˇ~<>|*§", 12));
    }

    /****/
    @Test
    public void testDecryptNumbers() {
        assertEquals("1234567890", ex18Interface.decrypt("1234567890", 12));
    }

    /****/
    @Test
    public void testDecryptWhitespace() {
        assertEquals("\t\n ", ex18Interface.decrypt("\t\n ", 12));
    }

    /****/
    @Test
    public void testDecryptöäõü() {
        //assertEquals("öäõüÖÄÕÜ", ex18Interface.decrypt("öäõüÖÄÕÜ", 5));
        //assertEquals("öäõü", ex18Interface.decrypt("öäõü", 5));
    }

    /****/
    @Test
    public void testDecryptNoRotation() {
        assertEquals("you too brutus?", ex18Interface.decrypt("you too brutus?", 0));
    }

    /****/
    @Test
    public void testDecryptRotateOverAlphabet() {
        assertEquals("zzzzz", ex18Interface.decrypt("aaaaa", 1));
    }

    /****/
    @Test
    public void testDecryptNull() {
        // test that decrypt(null, 1) returns null.
        assertEquals(null, ex18Interface.decrypt(null, 1));
    }

    /****/
    @Test
    public void testDecryptEmpty() {
        assertEquals("", ex18Interface.decrypt("", 1));
        assertEquals("", ex18Interface.decrypt("", 6));
        assertEquals("", ex18Interface.decrypt("", 99));
    }

    /****/
    @Test
    public void testEncryptCorrect() {
        assertEquals("zv u csvuvt?", ex18Interface.encrypt("you too Brutus?", 1));
        assertEquals("bx w euxwxv?", ex18Interface.encrypt("you too Brutus?", 3));
    }

    /****/
    @Test
    public void testEncryptMinimizeAfterEncrypt() {
        assertEquals("bcdefghijklmnopqrstuvwxyz", ex18Interface.encrypt("abcdefghijklmnopqrstuvwxyz", 1));
    }

    /****/
    @Test
    public void testEncryptOtherSymbols() {
        assertEquals("@@@", ex18Interface.encrypt("@@@", 1));
        assertEquals("!\"#¤%&/()=?`@£$€{[]}\\´,.-;:_+,'ˇ~<>|*§", ex18Interface.encrypt("!\"#¤%&/()=?`@£$€{[]}\\´,.-;:_+,'ˇ~<>|*§", 12));
    }

    /****/
    @Test
    public void testEncryptNumbers() {
        assertEquals("1234567890", ex18Interface.encrypt("1234567890", 12));
    }

    /****/
    @Test
    public void testEncryptWhitespace() {
        assertEquals("\t\n ", ex18Interface.encrypt("\t\n ", 12));
    }

    /****/
    @Test
    public void testEncryptRemoveAllSameLetters() {
        assertEquals("", ex18Interface.encrypt("zzzzz", 1));
    }

    /****/
    @Test
    public void testEncryptöäõü() {
        //assertEquals("öäõüÖÄÕÜ", ex18Interface.encrypt("öäõüÖÄÕÜ", 5));
        //assertEquals("öäõü", ex18Interface.encrypt("öäõü", 5));
    }

    /****/
    @Test
    public void testEncryptEmpty() {
        assertEquals("", ex18Interface.encrypt("", 1));
    }

    /****/
    @Test
    public void testEncryptNoRotation() {
        assertEquals("sdfghj", ex18Interface.encrypt("asdfghj", 0));
    }

    /****/
    @Test
    public void testEncryptCapitalToLowercase() {
        assertEquals("sdfgh", ex18Interface.encrypt("ASdfGH", 0));
        assertEquals("defghi", ex18Interface.encrypt("aBcDeFg", 2));
    }

    /****/
    @Test
    public void testEncryptNull() {
        assertEquals(null, ex18Interface.encrypt(null, 1));
    }

    /****/
    @Test
    public void testEncryptLargeRotation() {
        assertEquals("xvelj nkyflk vultrkfe j cbv jcmvi e kyv dev", ex18Interface.encrypt("genius without education is like silver in the mine", 43));
    }

    /****/
    @Test
    public void testFindMostFrequentlyOccurringLetterCorrect() {
        assertEquals("v", ex18Interface.findMostFrequentlyOccurringLetter("travhf jvgubhg rqhpngvba vf yvxr fvyire va gur zvar"));
        assertEquals("o", ex18Interface.findMostFrequentlyOccurringLetter("you too Brutus?"));
        assertEquals("a", ex18Interface.findMostFrequentlyOccurringLetter("qqwweerrttaassffbbhh"));
    }

    /****/
    @Test
    public void testFindMostFrequentlyOccurringLetterIgnoresCapitalization() {
        assertEquals("a", ex18Interface.findMostFrequentlyOccurringLetter("AaAaassss"));
    }

    /****/
    @Test
    public void testFindMostFrequentlyOccurringLetterEmpty() {
        assertEquals("", ex18Interface.findMostFrequentlyOccurringLetter(""));
    }

    /****/
    @Test
    public void testFindMostFrequentlyOccurringLetterNonLetter() {
        //assertEquals("", ex18Interface.findMostFrequentlyOccurringLetter("@@@"));
    }

    /****/
    @Test
    public void testFindMostFrequentlyOccurringLetterNull() {
        assertEquals(null, ex18Interface.findMostFrequentlyOccurringLetter(null));
    }

    /****/
    @Test
    public void testMinimizeTextCorrect() {
        assertEquals("trahf jgubhg rqhpngba f yxr fyire a gur zar", ex18Interface.minimizeText("travhf jvgubhg rqhpngvba vf yvxr fvyire va gur zvar"));
        assertEquals("yu t brutus?", ex18Interface.minimizeText("you too Brutus?"));
    }

    /****/
    @Test
    public void testMinimizeTextOnlyNonAlphabetSymbols() {
        assertEquals("@@@", ex18Interface.minimizeText("@@@"));
    }

    /****/
    @Test
    public void testMinimizeTextEmpty() {
        assertEquals("", ex18Interface.minimizeText(""));
    }

    /****/
    @Test
    public void testMinimizeTextNull() {
        assertEquals(null, ex18Interface.minimizeText(null));
    }
}
