package chap13;

public interface Drawable {
    static void test() {
        test2();
        System.out.println("Drawable.test");
    }
    void draw();

    private static void test2() {
        System.out.println("Drawable.test2");
    }

    default void test3(){
        System.out.println("Drawable.test3");
    }
}
