package chap13;

import java.util.ArrayList;
import java.util.List;

public class ChildTest extends Test{
    @Override
    public void test2() {
        System.out.println("ChildTest.test2");
    }

    public static void main(String[] args) {
        ChildTest childTest = new ChildTest();
        childTest.test();
        childTest.test2();
        Test test = new Test() {
            @Override
            public void test2() {
                System.out.println("hello");
            }
        };
        test.test2();
        Drawable.test();
        Ellipse ellipse = new Ellipse();
        ellipse.test3();
    }
}
