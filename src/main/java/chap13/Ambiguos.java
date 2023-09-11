package chap13;

import java.io.Serializable;

public class Ambiguos implements Serializable {

    public static void main(String[] args) {

    }

    static interface A {
        default void hello() {
            System.out.println("A.hello");
        }
    }

    static interface B extends A {
        default void hello() {
            System.out.println("B.hello");
        }
    }

    static class D implements A {
        @Override
        public void hello() {
            System.out.println("D.hello");
        }
    }


    static class C extends D implements A, B {
        public static void main(String[] args) {
            new C().hello();
        }

        @Override
        public void hello() {
            System.out.println("C.hello");
        }
    }

}
