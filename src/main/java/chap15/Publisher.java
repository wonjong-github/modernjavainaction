package chap15;

public interface Publisher<T> {
    void subscribe(Subscriber<? super T> subscriber);
}
