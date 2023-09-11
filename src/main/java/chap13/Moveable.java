package chap13;

public interface Moveable {
    int getX();

    int getY();

    void setX(int x);

    void setY(int y);

    default void moveHorizontally(int distance) {
        setX(getX() + distance);
    }

    default void moveVertically(int distance) {
        System.out.println("Moveable.moveVertically");
        setY(getY() + distance);
    }
}
