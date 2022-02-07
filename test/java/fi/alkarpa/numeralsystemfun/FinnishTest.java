package fi.alkarpa.numeralsystemfun;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author alkarpa
 */
public class FinnishTest {
    
    public FinnishTest() {
    }
    

    private void compare( long i, String text ) {
        String result = Finnish.toFinnish(i);
        assertEquals(text, result);
    }
    private void compare( String fi, long number ) {
        long result = Finnish.fromFinnish(fi);
        assertEquals(number, result);
    }
    
    @Test
    public void test1() {
        compare(1, "yksi");
    }
    
    @Test
    public void test0() {
        compare(0, "nolla");
    }
    
    @Test
    public void test10() {
        compare(10, "kymmenen");
    }
    @Test
    public void test110() {
        compare(110, "satakymmenen");
    }
    
    @Test
    public void test11() {
        compare(11, "yksitoista");
    }
    @Test
    public void testMinus11() {
        compare(-11, "miinusyksitoista");
    }
    @Test void test17() {
        compare(17, "seitsemäntoista");
    }
    
    @Test
    public void test111() {
        compare(111, "satayksitoista");
    }
    
    @Test
    public void test123() {
        compare(123, "satakaksikymmentäkolme");
    }
    
    @Test
    public void test1000() {
        compare(1000, "tuhat");
    }
    
    @Test
    public void test1100() {
        compare(1100, "tuhatsata");
    }
    
    @Test
    public void test101000() {
        compare(101000, "satayksituhatta");
    }
    
    @Test
    public void test1000000() {
        compare(1000000, "miljoona");
    }
    @Test
    public void test2000000() {
        compare(2000000, "kaksimiljoonaa");
    }
    @Test
    public void test1000000000() {
        compare(1000000000, "miljardi");
    }
    
    @Test
    public void test2147483647() {
        compare(2147483647, "kaksimiljardia"+
                "sataneljäkymmentäseitsemänmiljoonaa"+
                "neljäsataakahdeksankymmentäkolmetuhatta"+
                "kuusisataaneljäkymmentäseitsemän");
    }
    public void testLongMax() {
        compare(Long.MAX_VALUE, "yhdeksänkvadriljoonaa"
                + "kaksisataakaksikymmentäkolmetriljoonaa"
                + "kolmesataaseitsemänkymmentäkaksibiljoonaa"
                + "kolmekymmentäkuusimiljardia"
                + "kahdeksansataaviisikymmentäneljämiljoonaa"
                + "seitsemänsataaseitsemänkymmentäviisituhatta"
                + "kahdeksansataaseitsemän");
    }
    public void testLongMin() {
        compare(Long.MIN_VALUE, "miinusyhdeksänkvadriljoonaa"
                + "kaksisataakaksikymmentäkolmetriljoonaa"
                + "kolmesataaseitsemänkymmentäkaksibiljoonaa"
                + "kolmekymmentäkuusimiljardia"
                + "kahdeksansataaviisikymmentäneljämiljoonaa"
                + "seitsemänsataaseitsemänkymmentäviisituhatta"
                + "kahdeksansataakahdeksan");
    }
    
    @Test
    public void testMiinusviisi() {
        compare("miinusviisi", -5);
    }
    
    @Test
    public void testFinnishToMaxInt() {
        compare("kaksimiljardia"+
                "sataneljäkymmentäseitsemänmiljoonaa"+
                "neljäsataakahdeksankymmentäkolmetuhatta"+
                "kuusisataaneljäkymmentäseitsemän", 2147483647);
    }
    
    @Test
    public void testDomainNoEmpty() {
        Finnish finnish = new Finnish();
        boolean empty = finnish.isInNumeralDomain("");
        assertFalse(empty);
    }
    @Test
    public void testDomainNoNonsense() {
        Finnish finnish = new Finnish();
        boolean nonsense = finnish.isInNumeralDomain("nonsense");
        assertFalse(nonsense);
    }
    @Test
    public void testDomainYesTuhatviisitoista() {
        Finnish finnish = new Finnish();
        boolean tvt = finnish.isInNumeralDomain("tuhatviisitoista");
        assertTrue(tvt);
    }
    
    @Test
    public void testBackAndForth1to100() {
        for ( int i = 0; i <= 100; ++i ) {
            String numeral = Finnish.toFinnish(i);
            long back = Finnish.fromFinnish(numeral);
            assertEquals(i, back);
        }
    }
    
}
