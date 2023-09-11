package appd;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class InnerClass {
    Function<Object, String> f = new Function<Object, String>() {
        @Override
        public String apply(Object o) {
            return o.toString();
        }
    };
}
