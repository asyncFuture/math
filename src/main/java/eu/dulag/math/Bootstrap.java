package eu.dulag.math;

import eu.dulag.math.contract.*;
import org.fusesource.jansi.AnsiConsole;

import java.util.concurrent.ThreadLocalRandom;

public class Bootstrap {

    private final Console console;

    private Contract contract;

    public static void main(String[] args) {
        Bootstrap test = new Bootstrap();
    }

    public Bootstrap() {
        AnsiConsole.systemInstall();

        this.console = new Console();
        this.console.clear();
        this.register(new Addition(), new Subtraction(), new Multiply(), new Division());
        this.console.run(Contract.string() + " (§3i§r) > ", line -> {
            console.clear();
            if (line.isEmpty()) return;
            if (!isNumber(line)) {
                String[] strings = line.split(" ");
                String prefix = strings[0];
                if (Contract.exists(prefix)) {
                    if (strings.length < 2) return;
                    if (!isNumber(strings[1])) return;
                    contract = Contract.contract(prefix);
                    contract.generate(Integer.parseInt(strings[1])).layer(console);
                } else console.println("§c" + prefix + " doses not exists!");
                return;
            }
            if (contract == null) return;
            boolean calculate = contract.calculate(Integer.parseInt(line));
            if (!calculate) {
                console.beep();
                return;
            }
            contract.generate().layer(console);
        });
    }

    public Console register(Contract... contracts) {
        Contract.register(contracts);
        return console;
    }

    public static boolean isNumber(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    public static int rdm(int origin, int bound) {
        return ThreadLocalRandom.current().nextInt(origin, bound);
    }

    public static int[] rdm(int length, int origin, int bound) {
        int[] ints = new int[length];
        for (int i = 0; i < length; i++) ints[i] = rdm(origin, bound);
        return ints;
    }
}