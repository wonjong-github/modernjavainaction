package chap7;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.LongStream;

public class Main {
    public long sideEffectSum(long n){
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }
    class Accumulator{
        public long total = 0;
        public void add(long value){total += value;}
    }
}
