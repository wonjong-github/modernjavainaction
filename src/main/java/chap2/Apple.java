package chap2;

public class Apple extends Fruit implements AppleIn{
    private Color color;
    private String name;
    private Integer weight;

    public Apple(Color color, String name, int weight) {
        this.color = color;
        this.name = name;
        this.weight = weight;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
