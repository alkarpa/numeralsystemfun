package fi.alkarpa.numeralsystemfun;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author alkarpa
 */
public class BinaryTest {
    
    static Binary binary;
    
    public BinaryTest() {
        binary = new Binary();
    }
    
    @Test
    public void testZero() {
        long zero = binary.fromNumeralSystem("0");
        assertEquals(0, zero);
    }
    
    @Test
    public void testOne() {
        long one = binary.fromNumeralSystem("1");
        assertEquals(1, one);
    }
    
    @Test
    public void testIntegerMax() {
        long max = binary.fromNumeralSystem("01111111"
                + "11111111"
                + "11111111"
                + "11111111");
        assertEquals(Integer.MAX_VALUE, max);
    }
    @Test
    public void testIntegerMin() {
        long min = binary.fromNumeralSystem("10000000"
                + "00000000"
                + "00000000"
                + "00000000");
        assertEquals(Integer.MIN_VALUE, min);
    }
    @Test
    public void testLong() {
        String max = binary.toNumeralSystem(Long.MAX_VALUE);
        assertEquals("01111111"
                + "11111111"
                + "11111111"
                + "11111111"
                + "11111111"
                + "11111111"
                + "11111111"
                + "11111111",max);
    }
    @Test
    public void testLongMax() {
        long max = binary.fromNumeralSystem("01111111"
                + "11111111"
                + "11111111"
                + "11111111"
                + "11111111"
                + "11111111"
                + "11111111"
                + "11111111");
        assertEquals(Long.MAX_VALUE, max);   
    }
    @Test
    public void testLongMin() {
        long min = binary.fromNumeralSystem("10000000"
                + "00000000"
                + "00000000"
                + "00000000"
                + "00000000"
                + "00000000"
                + "00000000"
                + "00000000");
        assertEquals(Long.MIN_VALUE, min);   
    }
    
    @Test
    public void testBackAndForthAbsoluteTens() {
        for (int i = - 10; i <= 10; ++i) {
            String numeral = binary.toNumeralSystem(i);
            long back = binary.fromNumeralSystem(numeral);
            assertEquals(i, back);
        }
    }
}
