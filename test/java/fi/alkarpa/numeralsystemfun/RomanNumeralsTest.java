package fi.alkarpa.numeralsystemfun;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author alkarpa
 */
public class RomanNumeralsTest {
    
    public RomanNumeralsTest() {
    }
    
    private void compare(int i, String text) {
        String result = RomanNumerals.toRoman(i);
        assertEquals( result, text );
    }
    
    @Test
    public void test1to10() {
        compare(1, "I");
        compare(2, "II");
        compare(3, "III");
        compare(4, "IV");
        compare(5, "V");
        compare(6, "VI");
        compare(7, "VII");
        compare(8, "VIII");
        compare(9, "IX");
        compare(10, "X");
    }
    @Test
    public void test11to20() {
        compare(11, "XI");
        compare(12, "XII");
        compare(13, "XIII");
        compare(14, "XIV");
        compare(15, "XV");
        compare(16, "XVI");
        compare(17, "XVII");
        compare(18, "XVIII");
        compare(19, "XIX");
        compare(20, "XX");
    }
    @Test
    public void test99() {
        compare(99, "XCIX");
    }
    @Test
    public void test100() {
        compare(100, "C");
    }
    @Test
    public void test500() {
        compare(500, "D");
    }
    @Test
    public void test1000() {
        compare(1000, "M");
    }
    @Test
    public void test777() {
        compare(777, "DCCLXXVII");
    }
    @Test
    public void test999() {
        compare(999, "CMXCIX");
    }
    @Test
    public void test3889() {
        compare(3889, "MMMDCCCLXXXIX");
    }
    @Test
    public void test3999() {
        compare(3999, "MMMCMXCIX");
    }
    
    @Test
    public void testNoFailuresFrom1To100() {
        for ( int i = 1; i <= 100; ++i ) {
            if ( RomanNumerals.toRoman(i).startsWith("ERROR") ) {
                fail(i + " failed with message '" + RomanNumerals.toRoman(i));
            }
        }
    }
    
    @Test
    public void testBadNumeralsToInt() {
        int bad = RomanNumerals.fromRoman("HAPPY");
        assertEquals(bad, -1);
    }
    @Test
    public void testMMMDCCCLXXXIXToInt() {
        int MMMDCCCLXXXIX = RomanNumerals.fromRoman("MMMDCCCLXXXIX");
        assertEquals(MMMDCCCLXXXIX, 3889);
    }
    @Test 
    public void testBackAndForthAll() {
        for ( int i = 1; i <= 3999; ++i ) {
            String numeral = RomanNumerals.toRoman(i);
            int back = RomanNumerals.fromRoman(numeral);
            assertEquals(i, back);
        }
    }
    @Test
    public void testXXXisNumeral() {
        assert( new RomanNumerals().isInNumeralDomain("XXX") );
    }
    @Test
    public void testXXXXisNotNumeral() {
        assert( !(new RomanNumerals().isInNumeralDomain("XXXX")) );
    }
    
}
