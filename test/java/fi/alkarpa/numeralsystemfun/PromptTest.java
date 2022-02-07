package fi.alkarpa.numeralsystemfun;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 *
 * @author alkarpa
 */
public class PromptTest {
    
    @Test
    public void testHelp() {
        String command = "help";
        ByteArrayInputStream in = new ByteArrayInputStream(command.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        
        List<NumeralSystem> list = new ArrayList<>();
        
        Prompt prompt = new Prompt(list, in, ps);
        prompt.start();
        
        String outString = out.toString();
        assert( outString.contains( prompt.getMessage(command) ) );
    }
    
    @Test
    public void testSystems() {
        String command = "systems";
        ByteArrayInputStream in = new ByteArrayInputStream(command.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        
        List<NumeralSystem> list = new ArrayList<>();
        NumeralSystem fin = new Finnish();
        list.add( fin );
        
        Prompt prompt = new Prompt(list, in, ps);
        prompt.start();
        
        String outString = out.toString();
        assert( outString.contains( fin.getDescription() ) );
    }
    @Test
    public void testExit() {
        String command = "exit\nother command";
        ByteArrayInputStream in = new ByteArrayInputStream(command.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        
        List<NumeralSystem> list = new ArrayList<>();
        
        Prompt prompt = new Prompt(list, in, ps);
        prompt.start();
        
        String outString = out.toString();
        assert( outString.contains( prompt.getMessage(command) ) );
    }
    @Test
    public void test15ToViisitoista() {
        String command = "15";
        ByteArrayInputStream in = new ByteArrayInputStream(command.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        
        List<NumeralSystem> list = new ArrayList<>();
        NumeralSystem fin = new Finnish();
        list.add( fin );
        
        Prompt prompt = new Prompt(list, in, ps);
        prompt.start();
        
        String outString = out.toString();
        assert( outString.contains("viisitoista") );
    }
    @Test
    public void testMiinusviisitoista() {
        String command = "miinusviisitoista";
        ByteArrayInputStream in = new ByteArrayInputStream(command.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        
        List<NumeralSystem> list = new ArrayList<>();
        NumeralSystem fin = new Finnish();
        list.add( fin );
        
        Prompt prompt = new Prompt(list, in, ps);
        prompt.start();
        
        String outString = out.toString();
        assert( outString.contains("-15") );
    }
}
