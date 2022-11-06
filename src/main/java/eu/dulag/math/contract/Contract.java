package eu.dulag.math.contract;

import eu.dulag.math.Console;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

public abstract class Contract {

    private static final Map<String, Contract> CONTRACTS = new HashMap<>();

    public static void register(Contract... contracts) {
        for (Contract contract : contracts) {
            Meta meta = contract.meta();
            if (meta == null) continue;
            CONTRACTS.put(meta.prefix(), contract);
        }
    }

    public static boolean exists(String prefix) {
        return CONTRACTS.containsKey(prefix);
    }

    public static Contract contract(String prefix) {
        return CONTRACTS.get(prefix);
    }

    protected int[] ints = new int[2];

    protected int i;

    public abstract Contract layer(Console console);

    public abstract Contract generate(int i);

    public abstract Contract generate();

    public abstract boolean calculate(int result);

    public Meta meta() {
        return this.getClass().getAnnotation(Meta.class);
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Meta {

        String prefix();
    }
}