package fi.alkarpa.numeralsystemfun;

/**
 *
 * @author alkarpa
 */
public class Binary implements NumeralSystem {

    private static final String NAME = "Binary";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return String.format(
                "Java binary, two's complement integer. Supports integers from %d to %d",
                Long.MIN_VALUE, Long.MAX_VALUE);
    }

    @Override
    public boolean isInNumberDomain(long number) {
        return true;
    }

    @Override
    public boolean isInNumeralDomain(String numeral) {
        int maxSize = Long.BYTES * 8;
        return numeral.matches("[01]{1," + maxSize + "}");
    }

    @Override
    public String toNumeralSystem(long number) {
        // Ignore Integer.toBinaryString(number) for fun
        int byteLength = (number >= Integer.MIN_VALUE && number <= Integer.MAX_VALUE)
                ? Integer.BYTES
                : Long.BYTES;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < (byteLength * 8); ++i) {
            int bit = (int) ((number >> i) & 0x0001);
            sb.append(bit);
        }
        sb.reverse();
        return sb.toString();
    }

    @Override
    public long fromNumeralSystem(String numeral) {
        boolean negative = numeral.charAt(0) == '1'
                && (numeral.length() == Integer.BYTES * 8 || numeral.length() == Long.BYTES * 8);
        char[] cs = numeral.toCharArray();
        
        // Two's complement negative operated backwards
        if (negative) {
            for (int i = cs.length - 1; i >= 0; --i) {
                if (cs[i] == '0') {
                    cs[i] = '1';
                } else {
                    cs[i] = '0';
                    break;
                }
            }
            for (int i = 0; i < cs.length; ++i) {
                cs[i] = (cs[i] == '1') ? '0' : '1';
            }
        }
        
        long ret = 0;
        long pow = 1;
        for (int i = cs.length - 1; i >= 0; --i) {
            ret += cs[i] == '1' ? pow : 0;
            pow *= 2;
        }
        if (negative) ret *= -1;
        return ret;
    }

}
