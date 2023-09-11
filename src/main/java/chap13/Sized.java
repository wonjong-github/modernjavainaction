package chap13;

public interface Sized {
    int size();
    default boolean isEmpty() {
        return size() == 0;
    }
}
