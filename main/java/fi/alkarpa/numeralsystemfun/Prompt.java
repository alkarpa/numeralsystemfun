package fi.alkarpa.numeralsystemfun;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import java.util.Scanner;

/**
 *
 * @author alkarpa
 */
public class Prompt {

    private static final Map<String, String> MESSAGES = Map.ofEntries(
            entry("splash",
                    """
               =========== NumeralSystemFun ============
               Enter some numbers or write some numerals
                  Maybe they're supported, maybe not~
               =========================================
               """),
            entry("help",
                    """
               Commands:
                  exit - ends the program
                  help - prints this help
               systems - prints a list of all the available numeral systems
               """),
            entry("exit",
                    "Bye"),
            entry("prompt",
                    "\r\nEnter your input. Type help for a list of special commands")
    );
    private final Map<String, String> messages;

    private final InputStream inputstream;
    private final PrintStream printstream;
    private final List<NumeralSystem> systems;

    public Prompt(
            List<NumeralSystem> numeralsystems,
            InputStream inputstream,
            PrintStream printstream) {
        this.systems = numeralsystems;
        this.inputstream = inputstream;
        this.printstream = printstream;

        this.messages = new HashMap<>();
        messages.put("systems", listSystems());
    }

    private int getPaddingWidth() {
        int padding = 0;
        for (NumeralSystem ns : systems) {
            padding = Math.max(padding, ns.getName().length());
        }
        return padding;
    }

    public void start() {

        Scanner scanner = new Scanner(inputstream);
        boolean running = true;

        printstream.println(getMessage("splash"));

        printPrompt();
        while (running && scanner.hasNext()) {
            String next = scanner.next();
            printstream.println(getMessage(next));

            switch (next) {
                case "exit" ->
                    running = false;
                default -> {
                    handleInput(next);
                    printPrompt();
                }
            }
        }
    }

    protected String getMessage(String key) {
        String message = MESSAGES.containsKey(key)
                ? MESSAGES.get(key)
                : messages.containsKey(key)
                ? messages.get(key)
                : "";
        return message;
    }

    private void printPrompt() {
        printstream.println(getMessage("prompt"));
    }

    private String listSystems() {
        StringBuilder sb = new StringBuilder();
        for (NumeralSystem ns : systems) {
            sb.append(getPadName(ns))
                    .append(": ")
                    .append(ns.getDescription())
                    .append("\r\n");
        }
        return sb.toString();
    }

    private void handleInput(String s) {
        if (s.matches("-?\\d+")) {
            handleNumber(s);
        }
        handleString(s);
    }

    private String getPadName(NumeralSystem ns) {
        int padding = getPaddingWidth();
        return String.format("%" + padding + "s", ns.getName());
    }

    private void handleNumber(String s) {
        printstream.println("---Number " + s + " to numeral ------------");
        long number;
        try {
            number = Long.parseLong(s);
        } catch (NumberFormatException e) {
            printstream.println("Could not cast " + s + " into Long");
            return;
        }

        for (NumeralSystem ns : systems) {
            if (ns.isInNumberDomain(number)) {
                String numeral = ns.toNumeralSystem(number);
                printstream.println(getPadName(ns) + ": " + numeral);
            }
        }
    }

    private void handleString(String s) {

        ArrayList<String> fromNumerals = new ArrayList<>();

        for (NumeralSystem ns : systems) {
            if (ns.isInNumeralDomain(s)) {
                long number = ns.fromNumeralSystem(s);
                fromNumerals.add(getPadName(ns) + ": " + number);
            }
        }
        if (!fromNumerals.isEmpty()) {
            printstream.println("---Numeral " + s + " to number ------------");
            for (String num : fromNumerals) {
                printstream.println(num);
            }
        }
    }

}
