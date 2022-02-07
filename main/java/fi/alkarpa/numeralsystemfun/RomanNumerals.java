package fi.alkarpa.numeralsystemfun;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author alkarpa
 */
public class RomanNumerals implements NumeralSystem {
    private static final String NAME = "Roman Numerals";
    private static final String DESCRIPTION = String.format(
            "Numeral system used in ancient Rome. Supports integers from %d to %d",
            1, 3999);

    
    @Override
    public String toNumeralSystem(long number) {
        return toRoman(number);
    }

    @Override
    public long fromNumeralSystem(String numeral) {
        return fromRoman(numeral);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isInNumberDomain(long number) {
        return (number > 0 && number < 4000);
    }

    @Override
    public boolean isInNumeralDomain(String numeral) {
        // to and back check
        int toNumber = fromRoman(numeral);
        String fromNumber = toRoman(toNumber);
        return numeral.equals(fromNumber);
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    enum ROMAN {

        I(1, true),
        V(5, false),
        X(10, true),
        L(50, false),
        C(100, true),
        D(500, false),
        M(1000, true);

        private final int value;
        private final boolean repeating;

        ROMAN(int value, boolean repeating) {
            this.value = value;
            this.repeating = repeating;
        }

        public int getValue() {
            return value;
        }

        public boolean isRepeating() {
            return repeating;
        }

    }

    
    /**
     * A recursive function that on each pass seeks the leftmost Roman numeral for the given integer,
     * then calls itself with the leftmost Roman numerals integer value subtracted.
     * Eventually returns the complete Roman numeral form for the integer.
     * @param i An integer between 1 and 3889
     * @return Roman numeral form for the integer
     * @throws Exception 
     */
    private static String findBase(int i) throws Exception {
        if (i == 0) {
            return "";
        }
        if ( i < 0 ) {
            throw new Exception("A negative number snuck in.");
        }
        String ret = "";

        int iMinusBase = i;
        ROMAN lastRepeating = ROMAN.I;
        for (ROMAN roman : ROMAN.values()) {
            int currValue = roman.getValue();
            if (i == currValue) {
                return roman.name();
            }
            int lastValue = lastRepeating.getValue();
            int incrementValue = roman.isRepeating() ? currValue : lastValue;
            int maxIncrements = roman.isRepeating() ? 2 : 3;

            if (i >= currValue - lastValue && i < currValue) {
                ret = lastRepeating.name() + roman.name();
                iMinusBase -= (currValue - lastValue);
                break;
            }
            else if (i < currValue + ((maxIncrements + 1 ) * incrementValue)) {
                ret = roman.name();
                iMinusBase -= currValue;
                for ( int j = 1; i >= currValue + (j * incrementValue); ++j ) {
                    ret += roman.isRepeating() ? roman.name() : lastRepeating.name();
                    iMinusBase -= incrementValue;
                }
                break;
            }
            if ( roman.isRepeating() ) {
                lastRepeating = roman;            
            }
        }

        return ret + findBase(iMinusBase);
    }

    /**
     * Returns the Roman numeral form for the given integer.
     * @param i an integer between 1 and 3999
     * @return 
     */
    public static String toRoman(long i) {
        if (i < 1 || i > 3999) {
            return "";
        }
        try {
            return findBase((int)i);
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    private static int getNumeralByChar( char c ) {
        return ROMAN.valueOf(""+c).value;
    }
    
    /**
     * Recursive Roman numeral parser that turns the (repeating) character at
     * the numeral's beginning into an integer value and then calls itself on
     * the numeral sans the parsed (repeating) character eventually adding them
     * all together.
     * @param numeral
     * @return 
     */
    private static int readLeftmostNumeral( String numeral ) {
        if ( numeral.isBlank() ) return 0;
        char lastChar = numeral.charAt(0);
        int count = 0;
        while ( count<numeral.length() && lastChar == numeral.charAt(count) ) {
            ++count;
        }
        int numeralValue = getNumeralByChar(lastChar);
        int value = count * numeralValue;
        if ( count < numeral.length() && numeralValue < getNumeralByChar( numeral.charAt(count) ) ) {
            value *= -1;
        }
        
        //System.out.println("Value: " + value + "; Leftmost: " + numeral.substring(count));
        return value + readLeftmostNumeral( numeral.substring(count) );
    }
    
    /**
     * Returns an integer that equals the given Roman numeral.
     * Does not check for incorrectly formatted Roman numerals.
     * @param numerals A preferably correctly formatted Roman numeral
     * @return 
     */
    public static int fromRoman(String numerals) {
        String ALLNUMERALS = Stream
                .of( ROMAN.values() )
                .map( r -> r.name() )
                .collect( Collectors.joining() );
        if ( !numerals.matches("^["+ALLNUMERALS+"]+$") ) return -1;
        
        int ret = readLeftmostNumeral(numerals);
        
        
        return ret;
    }
    
}
