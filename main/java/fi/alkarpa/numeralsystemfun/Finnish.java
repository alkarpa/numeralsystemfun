package fi.alkarpa.numeralsystemfun;

import java.util.Map;
import static java.util.Map.entry;

/**
 * Translates integers to Finnish and Finnish numbers to integers.
 * @author alkarpa
 */
public class Finnish implements NumeralSystem {

    private static final String NAME = "Finnish";
    private static final String DESCRIPTION = String.format(
            "Written language. Supports integers from %d to %d.", 
            Long.MIN_VALUE, Long.MAX_VALUE);

    private static final String[] powersOfThousand = {
        "",
        "tuhat",
        "miljoona",
        "miljardi",
        "biljoona",
        "triljoona",
        "kvadriljoona",
        "kvintiljoona"};
    private static final String[] numbers = {
        "", "yksi", "kaksi", "kolme", "neljä", "viisi",
        "kuusi", "seitsemän", "kahdeksan", "yhdeksän",};

    private static String pluralize(String word) {
        return switch (word) {
            case "" ->
                "";
            case "tuhat" ->
                "tuhatta";
            default ->
                word + "a";
        };
    }

    private static String powerOfThousandWord(int powerOfThousand, boolean plural) {
        String baseWord = powersOfThousand[powerOfThousand];
        return plural ? pluralize(baseWord) : baseWord;
    }

    private static String numberWord(int i, boolean onesMatter) {
        if (!onesMatter && i == 1) {
            return "";
        }
        return numbers[i];
    }

    private static String hundredWord(int i) {
        return switch (i) {
            case 0 ->
                "";
            case 1 ->
                "sata";
            default ->
                "sataa";
        };
    }

    private static String tens(int i) {
        if (i == 0) {
            return "";
        }
        if (i == 10) {
            return "kymmenen";
        }
        String postTens = (i > 10 && i < 20) ? "toista" : "";
        String midTens = (i >= 20) ? "kymmentä" : "";
        int digits = i % 10;
        int tens = i / 10;
        return numberWord(tens, false) + midTens + numberWord(digits, true) + postTens;
    }

    /**
     * Reads an integer into a Finnish word power of a thousand at a time using
     * recursion.
     * @param i
     * @param powerOfThousand
     * @return 
     */
    private static String numToFinnish(long i, int powerOfThousand) {
        if (i == 0) {
            return "";
        }

        int modThousand = Math.abs( (int)(i % 1000) );
        String fin = "";

        int hundreds = modThousand / 100;
        fin += numberWord(hundreds, false) + hundredWord(hundreds);

        int tens = modThousand % 100;
        if (powerOfThousand == 0 || modThousand > 1) {
            fin += tens(tens);
        }

        fin += modThousand > 0 ? powerOfThousandWord(powerOfThousand, modThousand > 1) : "";

        String bigger = numToFinnish(i / 1000, powerOfThousand + 1);
        return bigger + fin;
    }

    public static String toFinnish(long i) {
        if (i == 0) {
            return "nolla";
        }
        String sign = (i < 0) ? "miinus" : "";

        String rec = numToFinnish(i, 0);
        return sign + rec;
    }


    /**
     * A helper class for translating Finnish into a number.
     * Create an instance for the Finnish String, then read the text using
     * read(), and finally check if the String translated well using isOk()
     * and get the translated value using getValue()
     */
    private static final class Fin {

        private final String text;
        private int sign = 1;
        private long value;
        private int index;
        private boolean ok;

        public Fin(String text) {
            this.text = text;
            this.value = 0;
            this.index = 0;
            this.ok = false;
            
            read();
        }

        /**
         * Returns the value read from the Finnish String.
         * Doesn't have error handling for bad Finnish Strings.
         * @return whatever was deciphered from the Finnish String
         */
        public long getValue() {
            return sign * value;
        }
        /**
         * Tells whether the Finnish String was translated successfully.
         * @return 
         */
        public boolean isOk() {
            return ok;
        }

        /**
         * Reads onward from the last read.
         */
        private String readText() {
            return text.substring(index);
        }

        /**
         * Checks if the remaining text starts with the given String
         * and "reads it" if yes (moves the head/index etc.)
         * @param s the String to check for
         * @return whether the remaining text starts with s
         */
        private boolean checkFor(String s) {
            if (readText().startsWith(s)) {
                index += s.length();
                return true;
            }
            return false;
        }
        
        /**
         * Adds to the number value and sets related helper variables to avoid
         * human error.
         * @param add 
         */
        private void addValue(int add) {
            ok = true;
            value += add;
        }

        private boolean specialCases() {
            Map<String, Integer> sc = Map.ofEntries(
                entry("nolla", 0),
                entry("yksi", 1)
            );
            if ( sc.keySet().contains(text) ) {
                checkFor(text); // just to "read" the whole text
                addValue(sc.get(text));
                return true;
            }
            return false;
        }
        
        /**
         * Attempts to read the Finnish String into a number
         */
        private void read() {
            if ( specialCases() ) return;
            
            if (checkFor("miinus")) sign = -1;
            
            boolean run = true;
            while ( run ) {
                int multiplier = readMultiplier(0);
                int thousands = readThousands();
                if (thousands == 0) {
                    addValue( multiplier );
                    run = false;
                } else {
                    addValue( multiplier * thousands );
                    run = index < text.length();
                }
            }
            
            if ( index < text.length() ) ok = false;
        }

        /**
         * Multipliers for the powers of thousands.
         * Powers of thousands can have hundreds, tens and digits as multipliers.
         * @param depth
         * @return 
         */
        private int readMultiplier(int depth) {
            boolean found = false;
            int multiplier = depth == 0 ? 1 : 0;
            
            if ( checkFor("kymmenen") ) return 10;

            for (int i = 1; i < numbers.length; ++i) {
                if (checkFor(numbers[i])) {
                    found = true;
                    multiplier = i;
                    break;
                }
            }
            if (found && checkFor("toista")) {
                return 10 + multiplier;
            }

            if (depth == 0 && (checkFor("sataa") || (!found && checkFor("sata")))) {
                return multiplier * 100 + readMultiplier(depth + 1);
            } else if (depth < 2 && found &&  checkFor("kymmentä")) {
                return multiplier * 10 + readMultiplier(depth + 2);
            }

            return multiplier;
        }

        /**
         * Powers of thousand have rules of their own and are read
         * separately from the multipliers.
         * @return 
         */
        private int readThousands() {
            for (int i = 1; i < powersOfThousand.length; ++i ) {
                String pot = powersOfThousand[i];
                if ( checkFor( pluralize( pot ) ) || checkFor( pot ) ) {
                    return (int) Math.pow( 10, i*3 );
                }
            }
            return 0;
        }
    }

    public static long fromFinnish(String s) {
        Fin fin = new Fin(s);
        long ret = fin.getValue();
        return ret;
    }

    @Override
    public String toNumeralSystem(long number) {
        return toFinnish(number);
    }

    @Override
    public long fromNumeralSystem(String numeral) {
        return fromFinnish(numeral);
    }

    @Override
    public String getName() {
        return NAME;
    }
    
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public boolean isInNumberDomain(long number) {
        return true;
    }

    @Override
    public boolean isInNumeralDomain(String numeral) {
        boolean ok = true;
        if ( !numeral.matches("[a-zä]+") ) ok = false;
        
        Fin fin = new Fin(numeral);
        if (!fin.isOk()) ok = false;
        
        return ok;
    }

}
