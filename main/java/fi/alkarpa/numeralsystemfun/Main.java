package fi.alkarpa.numeralsystemfun;

import java.util.ArrayList;

/**
 * A command-line interface for translating numbers to numerals and back.
 * @author alkarpa
 */
public class Main {
    
    private static void initialize() {
        ArrayList<NumeralSystem> systems = new ArrayList<>();
        systems.add( new Finnish() );
        systems.add( new RomanNumerals() );
        systems.add( new Binary() );
        
        new Prompt(systems, System.in, System.out).start();
        
    }
    
    public static void main(String[] args) {
        initialize();       
    }
    
}
