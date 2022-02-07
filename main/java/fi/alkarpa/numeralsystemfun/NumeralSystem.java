package fi.alkarpa.numeralsystemfun;

/**
 *
 * @author alkarpa
 */
public interface NumeralSystem {
    
    public String getName();
    public String getDescription();
    
    public boolean isInNumberDomain(long number);
    public boolean isInNumeralDomain(String numeral);
    
    public String toNumeralSystem(long number);
    public long fromNumeralSystem(String numeral);
    
}
