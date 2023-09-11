package chap2;
@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);

    boolean equals(Object object);
    public String toString();
}
