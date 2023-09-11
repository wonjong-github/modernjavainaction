package chap13;

public abstract class Test {
    public void test(){
        System.out.println("Test.test");
    }

    abstract public void test2();

    public String test3() {
        return "Test";
    }
}
