package chap13;

public interface Rotatable {
    void setRotationAngle(int angleInDegress);

    int getRotationAngle();

    default void rotateBy(int angleInDegress) {
        System.out.println("Rotatable.rotateBy");
        setRotationAngle((getRotationAngle() + angleInDegress) % 360);
    }
}
