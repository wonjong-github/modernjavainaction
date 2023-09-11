package chap15;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ArithmetricCell extends SimpleCell {

    private int left;
    private int right;

    public ArithmetricCell(String name) {
        super(name);
    }

    public void setLeft(int left) {
        this.left = left;
        onNext(left + this.right);
    }

    public void setRight(int right) {
        this.right = right;
        onNext(right + this.left);

        ArrayList<String> ss = new ArrayList<>();

        ss.forEach(System.out::println);
    }

    public static void main(String[] args) {
        ArithmetricCell c3 = new ArithmetricCell("C3");
        SimpleCell c1 = new SimpleCell("C1");
        SimpleCell c2 = new SimpleCell("C2");

        c1.subscribe(c3::setLeft);
        c2.subscribe(c3::setRight);
//
//        c1.onNext(10);
//        c2.onNext(20);
//        c1.onNext(15);

        ArithmetricCell c5 = new ArithmetricCell("C5");
        SimpleCell c4 = new SimpleCell("C4");
        c3.subscribe(c5::setLeft);
        c4.subscribe(c5::setRight);

        c1.onNext(10);
        c2.onNext(20);
        c1.onNext(15);
        c4.onNext(1);
        c4.onNext(3);

        Consumer<Integer> setLeft = c3::setLeft;
    }
}
