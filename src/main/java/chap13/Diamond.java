package chap13;

public class Diamond {
    static interface A {
        default void hello() {
            System.out.println("A.hello");
        }
    }

    static interface B extends A {
    }

    static interface C extends A {
        void hello();
    }

    static class D implements B, C{

        public static void main(String[] args) {
            new D().hello();
        }

        @Override
        public void hello() {

        }
    }
}
