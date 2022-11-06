package eu.dulag.math.contract;

import eu.dulag.math.Console;
import eu.dulag.math.Bootstrap;

@Contract.Meta(prefix = "div")
public class Division extends Contract {

    @Override
    public Contract layer(Console console) {
        console.prompt("§3" + ints[0] * ints[1] + " §r/ §3" + ints[1] + " §r= ", false);
        return this;
    }

    @Override
    public Contract generate(int i) {
        ints[0] = Bootstrap.rdm(0, 10);
        ints[1] = this.i = i;
        return this;
    }

    @Override
    public Contract generate() {
        return generate(i);
    }

    @Override
    public boolean calculate(int result) {
        return (ints[0] * ints[1] / ints[1] == result);
    }
}