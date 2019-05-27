import static org.junit.Assert.*;

import static org.junit.Assert.*;
public class EX03Test {

    @org.junit.Test
    public void testEncrypt() throws Exception {
        assertEquals("", EX03.encrypt("zzzzz", 1));
        assertEquals("zv u csvuvt?", EX03.encrypt("you too Brutus?", 1));
        assertEquals("bx w euxwxv?", EX03.encrypt("you too Brutus?", 3));
        assertEquals("", EX03.encrypt("", 1));
        assertEquals("@@@", EX03.encrypt("@@@", 1));
        assertEquals("cdefghijklmnopqrstuvwxyza", EX03.encrypt("abcdefghijklmnopqrstuvwxyz", 1));
    }

    @org.junit.Test
    public void testFindMostFrequentlyOccurringLetter() throws Exception {
        assertEquals("v", EX03.findMostFrequentlyOccurringLetter("travhf jvgubhg rqhpngvba vf yvxr fvyire va gur zvar"));
        assertEquals("", EX03.findMostFrequentlyOccurringLetter(""));
        assertEquals("o", EX03.findMostFrequentlyOccurringLetter("you too Brutus?"));
        assertEquals("", EX03.findMostFrequentlyOccurringLetter("@@@"));
        assertEquals("a", EX03.findMostFrequentlyOccurringLetter("qqwweerrttaassffbbhh"));
    }

    @org.junit.Test
    public void testMinimizeText() throws Exception {
        assertEquals("trahf jgubhg rqhpngba f yxr fyire a gur zar", EX03.minimizeText("travhf jvgubhg rqhpngvba vf yvxr fvyire va gur zvar"));
        assertEquals("", EX03.minimizeText(""));
        assertEquals("yu t brutus?", EX03.minimizeText("you too Brutus?"));
        assertEquals("@@@", EX03.minimizeText("@@@"));
    }

    @org.junit.Test
    public void testDecrypt() throws Exception {
        assertEquals("genius without education is like silver in the mine", EX03.decrypt("travhf jvgubhg rqhpngvba vf yvxr fvyire va gur zvar", 13));
        assertEquals("zzzzz", EX03.decrypt("aaaaa", 1));
        assertEquals("you too brutus?", EX03.decrypt("zpv upp csvuvt?", 1));
        assertEquals("you too brutus?", EX03.decrypt("zpv upp csvuvt?", 27));
        assertEquals("", EX03.decrypt("", 6));
        assertEquals("@@@", EX03.decrypt("@@@", 6));
        assertEquals("you too brutus?", EX03.decrypt("you too brutus?", 0));
        assertEquals("you too brutus?", EX03.decrypt("you too brutus?", 26));
    }
}